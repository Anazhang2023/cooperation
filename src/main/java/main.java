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
    private static final String[] OPERATORS = {"+", "-", "*", "/"};
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
            //判断两个式子是否相同
            for (String s1:generatedExercises) {
                if (!ifContains(exercise,s1)) {
                    //将式子放入题目的List
                    generatedExercises.add(exercise);
                    //生成答案
                    double answer = evaluateExpression(exercise);
                    //写入答案
                    answers.add(String.valueOf(answer));
                }
            }
        }
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
        return "String";
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
                writer.write((i + 1) + ". " + exercises.get(i));
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
                writer.write((i + 1) + ". " + answers.get(i));
                writer.newLine();
            }
        }
    }
}