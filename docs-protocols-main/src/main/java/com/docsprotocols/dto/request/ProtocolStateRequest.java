package com.docsprotocols.dto.request;

import com.docsprotocols.dto.enumeration.ProtocolState;
import jakarta.validation.constraints.NotNull;

public record ProtocolStateRequest(
        @NotNull ProtocolState protocolState) {
}
