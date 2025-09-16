package com.eventhub.common.mapping;

import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING, // @Component gibi bean üretir
        unmappedTargetPolicy = ReportingPolicy.IGNORE,           // eşleşmeyen alanları hata yapma
        typeConversionPolicy = ReportingPolicy.ERROR              // şüpheli cast'lerde uyarı al
)
public interface MapperConfig { }