import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NumberSeparator {
    Set<Set<Integer>> allGroups = new HashSet<>();

    public void numberSeparate(Path pathDst, Path pathSrc, int countLines) throws IOException,
            InterruptedException, ExecutionException {
        int maxCountElementsInString = 0;
        String[][] wordsArray = new String[countLines][];
        try (BufferedReader reader = new BufferedReader(new FileReader(pathSrc.toFile()))) {
            String line;
            int numberLine = 0;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(";");
                maxCountElementsInString = Math.max(words.length, maxCountElementsInString);
                wordsArray[numberLine++] = words;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<ColumnWalker> tasks = new ArrayList<>();
        for (int i = 0; i < maxCountElementsInString; i++) {
            ColumnWalker columnWalker = new ColumnWalker(i, wordsArray);
            tasks.add(columnWalker);
        }
        List<Future<Set<Set<Integer>>>> futures = executorService.invokeAll(tasks);
        for (Future<Set<Set<Integer>>> future : futures) {
            allGroups.addAll(future.get());
        }
        executorService.shutdown();
        Set<Set<Integer>> groups = mergeGroups();
        StringBuilder stringBuilder = new StringBuilder("Всего групп " + groups.size() + "\n");
        int groupNumber = 0;
        for (Set<Integer> g : groups) {
            stringBuilder.append(fileWriter(g, ++groupNumber, wordsArray));
        }
        try (BufferedWriter writer = Files.newBufferedWriter(pathDst)) {
            writer.write(stringBuilder.toString().strip());
        }
    }

    private String fileWriter(Set<Integer> numberLines, int numberGroup, String[][] lines) {
        Set<String> linesInGroup = new LinkedHashSet<>();
        for (int i : numberLines) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < lines[i].length; j++) {
                line.append(lines[i][j]).append(";");
            }
            line.deleteCharAt(line.length()-1);
            linesInGroup.add(line.toString());
        }
        return "Группа " + numberGroup + "\n"
                + String.join("\n", linesInGroup) + "\n";
    }

    private Set<Set<Integer>> mergeGroups() {
        Set<Set<Integer>> mergeSet = new HashSet<>();
        for (Set<Integer> s : allGroups) {
            boolean merged = false;
            for (Set<Integer> m : mergeSet) {
                if (!Collections.disjoint(s, m)) {
                    m.addAll(s);
                    merged = true;
                }
            }
            if (!merged) {
                mergeSet.add(s);
            }
        }
        return mergeSet;
    }

    public List<String> loadFile(Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
