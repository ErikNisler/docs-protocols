package service;

import com.docsprotocols.dto.DocumentDto;
import com.docsprotocols.repository.DocumentRepository;
import com.docsprotocols.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DocumentServiceTest {

    @InjectMocks
    DocumentService documentService;
    @Mock
    DocumentRepository documentRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDocument() {
        DocumentDto mockDocument = Mockito.mock(DocumentDto.class);
        when(documentRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(documentService.create(mockDocument)).thenReturn(mockDocument);
    }

    @Test
    void createDocument_throwsIllegalArgumentException() {

    }

    @Test
    void updateDocument() {

    }

    @Test
    void updateDocument_throwsNoSuchElementException() {

    }

    @Test
    void deleteDocument() {

    }

    @Test
    void deleteDocument_throwsNoSuchElementException() {

    }
}
