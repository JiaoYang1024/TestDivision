package com.jiaoyang.TestDivision;


public class TestDivision {

    public static void main(String[] args) {

        String[] ArrA = new String[]{"2", "4", "999", "6", "1", "1", "10", "3000", "0", "8888", "8888", "99999"};
        String[] ArrB = new String[]{"2", "2", "1", "4", "5", "500", "3", "2999", "2", "22", "23", "99999"};
        for (int i = 0; i < ArrA.length; i++) {
            String A = ArrA[i];
            String B = ArrB[i];
            System.out.println(A + " / " + B + " = " + MathUtil.division(A, B));
            //System.out.println(A + " + " + B + "  =  " + MathUtil.plus(A, B));
        }

       /* String[] ArrE = new String[]{"2",  "12", "500", "999",  "3000", "8888",  "3999", "50000","0","8888888"};
        String[] ArrF = new String[]{"2",  "8",  "0",     "1",    "2999", "22",   "3998", "50000","666","100000000"};
        for (int i = 0; i < ArrE.length; i++) {
            String A = ArrE[i];
            String B = ArrF[i];
            System.out.println(A + " - " + B + "  =  " + MathUtil.subtract(A, B));
        }*/

        /*String[] ArrG = new String[]{"2",  "12", "500", "999", "6", "10", "3000",  "3999", "50000","1234"};
        String[] ArrH = new String[]{"2",  "8",   "0",   "1",  "4",  "3",  "9",      "8",    "5","5678"};

        for (int i = 0; i < ArrG.length; i++) {
            String A = ArrG[i];
            String B = ArrH[i];
            System.out.println(A + " * " + B + "  =  " + MathUtil.multiply(A,B));
        }*/
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
        DivesionResulter resulter = new DivesionResulter(paramB);

        for (int i = 0; i < paramA.length(); i++) {
            resulter.divide(paramA.charAt(i));
        }

        return resulter.toResult();
    }


    /**
     * 计算两个长整数的和
     *
     * @param paramA   参数A
     * @param paramB   参数B
     * @return 结果
     */
    static String plus(String paramA, String paramB) {

        return calculate(paramA,paramB,new CarryResulter(CarryResulter.TYPE_PLUS));
    }

    /**
     * 计算两个长整数的差
     *
     * @param paramA   参数A
     * @param paramB   参数B
     * @return 结果
     */
    static String subtract(String paramA, String paramB) {

        String difference;

        if (isBiggerOrEqual(paramA,paramB)){
             difference =  calculate(paramA,paramB,new SubtractResulter());
        }else {
            difference =  "-"+calculate(paramB,paramA,new SubtractResulter());
        }

        return difference;
    }

    /**
     * 计算两个长整数的乘积
     *
     * @param paramA   参数A
     * @param paramB   参数B
     * @return 结果
     */
    static String multiply(String paramA, String paramB) {

        Adder adder = new Adder();
        for (int i = paramA.length()-1 ;i >=0 ; i--){
           String tempResult = calculate(paramA.substring(i,i+1),paramB,new CarryResulter(CarryResulter.TYPE_MULTIPLY));
           tempResult = moveLeft(tempResult,paramA.length()-1-i);
           adder.add(tempResult);
        }

        return adder.toResult();
    }



    /**
     * 计算两个长整数的和或者差,也可以用来计算个位数与长整数的乘积
     *
     * @param paramA   参数A
     * @param paramB   参数B
     * @param resulter 根据运算方法传入不同的resulter
     * @return 结果
     */
    private static String calculate(String paramA, String paramB, Resulter resulter) {


        int length = Math.max(paramA.length(), paramB.length());

        for (int i = 0; i < length; i++) {

            int a = resulter.getValue(paramA, paramA.length() - 1 - i);
            int b = resulter.getValue(paramB, paramB.length() - 1 - i);
            resulter.append(a, b);
        }

        return resulter.toString();
    }


    /**
     * 计算乘法竖式时,把需要相加的结果进行左移，空位补零
     *
     * @param number   需要左移的数字
     * @param size   左移的位数
     * @return 结果
     */
    private static String moveLeft(String number,int size){
           StringBuilder result = new StringBuilder(number);
        for (int i=1;i<= size;i++){
            result.append("0");
        }

        return result.toString();
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
    /**
     * 从字符串中提取指定位置的数字
     *
     * @param param 字符串
     * @param index 位置索引
     * @return
     */
     static int getValue(String param, int index) {
        if (index >= 0) {
            return charToInt(param.charAt(index));
        } else {
            return 0;
        }

    }

    /**
     * 从字符串中提取指定位置的数字,用于乘法运算
     *
     * @param param 字符串
     * @param index 位置索引
     * @return
     */
    static int getValueForMultiply(String param, int index) {
        if (index >= 0) {
            return charToInt(param.charAt(index));
        } else {
            return charToInt(param.charAt(0));
        }

    }
    /**
     * char类型转化为int类型
     * @param c
     * @return
     */
    private static int charToInt(char c) {

        return c - '0';
    }


    /**
     * 格式化字符串，去掉无用的0,如：000 -> 0 ，014-> 14
     * @param s
     */
     static void formatString(StringBuilder s) {

        while (s.charAt(0) == '0' && s.length() > 1) {
            s.deleteCharAt(0);
        }
    }


}

class DivesionResulter {

    private String divisor;
    private StringBuilder result = new StringBuilder();
    private StringBuilder quotient = new StringBuilder();
    private StringBuilder remainder = new StringBuilder();


    public DivesionResulter(String divisor){
        this.divisor = divisor;
    }

    void divide(char appendNumber){
        remainder.append(appendNumber);
        MathUtil.formatString(remainder);
        for (int j = 9; j >= 0; j--) {
            String product = MathUtil.multiply(j+"", this.divisor);

            if (MathUtil.isBiggerOrEqual(remainder.toString(), product)) {
                remainder.replace(0, remainder.length(), MathUtil.subtract(remainder.toString(), product));
                quotient.append(j);
                break;
            }
        }
    }

    String toResult(){

        MathUtil.formatString(quotient);
        result.append(quotient);
        if (!remainder.toString().equals("0")) {
            result.append(" ...");
            result.append(remainder);
        }

        return result.toString();
    }

}

class CarryResulter extends Resulter {

   private int add = 0;
   private   int type;
    public static final int TYPE_PLUS = 0;
    public static final int TYPE_MULTIPLY = 1;

    public CarryResulter(int type) {
        this.type = type;
    }

   private int calculate(int numberA, int numberB) {
        int result = 0;
        switch (type) {
            case TYPE_PLUS:
                result = numberA + numberB;
                break;
            case TYPE_MULTIPLY:
                result = numberA * numberB;
                break;
                
        }

        return  result;

    }


    @Override
    void append(int numberA, int numberB) {
        int result = calculate(numberA, numberB) + add;
        int rest = result % 10;
        add = result / 10;

        content.append(rest);
    }

    @Override
    void handleResult() {
        if (add != 0) {
            content.append(add);
        }
        MathUtil.formatString(content.reverse());
    }

    @Override
    int getValue(String number, int index) {
        if (this.type == TYPE_PLUS){
            return MathUtil.getValue(number,index);
        }else {
            return MathUtil.getValueForMultiply(number,index);
        }

    }

}

class SubtractResulter extends Resulter {

  private int borrowedNumber = 0;

    @Override
    public void append(int numberA, int numberB) {

        int result = numberA - numberB - borrowedNumber;
        borrowedNumber = result >= 0 ? 0 : 1;
        result += result >= 0 ? 0 : 10;

        content.append(result);
    }

    @Override
    void handleResult() {
        MathUtil.formatString(content.reverse());
    }

    @Override
    int getValue(String number, int index) {
        return MathUtil.getValue(number,index);
    }


}

abstract class Resulter {

    StringBuilder content = new StringBuilder();


    abstract void append(int numberA, int numberB);

    abstract void handleResult();
    abstract  int getValue(String number, int index);
    public String toString() {
        handleResult();
        return content.toString();
    }
}


class Adder {

    private String result = "0";

    void add(String extra){
       result = MathUtil.plus(result,extra);
    }

     String toResult(){
        return result;
    }

}