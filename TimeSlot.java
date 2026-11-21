package ridesharingsystem;

public class TimeSlot implements ITimeSlot {

    private final IDateTime start;
    private final IDateTime end;

    public TimeSlot(IDateTime start, IDateTime end) {

        if (start == null || end == null) {
            throw new IllegalArgumentException(
                    "Start and end cannot be null.");
        }

        if (start.compareTo(end) >= 0) {
            throw new IllegalArgumentException(
                    "End time must be after start time.");
        }

        this.start = start;
        this.end = end;
    }

    @Override
    public IDateTime getStart() {
        return start;
    }

    @Override
    public IDateTime getEnd() {
        return end;
    }

    @Override
    public int compareTo(ITimeSlot other) {

        if (other == null) {
            throw new IllegalArgumentException(
                    "Other time slot cannot be null.");
        }

        // This slot ends before or when the other starts.
        if (end.compareTo(other.getStart()) <= 0) {
            return -1;
        }

        // This slot starts after or when the other ends.
        if (start.compareTo(other.getEnd()) >= 0) {
            return 1;
        }

        // The two time slots overlap.
        return 0;
    }

    @Override
    public String toString() {
        return "[" + start.format() + " - " + end.format() + ")";
    }
}