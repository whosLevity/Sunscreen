package me.combimagnetron.sunscreen.element;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface Operator {

    Map<String, Operator> operators = Map.of(
            "+", new AdditionOperator(), "-", new SubtractionOperator(), "*", new MultiplicationOperator(),
            "/", new DivisionOperator(), "%", new ModuloOperator()
    );

    String operator();

    default String operatorWithSpaces() {
        return " " + operator() + " ";
    }

    double calc(double a, double b);

    static Operator find(String string) {
        if (string.split(" ").length < 2) {
            return operators.get("+");
        }
        return operators.get(string.split(" ")[1]);
    }

    static Operator get(String operator) {
        return operators.get(operator.trim());
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
