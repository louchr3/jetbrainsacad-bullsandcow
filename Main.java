package bullscows;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int turnCount = 1;
        int length = 0;
        int possibleChar;

        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();

        try {
            length = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Error" + input + " isn't a valid number.");
            return;
        }

        if (length <= 0) {
            System.out.println("Error: length must be greater than 0");
            return;
        }

        if (length > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }


        System.out.println("Input the number of possible symbols in the code:");
        possibleChar = Integer.parseInt(scanner.nextLine());
        if (possibleChar == 0) {
            System.out.println("Error: There are not enough unique digits to generate the secret code.");
            return;
        }
        if (possibleChar > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }
        if (possibleChar < length) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + possibleChar + " unique symbols.");
            return;
        }


        String code = generateCode(length, possibleChar);
        boolean gameFinished;

        System.out.println("Okay, let's start a game!");
        do {
            System.out.println("Turn " + turnCount + ":");
            String guess = scanner.nextLine();
            gameFinished = getGrade(guess, code);
            turnCount++;
        } while(!gameFinished);
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public static String generateCode(int length, int possibleChar) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        int numStart = 0;
        int numEnd = Math.min(possibleChar, 9);
        int letterStart = 10;
        int letterEnd = possibleChar > 10 ? possibleChar - 1 : -1;
        do {
            char digit = chars[random.nextInt(possibleChar)];
            if (code.toString().indexOf(digit) == -1) {
                code.append(digit);
            }

        } while(code.length() < length);

        System.out.print("The secret is prepared: ");
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
        System.out.print(" (" + chars[numStart] + "-" + chars[numEnd]);
        if (possibleChar > 10) {
            System.out.print(", " + chars[letterStart]);
        }
        if (letterEnd != -1 && possibleChar > 11) {
            System.out.println("-" + chars[letterEnd] + ").");
        } else {
            System.out.println(").");
        }
        return code.toString();
    }

    public static boolean getGrade(String input, String code) {
        int bullCount = 0;
        int cowCount = 0;

        for (int i = 0; i < code.length(); i++) {
            for (int k = 0; k < code.length(); k++) {
                if (input.charAt(i) == code.charAt(i)) {
                    bullCount++;
                    break;
                }
                if (input.charAt(i) == code.charAt(k)) {
                    cowCount++;
                    break;
                }
            }
        }

        if (bullCount == 0 && cowCount > 0) {
            System.out.println("Grade: " + cowCount + " cow(s). ");
        } else if (bullCount > 0 && cowCount == 0) {
            System.out.println("Grade: " + bullCount + " bull(s). ");
        } else if (bullCount > 0 && cowCount > 0) {
            System.out.println("Grade: " + bullCount + " bull(s) and " + cowCount + " cow(s). ");
        } else {
            System.out.println("Grade: None.");
        }

        if (bullCount == code.length()) {
            return true;
        }
        return false;
    }
}