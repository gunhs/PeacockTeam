import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NumberSeparator {
    HashSet<Integer> numbersFirstGroupLines = new HashSet<>();
    HashSet<Integer> numbersBadLines = new HashSet<>();

    public void numberSeparate(List<String> lines, Path path) throws IOException {
        List<String[]> wordsInLine = new ArrayList<>();
        Set<String> firstGroup = new LinkedHashSet<>();
        Set<String> secondGroup = new LinkedHashSet<>();
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
        String firstTypeGroup = "имеют совпадения";
        String secondTypeGroup = "не имеют совпадений";
        if (firstGroup.size() > secondGroup.size()) {
            fileWriter(firstGroup, secondGroup, firstTypeGroup, secondTypeGroup, path);
        } else {
            fileWriter(secondGroup, firstGroup, secondTypeGroup, firstTypeGroup, path);
        }
    }

    private void fileWriter(Set<String> firstGroup, Set<String> secondGroup,
                            String typeFirstGroup, String typeSecondGroup, Path path) throws IOException {

        String stringFirstGroup = "Первая группа (" + typeFirstGroup + "): " + firstGroup.size() + " элементов\n" +
                String.join("\n", firstGroup);
        String stringSecondGroup = secondGroup.isEmpty() ? "" :
                "\nВторая группа (" + typeSecondGroup + "): " + secondGroup.size() + " элементов\n" +
                        String.join("\n", secondGroup);
        String result = stringFirstGroup + stringSecondGroup;
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(result);
        }
    }

    private void lineWalker(List<String> lines, List<String[]> wordsInLine, int j) {
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

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    private boolean checkLine(String[] words) {
        for (String w : words) {
            if (!w.matches("\"\\d*\"")) {
                return true;
            }
        }
        return false;
    }
}
