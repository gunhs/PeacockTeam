import java.util.*;
import java.util.concurrent.Callable;

public class ColumnWalker implements Callable<Set<Set<Integer>>> {
    private final int numberColumn;
    private final String[][] words;

    public ColumnWalker(int numberColumn, String[][] words) {
        this.numberColumn = numberColumn;
        this.words = words;
    }

    @Override
    public Set<Set<Integer>> call() {
        HashMap<String, Set<Integer>> wordNumbersLines = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (numberColumn > words[i].length - 1) {
                continue;
            }
            String currentWord = words[i][numberColumn];
            if (!currentWord.matches("\"[\\d.]+\"") || currentWord.equals("\"\"") || currentWord.isEmpty()) {
                continue;
            }
            if (wordNumbersLines.get(currentWord) == null) {
                Set<Integer> lines = new LinkedHashSet<>();
                lines.add(i);
                wordNumbersLines.put(currentWord, lines);
            } else {
                wordNumbersLines.get(currentWord).add(i);
            }
        }
        Set<Set<Integer>> newGroups = new HashSet<>();
        wordNumbersLines.entrySet().stream().filter(e -> e.getValue().size() > 1).forEach(e -> newGroups.add(e.getValue()));
//        allGroups.addAll(newGroups);
        return newGroups;
    }
}
