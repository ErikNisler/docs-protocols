package com.docsprotocols.dto;

import com.docsprotocols.dto.enumeration.ProtocolState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProtocolDto {
    @NotNull
    private String username;
    private LocalDateTime creationDate;
    @NotNull
    private List<DocumentDto> documents;
    @NotNull
    private ProtocolState protocolState;
}
