import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberSeparator {
    HashSet<Integer> numbersBadLines = new HashSet<>();
    TreeMap<Integer, Set<Integer>> groups = new TreeMap<>();
    int countOfGroup = 0;

    public void numberSeparate(List<String> lines, Path path) throws IOException {
        List<String[]> wordsInLine = new ArrayList<>();
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

        TreeMap<Integer, Set<String[]>> groupLines = new TreeMap<>();
        groups.forEach((k, v) -> {
            Set<String[]> buffer = new HashSet<>();
            v.forEach(n -> buffer.add(wordsInLine.get(n)));
            groupLines.put(k, buffer);
        });

        Comparator<Integer> comparator = (key1, key2) -> {
            int size1 = groupLines.get(key1).stream().mapToInt(l -> l.length)
                    .max().orElse(0);
            int size2 = groupLines.get(key2).stream().mapToInt(l -> l.length)
                    .max().orElse(0);
            return Integer.compare(size1, size2);
        };
        TreeMap<Integer, Set<String[]>> sortedMap = new TreeMap<>(comparator);
        sortedMap.putAll(groupLines);
        StringBuilder stringBuilder = new StringBuilder("Всего групп " + groups.size() + "\n");
        AtomicInteger numberGroup = new AtomicInteger(1);
        sortedMap.forEach((k, v) -> stringBuilder.append(fileWriter(numberGroup.getAndIncrement(), v)));
//        groups.forEach((k, v) -> stringBuilder.append(fileWriter(k, v)));
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(stringBuilder.toString().strip());
        }
    }

    private String fileWriter(int numberGroup, Set<String[]> words) {
        Set<String> linesInGroup = new LinkedHashSet<>();
        for (String[] i : words) {
            String line = String.join(";", i);
            linesInGroup.add(line);
        }
        return "Группа " + numberGroup + "\n" + String.join("\n", linesInGroup) + "\n";
    }

    private void lineWalker(List<String> lines, List<String[]> wordsInLine, int j) {
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, Set<Integer>> groupInColumn = new HashMap<>();
        for (int k = 0; k < lines.size(); k++) {
            if ((j > wordsInLine.get(k).length - 1) || numbersBadLines.contains(k)) {
                continue;
            }
            String currentString = wordsInLine.get(k)[j];
            if (currentString.matches("\"\"")) {
                continue;
            }
            if (map.containsKey(currentString)) {
                int numberOldLine = map.get(currentString);
                if (checkGroup(k, numberOldLine, groupInColumn.get(currentString))) {
                    groupInColumn.remove(currentString);
                    map.put(currentString, k);
                    continue;
                }
                if (groupInColumn.get(currentString) == null) {
                    Set<Integer> numbersLinesInGroup = new LinkedHashSet<>();
                    numbersLinesInGroup.add(numberOldLine);
                    numbersLinesInGroup.add(k);
                    groupInColumn.put(currentString, numbersLinesInGroup);
                } else {
                    groupInColumn.get(currentString).add(k);
                }
            }
            map.put(currentString, k);
        }

        for (Map.Entry<String, Set<Integer>> entry : groupInColumn.entrySet()) {
            countOfGroup++;
            groups.put(countOfGroup, entry.getValue());
        }
    }

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    private boolean checkGroup(int k, int numberOldLine, Set<Integer> oldNumbers) {
        for (Map.Entry<Integer, Set<Integer>> entry : groups.entrySet()) {
            if (entry.getValue().contains(k)) {
                if (oldNumbers != null) {
                    oldNumbers.forEach(n -> groups.get(entry.getKey()).add(n));
                }
                groups.get(entry.getKey()).add(numberOldLine);
                return true;
            } else if (entry.getValue().contains(numberOldLine)) {
                groups.get(entry.getKey()).add(k);
                if (oldNumbers != null) {
                    oldNumbers.forEach(n -> groups.get(entry.getKey()).add(n));
                }
                return true;
            }
        }
        return false;
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
