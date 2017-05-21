package nl.parkhaven.wasschema.modules.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DatesStringMakerTest {

    private DatesStringMaker datesStringMaker;

    @Before
    public void setup() {
        datesStringMaker = new DatesStringMaker();
    }

    @Test
    public void testGetWeekRangeAndNumber() {
        String[] dates = datesStringMaker.getWeekRange();
        int[] weekNumber = datesStringMaker.getWeekNumber();

        assertThat(dates.length, is(2));
        assertThat(weekNumber.length, is(2));
    }

    @Test
    public void testWashDates() {
        Map<Long, String> dates = datesStringMaker.getDates();

        Assert.assertTrue(dates.size() == 14);
        Assert.assertTrue(dates.get(1L).contains("Mon"));
        Assert.assertTrue(dates.get(6L).contains("Sat"));
    }

}
