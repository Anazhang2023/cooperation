import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @Author : ZhangYiXin
 * @create 2024/9/27 12:35
 */
public class main {
    //-n的参数
    private static int numberOfExercises;
    //-r的参数
    private static int range;
    private static final String[] OPERATORS = {"+", "-", "*", "÷"};
    private static List<String> generatedExercises = new ArrayList<String>();
    private static List<String> answers = new ArrayList<String>();

    /**
     * 判断是否为符号
     * @param c
     * @return
     */
    public static boolean isOp(String c){
        for(int i=0;i<OPERATORS.length;i++){
            if(OPERATORS[i].equals(c)){
                return true;
            }
        }
        return false;
    }
    //界面
    public static void main(String[] args) {
        //参数设置
        parseArguments(args);
        try {
            //生成题目集合和答案集合
            generateExercises();
            //写入题目文件和答案文件
            writeToFile("Exercises.txt", generatedExercises);
            writeAnswersToFile("Answers.txt", answers);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    /**
     * 生成题目集合和答案集合
     */
    private static void generateExercises() {
        Random rand = new Random();
        //生成题目和答案
        while (generatedExercises.size() < numberOfExercises) {
            String exercise = generateRandomExpression(rand,generatedExercises.size());
            //将式子放入题目的List
            generatedExercises.add(exercise);
            System.out.println(exercise);
            //判断两个式子是否相同
            //生成答案
            String answer = evaluateExpression(exercise);
            //写入答案
            answers.add(answer);
        }
        return;
    }
    /**
     * 生成答案
     */
    public static String evaluateExpression(String expression) {
        // 输入式子格式转换
        String[] tokens = expression.split(" ");
        List<String> list = new ArrayList<String>();
        for (int i=0;i<tokens.length;i++){
            list.add(tokens[i]);
        }
        // 变化为后缀表达式
        List<String> list1 = simpleTosuffix(list);
        // 后缀表达式计算
        Number number = count(list1);
        return number.toString(number);
    }

    /**
     * 一般计算式转换为后缀表达式
     * @param list
     * @return
     */
    public static List<String> simpleTosuffix(List<String> list){
        List<String> Postfixlist = new ArrayList<String>();//存放后缀表达式
        Stack<String> stack = new Stack<String>();//暂存操作符
        for(int i=0;i<list.size();i++){
            String s = list.get(i);
            if(s.equals("=")||s.equals("\r\n")){
                continue;
            }
            else if(s.equals("(")){
                stack.push(s);
            }else if(s.equals(OPERATORS[2])||s.equals(OPERATORS[3])){
                stack.push(s);
            }else if(s.equals(OPERATORS[0])||s.equals(OPERATORS[1])){
                if(!stack.empty()){
                    while(!(stack.peek().equals("("))){
                        Postfixlist.add(stack.pop());
                        if(stack.empty()){
                            break;
                        }
                    }
                    stack.push(s);
                }else{
                    stack.push(s);
                }
            }else if(s.equals(")")){
                while(!(stack.peek().equals("("))){
                    Postfixlist.add(stack.pop());
                }
                stack.pop();
            }else{
                Postfixlist.add(s);
            }
        }

            while(!stack.empty()){
                Postfixlist.add(stack.pop());
            }

        return Postfixlist;
    }
    /**
     * 后缀表达式计算
     */
    public static Number count(List<String> list){
        Stack<Number> stack = new Stack<>();
        for(int i=0;i<list.size();i++){
            String s = list.get(i);
            if(!isOp(s)){
                Number number=Number.ToNumber(s);
                stack.push(number);
            }else{
                if(s.equals(OPERATORS[0])){
                    Number a1 = stack.pop();
                    Number a2 = stack.pop();
                    Number v = a2.add(a1,a2);
                    stack.push(v);
                }else if(s.equals(OPERATORS[1])){
                    Number a1 = stack.pop();
                    Number a2 = stack.pop();
                    Number v = a2.sub(a2,a1);
                    stack.push(v);
                }else if(s.equals(OPERATORS[2])){
                    Number a1 = stack.pop();
                    Number a2 = stack.pop();
                    Number v = a2.mut(a1,a2);
                    stack.push(v);
                }else if(s.equals(OPERATORS[3])){
                    Number a1 = stack.pop();
                    Number a2 = stack.pop();
                    Number v = a2.div(a2,a1);
                    stack.push(v);
                }

            }
        }

        return stack.pop();
    }
    /**
     * 生成随机题目
     * @param rand
     * @return
     */
    private static String generateRandomExpression(Random rand,int i) {
        //符号范围是1-3
        int  opeNum=  rand.nextInt(3)+1;
        //生成具体表达式和格式
        String res=formExpression(range,opeNum);
        //生成格式
        String result =  res +"\r\n";
        return result;
    }
    /**
     * 具体的生成器
     */
    private static String formExpression(int r,int opeNum){
        Random random = new Random();
        Number[] numList = new Number[opeNum+1];
        String[] numListString = new String[opeNum+1];
        String[] opeList = new String[opeNum];
        StringBuffer str = new StringBuffer();
        //随机生成符号
        for(int i=0;i<opeNum;i++) {
            opeList[i]=OPERATORS[random.nextInt(4)];
        }
        //先生成一个数值
        Number number0 = built(r, random);
        numList[0]=number0;
        //随机生成数值
        for(int i=0;i<opeNum;i++){
            //减号
            if(opeList[i]==OPERATORS[1]){
                //比前面一个数字小
                Number number=built(r,random);
                while(!number.judge(numList[i],number)){
                    number=built(r,random);
                }
                numList[i+1]=number;
            }else{
                Number number=built(r,random);
                numList[i+1]=number;
            }
        }
        //转换为Sring格式
        for(int i=0;i<=opeNum;i++){
            numListString[i]=numList[i].toString(numList[i]);
        }
        //输出式子
        for(int i=0;i<2*opeNum+1;i++) {
            if(i%2==0) {
                str.append(numListString[i/2]+" ");
            }
            if(i%2!=0) {
                str.append(opeList[(i-1)/2]+" ");
            }
        }
        str.append("="+" ");
        return str.toString();
    }

    /**
     * 生成数值且不超过限定值
     * @param r
     * @param random
     * @return
     */
    public static Number built(int r, Random random){
        Number number = new Number();
        //0-9
        while(number.fro<=0){
            number.fro=random.nextInt(r)-1;
        }

        while(number.down<=0){
            number.down=random.nextInt(r);
        }
        number.up=random.nextInt(number.down)+1;

        return number;
    }

    /**
     * 比较是否相同
     * @param s1
     * @param s2
     * @return
     */
    private static boolean ifContains(String s1, String s2) {
        int i = 0, j = 0;
        while (i < s1.length() && j < s2.length()) {
            char op1 = s1.charAt(i);
            char op2 = s2.charAt(j);

            if (op1 != op2) {
                return false;
            }

            if (op1 == '+') {
                i++;
                j++;
            } else if (op1 == '*') {
                while (j < s2.length() && s2.charAt(j) == '*') {
                    j++;
                }
                i++;
                int left = 1;
                while (j < s2.length() && Character.isDigit(s2.charAt(j))) {
                    left *= Integer.parseInt(Character.toString(s2.charAt(j++)));
                }
                if (left != Integer.parseInt(Character.toString(s1.charAt(i - 1)))) {
                    return false;
                }
            }
        }
        return i == s1.length() && j == s2.length();
    }

    /**
     * 解析-n,-r
     * @param args
     */
    private static void parseArguments(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: Myapp.exe -n <number> -r <range>");
        }
        for (int i = 0; i < args.length; i++) {
            if ("-n".equals(args[i])) {
                numberOfExercises = Integer.parseInt(args[++i]);
            } else if ("-r".equals(args[i])) {
                range = Integer.parseInt(args[++i]);
            }
        }
        if (numberOfExercises <= 0 || range <= 0) {
            throw new IllegalArgumentException("Both -n and -r must be positive integers.");
        }
    }
    /**
     * 把题目集合写入文件
     * @param filename
     * @param exercises
     * @throws IOException
     */
    private static void writeToFile(String filename, List<String> exercises) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < exercises.size(); i++) {
                writer.write( exercises.get(i));
                writer.newLine();
            }
        }
    }

    /**
     * 把答案集合写入文件
     * @param fileName
     * @param answers
     * @throws IOException
     */
    private static void writeAnswersToFile(String fileName, List<String> answers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < answers.size(); i++) {
                writer.write(answers.get(i));
                writer.newLine();
            }
        }
    }
}