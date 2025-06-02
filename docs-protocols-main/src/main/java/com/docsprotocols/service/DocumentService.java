package com.docsprotocols.service;

import com.docsprotocols.dto.DocumentDto;
import com.docsprotocols.entity.DocumentEntity;
import com.docsprotocols.mapper.DocumentMapper;
import com.docsprotocols.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Transactional
    public DocumentDto get(Long id) {
        Optional<DocumentEntity> documentEntity = documentRepository.findById(id);
        return documentEntity.map(documentMapper::toDto).orElse(null);
    }

    @Transactional
    public List<DocumentDto> getAll() {
        List<DocumentEntity> documentEntities = documentRepository.findAll();
        if (CollectionUtils.isEmpty(documentEntities)) {
            return Collections.emptyList();
        }
        return documentEntities.stream().map(documentMapper::toDto).toList();
    }

    @Transactional
    public DocumentDto create(DocumentDto documentDto) {
        Optional<DocumentEntity> existingDoc = documentRepository.findByName(documentDto.getName());
        if (existingDoc.isPresent()) {
            throw new IllegalArgumentException("Document with this name already exists");
        }
        DocumentEntity savedEntity = documentRepository.save(documentMapper.toEntity(documentDto));
        return documentMapper.toDto(savedEntity);
    }

    @Transactional
    public void delete(Long id) {
        Optional<DocumentEntity> documentEntityOptional = documentRepository.findById(id);
        if (documentEntityOptional.isEmpty()) {
            throw new NoSuchElementException(String.format("Document with id %d not found", id));
        }
        documentRepository.delete(documentEntityOptional.get());
    }

    @Transactional
    public DocumentDto update(Long id, DocumentDto documentDto) {
        Optional<DocumentEntity> documentEntityOptional = documentRepository.findById(id);
        if (documentEntityOptional.isEmpty()) {
            throw new NoSuchElementException(String.format("Document with id %d not found", id));
        }
        DocumentEntity updatedEntity = documentEntityOptional.get();
        updatedEntity.setUsername(documentDto.getUsername());
        updatedEntity.setName(documentDto.getName());
        updatedEntity.setDocumentType(documentDto.getDocumentType());
        documentRepository.save(updatedEntity);
        return documentMapper.toDto(updatedEntity);
    }
}
