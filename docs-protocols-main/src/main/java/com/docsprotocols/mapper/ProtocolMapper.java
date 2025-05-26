package com.docsprotocols.mapper;

import com.docsprotocols.dto.ProtocolDto;
import com.docsprotocols.entity.ProtocolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DocumentMapper.class)
public interface ProtocolMapper {

    ProtocolDto toDto(ProtocolEntity protocolEntity);

    @Mapping(target = "creationDate", expression = "java(protocolDto.getCreationDate() == null ? java.time.LocalDateTime.now() : protocolDto.getCreationDate())")
    ProtocolEntity toEntity(ProtocolDto protocolDto);

}
