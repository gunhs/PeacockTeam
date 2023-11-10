import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NumberSeparator {

    public void numberSeparate(Path pathDst, Path pathSrc) throws IOException {
        List<String> lines = loadFile(pathSrc);
        List<HashMap<String, Integer>> positionWordLine = new ArrayList<>();
        List<Integer> linesGroups = new ArrayList<>();
        List<Set<Integer>> groups = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String[] words = lines.get(i).split(";");
            linesGroups.add(-1);
            for (int j = 0; j < words.length; j++) {
                if (positionWordLine.size() == j) {
                    positionWordLine.add(new HashMap<>());
                }
                String currentWord = words[j];
                if (currentWord.equals("\"\"") || currentWord.isEmpty()) {
                    continue;
                }
                if (positionWordLine.get(j).get(currentWord) != null) {
                    int oldNumber = positionWordLine.get(j).get(currentWord);
                    if (linesGroups.get(i) != -1) {
                        groups.get(linesGroups.get(i)).add(oldNumber);
                        linesGroups.set(oldNumber, linesGroups.get(i));
                    } else if (linesGroups.get(oldNumber) != -1) {
                        groups.get(linesGroups.get(oldNumber)).add(i);
                        linesGroups.set(i, linesGroups.get(oldNumber));
                    } else {
                        Set<Integer> linesInGroup = new HashSet<>();
                        linesInGroup.add(oldNumber);
                        linesInGroup.add(i);
                        groups.add(linesInGroup);
                        linesGroups.set(oldNumber, groups.size() - 1);
                        linesGroups.set(i, groups.size() - 1);
                    }
                }
                positionWordLine.get(j).put(currentWord, i);
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(pathDst)) {
            int countGroups = 0;
            for (Set<Integer> group : groups) {
                if (group.size() > 1) {
                    Set<String> linesInGroup = new LinkedHashSet<>();
                    for (int line : group) {
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
