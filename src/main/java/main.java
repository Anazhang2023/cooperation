import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            /**
             *   for (int i=0;i<generatedExercises.size()-1;i++) {
             *                 String s1=generatedExercises.get(i);
             *                 if (!ifContains(exercise,s1)) {
             *                     //生成答案
             *                     double answer = evaluateExpression(exercise);
             *                     //写入答案
             *                     answers.add(String.valueOf(answer));
             *
             *                 }else{
             *                     generatedExercises.remove(exercise);
             *                 }
             *             }
              */

        }
        return;
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
        String result = i+1 + "." + res +"\r\n";
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
        number.fro=random.nextInt(r);
        //1-10
        number.down=random.nextInt(r)+1;
        //1-10(<=down)
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
     * 生成答案
     */
    private static double evaluateExpression(String expression) {
        return 0.1;
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