package com.docsprotocols.controller;

import com.docsprotocols.dto.ProtocolDto;
import com.docsprotocols.dto.request.ProtocolRequest;
import com.docsprotocols.dto.request.ProtocolStateRequest;
import com.docsprotocols.service.ProtocolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/protocols")
@RequiredArgsConstructor
public class ProtocolController {

    private final ProtocolService protocolService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        try {
            ProtocolDto result = protocolService.get(id);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> get() {
        try {
            List<ProtocolDto> result = protocolService.getAll();
            if (CollectionUtils.isEmpty(result)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProtocolDto protocolDto) {
        try {
            ProtocolDto result = protocolService.create(protocolDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                       @RequestBody @Valid ProtocolRequest protocolRequest) {
        try {
            ProtocolDto result = protocolService.update(id, protocolRequest);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

    // Update state
    @PutMapping("/state/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                       @RequestBody @Valid ProtocolStateRequest protocolStateRequest) {
        try {
            ProtocolDto result = protocolService.update(id, protocolStateRequest);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }
}
