package io.aimc.calculator.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "solution_history")
@Getter
@Setter
@Builder
public class SolutionHistoryElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date addDate;
    private String expression;
    private String solution;
}
