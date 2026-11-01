package com.fitpath.controller;

import com.fitpath.service.DashboardService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService service;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> summary() {
        return new ResponseEntity<>(service.summary(), HttpStatus.OK);
    }
}
