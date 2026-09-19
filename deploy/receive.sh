#!/usr/bin/env bash
# Execute sur le VPS (compte deploy) : controle, charge l'image, prepare la release, l'active.
set -Eeuo pipefail

SHA="${1:?usage: receive.sh <sha_du_commit>}"
BASE=/opt/ecom
IN="$BASE/incoming/$SHA"
REL="$BASE/releases/sha-$SHA"

cd "$IN"
sha256sum -c SHA256SUMS

gzip -dc image.tar.gz | docker image load

mkdir -p "$REL"
cp compose.yml release.env activate.sh "$REL/"

bash "$REL/activate.sh" "$REL"

rm -rf "$IN"
echo "Livraison $SHA terminee."
