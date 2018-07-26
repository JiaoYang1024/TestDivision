package com.jiaoyang;

public class TestDivision {

    public static void main(String[] args) {

       /* String[] ArrA = new String[]{"2", "4", "999", "6", "1", "1", "10", "3000", "0","8888","8888"};
        String[] ArrB = new String[]{"2", "2", "1", "4", "5", "500", "3", "2999", "2","22","23"};
        for (int i = 0; i < ArrA.length; i++) {
            String A = ArrA[i];
            String B = ArrB[i];
            System.out.println(A + " / " + B + " = " + MathUtil.division(A, B));
        }*/

       /* String[] ArrC = new String[]{"2", "4", "999", "6", "1", "1", "10", "3000", "0","8888","8888","1111","3999"};
        String[] ArrD = new String[]{"2", "2", "1", "4", "5", "500", "3", "2999", "2","22","23","2222","3998"};
        for (int i = 0; i < ArrC.length; i++) {
            String A = ArrC[i];
            String B = ArrD[i];
            System.out.println(A + " >= " + B + "  =  " + MathUtil.isBiggerOrEqual(A, B));
        }*/

        String[] ArrC = new String[]{"2", "4", "999", "6",   "10", "3000", "8888","8888","3999"};
        String[] ArrD = new String[]{"2", "2", "1", "4",   "3", "2999", "22","23","3998"};
        for (int i = 0; i < ArrC.length; i++) {
            String A = ArrC[i];
            String B = ArrD[i];
            System.out.println(A + " >= " + B + "  =  " + MathUtil.subtract(A, B));
        }
    }
}

class MathUtil {

    /**
     * 计算两个长整数的商
     *
     * @param paramA        被除数
     * @param paramB        除数
     * @return 商 ...余数  
     */
    static String division(String paramA, String paramB) {
        StringBuilder result = new StringBuilder();
        String quotient = "0";
        String remainder = paramA;
            while (isBiggerOrEqual(remainder, paramB)) {
                remainder = subtract(remainder, paramB);
                quotient = plus(quotient, "1");
            }
                result.append(quotient);

                if (!remainder.equals("0")){
                    result.append(" ...");
                    result.append(remainder);
                }
        return result.toString();
    }

    /**
     * 计算两个长整数的和
     *
     * @param paramA 参数A
     * @param paramB 参数B
     * @return 和
     */
    static String plus(String paramA, String paramB) {
        Resulter resulter = new Resulter();

        int length = Math.max(paramA.length(), paramB.length());

        for (int i = 0; i < length; i++) {

            int a = getValue(paramA, paramA.length() - 1 - i);
            int b = getValue(paramB, paramB.length() - 1 - i);
            resulter.append(a + b);
        }

        return resulter.toString();
    }


    /**
     * 计算两个长整数的差，不考虑负数的情况
     *
     * @param paramA 参数A
     * @param paramB 参数B
     * @return 差
     */
    static String subtract(String paramA, String paramB) {

        /*if (paramA.equals(paramB)) {
            return "0";                   //要不要呢？？？  是为了代码更简洁？还是为了效率更高？
        }*/

        SubtractResulter resulter = new SubtractResulter();


        int length = paramA.length();

        for (int i = 0; i < length; i++) {

            int a = getValue(paramA, paramA.length() - 1 - i);
            int b = getValue(paramB, paramB.length() - 1 - i);
            resulter.append(a - b);
        }

        return resulter.toString();
    }




    private static int getValue(String param, int index) {
        if (index >= 0) {

            return charToInt(param.charAt(index));
        } else {
            return 0;
        }

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
            return paramA.compareTo(paramB) >= 0;   //compareTo对我的感触比较大，如果JavaApi没有提供这个方法，我能不能
        }                                           //自己封装一个呢？

    }

    private static int charToInt(char c) {

        return c - '0';
    }



}

class Resulter {

    private StringBuilder content = new StringBuilder();
    private int added = 0;

    void append(int result) {
        int tempResult = result + added;
        int rest = tempResult % 10;
        added = tempResult / 10;

        content.append(rest);
    }


    public String toString() {

        if (added != 0) {
            content.append(added);
        }
        return content.reverse().toString();
    }

}

class SubtractResulter {

    StringBuilder content = new StringBuilder();

    int lendNumber = 0;

    void append(int result){
        int  tempResult = result - lendNumber;
          /* if (tempResult >= 0){
               lendNumber = 0;
           }else {
               tempResult += 10;
               lendNumber =1;
           }*/

        //要不要改成三目运算符？
        lendNumber = tempResult >= 0 ? 0 : 1;
        tempResult += tempResult >= 0 ? 0 :10;
        content.append(tempResult);
    }


    public String toString(){
        while (content.charAt(content.length() -1) == '0' && content.length() > 1 ) {
            content.deleteCharAt(content.length() - 1);
        }
        return content.reverse().toString();

    }
}