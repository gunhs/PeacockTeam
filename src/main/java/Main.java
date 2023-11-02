import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    static HashSet<Integer> numbersLines = new HashSet<>();

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        List<String> lines = Files.readAllLines(Paths.get("data/lng.txt"));
        List<String[]> wordsInLine = new ArrayList<>();
        List<String> firstGroup = new ArrayList<>();
        List<String> secondGroup = new ArrayList<>();
        int i = 1;
        for (String l : lines) {
            wordsInLine.add(l.split(";"));
        }
        int maxCountElemntIsString = 0;
        for (int j = 0; j < wordsInLine.size(); j++) {
            maxCountElemntIsString = Math.max(wordsInLine.get(i).length, maxCountElemntIsString);
        }
        for (int j = 0; j < maxCountElemntIsString; j++) {
            lineWalker(lines, wordsInLine, j);
        }
        for (Integer num : numbersLines) {
            firstGroup.add(lines.get(num));
        }
        for (int j = 0; j < lines.size(); j++) {
            if (!numbersLines.contains(j)) {
                secondGroup.add(lines.get(j));
            }
        }
        if ((lines.size() - firstGroup.size()) < (lines.size() / 2)) {
            fileWriter(firstGroup, secondGroup, "имеют совпадения", "не имеют совпадений");
        } else {
            fileWriter(secondGroup, firstGroup, "не имеют совпадений", "имеют совпадения");
        }
        System.out.println(((System.currentTimeMillis() - start) / 1000) + " сек");
    }

    private static void fileWriter(List<String> firstGroup, List<String> secondGroup,
                                   String typeFirstGroup, String typeOfSecondGroup) throws IOException {
        Path path = Path.of("data/out.txt");
        String st = "Первая группа (" + typeFirstGroup + "): " + firstGroup.size() + " элементов\n" +
                String.join("\n", firstGroup) +
                "\nВторая группа (" + typeOfSecondGroup + "): " + secondGroup.size() + " элементов\n" +
                String.join("\n", secondGroup);
        Files.write(path, st.getBytes());
    }

    private static void lineWalker(List<String> lines, List<String[]> wordsInLine, int j) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int k = 0; k < lines.size(); k++) {
            if (j > wordsInLine.get(k).length - 1) {
                continue;
            }
            String currentString = wordsInLine.get(k)[j];
            if (numbersLines.contains(k) || !currentString.matches("\"\\d+\"")) {
                continue;
            }
            if (map.containsKey(currentString)) {
                numbersLines.add(map.get(currentString));
                numbersLines.add(k);
                continue;
            }
            map.put(currentString, k);
        }
    }
}
