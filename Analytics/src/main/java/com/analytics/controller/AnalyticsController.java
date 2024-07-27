package com.analytics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analytics.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
	@Autowired
    private AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<?> getAnalyticsData() {
        Map<String, Object> analyticsData = analyticsService.getAnalyticsData();
        if (analyticsData.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "No analytics data found"));
        }
        return ResponseEntity.ok(analyticsData);
    }
}
