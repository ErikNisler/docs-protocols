package com.docsprotocols.entity;

import com.docsprotocols.dto.enumeration.DocumentType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "document_type")
    @Enumerated(value = EnumType.STRING)
    private DocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    private ProtocolEntity protocolEntity;

}
