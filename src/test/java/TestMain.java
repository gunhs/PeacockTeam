import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TestMain {
    @Test
    @DisplayName("3 строчки с повторениями")
    void testThreeLineMatch() throws IOException, ExecutionException, InterruptedException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testThreeLineMatchResult.txt");
        Path dstPath = Paths.get("data/tests/testThreeLineMatchDst.txt");
        Path srcPath = Paths.get("data/tests/testThreeLineMatchSrc.txt");
        List<String> srcLines = numberSeparator.loadFile(srcPath);
        List<String> expectedLines = numberSeparator.loadFile(dstPath);
//        numberSeparator.numberSeparate(srcLines, path, srcPath);
        numberSeparator.numberSeparate(path, srcPath);
        List<String> actualLines = numberSeparator.loadFile(path);
        System.out.println(dstPath.toAbsolutePath());
        System.out.println(path.toAbsolutePath());
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки без повторения")
    void testThreeLineNonMatch() throws IOException, ExecutionException, InterruptedException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testThreeLineNonMatchResult.txt");
        Path dstPath = Paths.get("data/tests/testThreeLineNonMatchDst.txt");
        Path srcPath = Paths.get("data/tests/testThreeLineNonMatchSrc.txt");
        List<String> srcLines = numberSeparator.loadFile(srcPath);
        List<String> expectedLines = numberSeparator.loadFile(dstPath);
//        numberSeparator.numberSeparate(srcLines, path, srcPath);
        numberSeparator.numberSeparate(path, srcPath);
        List<String> actualLines = numberSeparator.loadFile(path);
        System.out.println(dstPath.toAbsolutePath());
        System.out.println(path.toAbsolutePath());
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("3 строчки c повторениями и без")
    void testThreeLineHalfMatch() throws IOException, ExecutionException, InterruptedException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testThreeLineHalfMatchResult.txt");
        Path dstPath = Paths.get("data/tests/testThreeLineHalfMatchDst.txt");
        Path srcPath = Paths.get("data/tests/testThreeLineHalfMatchSrc.txt");
        List<String> srcLines = numberSeparator.loadFile(srcPath);
        List<String> expectedLines = numberSeparator.loadFile(dstPath);
//        numberSeparator.numberSeparate(srcLines, path, srcPath);
        numberSeparator.numberSeparate(path, srcPath);
        List<String> actualLines = numberSeparator.loadFile(path);
        System.out.println(dstPath.toAbsolutePath());
        System.out.println(path.toAbsolutePath());
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("4 строчки проверка дубликтов")
    void testThreeLineDuplicateLines() throws IOException, ExecutionException, InterruptedException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testThreeLineDuplicateResult.txt");
        Path dstPath = Paths.get("data/tests/testThreeLineDuplicateDst.txt");
        Path srcPath = Paths.get("data/tests/testThreeLineDuplicateSrc.txt");
        List<String> srcLines = numberSeparator.loadFile(srcPath);
        List<String> expectedLines = numberSeparator.loadFile(dstPath);
        numberSeparator.numberSeparate(path, srcPath);
//        numberSeparator.numberSeparate(srcLines, path, srcPath);
        List<String> actualLines = numberSeparator.loadFile(path);
        System.out.println(dstPath.toAbsolutePath());
        System.out.println(path.toAbsolutePath());
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("2 строчки проверка дубликтов")
    void testTwoLineDuplicateLines() throws IOException, ExecutionException, InterruptedException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testTwoLineDuplicateResult.txt");
        Path dstPath = Paths.get("data/tests/testTwoLineDuplicateDst.txt");
        Path srcPath = Paths.get("data/tests/testTwoLineDuplicateSrc.txt");
        List<String> srcLines = numberSeparator.loadFile(srcPath);
        List<String> expectedLines = numberSeparator.loadFile(dstPath);
        numberSeparator.numberSeparate(path, srcPath);
//        numberSeparator.numberSeparate(srcLines, path, srcPath);
        List<String> actualLines = numberSeparator.loadFile(path);
        System.out.println(dstPath.toAbsolutePath());
        System.out.println(path.toAbsolutePath());
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("5 строчек. 3 и 2 совпадения. Две группы")
    void testFiveLineTwoGroup() throws IOException, ExecutionException, InterruptedException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testFiveLineTwoGroupResult.txt");
        Path dstPath = Paths.get("data/tests/testFiveLineTwoGroupDst.txt");
        Path srcPath = Paths.get("data/tests/testFiveLineTwoGroupSrc.txt");
        List<String> srcLines = numberSeparator.loadFile(srcPath);
        List<String> expectedLines = numberSeparator.loadFile(dstPath);
        numberSeparator.numberSeparate(path, srcPath);
        List<String> actualLines = numberSeparator.loadFile(path);
        System.out.println(dstPath.toAbsolutePath());
        System.out.println(path.toAbsolutePath());
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    @DisplayName("5 строчек. 4 и 2 совпадения. Одна группа")
    void testFiveLineOneGroup() throws IOException {
        NumberSeparator numberSeparator = new NumberSeparator();
        Path path = Paths.get("data/tests/testFiveLineOneGroupResult.txt");
        Path dstPath = Paths.get("data/tests/testFiveLineOneGroupDst.txt");
        Path srcPath = Paths.get("data/tests/testFiveLineOneGroupSrc.txt");
        List<String> expectedLines = numberSeparator.loadFile(dstPath);
        numberSeparator.numberSeparate(path, srcPath);
        List<String> actualLines = numberSeparator.loadFile(path);
        System.out.println(dstPath.toAbsolutePath());
        System.out.println(path.toAbsolutePath());
        assertIterableEquals(expectedLines, actualLines);
    }
}