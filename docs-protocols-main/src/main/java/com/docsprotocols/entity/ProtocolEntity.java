package com.docsprotocols.entity;

import com.docsprotocols.dto.enumeration.ProtocolState;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "protocols")
@Data
public class ProtocolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "protocol_id")
    private List<DocumentEntity> documents;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "protocol_state")
    private ProtocolState protocolState;
}
