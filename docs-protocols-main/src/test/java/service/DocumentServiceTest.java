package service;

import com.docsprotocols.dto.DocumentDto;
import com.docsprotocols.dto.enumeration.DocumentType;
import com.docsprotocols.entity.DocumentEntity;
import com.docsprotocols.mapper.DocumentMapper;
import com.docsprotocols.repository.DocumentRepository;
import com.docsprotocols.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DocumentServiceTest {

    @InjectMocks
    DocumentService documentService;
    @Mock
    DocumentRepository documentRepository;
    @Mock
    DocumentMapper documentMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDocuments_notFound() {
        when(documentRepository.findAll()).thenReturn(Collections.emptyList());
        List<DocumentDto> docs = documentService.getAll();

        verify(documentMapper, never()).toDto(any());
        assertEquals(Collections.emptyList(), docs);
    }

    @Test
    void getDocuments() {
        DocumentDto mockDocument = Mockito.mock(DocumentDto.class);
        DocumentEntity mockEntity = Mockito.mock(DocumentEntity.class);
        when(documentRepository.findAll()).thenReturn(List.of(mockEntity));
        when(documentMapper.toDto(any())).thenReturn(mockDocument);
        List<DocumentDto> docs = documentService.getAll();

        assertNotNull(docs);
        assertEquals(mockDocument, docs.getFirst());
    }

    @Test
    void getDocument() {
        DocumentDto mockDocument = Mockito.mock(DocumentDto.class);
        DocumentEntity mockEntity = Mockito.mock(DocumentEntity.class);
        when(documentRepository.findById(1L)).thenReturn(Optional.of(mockEntity));
        when(documentMapper.toDto(any())).thenReturn(mockDocument);
        DocumentDto doc = documentService.get(1L);

        assertNotNull(doc);
        assertEquals(mockDocument, doc);
    }

    @Test
    void getDocument_notFound() {
        when(documentRepository.findById(1L)).thenReturn(Optional.empty());
        DocumentDto doc = documentService.get(1L);

        assertNull(doc);
        verify(documentMapper, never()).toDto(any());
    }

    @Test
    void createDocument() {
        DocumentDto mockDocument = Mockito.mock(DocumentDto.class);
        DocumentEntity mockEntity = Mockito.mock(DocumentEntity.class);

        when(documentRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(documentMapper.toEntity(any())).thenReturn(mockEntity);
        when(documentRepository.save(mockEntity)).thenReturn(mockEntity);
        when(documentMapper.toDto(mockEntity)).thenReturn(mockDocument);

        DocumentDto created = documentService.create(mockDocument);
        assertEquals(mockDocument, created);
        verify(documentRepository).save(mockEntity);
    }

    @Test
    void createDocument_throwsIllegalArgumentException() {
        DocumentDto mockDocument = Mockito.mock(DocumentDto.class);
        DocumentEntity mockEntity = Mockito.mock(DocumentEntity.class);

        when(mockDocument.getName()).thenReturn("existingDoc");
        when(documentRepository.findByName("existingDoc")).thenReturn(Optional.of(mockEntity));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> documentService.create(mockDocument)
        );
        assertEquals("Document with this name already exists", exception.getMessage());

        verify(documentRepository, never()).save(any());
    }

    @Test
    void updateDocument() {
        DocumentDto inputDto = new DocumentDto();
        inputDto.setUsername("UpdatedName");
        inputDto.setName("UpdatedName");
        inputDto.setDocumentType(DocumentType.PDF);

        DocumentEntity existingEntity = new DocumentEntity();
        existingEntity.setId(1L);
        existingEntity.setName("OldName");
        existingEntity.setUsername("OldUser");
        existingEntity.setDocumentType(DocumentType.PDF);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(documentRepository.save(any())).thenReturn(existingEntity);
        when(documentMapper.toDto(any())).thenReturn(inputDto);  // Just return back for testing

        DocumentDto result = documentService.update(1L, inputDto);

        assertEquals("UpdatedName", result.getName());
        verify(documentRepository).save(existingEntity);
    }

    @Test
    void updateDocument_throwsNoSuchElementException() {
        DocumentDto inputDto = new DocumentDto();
        inputDto.setUsername("UpdatedName");
        inputDto.setName("UpdatedName");
        inputDto.setDocumentType(DocumentType.PDF);

        when(documentRepository.findById(1L)).thenReturn(Optional.empty());
        NoSuchElementException nee = assertThrows(NoSuchElementException.class, () ->
                documentService.update(1L, inputDto));
        assertEquals(String.format("Document with id %d not found", 1L), nee.getMessage());
        verify(documentRepository,never()).save(any());
        verify(documentMapper, never()).toDto(any());
    }

    @Test
    void deleteDocument() {
        DocumentDto inputDto = new DocumentDto();
        inputDto.setUsername("UpdatedName");
        inputDto.setName("UpdatedName");
        inputDto.setDocumentType(DocumentType.PDF);

        DocumentEntity existingEntity = new DocumentEntity();
        existingEntity.setId(1L);
        existingEntity.setName("OldName");
        existingEntity.setUsername("OldUser");
        existingEntity.setDocumentType(DocumentType.PDF);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(existingEntity));

        documentService.delete(1L);

        verify(documentRepository).delete(existingEntity);
    }

    @Test
    void deleteDocument_throwsNoSuchElementException() {
        DocumentDto inputDto = new DocumentDto();
        inputDto.setUsername("UpdatedName");
        inputDto.setName("UpdatedName");
        inputDto.setDocumentType(DocumentType.PDF);

        when(documentRepository.findById(1L)).thenReturn(Optional.empty());
        NoSuchElementException nee = assertThrows(NoSuchElementException.class, () ->
                documentService.update(1L, inputDto));
        assertEquals(String.format("Document with id %d not found", 1L), nee.getMessage());
        verify(documentRepository,never()).delete(any());
    }
}
