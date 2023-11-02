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
    void testThreeStringDuplicate() throws IOException {
        List<String> srcLines = Main.loadFile(Paths.get("data/testThreeLineMatchSrc.txt"));
        List<String> expectedLines = Main.loadFile(Paths.get("data/testThreeLineMatchDst.txt"));
        Main.numberSeparate(srcLines);
        Path path = Paths.get("data/out.txt");
        List<String> actualLines = Main.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки без повторения")
    void testThreeStringNonDuplicate() throws IOException {
        Path path = Paths.get("data/out.txt");
        List<String> srcLines = Main.loadFile(Paths.get("data/testThreeLineNonMatchSrc.txt"));
        List<String> expectedLines = Main.loadFile(Paths.get("data/testThreeLineNonMatchDst.txt"));
        Main.numberSeparate(srcLines);
        List<String> actualLines = Main.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);

    }

    @Test
    @DisplayName("3 строчки c повторениями и без")
    void testThreeStringHalfDuplicate() throws IOException {
        Path path = Paths.get("data/out.txt");
        List<String> srcLines = Main.loadFile(Paths.get("data/testThreeLineHalfMatchSrc.txt"));
        List<String> expectedLines = Main.loadFile(Paths.get("data/testThreeLineHalfMatchDst.txt"));
        Main.numberSeparate(srcLines);
        List<String> actualLines = Main.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки проверка дубликтов")
    void testDuplicateLines() throws IOException {
        Path path = Paths.get("data/out.txt");
        List<String> srcLines = Main.loadFile(Paths.get("data/testThreeLineDuplicateSrc.txt"));
        List<String> expectedLines = Main.loadFile(Paths.get("data/testThreeLineDuplicateDst.txt"));
        Main.numberSeparate(srcLines);
        List<String> actualLines = Main.loadFile(path);
        assertIterableEquals(expectedLines, actualLines);
    }
}