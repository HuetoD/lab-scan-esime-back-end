package ipn.esimecu.labscan.entity.constant;

import java.time.LocalDate;
import java.util.Comparator;

public enum Day implements Comparator<Day> {

    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5);

    Day(int value) { this.value = value; }

    private int value;

    public int value() { return value; }

    public String stringValue() { return String.valueOf(value()); }

    public static Day of(int value) {
        if((value - 1) < 0 || value > Day.values().length)
            throw new IllegalArgumentException("day value cannot be: ".concat(String.valueOf(value)));
        return Day.values()[value - 1];
    }

    public static Day of(LocalDate localDate) {
        return of(localDate.getDayOfWeek().getValue());
    }

    @Override
    public int compare(Day o1, Day o2) {
        return Integer.compare(o1.value(), o2.value());
    }
}
