package ubs.walterne.berlinClock;

// Returns color formatted Berlin Clock rows

public final class BerlinClockFace extends BerlinClock {
    private final String LAMP_YELLOW = "Y";
    private final String LAMP_RED = "R";

    public BerlinClockFace(String clockTime) {
        super(clockTime);
    }

    public String getColoredRow(int row) {
        switch (row) {
            case ROW1: return getRow(ROW1).replace(LAMP_ON, LAMP_YELLOW);
            case ROW2: return getRow(ROW2).replace(LAMP_ON, LAMP_RED);
            case ROW3: return getRow(ROW3).replace(LAMP_ON, LAMP_RED);
            case ROW4:  return getRow(ROW4).replace(LAMP_ON, LAMP_YELLOW)
                    .replace(LAMP_YELLOW + LAMP_YELLOW + LAMP_YELLOW, LAMP_YELLOW + LAMP_YELLOW + LAMP_RED);
            case ROW5: return getRow(ROW5).replace(LAMP_ON, LAMP_YELLOW);
        }
        return "";
    }

    @Override public String toString() {
        return (getRow(ROW1).replace(LAMP_ON, LAMP_YELLOW) +
                getRow(ROW2).replace(LAMP_ON, LAMP_RED) +
                getRow(ROW3).replace(LAMP_ON, LAMP_RED) +
                getRow(ROW4).replace(LAMP_ON, LAMP_YELLOW)
                        .replace(LAMP_YELLOW + LAMP_YELLOW + LAMP_YELLOW, LAMP_YELLOW + LAMP_YELLOW + LAMP_RED) +
                getRow(ROW5).replace(LAMP_ON, LAMP_YELLOW));
    }
}
