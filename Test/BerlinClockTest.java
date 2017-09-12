import org.junit.jupiter.api.Test;
import ubs.walterne.berlinClock.BerlinClock;
import ubs.walterne.berlinClock.BerlinClockFace;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BerlinClockTest {

    private static final int ROW1_SECONDS = 1;
    private static final int ROW2_HOURS = 2;
    private static final int ROW3_HOURS = 3;
    private static final int ROW4_MINUTES = 4;
    private static final int ROW5_MINUTES = 5;

   @Test
    void exceptionTesting() {
        assertThrows(IllegalArgumentException.class, () -> new BerlinClock("invalid time format"));
        assertThrows(IllegalArgumentException.class, () -> new BerlinClock("invalid:time:format"));
        assertThrows(IllegalArgumentException.class, () -> new BerlinClock("24:00:00"));
        assertThrows(IllegalArgumentException.class, () -> new BerlinClock("00:60:00"));
        assertThrows(IllegalArgumentException.class, () -> new BerlinClock("00:00:60"));
        assertThrows(IllegalArgumentException.class, () -> new BerlinClock("00:00:0A"));
        assertThrows(IllegalArgumentException.class, () -> new BerlinClock("00:00:"));
    }

    @Test
    void checkFormattedTime() {
        LocalTime localTime = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS);

        BerlinClockFace berlinClockFace = new BerlinClockFace(localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond());

        System.out.println(berlinClockFace.getColoredRow(ROW1_SECONDS));
        System.out.println(berlinClockFace.getColoredRow(ROW2_HOURS));
        System.out.println(berlinClockFace.getColoredRow(ROW3_HOURS));
        System.out.println(berlinClockFace.getColoredRow(ROW4_MINUTES));
        System.out.println(berlinClockFace.getColoredRow(ROW5_MINUTES));
    }

    @Test
    void testRow1_secondLampToBlinkEvery2Seconds() {
        assertEquals("1", new BerlinClock("00:00:00").getRow(ROW1_SECONDS));
        assertEquals("X", new BerlinClock("00:00:01").getRow(ROW1_SECONDS));
        assertEquals("1", new BerlinClock("00:00:02").getRow(ROW1_SECONDS));
        assertEquals("X", new BerlinClock("00:00:03").getRow(ROW1_SECONDS));
    }

    @Test
    void testRow2_fourHourBlockTest() {
        assertEquals("XXXX", new BerlinClock("01:00:00").getRow(ROW2_HOURS));
        assertEquals("XXXX", new BerlinClock("02:00:00").getRow(ROW2_HOURS));
        assertEquals("XXXX", new BerlinClock("03:00:00").getRow(ROW2_HOURS));
        assertEquals("XXXX", new BerlinClock("04:00:00").getRow(ROW2_HOURS));
        assertEquals("1XXX", new BerlinClock("05:00:00").getRow(ROW2_HOURS));
        assertEquals("1XXX", new BerlinClock("06:00:00").getRow(ROW2_HOURS));
        assertEquals("11XX", new BerlinClock("10:00:00").getRow(ROW2_HOURS));
        assertEquals("111X", new BerlinClock("15:00:00").getRow(ROW2_HOURS));
        assertEquals("1111", new BerlinClock("20:00:00").getRow(ROW2_HOURS));
        assertEquals("1111", new BerlinClock("21:00:00").getRow(ROW2_HOURS));
    }

    @Test
    void testRow3_singleHourBlocksTest() {
        assertEquals("1XXX", new BerlinClock("01:00:00").getRow(ROW3_HOURS));
        assertEquals("11XX", new BerlinClock("02:00:00").getRow(ROW3_HOURS));
        assertEquals("111X", new BerlinClock("03:00:00").getRow(ROW3_HOURS));
        assertEquals("1111", new BerlinClock("04:00:00").getRow(ROW3_HOURS));
        assertEquals("XXXX", new BerlinClock("05:00:00").getRow(ROW3_HOURS));
        assertEquals("1XXX", new BerlinClock("06:00:00").getRow(ROW3_HOURS));
        assertEquals("XXXX", new BerlinClock("10:00:00").getRow(ROW3_HOURS));
        assertEquals("1XXX", new BerlinClock("11:00:00").getRow(ROW3_HOURS));
        assertEquals("11XX", new BerlinClock("22:00:00").getRow(ROW3_HOURS));
        assertEquals("111X", new BerlinClock("23:00:00").getRow(ROW3_HOURS));
    }

    @Test
    void testRow4_fiveMinuteBlocksTest() {
        assertEquals("XXXXXXXXXXX", new BerlinClock("00:00:00").getRow(ROW4_MINUTES));
        assertEquals("XXXXXXXXXXX", new BerlinClock("00:01:00").getRow(ROW4_MINUTES));
        assertEquals("XXXXXXXXXXX", new BerlinClock("00:02:00").getRow(ROW4_MINUTES));
        assertEquals("XXXXXXXXXXX", new BerlinClock("00:03:00").getRow(ROW4_MINUTES));
        assertEquals("XXXXXXXXXXX", new BerlinClock("00:04:00").getRow(ROW4_MINUTES));
        assertEquals("1XXXXXXXXXX", new BerlinClock("00:05:00").getRow(ROW4_MINUTES));
        assertEquals("1XXXXXXXXXX", new BerlinClock("00:06:00").getRow(ROW4_MINUTES));
        assertEquals("1XXXXXXXXXX", new BerlinClock("00:09:00").getRow(ROW4_MINUTES));
        assertEquals("11XXXXXXXXX", new BerlinClock("00:10:00").getRow(ROW4_MINUTES));
        assertEquals("11XXXXXXXXX", new BerlinClock("00:11:00").getRow(ROW4_MINUTES));
        assertEquals("11XXXXXXXXX", new BerlinClock("00:14:00").getRow(ROW4_MINUTES));
        assertEquals("111XXXXXXXX", new BerlinClock("00:15:00").getRow(ROW4_MINUTES));
        assertEquals("111XXXXXXXX", new BerlinClock("00:16:00").getRow(ROW4_MINUTES));
        assertEquals("1111111111X", new BerlinClock("00:54:00").getRow(ROW4_MINUTES));
        assertEquals("11111111111", new BerlinClock("00:55:00").getRow(ROW4_MINUTES));
        assertEquals("11111111111", new BerlinClock("00:56:00").getRow(ROW4_MINUTES));
        assertEquals("11111111111", new BerlinClock("00:59:00").getRow(ROW4_MINUTES));
    }

    @Test
    void testRow5_singleMinuteBlocksTest() {
        assertEquals("1XXX", new BerlinClock("00:01:00").getRow(ROW5_MINUTES));
        assertEquals("11XX", new BerlinClock("00:02:00").getRow(ROW5_MINUTES));
        assertEquals("111X", new BerlinClock("00:03:00").getRow(ROW5_MINUTES));
        assertEquals("1111", new BerlinClock("00:04:00").getRow(ROW5_MINUTES));
        assertEquals("XXXX", new BerlinClock("00:05:00").getRow(ROW5_MINUTES));
        assertEquals("XXXX", new BerlinClock("00:15:00").getRow(ROW5_MINUTES));
        assertEquals("1XXX", new BerlinClock("00:16:00").getRow(ROW5_MINUTES));
        assertEquals("1111", new BerlinClock("00:19:00").getRow(ROW5_MINUTES));
        assertEquals("XXXX", new BerlinClock("00:20:00").getRow(ROW5_MINUTES));
        assertEquals("1XXX", new BerlinClock("00:21:00").getRow(ROW5_MINUTES));
    }

    @Test
    void testColoredClockFace() {
        assertEquals("Y", new BerlinClockFace("00:00:00").getColoredRow(ROW1_SECONDS));
        assertEquals("X", new BerlinClockFace("00:00:01").getColoredRow(ROW1_SECONDS));

        assertEquals("XXXX", new BerlinClockFace("00:00:00").getColoredRow(ROW2_HOURS));
        assertEquals("XXXX", new BerlinClockFace("00:00:00").getColoredRow(ROW3_HOURS));

        assertEquals("XXXX", new BerlinClockFace("01:00:00").getColoredRow(ROW2_HOURS));
        assertEquals("RXXX", new BerlinClockFace("01:00:00").getColoredRow(ROW3_HOURS));

        assertEquals("XXXX", new BerlinClockFace("04:00:00").getColoredRow(ROW2_HOURS));
        assertEquals("RRRR", new BerlinClockFace("04:00:00").getColoredRow(ROW3_HOURS));

        assertEquals("RXXX", new BerlinClockFace("05:00:00").getColoredRow(ROW2_HOURS));
        assertEquals("XXXX", new BerlinClockFace("05:00:00").getColoredRow(ROW3_HOURS));

        assertEquals("RXXX", new BerlinClockFace("06:00:00").getColoredRow(ROW2_HOURS));
        assertEquals("RXXX", new BerlinClockFace("06:00:00").getColoredRow(ROW3_HOURS));

        assertEquals("XXXXXXXXXXX", new BerlinClockFace("00:00:00").getColoredRow(ROW4_MINUTES));
        assertEquals("XXXX", new BerlinClockFace("00:00:00").getColoredRow(ROW5_MINUTES));

        assertEquals("XXXXXXXXXXX", new BerlinClockFace("00:01:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YXXX", new BerlinClockFace("00:01:00").getColoredRow(ROW5_MINUTES));

        assertEquals("XXXXXXXXXXX", new BerlinClockFace("00:04:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YYYY", new BerlinClockFace("00:04:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YXXXXXXXXXX", new BerlinClockFace("00:05:00").getColoredRow(ROW4_MINUTES));
        assertEquals("XXXX", new BerlinClockFace("00:05:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YXXXXXXXXXX", new BerlinClockFace("00:06:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YXXX", new BerlinClockFace("00:06:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYXXXXXXXXX", new BerlinClockFace("00:14:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YYYY", new BerlinClockFace("00:14:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYRXXXXXXXX", new BerlinClockFace("00:15:00").getColoredRow(ROW4_MINUTES));
        assertEquals("XXXX", new BerlinClockFace("00:15:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYRXXXXXXXX", new BerlinClockFace("00:16:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YXXX", new BerlinClockFace("00:16:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYRYYXXXXXX", new BerlinClockFace("00:29:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YYYY", new BerlinClockFace("00:29:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYRYYRXXXXX", new BerlinClockFace("00:30:00").getColoredRow(ROW4_MINUTES));
        assertEquals("XXXX", new BerlinClockFace("00:30:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYRYYRXXXXX", new BerlinClockFace("00:31:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YXXX", new BerlinClockFace("00:31:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYRYYRYYRYX", new BerlinClockFace("00:54:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YYYY", new BerlinClockFace("00:54:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYRYYRYYRYY", new BerlinClockFace("00:55:00").getColoredRow(ROW4_MINUTES));
        assertEquals("XXXX", new BerlinClockFace("00:55:00").getColoredRow(ROW5_MINUTES));

        assertEquals("YYRYYRYYRYY", new BerlinClockFace("00:59:00").getColoredRow(ROW4_MINUTES));
        assertEquals("YYYY", new BerlinClockFace("00:59:00").getColoredRow(ROW5_MINUTES));
    }

    @Test
    void testFullColoredClockFace() {

        assertEquals("X", new BerlinClockFace("23:59:59").getColoredRow(ROW1_SECONDS));
        assertEquals("RRRR", new BerlinClockFace("23:59:59").getColoredRow(ROW2_HOURS));
        assertEquals("RRRX", new BerlinClockFace("23:59:59").getColoredRow(ROW3_HOURS));
        assertEquals("YYRYYRYYRYY", new BerlinClockFace("23:59:59").getColoredRow(ROW4_MINUTES));
        assertEquals("YYYY", new BerlinClockFace("23:59:59").getColoredRow(ROW5_MINUTES));

        assertEquals("Y", new BerlinClockFace("00:00:00").getColoredRow(ROW1_SECONDS));
        assertEquals("XXXX", new BerlinClockFace("00:00:00").getColoredRow(ROW2_HOURS));
        assertEquals("XXXX", new BerlinClockFace("00:00:00").getColoredRow(ROW3_HOURS));
        assertEquals("XXXXXXXXXXX", new BerlinClockFace("00:00:00").getColoredRow(ROW4_MINUTES));
        assertEquals("XXXX", new BerlinClockFace("00:00:00").getColoredRow(ROW5_MINUTES));
    }
}