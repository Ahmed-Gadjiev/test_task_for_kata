import java.util.Scanner;
import java.lang.Exception;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        String numberType = detectNumberType(input);
        String result = "";

        if (numberType.equals("arabic")) {
            result = Integer.toString(calculateArabic(input));
        } else if (numberType.equals("roman")) {
            result = calculateRoman(input);
        }


        System.out.println(result);
    }

    private static String detectNumberType(String input) throws Exception {
        String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String[] arabicNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        String[] inp = input.split("\\s+");

        if (inp.length > 3) {
            throw new Exception("err");
        }

        int countR = 0;
        int countA = 0;

        for (String i : romanNumbers) {
            if (i.equals(inp[0])) {
                countR++;
            }

            if (i.equals(inp[2])) {
                countR++;
            }
        }

        for (String i : arabicNumbers) {
            if (i.equals(inp[0])) {
                countA++;
            }

            if (i.equals(inp[2])) {
                countA++;
            }
        }

        if (countA > 0 && countR > 0) {
            throw new Exception("err");
        }

        if (countR == 2 && countA == 0) {
            return "roman";
        }

        if (countA == 2 && countR == 0) {
            return "arabic";
        }


        return "";
    }


    private static int calculateArabic(String input) throws Exception {
        String[] inp = input.split("\\s+");

        int op1 = Integer.parseInt(inp[0]);
        int op2 = Integer.parseInt(inp[2]);

        if (op1 > 10 || op2 > 10) {
            throw new Exception("err");
        }

        String operation = inp[1];

        return switch (operation) {
            case "+" -> op1 + op2;
            case "-" -> op1 - op2;
            case "*" -> op1 * op2;
            case "/" -> op1 / op2;
            default -> throw new Exception("err");
        };
    }


    private static String calculateRoman(String input) throws Exception {
        String[] inp = input.split("\\s+");

        int op1 = romanToInt(inp[0]);
        int op2 = romanToInt(inp[2]);

        if (op1 > 10 || op2 > 10) {
            throw new Exception("err");
        }

        String operation = inp[1];

        int result = switch (operation) {
            case "+" -> op1 + op2;
            case "-" -> op1 - op2;
            case "*" -> op1 * op2;
            case "/" -> op1 / op2;
            default -> throw new Exception("err");
        };

        if (result < 1) {
            throw new Exception("err");
        }

        return intToRoman(result);
    }


    private static int romanToInt(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char currentChar = roman.charAt(i);
            int currentValue = romanCharValue(currentChar);

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }


    public static String intToRoman(int arabicNumber) {
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (arabicNumber > 0) {
            if (arabicNumber >= arabicValues[index]) {
                result.append(romanNumerals[index]);
                arabicNumber -= arabicValues[index];
            } else {
                index++;
            }
        }

        return result.toString();
    }

    private static int romanCharValue(char c) {
        return switch (c) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }
}