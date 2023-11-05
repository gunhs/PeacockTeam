import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class NumberSeparator {
    TreeSet<Group> groups = new TreeSet<>();
    int numberGroup = 1;

    public void numberSeparate(List<String> lines, Path path) throws IOException {

        List<Line> lineList = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            Line line = new Line(i, lines.get(i));
            lineList.add(line);
        }
        int maxCountElemntIsString = 0;
        maxCountElemntIsString = lineList.stream().mapToInt(Line::getCountElements).max().orElse(maxCountElemntIsString);

        for (int j = 0; j < maxCountElemntIsString; j++) {
            ColumnWalker(lineList, j);
        }
        TreeSet<Group> treeGroups = mergeGroups();
        StringBuilder stringBuilder = new StringBuilder("Всего групп " + treeGroups.size() + "\n");
        int numberGroup = 1;
        for (Group g : treeGroups) {
            stringBuilder.append(fileWriter(g, numberGroup++));
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

    private void ColumnWalker(List<Line> lineList, int numberColumn) {
        HashMap<String, Integer> wordsInColumn = new HashMap<>();
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
                Group group = new Group();
                group.setGroupNumber(numberGroup++);
                group.addLine(lineList.get(numberOldLine));
                group.addLine(line);
                groups.add(group);
            }
            wordsInColumn.put(currentString, i);
        }
    }

    private TreeSet<Group> mergeGroups() {
        TreeSet<Group> mergedObjects = new TreeSet<>();
        for (Group group : groups) {
            boolean merged = false;
            for (Group mergedObj : mergedObjects) {
                if (!Collections.disjoint(group.getLines(), mergedObj.getLines())) {
                    Set<Line> newLines = group.getLines();
                    newLines.addAll(mergedObj.getLines());
                    mergedObj.getLines().clear();
                    mergedObj.getLines().addAll(newLines);
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                mergedObjects.add(group);
            }
        }
        return mergedObjects;
    }

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
