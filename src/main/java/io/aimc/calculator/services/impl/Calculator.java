package io.aimc.calculator.services.impl;

import io.aimc.calculator.services.ICalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class Calculator implements ICalculator {

    private static final short OPEN_BRACKET = '(';
    private static final short CLOSED_BRACKET = ')';
    private static final short DOT = '.';

    public String solution(String fullExpression) {

        while (getCountOpenBracket(fullExpression) > 0) {
            int indexOpenBracket = fullExpression.indexOf(OPEN_BRACKET);
            int indexClosedBracket;

            for (int i = 0; i < fullExpression.length(); i++) {
                if (fullExpression.charAt(i) == OPEN_BRACKET) {
                    indexOpenBracket = i;
                } else if (fullExpression.charAt(i) == CLOSED_BRACKET) {
                    indexClosedBracket = i;
                    fullExpression = replacePartExpressionOnResult(fullExpression, indexOpenBracket, indexClosedBracket);
                }
            }
        }

        return calculate(splitOnNumberAndOperation(fullExpression));
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
}

