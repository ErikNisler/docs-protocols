package com.docsprotocols.mapper;

import com.docsprotocols.dto.ProtocolDto;
import com.docsprotocols.entity.ProtocolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DocumentMapper.class)
public interface ProtocolMapper {

    ProtocolDto toDto(ProtocolEntity protocolEntity);
    ProtocolEntity toEntity(ProtocolDto protocolDto);

}
