import lombok.Data;

import java.util.Arrays;

@Data
public class Line {
    private int number;
    private int countElements;
    private String line;
    private String[] words;
    private boolean badLine;

    public Line(int number, String line) {
        this.number = number;
        this.line = line;
        this.words = line.split(";");
        this.countElements = words.length;
        this.badLine = checkLine(words);
    }

    private boolean checkLine(String[] words) {
        for (String w : words) {
            if (!w.matches("\"\\d*\"")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line1 = (Line) o;
        if (number != line1.number) return false;
        if (countElements != line1.countElements) return false;
        if (!line.equals(line1.line)) return false;
        return Arrays.equals(words, line1.words);
    }
}
