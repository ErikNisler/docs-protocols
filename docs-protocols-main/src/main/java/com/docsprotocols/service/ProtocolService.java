package com.docsprotocols.service;

import com.docsprotocols.dto.DocumentDto;
import com.docsprotocols.dto.ProtocolDto;
import com.docsprotocols.dto.enumeration.ProtocolState;
import com.docsprotocols.dto.request.ProtocolRequest;
import com.docsprotocols.dto.request.ProtocolStateRequest;
import com.docsprotocols.entity.DocumentEntity;
import com.docsprotocols.entity.ProtocolEntity;
import com.docsprotocols.mapper.DocumentMapper;
import com.docsprotocols.mapper.ProtocolMapper;
import com.docsprotocols.repository.DocumentRepository;
import com.docsprotocols.repository.ProtocolRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final ProtocolMapper protocolMapper;
    private final DocumentRepository documentRepository;

    @Transactional
    public ProtocolDto create(ProtocolDto protocolDto) {
        Optional<ProtocolEntity> existingProtocol = protocolRepository.findByUsername(protocolDto.getUsername());
        if (existingProtocol.isPresent()) {
            throw new IllegalArgumentException("Protocol with this name already exists");
        }
        if (documentExists(protocolDto.getDocuments())) {
            throw new IllegalArgumentException("Protocol contains existing document!");
        }
        ProtocolEntity savedEntity = protocolRepository.save(protocolMapper.toEntity(protocolDto));
        return protocolMapper.toDto(savedEntity);
    }

    private boolean documentExists(@NotNull List<DocumentDto> documents) {
        return documents.stream()
                .map(doc -> documentRepository.findByName(doc.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .isPresent();
    }

    @Transactional
    public ProtocolDto update(Long id, ProtocolStateRequest request) {
        Optional<ProtocolEntity> protocolEntityOptional = protocolRepository.findById(id);
        if (protocolEntityOptional.isEmpty()) {
            throw new NoSuchElementException("No such protocol exist to update");
        }
        ProtocolEntity updatedEntity = protocolEntityOptional.get();
        updatedEntity.setProtocolState(request.protocolState());
        protocolRepository.save(updatedEntity);
        return protocolMapper.toDto(updatedEntity);
    }

    @Transactional
    public ProtocolDto update(Long id, ProtocolRequest request) {
        Optional<ProtocolEntity> protocolEntityOptional = protocolRepository.findById(id);
        if (protocolEntityOptional.isEmpty()) {
            throw new NoSuchElementException("No such protocol exist to update");
        }
        ProtocolEntity updatedEntity = protocolEntityOptional.get();
        updatedEntity.setUsername(request.username());
        updatedEntity.setProtocolState(request.protocolState());
        updatedEntity.setCreationDate(request.creationDate() == null ? updatedEntity.getCreationDate() : request.creationDate());
        protocolRepository.save(updatedEntity);
        return protocolMapper.toDto(updatedEntity);
    }
}
