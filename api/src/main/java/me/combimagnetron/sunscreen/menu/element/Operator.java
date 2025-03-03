package me.combimagnetron.sunscreen.menu.element;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface Operator {

    Map<String, Class<? extends Operator>> operators = Map.of(
            "+", AdditionOperator.class, "-", SubtractionOperator.class, "*", MultiplicationOperator.class,
            "/", DivisionOperator.class, "%", ModuloOperator.class
    );

    String operator();

    default String operatorWithSpaces() {
        return " " + operator() + " ";
    }

    double calc(double a, double b);

    static Operator find(String string) {
        try {
            return operators.get(string.split(" ")[1]).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static Operator get(String operator) {
        try {
            return operators.get(operator).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    class AdditionOperator implements Operator {

        @Override
        public String operator() {
            return "\\+";
        }

        @Override
        public double calc(double a, double b) {
            return a + b;
        }
    }

    class SubtractionOperator implements Operator {

        @Override
        public String operator() {
            return "\\-";
        }

        @Override
        public double calc(double a, double b) {
            return a - b;
        }
    }

    class MultiplicationOperator implements Operator {

        @Override
        public String operator() {
            return "\\*";
        }

        @Override
        public double calc(double a, double b) {
            return a * b;
        }
    }

    class DivisionOperator implements Operator {

        @Override
        public String operator() {
            return "\\/";
        }

        @Override
        public double calc(double a, double b) {
            return a / b;
        }
    }

    class ModuloOperator implements Operator {

        @Override
        public String operator() {
            return "\\%";
        }

        @Override
        public double calc(double a, double b) {
            return a % b;
        }
    }

}
