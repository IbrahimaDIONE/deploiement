package com.acl.ecom.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VersionController {

    // APP_VERSION est injectee par Docker Compose (SHA du commit livre)
    @Value("${APP_VERSION:dev}")
    private String version;

    @GetMapping("/api/version")
    public Map<String, String> version() {
        return Map.of("application", "ecom", "version", version);
    }
}
