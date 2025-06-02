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

import java.util.Collections;
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

    @Test
    void getProtocols_notFound() {
        when(protocolRepository.findAll()).thenReturn(Collections.emptyList());
        List<ProtocolDto> protocols = protocolService.getAll();

        verify(protocolMapper, never()).toDto(any());
        assertEquals(Collections.emptyList(), protocols);
    }

    @Test
    void getProtocols() {
        when(protocolRepository.findAll()).thenReturn(List.of(protocolEntity));
        when(protocolMapper.toDto(any())).thenReturn(protocolDto);
        List<ProtocolDto> protocols = protocolService.getAll();

        assertNotNull(protocols);
        assertEquals(protocolDto, protocols.getFirst());
    }

    @Test
    void getProtocol() {
        when(protocolRepository.findById(1L)).thenReturn(Optional.of(protocolEntity));
        when(protocolMapper.toDto(any())).thenReturn(protocolDto);
        ProtocolDto result = protocolService.get(1L);

        assertNotNull(result);
        assertEquals(protocolDto, result);
    }

    @Test
    void getProtocol_notFound() {
        when(protocolRepository.findById(1L)).thenReturn(Optional.empty());
        ProtocolDto result = protocolService.get(1L);

        assertNull(result);
        verify(protocolMapper, never()).toDto(any());
    }

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
