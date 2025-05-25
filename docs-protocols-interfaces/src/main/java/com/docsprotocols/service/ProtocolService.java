package com.docsprotocols.service;

import com.docsprotocols.dto.ProtocolDto;
import com.docsprotocols.dto.enumeration.ProtocolState;
import com.docsprotocols.dto.request.ProtocolRequest;
import com.docsprotocols.entity.ProtocolEntity;
import com.docsprotocols.mapper.DocumentMapper;
import com.docsprotocols.mapper.ProtocolMapper;
import com.docsprotocols.repository.ProtocolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final ProtocolMapper protocolMapper;
    private final DocumentMapper documentMapper;

    @Transactional
    public ProtocolDto create(ProtocolDto protocolDto) {
        Optional<ProtocolEntity> existingProtocol = protocolRepository.findByUsername(protocolDto.getUsername());
        if (existingProtocol.isPresent()) {
            throw new IllegalArgumentException("Protocol with this name already exists");
        }
        ProtocolEntity savedEntity = protocolRepository.save(protocolMapper.toEntity(protocolDto));
        return protocolMapper.toDto(savedEntity);
    }

    @Transactional
    public ProtocolDto update(Long id, ProtocolState protocolState) {
        Optional<ProtocolEntity> protocolEntityOptional = protocolRepository.findById(id);
        if (protocolEntityOptional.isEmpty()) {
            throw new NoSuchElementException("No such protocol exist to update");
        }
        ProtocolEntity updatedEntity = protocolEntityOptional.get();
        updatedEntity.setProtocolState(protocolState);
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
