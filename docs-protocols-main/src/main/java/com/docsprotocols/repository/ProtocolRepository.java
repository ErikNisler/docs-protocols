package com.docsprotocols.repository;

import com.docsprotocols.entity.DocumentEntity;
import com.docsprotocols.entity.ProtocolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProtocolRepository extends JpaRepository<ProtocolEntity, Long> {

    Optional<ProtocolEntity> findByUsername(String userName);
}
