package com.eventhub.order_service.mapper;

import com.eventhub.common.mapping.CentralMapperConfig;
import com.eventhub.order_service.dto.OrderDto;
import com.eventhub.order_service.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;

@Mapper(config = CentralMapperConfig.class)
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto dto);
}
