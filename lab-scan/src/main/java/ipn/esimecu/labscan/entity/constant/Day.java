package ipn.esimecu.labscan.entity.constant;

public enum Day {

    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5);

    Day(int value) { this.value = value; }

    private int value;

    public int value() { return value; }

    public static Day of(int value) {
        if((value - 1) < 0 || value > Day.values().length)
            throw new IllegalArgumentException("day value cannot be: ".concat(String.valueOf(value)));
        return Day.values()[value - 1];
    }

}
