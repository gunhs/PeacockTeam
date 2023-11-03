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
        Path path = Paths.get("data/tests/testThreeLineMatchResult.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/tests/testThreeLineMatchSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/tests/testThreeLineMatchDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки без повторения")
    void testThreeLineNonMatch() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testThreeLineNonMatchResult.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/tests/testThreeLineNonMatchSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/tests/testThreeLineNonMatchDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки c повторениями и без")
    void testThreeLineHalfMatch() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testThreeLineHalfMatchResult.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/tests/testThreeLineHalfMatchSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/tests/testThreeLineHalfMatchDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("4 строчки проверка дубликтов")
    void testThreeLineDuplicateLines() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testThreeLineDuplicateResult.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/tests/testThreeLineDuplicateSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/tests/testThreeLineDuplicateDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("2 строчки проверка дубликтов")
    void testTwoLineDuplicateLines() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testTwoLineDuplicateResult.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/tests/testTwoLineDuplicateSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/tests/testTwoLineDuplicateDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("5 строчек. 3 и 2 совпадения. Две группы")
    void testFiveLineTwoGroup() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testFiveLineTwoGroupResult.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/tests/testFiveLineTwoGroupSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/tests/testFiveLineTwoGroupDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("5 строчек. 4 и 2 совпадения. Одна группа")
    void testFiveLineOneGroup() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testFiveLineOneGroupResult.txt");
        List<String> srcLines = numberSeparator.loadFile(Paths.get("data/tests/testFiveLineOneGroupSrc.txt"));
        List<String> expectedLines = numberSeparator.loadFile(Paths.get("data/tests/testFiveLineOneGroupDst.txt"));
        numberSeparator.numberSeparate(srcLines, path);
        List<String> actualLines = numberSeparator.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

}