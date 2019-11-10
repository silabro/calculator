package io.aimc.calculator.mapping;

import io.aimc.calculator.dto.CalculatorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CalculatorMapper {

    CalculatorMapper MAPPER = Mappers.getMapper(CalculatorMapper.class);

    @Mapping(target = "solution", source = "solution")
    CalculatorResponseDTO solutionToCalculatorResponseDTO(String solution);
}
