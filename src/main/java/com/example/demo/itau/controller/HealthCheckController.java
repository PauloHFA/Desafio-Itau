package com.example.demo.itau.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);
    private final ApplicationAvailability applicationAvailability;

    public HealthCheckController(ApplicationAvailability applicationAvailability) {
        this.applicationAvailability = applicationAvailability;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.debug("Health check solicitado");

        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("timestamp", LocalDateTime.now().toString());
        healthInfo.put("service", "demo.itau");
        healthInfo.put("version", "1.0.0");

        return ResponseEntity.ok(healthInfo);
    }

    @GetMapping("/readiness")
    public ResponseEntity<Map<String, Object>> readiness() {
        Map<String, Object> readinessInfo = new HashMap<>();

        ReadinessState readinessState = applicationAvailability.getReadinessState();
        readinessInfo.put("status", readinessState.toString());
        readinessInfo.put("timestamp", LocalDateTime.now().toString());
        readinessInfo.put("message", "Application readiness state");

        return ResponseEntity.ok(readinessInfo);
    }

    @GetMapping("/liveness")
    public ResponseEntity<Map<String, Object>> liveness() {
        Map<String, Object> livenessInfo = new HashMap<>();

        LivenessState livenessState = applicationAvailability.getLivenessState();
        livenessInfo.put("status", livenessState.toString());
        livenessInfo.put("timestamp", LocalDateTime.now().toString());
        livenessInfo.put("message", "Application liveness state");

        return ResponseEntity.ok(livenessInfo);
    }

    @GetMapping("/detailed")
    public ResponseEntity<Map<String, Object>> detailedHealth() {
        Map<String, Object> detailedHealth = new HashMap<>();

        // Informações básicas
        detailedHealth.put("status", "UP");
        detailedHealth.put("timestamp", LocalDateTime.now().toString());

        // Informações da aplicação
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("name", "demo.itau");
        appInfo.put("version", "1.0.0");
        appInfo.put("environment", "development");
        detailedHealth.put("application", appInfo);

        // Status dos componentes
        Map<String, Object> components = new HashMap<>();
        components.put("memory", getMemoryInfo());
        components.put("disk", getDiskInfo());
        detailedHealth.put("components", components);

        return ResponseEntity.ok(detailedHealth);
    }

    private Map<String, Object> getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> memoryInfo = new HashMap<>();

        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        memoryInfo.put("max", formatBytes(maxMemory));
        memoryInfo.put("used", formatBytes(usedMemory));
        memoryInfo.put("free", formatBytes(freeMemory));
        memoryInfo.put("usagePercentage", String.format("%.2f%%", (double) usedMemory / maxMemory * 100));

        return memoryInfo;
    }

    private Map<String, Object> getDiskInfo() {
        Map<String, Object> diskInfo = new HashMap<>();
        diskInfo.put("status", "OK");
        diskInfo.put("message", "Disk space monitoring not implemented");
        return diskInfo;
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        else if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        else if (bytes < 1024 * 1024 * 1024) return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        else return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }
}