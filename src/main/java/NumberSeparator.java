import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class NumberSeparator {
    HashSet<Integer> numbers = new HashSet<>();
    TreeSet<Group> groups = new TreeSet<>();
    int numberGroup = 0;

    public void numberSeparate(List<String> lines, Path path) throws IOException {
        List<Line> lineList = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            Line line = new Line(i, lines.get(i));
            lineList.add(line);
        }
        int maxCountElemntIsString = 0;
        maxCountElemntIsString = lineList.stream().mapToInt(Line::getCountElements).max().orElse(maxCountElemntIsString);

        for (int j = 0; j < maxCountElemntIsString; j++) {
            lineWalker(lineList, j);
        }

        HashSet<Line> lineHashSet = new HashSet<>();
        groups.forEach(g -> lineHashSet.addAll(g.getLines()));
        Group groupDel = null;
        for (Line line : lineHashSet) {
            int repeatLine = 0;
            for (Group g : groups) {
                if (g.getLines().contains(line)) {
                    repeatLine++;
                    groupDel = g;
                } else {
                }
                if (repeatLine > 1) {
                    assert groupDel != null;
                    g.getLines().addAll(groupDel.getLines());
                    System.out.println(groupDel.getGroupNumber());
                    groups.remove(groupDel);
                    repeatLine = 1;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder("Всего групп " + groups.size() + "\n");
        int numberGroup = 0;
        TreeSet<Group> sg = groups.stream().sorted().collect(Collectors.toCollection(TreeSet::new));
        for (Group group : sg) {
            numberGroup++;
            stringBuilder.append(fileWriter(group, numberGroup));
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(stringBuilder.toString().strip());
        }
    }

    private String fileWriter(Group group, int numberGroup) {
        Set<String> linesInGroup = new LinkedHashSet<>();
        for (Line l : group.getLines()) {
            linesInGroup.add(l.getLine());
        }
        return "Группа " + numberGroup + "\n"
                + String.join("\n", linesInGroup) + "\n";
    }

    private void lineWalker(List<Line> lineList, int numberColumn) {
        HashMap<String, Integer> wordsInColumn = new HashMap<>();
        HashMap<String, Group> wordGroup = new HashMap<>();
        HashSet<Integer> numbersLinesInColumn = new HashSet<>();
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);
            if ((numberColumn > (line.getCountElements() - 1)) || line.isBadLine()) {
                continue;
            }
            String currentString = line.getWords()[numberColumn];
            if (currentString.matches("\"\"")) {
                continue;
            }
            if (wordsInColumn.containsKey(currentString)) {
                int numberOldLine = wordsInColumn.get(currentString);
                if (numbers.contains(i) || numbers.contains(numberOldLine)) {
                    if (wordGroup.containsKey(currentString)) {
                        addGroup(wordGroup.get(currentString), lineList.get(i));
                        wordGroup.remove(currentString);
                    } else {
                        addLines(lineList.get(i), lineList.get(numberOldLine));
                    }
                    continue;
                }
                if (wordGroup.get(currentString) == null) {
                    numberGroup++;
                    Group group = new Group();
                    group.setGroupNumber(numberGroup);
                    group.addLine(lineList.get(numberOldLine));
                    group.addLine(lineList.get(i));
                    wordGroup.put(currentString, group);
                    numbersLinesInColumn.add(i);
                    numbersLinesInColumn.add(numberOldLine);
                } else {
                    wordGroup.get(currentString).addLine(lineList.get(i));
                    numbersLinesInColumn.add(i);
                }
            }
            wordsInColumn.put(currentString, i);
        }
        wordGroup.forEach((k, v) -> groups.add(v));
        numbers.addAll(numbersLinesInColumn);
    }

    private void addGroup(Group group, Line line) {
        for (Group g : groups) {
            if (g.getLines().contains(line)) {
                g.getLines().addAll(group.getLines());
                g.addLine(line);
            }
        }
        groups.remove(group);
    }

    private void addLines(Line line, Line oldLine) {
        for (Group g : groups) {
            if (g.getLines().contains(line) || g.getLines().contains(oldLine)) {
                g.addLine(oldLine);
                g.addLine(line);
            }
        }
    }

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
