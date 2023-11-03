import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TestMain {
    @Test
    @DisplayName("3 строчки с повторениями")
    void testThreeLineMatch() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("out.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/testThreeLineMatchSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/testThreeLineMatchDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки без повторения")
    void testThreeLineNonMatch() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("out.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/testThreeLineNonMatchSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/testThreeLineNonMatchDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки c повторениями и без")
    void testThreeLineHalfMatch() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("out.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/testThreeLineHalfMatchSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/testThreeLineHalfMatchDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки проверка дубликтов")
    void testDuplicateLines() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("out.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/testThreeLineDuplicateSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/testThreeLineDuplicateDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }
}