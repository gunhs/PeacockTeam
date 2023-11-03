import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        if (args.length == 0) {
            System.out.println("Please provide a file name as an argument.");
            return;
        }
        String fileName = args[0];

        Path path = Path.of("out.txt");
        NumberSeparator numberSeparator = new NumberSeparator();
        List<String> lines = numberSeparator.loadFile(Paths.get(fileName));
        numberSeparator.numberSeparate(lines, path);
        System.out.println(((System.currentTimeMillis() - start) / 1000) + " sec");
    }
}
