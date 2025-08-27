
package com.javarush.borisov.entity.mapper;

import com.javarush.borisov.entity.Contragent;
import com.javarush.borisov.entity.dto.ContragentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses ={UserMapper.class})
public interface ContragentMapper {

    ContragentDto toDto(Contragent contragent);

    Contragent toEntity(ContragentDto dto);
}