import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        if (args.length == 0) {
            System.out.println("Please provide a file name as an argument.");
            return;
        }

        String fileName = args[0];
                Path pathSrc =  Paths.get(fileName);
//                Path pathSrc = Paths.get("data/lng-big.csv");
//        Path pathSrc = Paths.get("data/lng.txt");
        Path pathDst = Paths.get("out.txt");
        NumberSeparator numberSeparator = new NumberSeparator();
        numberSeparator.numberSeparate(pathDst, pathSrc);
        System.out.println(((System.currentTimeMillis() - start) / 1000) + " sec");
    }
}