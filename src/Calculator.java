import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Calculator {
    static Scanner s = new Scanner(System.in);
    static int num1,num2;
    static int result;
    static char op;

    public static String calc(String input) throws IOException {
        String act="+-*/";
        List<Character> act_list = act.chars().mapToObj(c -> (char) c).toList();
        String[] roman = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String expr = input.toUpperCase().replace(" ", "");

        int op_cnt=0; //счетчик операций
        for(int i=0;i<expr.length();i++){
            if(act_list.contains(expr.charAt(i))){
                op_cnt++;
            }
            if(expr.charAt(i)=='+'){
                op='+';
            }
            if(expr.charAt(i)=='-'){
                op='-';
            }
            if(expr.charAt(i)=='*'){
                op='*';
            }
            if(expr.charAt(i)=='/'){
                op='/';
            }
        }
        if(act_list.contains(expr.charAt(0))){
            throw new IOException ("Выражение должно начинаться с числа");
        }
        if (op_cnt==0){
            throw new IOException("Cтрока не является математической операцией. Выражение должно сдержать один из операторов: +, -, *, /");
        }
        if(op_cnt>1) {
            throw new IOException("Выражение должно сдержать только один из операторов +, -, *, /");
        }
        String[] block = expr.split("[+-/*]");
        if(block.length<2){
            throw new IOException ("Выражение должно содержать два числа");
        }
        boolean flag = false;
            if(Arrays.asList(roman).contains(block[0]) && Arrays.asList(roman).contains(block[1])){
                flag=true;
            }
            else if (Arrays.asList(roman).contains(block[0])) {
                throw new IOException("Используются одновременно разные системы счисления");
            } else if (Arrays.asList(roman).contains(block[1])){
                throw new IOException("Используются одновременно разные системы счисления");
            }
            if(flag){
                if (calcArabic(romanToArabic(block[0]), romanToArabic(block[1]), op) < 0) {
                    throw new IOException("Результат меньше нуля! в римской системе нет отрицательных чисел");
                } else if(calcArabic(romanToArabic(block[0]), romanToArabic(block[1]), op) < 1){
                    throw new IOException("Результат меньше единицы!");
                }
                else {
                    result = calcArabic(romanToArabic(block[0]), romanToArabic(block[1]), op);
                    String resultRoman = arabicToRoman(result);
                    System.out.println("Результат для римских цифр:" + block[0] + op + block[1] + "=" + resultRoman);
                    return expr + "=" + resultRoman;
                }
            }
            else {
                num1 = Integer.parseInt(block[0]);
                num2 = Integer.parseInt(block[1].trim());
                if (num1 > 10 || num2 > 10) {
                    throw new IOException("Числа должны быть от 1 до 10");
                }
                result = calcArabic(num1, num2, op);
                return expr + "=" + result;
            }
    }
    public static int calcArabic (int num1, int num2, char oper) throws IOException {
        int result = 0;
        switch (oper) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                try {
                    result = num1 / num2;
                } catch (Exception e) {
                    System.out.println("Ошибка ввода: исключение " + e);
                    System.out.println("Введенные числа должны быть целые положительные");
                }
                break;
//            default:
//                throw new IOException("Не верный знак операции");
        }
            return result;
    }
    private static int romanToArabic (String roman) {
        try {
            if (roman.equals("I")) {
                return 1;
            } else if (roman.equals("II")) {
                return 2;
            } else if (roman.equals("III")) {
                return 3;
            } else if (roman.equals("IV")) {
                return 4;
            } else if (roman.equals("V")) {
                return 5;
            } else if (roman.equals("VI")) {
                return 6;
            } else if (roman.equals("VII")) {
                return 7;
            } else if (roman.equals("VIII")) {
                return 8;
            } else if (roman.equals("IX")) {
                return 9;
            } else if (roman.equals("X")) {
                return 10;
            }
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Неверный формат данных");
        }
        return -1;
    }
    private static String arabicToRoman (int numArabic) {
        String[] roman = {"O", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
        };
        final String s = roman[numArabic];
        return s;
    }
    public static void main(String[] agr) throws IOException  {
        System.out.println("Введите выражение типа 2+3. Числа могут быть или арабские от 1 до 10 или римские от I до X");
        String inp = s.nextLine();
        System.out.println(calc(inp));
    }
}