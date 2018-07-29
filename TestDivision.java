package com.jiaoyang;


public class TestDivision {

    public static void main(String[] args) {

        String[] ArrA = new String[]{"2", "4", "999", "6", "1", "1", "10", "3000", "0", "8888", "8888", "99999"};
        String[] ArrB = new String[]{"2", "2", "1", "4", "5", "500", "3", "2999", "2", "22", "23", "99999"};
        for (int i = 0; i < ArrA.length; i++) {
            String A = ArrA[i];
            String B = ArrB[i];
            System.out.println(A + " / " + B + " = " + MathUtil.division(A, B));
            System.out.println(A + " + " + B + "  =  " + MathUtil.calculate(A, B, new PlusResulter()));
        }

        String[] ArrE = new String[]{"2", "4", "12", "500", "999", "6", "10", "3000", "8888", "8888", "3999", "50000"};
        String[] ArrF = new String[]{"2", "2", "8", "0", "1", "4", "3", "2999", "22", "23", "3998", "50000"};
        for (int i = 0; i < ArrE.length; i++) {
            String A = ArrE[i];
            String B = ArrF[i];
            System.out.println(A + " - " + B + "  =  " + MathUtil.calculate(A, B, new SubtractResulter()));
        }
    }
}

class MathUtil {

    /**
     * 计算两个长整数的商
     *
     * @param paramA 被除数
     * @param paramB 除数
     * @return 商 ...余数
     */
    static String division(String paramA, String paramB) {
        StringBuilder result = new StringBuilder();
        String quotient = "0";
        String remainder = paramA;
        while (isBiggerOrEqual(remainder, paramB)) {
            remainder = calculate(remainder, paramB, new SubtractResulter());
            quotient = calculate(quotient, "1", new PlusResulter());
        }
        result.append(quotient);

        if (!remainder.equals("0")) {
            result.append(" ...");
            result.append(remainder);
        }
        return result.toString();
    }


    /**
     * 计算两个长整数的和或者差,做减法时只考虑A>=B的情况
     *
     * @param paramA   参数A
     * @param paramB   参数B
     * @param resulter 根据运算方法传入不同的resulter
     * @return 结果
     */
    static String calculate(String paramA, String paramB, Resulter resulter) {


        int length = Math.max(paramA.length(), paramB.length());

        for (int i = 0; i < length; i++) {

            int a = getValue(paramA, paramA.length() - 1 - i);
            int b = getValue(paramB, paramB.length() - 1 - i);
            resulter.append(a, b);
        }

        return resulter.toString();
    }


    /**
     * 判断A是否大于等于B
     *
     * @param paramA 参数A
     * @param paramB 参数B
     * @return
     */
    static boolean isBiggerOrEqual(String paramA, String paramB) {

        int lengthOfParamA = paramA.length();
        int lengthOfParamB = paramB.length();

        if (lengthOfParamA > lengthOfParamB) {
            return true;
        } else if (lengthOfParamA < lengthOfParamB) {
            return false;
        } else {
            return paramA.compareTo(paramB) >= 0;
        }

    }

    private static int getValue(String param, int index) {
        if (index >= 0) {
            return charToInt(param.charAt(index));
        } else {
            return 0;
        }

    }

    private static int charToInt(char c) {

        return c - '0';
    }


}

class PlusResulter extends Resulter {

    int add = 0;

    void append(int numberA, int numberB) {
        int result = numberA + numberB + add;
        int rest = result % 10;
        add = result / 10;

        content.append(rest);
    }


     void handleResult() {
        if (add != 0) {
            content.append(add);
        }
    }

}

class SubtractResulter extends Resulter {

    int rentNumber = 0;

    public void append(int numberA, int numberB) {

        int result = numberA - numberB - rentNumber;
        rentNumber = result >= 0 ? 0 : 1;
        result += result >= 0 ? 0 : 10;

        content.append(result);
    }


     void handleResult() {
        while (content.charAt(content.length() - 1) == '0' && content.length() > 1) {
            content.deleteCharAt(content.length() - 1);
        }
    }
}

abstract class Resulter {

    StringBuilder content = new StringBuilder();



    abstract void append(int numberA, int numberB);

    abstract void handleResult();

    public  String toString(){

        handleResult();
        return content.reverse().toString();
    }
}