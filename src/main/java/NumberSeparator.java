import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NumberSeparator {

    public void numberSeparate(Path pathDst, Path pathSrc) throws IOException {
        List<String> lines = loadFile(pathSrc);
        int groupNumber = 0;
        HashMap<Integer, HashMap<String, Integer>> positionWordLine = new HashMap<>();
        HashMap<Integer, Integer> linesGroups = new HashMap<>();
        HashMap<Integer, Set<Integer>> groups = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String[] words = lines.get(i).split(";");
            for (int j = 0; j < words.length; j++) {
                String currentWord = words[j];
                if (!currentWord.matches("\"[\\d.]+\"") || currentWord.equals("\"\"") || currentWord.isEmpty()) {
                    continue;
                }
                if (positionWordLine.get(j) != null) {
                    if (positionWordLine.get(j).get(currentWord) != null) {
                        int oldNumber = positionWordLine.get(j).get(currentWord);
                        if (linesGroups.get(i) != null) {
                            groups.get(linesGroups.get(i)).add(oldNumber);
                            linesGroups.put(oldNumber, linesGroups.get(i));
                        } else if (linesGroups.get(oldNumber) != null) {
                            groups.get(linesGroups.get(oldNumber)).add(i);
                            linesGroups.put(i, linesGroups.get(oldNumber));
                        } else {
                            int g = groupNumber++;
                            Set<Integer> linesInGroup = new HashSet<>();
                            linesInGroup.add(positionWordLine.get(j).get(currentWord));
                            linesInGroup.add(i);
                            groups.put(g, linesInGroup);
                            linesGroups.put(i, g);
                            linesGroups.put(oldNumber, g);
                        }
                    }
                    positionWordLine.get(j).put(currentWord, i);
                } else {
                    HashMap<String, Integer> wordLines = new HashMap<>();
                    wordLines.put(currentWord, i);
                    positionWordLine.put(j, wordLines);
                }
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(pathDst)) {
        int countGroups = 0;
            for (Map.Entry<Integer, Set<Integer>> entry : groups.entrySet()) {
                if (entry.getValue().size() > 1) {
                    Set<String> linesInGroup = new LinkedHashSet<>();
                    for (int line : entry.getValue()) {
                        linesInGroup.add(lines.get(line));
                    }
                    countGroups++;
                    writer.write("Группа " + countGroups + "\n"
                            + String.join("\n", linesInGroup) + "\n");
                }
            }
            writer.write("\nКоличество групп: " + countGroups);
        }
    }
    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
