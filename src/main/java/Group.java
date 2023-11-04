import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class Group implements Comparable<Group> {
    private Set<Line> lines = new LinkedHashSet<>();
    private int maxSizeLine = 0;
    private int groupNumber;

    public void addLine(Line line) {
        lines.add(line);
        for (Line l : lines) {
            maxSizeLine = Math.max(l.getCountElements(), maxSizeLine);
        }
    }

    @Override
    public int compareTo(Group o) {
        if (o.getMaxSizeLine() > this.getMaxSizeLine()) {
            return 1;
        } else if (o.getMaxSizeLine() < this.maxSizeLine) {
            return -1;
        } else return Integer.compare(o.getGroupNumber(), this.getGroupNumber());
    }
}

