package com.pedromolon.agregadordeinvestimentos.mapper;

import com.pedromolon.agregadordeinvestimentos.dto.request.StockRequest;
import com.pedromolon.agregadordeinvestimentos.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

    Stock toEntity(StockRequest request);

}
