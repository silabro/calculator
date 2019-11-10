package io.aimc.calculator.services.impl;

import io.aimc.calculator.domain.entity.SolutionHistoryElement;
import io.aimc.calculator.domain.repository.SolutionsRepository;
import io.aimc.calculator.services.ICalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CalculatorService implements ICalculatorService {

    private static final short OPEN_BRACKET = '(';
    private static final short CLOSED_BRACKET = ')';
    private static final short DOT = '.';

    private final SolutionsRepository solutionsRepository;

    public String solution(final String fullExpression) {
        String expressionOnProcess = fullExpression;
        String solution;

        while (getCountOpenBracket(expressionOnProcess) > 0) {
            int indexOpenBracket = expressionOnProcess.indexOf(OPEN_BRACKET);
            int indexClosedBracket;

            for (int i = 0; i < expressionOnProcess.length(); i++) {
                if (expressionOnProcess.charAt(i) == OPEN_BRACKET) {
                    indexOpenBracket = i;
                } else if (expressionOnProcess.charAt(i) == CLOSED_BRACKET) {
                    indexClosedBracket = i;
                    expressionOnProcess = replacePartExpressionOnResult(expressionOnProcess, indexOpenBracket, indexClosedBracket);
                }
            }
        }

        solution = calculate(splitOnNumberAndOperation(expressionOnProcess));
        saveSolutionToRepository(fullExpression, solution);
        return solution;
    }

    private static ArrayList<String> splitOnNumberAndOperation(String expression) {

        StringBuilder currentNumber = new StringBuilder();
        ArrayList<String> numbersAndOperations = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (checkCharIsDigitOrDot(currentChar)) {
                currentNumber.append(currentChar);
            } else {
                if (currentNumber.length() > 0) {
                    numbersAndOperations.add(currentNumber.toString());
                    currentNumber = new StringBuilder();
                }
                numbersAndOperations.add(Character.toString(currentChar));
            }
        }
        numbersAndOperations.add(currentNumber.toString());

        return numbersAndOperations;
    }

    private static ArrayList<String> primCalculate(ArrayList<String> numbersAndOperations, int lvl) {

        ArrayList<String> operationsOneLvl = new ArrayList<>(Collections.singletonList("^"));
        ArrayList<String> operationsTwoLvl = new ArrayList<>(Arrays.asList("*", "/"));
        ArrayList<String> operationsThreeLvl = new ArrayList<>(Arrays.asList("+", "-"));

        ArrayList<String> currentOperations;

        switch (lvl) {
            case 1:
                currentOperations = operationsOneLvl;
                break;
            case 2:
                currentOperations = operationsTwoLvl;
                break;
            case 3:
                currentOperations = operationsThreeLvl;
                break;
            default:
                currentOperations = null;
                break;
        }

        double op1;
        double op2;
        double opResult;
        boolean op2Negative = false;
        boolean op1Negative = false;
        int counter = numbersAndOperations.size();

        for (int i = 0; i < counter; i++) {
            if (currentOperations.contains(numbersAndOperations.get(i))) {
                switch (numbersAndOperations.get(i)) {
                    case "^":
                        if (((i - 2 == 0 && numbersAndOperations.get(i - 2).equals("-")))) {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                            op1 *= -1;
                            op1Negative = true;
                        } else {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                        }

                        if (!(numbersAndOperations.get(i + 1).equals("-"))) {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 1));

                            numbersAndOperations.remove(i + 1);
                        } else {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 2));
                            op2 *= -1;

                            numbersAndOperations.remove(i + 2);
                            numbersAndOperations.remove(i + 1);
                            op2Negative = true;
                        }

                        opResult = Math.pow(op1, op2);
                        numbersAndOperations.remove(i);
                        numbersAndOperations.add(i, Double.toString(opResult));
                        numbersAndOperations.remove(i - 1);
                        if (op1Negative) {
                            numbersAndOperations.remove(i - 2);
                        }
                        i -= op1Negative ? 2 : 1;
                        counter -= op1Negative ? 1 : 0;
                        counter -= op2Negative ? 4 : 3;

                        op1Negative = false;
                        op2Negative = false;
                        break;
                    case "*":
                        if (((i - 2 == 0 && numbersAndOperations.get(i - 2).equals("-")))) {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                            op1 *= -1;
                            op1Negative = true;
                        } else {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                        }

                        if (!(numbersAndOperations.get(i + 1).equals("-"))) {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 1));

                            numbersAndOperations.remove(i + 1);
                        } else {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 2));
                            op2 *= -1;

                            numbersAndOperations.remove(i + 2);
                            numbersAndOperations.remove(i + 1);
                            op2Negative = true;
                        }

                        opResult = op1 * op2;
                        numbersAndOperations.remove(i);
                        numbersAndOperations.add(i, Double.toString(opResult));
                        numbersAndOperations.remove(i - 1);
                        if (op1Negative) {
                            numbersAndOperations.remove(i - 2);
                        }
                        i -= op1Negative ? 2 : 1;
                        counter -= op1Negative ? 1 : 0;
                        counter -= op2Negative ? 4 : 3;

                        op1Negative = false;
                        op2Negative = false;

                        break;
                    case "/":
                        if (((i - 2 == 0 && numbersAndOperations.get(i - 2).equals("-")))) {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                            op1 *= -1;
                            op1Negative = true;
                        } else {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                        }

                        if (!(numbersAndOperations.get(i + 1).equals("-"))) {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 1));

                            numbersAndOperations.remove(i + 1);
                        } else {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 2));
                            op2 *= -1;

                            numbersAndOperations.remove(i + 2);
                            numbersAndOperations.remove(i + 1);
                            op2Negative = true;
                        }

                        opResult = op1 / op2;
                        numbersAndOperations.remove(i);
                        numbersAndOperations.add(i, Double.toString(opResult));
                        numbersAndOperations.remove(i - 1);
                        if (op1Negative) {
                            numbersAndOperations.remove(i - 2);
                        }
                        i -= op1Negative ? 2 : 1;
                        counter -= op1Negative ? 1 : 0;
                        counter -= op2Negative ? 4 : 3;

                        op1Negative = false;
                        op2Negative = false;
                        break;
                    case "+":
                        if (((i - 2 == 0 && numbersAndOperations.get(i - 2).equals("-")))) {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                            op1 *= -1;
                            op1Negative = true;
                        } else {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                        }

                        if (!(numbersAndOperations.get(i + 1).equals("-"))) {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 1));

                            numbersAndOperations.remove(i + 1);
                        } else {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 2));
                            op2 *= -1;

                            numbersAndOperations.remove(i + 2);
                            numbersAndOperations.remove(i + 1);
                            op2Negative = true;
                        }

                        opResult = op1 + op2;
                        numbersAndOperations.remove(i);
                        numbersAndOperations.add(i, Double.toString(opResult));
                        numbersAndOperations.remove(i - 1);
                        if (op1Negative) {
                            numbersAndOperations.remove(i - 2);
                        }
                        i -= op1Negative ? 2 : 1;
                        counter -= op1Negative ? 1 : 0;
                        counter -= op2Negative ? 4 : 3;

                        op1Negative = false;
                        op2Negative = false;
                        break;
                    case "-":
                        if (i == 0) {
                            break;
                        }
                        if (((i - 2 == 0 && numbersAndOperations.get(i - 2).equals("-")))) {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                            op1 *= -1;
                            op1Negative = true;
                        } else {
                            op1 = Double.parseDouble(numbersAndOperations.get(i - 1));
                        }

                        if (!(numbersAndOperations.get(i + 1).equals("-"))) {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 1));

                            numbersAndOperations.remove(i + 1);
                        } else {
                            op2 = Double.parseDouble(numbersAndOperations.get(i + 2));
                            op2 *= -1;

                            numbersAndOperations.remove(i + 2);
                            numbersAndOperations.remove(i + 1);
                            op2Negative = true;
                        }

                        opResult = op1 - op2;
                        numbersAndOperations.remove(i);
                        numbersAndOperations.add(i, Double.toString(opResult));
                        numbersAndOperations.remove(i - 1);
                        if (op1Negative) {
                            numbersAndOperations.remove(i - 2);
                        }
                        i -= op1Negative ? 2 : 1;
                        counter -= op1Negative ? 1 : 0;
                        counter -= op2Negative ? 4 : 3;

                        op1Negative = false;
                        op2Negative = false;
                        break;
                    default:
                        break;
                }

            }
        }

        return numbersAndOperations;
    }

    private static boolean checkCharIsDigitOrDot(final char c) {
        return Character.isDigit(c) || c == DOT;
    }

    private static int getCountOpenBracket(final String string) {
        int countOpenBracket = 0;
        for (Character ch : string.toCharArray()) {
            if (ch == OPEN_BRACKET) {
                countOpenBracket++;
            }
        }
        return countOpenBracket;
    }

    private static String replacePartExpressionOnResult(final String expression, final int indexOpenBracket, final int indexClosedBracket) {
        return expression.replace(expression.substring(indexOpenBracket, indexClosedBracket + 1),
                calculate(splitOnNumberAndOperation(expression.substring(indexOpenBracket + 1, indexClosedBracket))));
    }

    private static String calculate(ArrayList<String> numbersAndOperations) {
        while (numbersAndOperations.size() > 1) {
            for (int i = 1; i <= 3; i++) {
                numbersAndOperations = primCalculate(numbersAndOperations, i);
            }
        }
        return numbersAndOperations.get(0);
    }

    private synchronized void saveSolutionToRepository(String expression, String solution) {
        solutionsRepository.save(SolutionHistoryElement.builder()
                .addDate(new java.util.Date())
                .expression(expression)
                .solution(solution)
                .build());
    }
}

