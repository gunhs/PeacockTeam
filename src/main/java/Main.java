import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static List<Map<String, Integer>> positionWordLine;
    private static int[] linesGroups;
    private static List<List<Integer>> groups;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        if (args.length == 0) {
            System.out.println("Please provide a file name as an argument.");
            return;
        }

        String fileName = args[0];
        Path pathSrc = Paths.get(fileName);
        Path pathDst = Paths.get("out.txt");
        numberSeparate(pathDst, pathSrc);
        System.out.println(((System.currentTimeMillis() - start) / 1000) + " sec");
    }

    public static void numberSeparate(Path pathDst, Path pathSrc) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathSrc.toFile()))) {
            int countSize = loadFile(pathSrc).size();
            int numberCurrentString = -1;
            linesGroups = new int[countSize];
            groups = new ArrayList<>();
            positionWordLine = new ArrayList<>();
            String currentString;
            while ((currentString = reader.readLine()) != null) {
                numberCurrentString++;
                String[] words = currentString.split(";");
                lineHandler(words, numberCurrentString);
            }
            linesGroups = null;
            positionWordLine.clear();
            writeFile(pathSrc, pathDst);
        }
    }

    private static void lineHandler(String[] words, int numberCurrentString) {
        for (String w : words) {
            if (!w.matches("^(\"[\\d\\.]*\")*$")) {
                return;
            }
        }
        linesGroups[numberCurrentString] = -1;
        for (int j = 0; j < words.length; j++) {
            String currentWord = words[j];
            wordHandler(numberCurrentString, currentWord, j);
        }
    }

    private static void wordHandler(int numberCurrentString, String currentWord, int columnNumber) {
        if (positionWordLine.size() == columnNumber) {
            positionWordLine.add(new HashMap<>());
        }
        if (currentWord.equals("\"\"") || currentWord.isEmpty()) {
            return;
        }
        Map<String, Integer> wordMap = positionWordLine.get(columnNumber);
        if (wordMap.get(currentWord) != null) {
            mergeGroups(wordMap, numberCurrentString, currentWord);
        }
        positionWordLine.get(columnNumber).put(currentWord, numberCurrentString);
    }

    private static void mergeGroups(Map<String, Integer> wordMap, int numberCurrentString, String currentWord) {
        int existingLine = wordMap.get(currentWord);
        int existingGroupNumber = linesGroups[existingLine];
        int currentGroupNumber = linesGroups[numberCurrentString];
        if (currentGroupNumber != -1) {
            if (existingGroupNumber == currentGroupNumber) {
                return;
            }
            if (existingGroupNumber != -1) {
                List<Integer> currentGroup = groups.get(currentGroupNumber);
                groups.get(existingGroupNumber).addAll(currentGroup);
                for (Integer l : groups.get(currentGroupNumber)) {
                    linesGroups[l] = existingGroupNumber;
                }
                groups.set(currentGroupNumber, null);
            } else {
                groups.get(currentGroupNumber).add(existingLine);
                linesGroups[existingLine] = currentGroupNumber;
            }
        } else if (existingGroupNumber != -1) {
            groups.get(existingGroupNumber).add(numberCurrentString);
            linesGroups[numberCurrentString] = existingGroupNumber;
        } else {
            List<Integer> linesInGroup = new ArrayList<>();
            linesInGroup.add(existingLine);
            linesInGroup.add(numberCurrentString);
            groups.add(linesInGroup);
            int groupNumber = groups.size() - 1;
            linesGroups[existingLine] = groupNumber;
            linesGroups[numberCurrentString] = groupNumber;
        }
    }


    private static void writeFile(Path pathSrc, Path pathDst) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathDst.toFile()))) {
            int countGroups = 0;
            List<String> lines = loadFile(pathSrc);
            for (List<Integer> group : groups) {
                if (group == null) {
                    continue;
                }
                Set<String> linesInGroup = new LinkedHashSet<>();
                for (int line : group) {
                    linesInGroup.add(lines.get(line));
                }
                countGroups++;
                writer.write("Группа " + countGroups + "\n");
                for (String l : linesInGroup) {
                    writer.write(l + "\n");
                }
            }
            writer.write("\n" + "Количество групп: " + countGroups + "\n");
        }
    }

    public static List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}