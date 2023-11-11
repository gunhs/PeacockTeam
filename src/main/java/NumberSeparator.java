import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NumberSeparator {

    public void numberSeparate(Path pathDst, Path pathSrc) throws IOException {
        List<String> lines = loadFile(pathSrc);
        List<Map<String, Integer>> positionWordLine = new ArrayList<>();
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
                Map<String, Integer> wordMap = positionWordLine.get(j);
                if (wordMap.get(currentWord) != null) {
                    int existingLine = wordMap.get(currentWord);
                    if (linesGroups[i] != -1) {
                        groups.get(linesGroups[i]).add(existingLine);
                        linesGroups[existingLine] = linesGroups[i];
                    } else if (linesGroups[existingLine] != -1) {
                        groups.get(linesGroups[existingLine]).add(i);
                        linesGroups[i] = linesGroups[existingLine];
                    } else {
                        List<Integer> linesInGroup = new ArrayList<>();
                        linesInGroup.add(existingLine);
                        linesInGroup.add(i);
                        groups.add(linesInGroup);
                        int groupNumber = groups.size() - 1;
                        linesGroups[existingLine] = groupNumber;
                        linesGroups[i] = groupNumber;
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
                    writer.write("Группа " + countGroups + "\n");
                    for (String l : linesInGroup) {
                        writer.write(l+"\n");
                    }
                }
            }
            writer.write("\n" + "Количество групп: " + countGroups + "\n");
        }
    }

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
