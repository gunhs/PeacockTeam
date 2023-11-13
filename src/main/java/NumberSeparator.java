import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NumberSeparator {
    public void numberSeparate(Path pathDst, Path pathSrc) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathDst.toFile()));
             BufferedReader reader = new BufferedReader(new FileReader(pathSrc.toFile()))) {
            int countSize = loadFile(pathSrc).size();
            int numberCurrentString = -1;
            List<Map<String, Integer>> positionWordLine = new ArrayList<>();
            int[] linesGroups = new int[countSize];
            List<List<Integer>> groups = new ArrayList<>();
            String currentString;
            while ((currentString = reader.readLine()) != null) {
                numberCurrentString++;
                String[] words = currentString.split(";");
                linesGroups[numberCurrentString] = -1;
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
                        int currentGroupNumber = linesGroups[numberCurrentString];
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
                    positionWordLine.get(j).put(currentWord, numberCurrentString);
                }
            }
            linesGroups = null;
            positionWordLine.clear();
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

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
