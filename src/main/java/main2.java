import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : ZhangYiXin
 * @create 2024/9/28 9:20
 */
public class main2 {
    private static int numberOfExercises;
    private static int range;
    private static List<String> exerciseFromFile = new ArrayList<>();
    private static List<String> answersFromFile = new ArrayList<>();
    private static final String[] OPERATORS = {"+", "-", "*", "÷"};
    /**
     * 函数入口
     * @param args
     */
    public static void main(String[] args) {
        parseArguments(args);
        try {
            Map<String, Object> gradeResults = gradeAnswers(exerciseFromFile, answersFromFile);
            writeGradeToFile("Grade.txt", gradeResults);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    /**
     * 解析
     * @param args
     */
    private static void parseArguments(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: Myapp.exe -n <number> -r <range> [-e <exercisefile>.txt -a <answerfile>.txt]");
        }
        for (int i = 0; i < args.length; i++) {
            if ("-n".equals(args[i])) {
                numberOfExercises = Integer.parseInt(args[++i]);
            } else if ("-r".equals(args[i])) {
                range = Integer.parseInt(args[++i]);
            } else if ("-e".equals(args[i])) {
                // 读取题目文件
                String exercisesFile = args[++i];
                try {
                    exerciseFromFile = readExercisesFromFile(exercisesFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ("-a".equals(args[i])) {
                // 读取答案文件
                String answersFile = args[++i];
                try {
                    answersFromFile = readAnswersFromFile(answersFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (numberOfExercises <= 0 || range <= 0) {
            throw new IllegalArgumentException("Both -n and -r must be positive integers.");
        }
    }
    /**
     * 判断答案
     * @param exercises
     * @param userAnswers
     * @return
     */
    private static Map<String, Object> gradeAnswers(List<String> exercises, List<String> userAnswers) {
        List<Integer> correctIndices = new ArrayList<>();
        List<Integer> wrongIndices = new ArrayList<>();
        int correctCount = 0;
        int wrongCount = 0;

        for (int i = 0; i < exercises.size(); i++) {
            String exercise = exercises.get(i);
            String expectedAnswer=main.evaluateExpression(exercise);
            double expectedAnswerN=Number.valueOf(expectedAnswer);
            String userAnswer = userAnswers.get(i);
            double userAnswerN=Number.valueOf(userAnswer);
            if (Math.abs(expectedAnswerN - userAnswerN) < 1e-9) {
                correctCount++;
                correctIndices.add(i + 1);
            } else {
                wrongCount++;
                wrongIndices.add(i + 1);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("correctCount", correctCount);
        result.put("correctIndices", correctIndices);
        result.put("wrongCount", wrongCount);
        result.put("wrongIndices", wrongIndices);
        return result;
    }
    /**
     * 读取题目文件
     * @param filename
     * @return
     * @throws IOException
     */
    private static List<String> readExercisesFromFile(String filename) throws IOException {
        List<String> exercises = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                exercises.add(line);
            }
        }
        return exercises;
    }

    /**
     * 读取答案文件
     * @param filename
     * @return
     * @throws IOException
     */
    private static List<String> readAnswersFromFile(String filename) throws IOException {
        List<String> answers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                answers.add(line);
            }
        }
        return answers;
    }
    /**
     * 将判断结果写入文件
     * @param filename
     * @param gradeResults
     * @throws IOException
     */
    private static void writeGradeToFile(String filename, Map<String, Object> gradeResults) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Correct: " + gradeResults.get("correctCount") + " (");
            List<Integer> correctIndices = (List<Integer>) gradeResults.get("correctIndices");
            writer.write(correctIndices.toString().replaceAll("[\\[\\]]", ""));
            writer.write(")\n");

            writer.write("Wrong: " + gradeResults.get("wrongCount") + " (");
            List<Integer> wrongIndices = (List<Integer>) gradeResults.get("wrongIndices");
            writer.write(wrongIndices.toString().replaceAll("[\\[\\]]", ""));
            writer.write(")\n");
        }
    }
}
