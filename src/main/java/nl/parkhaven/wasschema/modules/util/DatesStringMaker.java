package nl.parkhaven.wasschema.modules.util;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class DatesStringMaker {

  private static String[] weekRange = new String[2];
  private static int[] weekNumber = new int[2];
  private static Map<Long, String> dates = new HashMap<>();

  public DatesStringMaker() {
    setWeekRangeAndWeekNumber();
    setDates();
  }

  public String[] getWeekRange() {
    return weekRange;
  }

  public int[] getWeekNumber() {
    return weekNumber;
  }

  public Map<Long, String> getDates() {
    return dates;
  }

  private void setWeekRangeAndWeekNumber() {
    for (int i = 0; i < 14; i += 7) {
      LocalDate date = LocalDate.now().plusDays(i);

      int dayOfTheWeek = date.getDayOfWeek().getValue();

      LocalDate firstDayOfTheWeek = date.minusDays(dayOfTheWeek - 1);
      LocalDate lastDayOfTheWeek = firstDayOfTheWeek.plusDays(7);

      int weekOfTheYear = date.get(WeekFields.of(Locale.UK).weekOfWeekBasedYear());

      int firstDay = firstDayOfTheWeek.getDayOfMonth();
      int firstDay_MonthOfTheYear = firstDayOfTheWeek.getMonthValue();

      int lastDay = lastDayOfTheWeek.getDayOfMonth();
      int lastDay_MonthOfTheYear = lastDayOfTheWeek.getMonthValue();

      weekRange[i / 7] = String.format("%02d", firstDay) + "-"
          + String.format("%02d", firstDay_MonthOfTheYear) + " Untill " + String
          .format("%02d", lastDay) + "-"
          + String.format("%02d", lastDay_MonthOfTheYear);

      weekNumber[i / 7] = weekOfTheYear;
    }
  }

  private void setDates() {
    LocalDate date;
    LocalDate firstDayOfTheWeek;
    for (int i = 0; i < 14; i = i + 7) {
      date = LocalDate.now().plusDays(i);

      int dayOfTheWeek = date.getDayOfWeek().getValue();

      firstDayOfTheWeek = date.minusDays(dayOfTheWeek - 1);

      String[] daysOfTheWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
      String[] monthOfTheYear = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
          "Oct", "Nov",
          "Dec"};

      for (int j = 0; j < 7; j++) {
        dates.put((long) j + i + 1, daysOfTheWeek[j] + " - " + firstDayOfTheWeek.plusDays(j)
            .getDayOfMonth() + " " + monthOfTheYear[firstDayOfTheWeek.getMonthValue() - 1]);
      }
    }
  }

}
