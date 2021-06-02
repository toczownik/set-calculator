package mypackage;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Calculator {
    private boolean isOn;
    private Set<Integer> a;
    private Set<Integer> b;

    public Calculator() {
        isOn = true;
    }

    public void start() {
        System.out.println("Welcome to my Set Calculator!");
        loop();
    }

    private void badInput() {
        System.out.println("Incorrect input, try again");
    }

    public void printA() {
        System.out.println(a);
    }

    private void loop() {
        Scanner scanner = new Scanner(System.in);
        int i;
        Character currentChar;
        Integer number;
        boolean lastComma = false;
        while(isOn) {
            String input = scanner.nextLine();
            String command = input.replaceAll(" ", "");
            if (command.equals("exit")) {
                isOn = false;
            } else if (command.charAt(0) == '[') {
                i = 1;
                a = new LinkedHashSet<>();
                number = 0;
                while ((currentChar = command.charAt(i)) != ']') {
                    if (currentChar == ',') {
                        if (!lastComma) {
                            lastComma = true;
                            a.add(number);
                            number = 0;
                        } else {
                            badInput();
                            break;
                        }
                    } else if (Character.isDigit(currentChar)) {
                        number *= 10;
                        number +=  Character.getNumericValue(currentChar);
                        lastComma = false;
                    } else {
                        badInput();
                        break;
                    }
                    ++i;
                }
                a.add(number);
            } else {
                badInput();
            }
            isOn = false;
        }
    }
}
