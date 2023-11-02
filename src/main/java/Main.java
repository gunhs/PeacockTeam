import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static HashSet<Integer> numbersFirstGroupLines = new HashSet<>();
    static HashSet<Integer> numbersBadLines = new HashSet<>();

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        List<String> lines = loadFile(Paths.get("lng.txt"));
        numberSeparate(lines);
        System.out.println(((System.currentTimeMillis() - start) / 1000) + " сек");
    }

    public static void numberSeparate(List<String> lines) throws IOException {
        List<String[]> wordsInLine = new ArrayList<>();
        List<String> firstGroup = new ArrayList<>();
        List<String> secondGroup = new ArrayList<>();
        int i = 1;
        for (int j = 0; j < lines.size(); j++) {
            String[] words = lines.get(j).split(";");
            if (checkLine(words)) {
                numbersBadLines.add(j);
            }
            wordsInLine.add(words);
        }
        int maxCountElemntIsString = 0;
        for (int j = 0; j < wordsInLine.size(); j++) {
            maxCountElemntIsString = Math.max(wordsInLine.get(i).length, maxCountElemntIsString);
        }
        for (int j = 0; j < maxCountElemntIsString; j++) {
            lineWalker(lines, wordsInLine, j);
        }
        for (Integer num : numbersFirstGroupLines) {
            if (!numbersBadLines.contains(num)) {
                firstGroup.add(lines.get(num));
            }
        }
        for (int j = 0; j < lines.size(); j++) {
            if (!numbersFirstGroupLines.contains(j) && !numbersBadLines.contains(j)) {
                secondGroup.add(lines.get(j));
            }
        }
        if (firstGroup.isEmpty() && secondGroup.isEmpty()) {
            System.out.println("строки не обнаружены");
            return;
        }
        Set<String> setFirstGroup = new LinkedHashSet<>(firstGroup);
        firstGroup.clear();
        firstGroup.addAll(setFirstGroup);
        Set<String> setSecondGroup = new LinkedHashSet<>(secondGroup);
        secondGroup.clear();
        secondGroup.addAll(setSecondGroup);
        if (firstGroup.size() > secondGroup.size()) {
            fileWriter(firstGroup, secondGroup, "имеют совпадения", "не имеют совпадений");
        } else {
            fileWriter(secondGroup, firstGroup, "не имеют совпадений", "имеют совпадения");
        }
    }


    private static void fileWriter(List<String> firstGroup, List<String> secondGroup,
                                   String typeFirstGroup, String typeOfSecondGroup) throws IOException {
        Path path = Path.of("data/out.txt");
        String stringFirstGroup = "Первая группа (" + typeFirstGroup + "): " + firstGroup.size() + " элементов\n" +
                String.join("\n", firstGroup);
        String stringSecondGroup = secondGroup.isEmpty() ? "" :
                "\nВторая группа (" + typeOfSecondGroup + "): " + secondGroup.size() + " элементов\n" +
                        String.join("\n", secondGroup);
        String result = stringFirstGroup + stringSecondGroup;
        Files.write(path, result.getBytes());
    }

    private static void lineWalker(List<String> lines, List<String[]> wordsInLine, int j) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int k = 0; k < lines.size(); k++) {
            if (j > wordsInLine.get(k).length - 1) {
                continue;
            }
            String currentString = wordsInLine.get(k)[j];
            if (numbersFirstGroupLines.contains(k) || numbersBadLines.contains(k)
                    || currentString.matches("\"\"")) {
                continue;
            }
            if (map.containsKey(currentString)) {
                numbersFirstGroupLines.add(map.get(currentString));
                numbersFirstGroupLines.add(k);
                continue;
            }
            map.put(currentString, k);
        }
    }

    public static List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    private static boolean checkLine(String[] words) {
        for (String w : words) {
            if (!w.matches("\"\\d*\"")) {
                return true;
            }
        }
        return false;
    }
}
