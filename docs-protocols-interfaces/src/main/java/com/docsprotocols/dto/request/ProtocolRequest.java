package com.docsprotocols.dto.request;

import com.docsprotocols.dto.enumeration.ProtocolState;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ProtocolRequest(  @NotNull String username,
                                LocalDateTime creationDate,
                                @NotNull ProtocolState protocolState) {
}