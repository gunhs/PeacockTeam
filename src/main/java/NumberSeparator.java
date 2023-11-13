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
            linesGroups[i] = -1;
            String[] words = lines.get(i).split(";");
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
                    int existingGroupNumber = linesGroups[existingLine];
                    int currentGroupNumber = linesGroups[i];
                    if (currentGroupNumber != -1) {
                        if (existingGroupNumber == currentGroupNumber) {
                            continue;
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
                        groups.get(existingGroupNumber).add(i);
                        linesGroups[i] = existingGroupNumber;
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
                if  (group== null){
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

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
