package io.aimc.calculator.rest;

import io.aimc.calculator.dto.CalculatorResponseDTO;
import io.aimc.calculator.services.ICalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.aimc.calculator.mapping.CalculatorMapper.MAPPER;

@Slf4j
@RestController
@RequestMapping(value = "/api/calculator", produces = "application/json")
@RequiredArgsConstructor
public class CalculatorRest {

    private final ICalculatorService calculator;

    @PostMapping(value = "/solve/{expression}")
    public CalculatorResponseDTO calculate(@PathVariable String expression) {
        log.info("Calculate Request: " + expression);

        CalculatorResponseDTO response = MAPPER.solutionToCalculatorResponseDTO(calculator.solution(expression));

        log.info("Calculate Response: " + response);
        return response;
    }

}
