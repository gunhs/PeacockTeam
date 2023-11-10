import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NumberSeparator {

    public void numberSeparate(Path pathDst, Path pathSrc) throws IOException {
        List<String> lines = loadFile(pathSrc);
        List<HashMap<String, Integer>> positionWordLine = new ArrayList<>();
        int[] linesGroups = new int[lines.size()];
        List<List<Integer>> groups = new ArrayList<>(lines.size());
        for (int i = 0; i < lines.size(); i++) {
            String[] words = lines.get(i).split(";");
            linesGroups[i] = -1;
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
                    if (linesGroups[i] != -1) {
                        groups.get(linesGroups[i]).add(oldNumber);
                        linesGroups[oldNumber] = linesGroups[i];
                    } else if (linesGroups[oldNumber] != -1) {
                        groups.get(linesGroups[oldNumber]).add(i);
                        linesGroups[i] = linesGroups[oldNumber];
                    } else {
                        List<Integer> linesInGroup = new ArrayList<>();
                        linesInGroup.add(oldNumber);
                        linesInGroup.add(i);
                        groups.add(linesInGroup);
                        linesGroups[oldNumber] = groups.size() - 1;
                        linesGroups[i] = groups.size() - 1;
                    }
                }
                positionWordLine.get(j).put(currentWord, i);
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(pathDst)) {
            int countGroups = 0;
            for (List<Integer> group : groups) {
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
