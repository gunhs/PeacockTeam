import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
//        if (args.length == 0) {
//            System.out.println("Please provide a file name as an argument.");
//            return;
//        }

//        String fileName = args[0];
        //        Path pathSrc =  Paths.get(fileName);
                Path pathSrc = Paths.get("data/lng-big.csv");
//        Path pathSrc = Paths.get("data/lng.txt");
        Path pathDst = Paths.get("out.txt");
        NumberSeparator numberSeparator = new NumberSeparator();
        List<String> lines = numberSeparator.loadFile(pathSrc);
        int countLines= lines.size();
        lines.clear();
//        numberSeparator.numberSeparate(lines, pathDst, pathSrc);
        numberSeparator.numberSeparate(pathDst, pathSrc, countLines);
        System.out.println(((System.currentTimeMillis() - start) / 1000) + " sec");
    }
}