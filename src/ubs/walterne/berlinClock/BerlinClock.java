package ubs.walterne.berlinClock;

/*Berlin Clock

Create a representation of the Berlin Clock for a given time (hh::mm:ss).

The Berlin Uhr (Clock) is a rather strange way to show the time.
On the top of the clock there is a yellow lamp that blinks on/off every two seconds.
The time is calculated by adding rectangular lamps.

The top two rows of lamps are red. These indicate the hours of a day. In the top row there are 4 red lamps.
Every lamp represents 5 hours. In the lower row of red lamps every lamp represents 1 hour.
So if two lamps of the first row and three of the second row are switched on that indicates 5+5+3=13h or 1 pm.

The two rows of lamps at the bottom count the minutes. The first of these rows has 11 lamps, the second 4.
In the first row every lamp represents 5 minutes.
In this first row the 3rd, 6th and 9th lamp are red and indicate the first quarter, half and last quarter of an hour.
The other lamps are yellow. In the last row with 4 lamps every lamp represents 1 minute.

The lamps are switched on from left to right.

Y = Yellow
R = Red
X = Off
*/

public class BerlinClock {

    private static final String ILLEGAL_CONSTRUCTOR_ARGUMENT = "Provide time in format HH:MM:SS";
    final String LAMP_ON = "1";
    final String LAMP_OFF = "X";
    private final int SECONDS_FLASH = 2;

    private final int UNITS_IN_A_LAMP = 5;
    private final int FOUR_LAMPS = 4;
    private final int ELEVEN_LAMPS = 11;

    private final int THREE_TIME_ELEMENTS = 3;
    private final int HH = 0;
    private final int MM = 1;
    private final int SS = 2;

    final int ROW1 = 1;
    final int ROW2 = 2;
    final int ROW3 = 3;
    final int ROW4 = 4;
    final int ROW5 = 5;

    private String row1_seconds = "";
    private String row2_fourFourHourBlocks = "";
    private String row3_fourOneHourBlocks = "";
    private String row4_elevenFiveMinuteBlocks = "";
    private String row5_fourOneMinuteBlocks = "";

    public BerlinClock(String clockTime) {
        String[] splitTime = (clockTime.split(":"));
        if (splitTime.length == THREE_TIME_ELEMENTS) {

            validateTime(splitTime);

            setRow1_seconds(Integer.valueOf(splitTime[SS]));

            row2_fourFourHourBlocks = setRow(FOUR_LAMPS, (Integer.valueOf(splitTime[HH]) / UNITS_IN_A_LAMP), row2_fourFourHourBlocks);
            row3_fourOneHourBlocks = setRow(FOUR_LAMPS, (Integer.valueOf(splitTime[HH]) % UNITS_IN_A_LAMP), row3_fourOneHourBlocks);
            row4_elevenFiveMinuteBlocks = setRow(ELEVEN_LAMPS, (Integer.valueOf(splitTime[MM]) / UNITS_IN_A_LAMP), row4_elevenFiveMinuteBlocks);
            row5_fourOneMinuteBlocks = setRow(FOUR_LAMPS, (Integer.valueOf(splitTime[MM]) % UNITS_IN_A_LAMP), row5_fourOneMinuteBlocks);

        } else throw new IllegalArgumentException(ILLEGAL_CONSTRUCTOR_ARGUMENT);
    }

    private void validateTime(String[] splitTime) {
        if (Integer.valueOf(splitTime[HH]) < 0 || Integer.valueOf(splitTime[HH]) > 23)
            throw new IllegalArgumentException(ILLEGAL_CONSTRUCTOR_ARGUMENT);
        if (Integer.valueOf(splitTime[MM]) < 0 || Integer.valueOf(splitTime[MM]) > 59)
            throw new IllegalArgumentException(ILLEGAL_CONSTRUCTOR_ARGUMENT);
        if (Integer.valueOf(splitTime[SS]) < 0 || Integer.valueOf(splitTime[SS]) > 59)
            throw new IllegalArgumentException(ILLEGAL_CONSTRUCTOR_ARGUMENT);
    }

    private void setRow1_seconds(int seconds) {
        if (seconds % SECONDS_FLASH == 0) row1_seconds = LAMP_ON;
        else row1_seconds = LAMP_OFF;
    }

    private String setRow(int numberOfLamps, int timeUnit, String clockRow) {
        for (int lampsOn = 0; lampsOn < timeUnit; lampsOn++) clockRow += LAMP_ON;
        while (clockRow.length() < numberOfLamps) clockRow += LAMP_OFF;
        return clockRow;
    }

    public String getRow(int row) {
        switch (row) {
            case ROW1:
                return row1_seconds;
            case ROW2:
                return row2_fourFourHourBlocks;
            case ROW3:
                return row3_fourOneHourBlocks;
            case ROW4:
                return row4_elevenFiveMinuteBlocks;
            case ROW5:
                return row5_fourOneMinuteBlocks;
        }
        return null;
    }

    @Override public String toString() {
        return row1_seconds + row2_fourFourHourBlocks + row3_fourOneHourBlocks + row4_elevenFiveMinuteBlocks + row5_fourOneMinuteBlocks;
    }
}
