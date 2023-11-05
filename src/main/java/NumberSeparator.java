import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NumberSeparator {
    int countGroup = 1;
    TreeMap<Integer, List<Integer>> allGroups = new TreeMap<>();

    public void numberSeparate(List<String> lines, Path path) throws IOException {
        int maxCountElementsInString = 0;
        List<String[]> wordsInLines = new ArrayList<>();
        for (String line : lines) {
            String[] words = line.split(";");
            maxCountElementsInString = Math.max(words.length, maxCountElementsInString);
            wordsInLines.add(words);
        }
        for (int i = 0; i < maxCountElementsInString; i++) {
            ColumnWalker(lines, i, wordsInLines);
        }

        TreeMap<Integer, List<Integer>> treeGroups = mergeGroups();
        StringBuilder stringBuilder = new StringBuilder("Всего групп " + treeGroups.size() + "\n");

        for (Map.Entry<Integer, List<Integer>> group : treeGroups.entrySet()) {
            stringBuilder.append(fileWriter(group.getValue(), group.getKey(), lines));
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(stringBuilder.toString().strip());
        }
    }

    private String fileWriter(List<Integer> numberLines, int numberGroup, List<String> lines) {
        Set<String> linesInGroup = new LinkedHashSet<>();
        for (int i = 0; i < numberLines.size(); i++) {
            if (numberLines.contains(i)) {
                linesInGroup.add(lines.get(i));
            }
        }
        return "Группа " + numberGroup + "\n"
                + String.join("\n", linesInGroup) + "\n";
    }

    private void ColumnWalker(List<String> lineList, int numberColumn, List<String[]> words) {
        HashMap<String, Integer> wordsInColumn = new HashMap<>();
        HashMap<String, List<Integer>> numberLinesInGroup = new HashMap<>();
        for (int i = 0; i < lineList.size(); i++) {
            if (numberColumn > words.get(i).length - 1) {
                continue;
            }
            String currentWord = words.get(i)[numberColumn];
            if (!currentWord.matches("\"[\\d.]+\"") || currentWord.equals("\"\"") || currentWord.isEmpty()) {
                continue;
            }
            if (wordsInColumn.containsKey(currentWord)) {
                if (numberLinesInGroup.get(currentWord) == null) {
                    List<Integer> list = new ArrayList<>();
                    list.add(wordsInColumn.get(currentWord));
                    list.add(i);
                    numberLinesInGroup.put(currentWord, list);
                } else {
                    numberLinesInGroup.get(currentWord).add(i);
                }
            }
            wordsInColumn.put(currentWord, i);
        }
        TreeMap<Integer, List<Integer>> newGroups = new TreeMap<>();
        for (Map.Entry<String, List<Integer>> entry : numberLinesInGroup.entrySet()) {
            newGroups.put(countGroup++, entry.getValue());
        }
        allGroups.putAll(newGroups);
    }

    private TreeMap<Integer, List<Integer>> mergeGroups() {
        TreeMap<Integer, List<Integer>> treeGroup = new TreeMap<>();
        int numberGroup = 0;
        for (Map.Entry<Integer, List<Integer>> group : allGroups.entrySet()) {
            boolean merged = false;
            for (Map.Entry<Integer, List<Integer>> mergeEntry : treeGroup.entrySet()) {
                if (!Collections.disjoint(group.getValue(), mergeEntry.getValue())) {
                    mergeEntry.getValue().addAll(group.getValue());
                    merged = true;
                }
            }
            if (!merged) {
                treeGroup.put(++numberGroup, group.getValue());
            }
        }
        return treeGroup;
    }

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
