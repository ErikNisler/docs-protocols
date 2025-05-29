package service;

import com.docsprotocols.dto.DocumentDto;
import com.docsprotocols.dto.ProtocolDto;
import com.docsprotocols.dto.enumeration.DocumentType;
import com.docsprotocols.dto.enumeration.ProtocolState;
import com.docsprotocols.dto.request.ProtocolRequest;
import com.docsprotocols.dto.request.ProtocolStateRequest;
import com.docsprotocols.entity.DocumentEntity;
import com.docsprotocols.entity.ProtocolEntity;
import com.docsprotocols.mapper.ProtocolMapper;
import com.docsprotocols.repository.DocumentRepository;
import com.docsprotocols.repository.ProtocolRepository;
import com.docsprotocols.service.ProtocolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProtocolServiceTest {

    @InjectMocks
    private ProtocolService protocolService;
    @Mock
    private ProtocolRepository protocolRepository;
    @Mock
    private ProtocolMapper protocolMapper;
    @Mock
    private DocumentRepository documentRepository;

    private DocumentEntity documentEntity;
    private ProtocolDto protocolDto;
    private ProtocolEntity protocolEntity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        DocumentDto documentDto = new DocumentDto();
        documentDto.setUsername("UpdatedName");
        documentDto.setName("UpdatedName");
        documentDto.setDocumentType(DocumentType.PDF);

        documentEntity = new DocumentEntity();
        documentEntity.setUsername(documentDto.getUsername());
        documentEntity.setName(documentDto.getName());
        documentEntity.setDocumentType(documentDto.getDocumentType());

        protocolDto = new ProtocolDto();
        protocolDto.setDocuments(List.of(documentDto));
        protocolDto.setProtocolState(ProtocolState.NEW);
        protocolDto.setUsername("Protocol");

        protocolEntity = new ProtocolEntity();
        protocolEntity.setProtocolState(ProtocolState.NEW);
        protocolEntity.setUsername("Protocol");
        protocolEntity.setDocuments(List.of(documentEntity));
    }

    @Test
    void createProtocol() {
        when(protocolRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(protocolRepository.save(any())).thenReturn(protocolEntity);
        when(protocolMapper.toDto(protocolEntity)).thenReturn(protocolDto);
        when(documentRepository.findByName(any())).thenReturn(Optional.empty());

        ProtocolDto result = protocolService.create(protocolDto);
        assertNotNull(result);
        assertEquals(result, protocolDto);
        verify(protocolRepository).save(any());
    }

    @Test
    void createProtocol_AlreadyExists() {
        when(protocolRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(documentRepository.findByName(any())).thenReturn(Optional.of(documentEntity));
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> protocolService.create(protocolDto));
        assertEquals("Protocol contains existing document!", iae.getMessage());
        verify(protocolRepository, never()).save(any());
        verify(protocolMapper, never()).toDto(any());
    }

    @Test
    void update_ByStateRequest() {
        ProtocolStateRequest request = new ProtocolStateRequest(ProtocolState.NEW);

        when(protocolRepository.findById(any())).thenReturn(Optional.of(protocolEntity));
        when(protocolRepository.save(any())).thenReturn(protocolEntity);
        when(protocolMapper.toDto(protocolEntity)).thenReturn(protocolDto);

        ProtocolDto updated = protocolService.update(1L, request);
        assertNotNull(updated);
        verify(protocolMapper).toDto(any());
        verify(protocolRepository).save(any());
    }

    @Test
    void update_ByRequest() {
        ProtocolRequest request = new ProtocolRequest("UpdatedRequestName", null, ProtocolState.NEW);

        when(protocolRepository.findById(any())).thenReturn(Optional.of(protocolEntity));
        when(protocolRepository.save(any())).thenReturn(protocolEntity);
        when(protocolMapper.toDto(protocolEntity)).thenReturn(protocolDto);

        ProtocolDto updated = protocolService.update(1L, request);
        assertNotNull(updated);
        verify(protocolMapper).toDto(any());
        verify(protocolRepository).save(any());
    }
}
