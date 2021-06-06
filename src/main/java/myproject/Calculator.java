package myproject;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Calculator{
    private boolean isOn;
    private Set<Integer> a;
    private Set<Integer> b;
    private CalculatorMode mode;

    public Calculator() {
        isOn = true;
    }

    public void start() {
        System.out.println("Welcome to my Set Calculator!");
        loop();
    }

    public void printA() {
        System.out.println(a);
    }

    public void printB() {
        System.out.println(b);
    }

    private Set<Integer> readSet(String command) throws IncorrectInputException {
        Set<Integer> set = new LinkedHashSet<>();
        int i = 1;
        char currentChar;
        int number;
        boolean lastComma = false;
        set = new LinkedHashSet<>();
        number = 0;
        while ((currentChar = command.charAt(i)) != ']') {
            if (currentChar == ',') {
                if (!lastComma) {
                    lastComma = true;
                    set.add(number);
                    number = 0;
                } else {
                    throw new IncorrectInputException("There is no number between two commas!");
                }
            } else if (Character.isDigit(currentChar)) {
                number *= 10;
                number += Character.getNumericValue(currentChar);
                lastComma = false;
            } else {
                throw new IncorrectInputException("You used unknown symbol " + currentChar);
            }
            ++i;
        }
        set.add(number);
        return set;
    }

    private void processInput(String [] numbers) throws IncorrectInputException{
        if (numbers[0].equals("exit")) {
            isOn = false;
        } else if (numbers[0].charAt(0) == '[') {
            a = readSet(numbers[0]);
            b = readSet(numbers[1]);
        } else {
            throw new IncorrectInputException("Unknown command");
        }
    }

    private void otherSymbolsExist(String input, String otherSymbols) throws IncorrectInputException {
        if (input.contains(otherSymbols)) {
            throw new IncorrectInputException("Too many operation symbols");
        }
    }

    private void chooseMode(String input) throws IncorrectInputException {
        if (input.contains("+")) {
            otherSymbolsExist(input, "[-*]");
            mode = CalculatorMode.ADDITION;
        } else if (input.contains("-")) {
            otherSymbolsExist(input, "[+*]");
            mode = CalculatorMode.SUBTRACTION;
        } else if (input.contains("*")) {
            otherSymbolsExist(input, "[-+]");
            mode = CalculatorMode.MULTIPLICATION;
        }
    }

    private void executeCommand() {
        switch (mode) {
            case ADDITION:
                Set<Integer> unionSet = new LinkedHashSet<>(a);
                unionSet.addAll(b);
                System.out.println(unionSet);
                break;
            case SUBTRACTION:
                Set<Integer> intersectSet = a.stream().filter(b::contains).collect(Collectors.toSet());
                System.out.println(intersectSet);
                break;
        }
    }

    private void loop() {
        Scanner scanner = new Scanner(System.in);
        while(isOn) {
            String input = scanner.nextLine().replaceAll(" ", "");
            try {
                chooseMode(input);
            } catch (IncorrectInputException e) {
                System.out.println(e);
            }
            String [] numbers = input.split("[*-+]");
            try {
                processInput(numbers);
                executeCommand();
            } catch (IncorrectInputException e) {
                System.out.println(e);
            }
        }
    }
}

