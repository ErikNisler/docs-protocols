package com.docsprotocols.dto;

import com.docsprotocols.dto.enumeration.DocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentDto {

    @NotNull
    private String name;
    @NotNull
    private String username;
    private LocalDateTime creationDate;
    @NotNull
    private DocumentType documentType;
}
