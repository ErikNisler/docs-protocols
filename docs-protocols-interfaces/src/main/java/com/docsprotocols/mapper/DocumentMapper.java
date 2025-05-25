package com.docsprotocols.mapper;

import com.docsprotocols.dto.DocumentDto;
import com.docsprotocols.entity.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentDto toDto(DocumentEntity documentEntity);

    @Mapping(target = "creationDate", expression = "java(documentDto.getCreationDate() == null ? java.time.LocalDateTime.now() : documentDto.getCreationDate())")
    DocumentEntity toEntity(DocumentDto documentDto);

}
