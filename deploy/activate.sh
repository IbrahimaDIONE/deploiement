#!/usr/bin/env bash
# Active une release : bash activate.sh /opt/ecom/releases/sha-<SHA>
# Sert aussi au retour arriere : bash activate.sh /opt/ecom/previous
set -Eeuo pipefail

BASE=/opt/ecom
PROJECT=ecom-vps
PORT=8084
TARGET="$(readlink -f "${1:?usage: activate.sh <dossier_release>}")"

# Un seul deploiement a la fois
exec 9>"$BASE/.deploy.lock"
flock -n 9 || { echo "Un deploiement est deja en cours."; exit 1; }

compose() {
  docker compose -p "$PROJECT" \
    --env-file "$BASE/shared/.env" \
    --env-file "$TARGET/release.env" \
    -f "$TARGET/compose.yml" "$@"
}

echo "Activation de $TARGET"
compose up -d --remove-orphans

echo "Attente de /actuator/health ..."
ok=0
for _ in $(seq 1 60); do
  if curl -fsS "http://127.0.0.1:$PORT/actuator/health" 2>/dev/null | grep -q '"status":"UP"'; then
    ok=1; break
  fi
  sleep 3
done
if [ "$ok" != 1 ]; then
  echo "ERREUR : l'application n'est pas UP."
  compose logs --tail 100 app || true
  exit 1
fi

expected="$(grep '^APP_VERSION=' "$TARGET/release.env" | cut -d= -f2)"
actual="$(curl -fsS "http://127.0.0.1:$PORT/api/version" | sed -n 's/.*"version":"\([^"]*\)".*/\1/p')"
if [ "$expected" != "$actual" ]; then
  echo "ERREUR : version attendue $expected, version servie $actual"
  exit 1
fi

# Version validee : mise a jour des liens current / previous
if [ -L "$BASE/current" ]; then
  cur="$(readlink -f "$BASE/current")"
  if [ "$cur" != "$TARGET" ]; then
    ln -sfn "$cur" "$BASE/previous"
  fi
fi
ln -sfn "$TARGET" "$BASE/current"
echo "OK : version $actual active."
