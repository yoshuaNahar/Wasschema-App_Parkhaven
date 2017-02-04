package nl.parkhaven.wasschema.pojos;

import java.util.Date;

import org.joda.time.LocalDate;

public class DatePlacer {

	private Date today = new Date();

	private String overview;

	public DatePlacer(int isCurrentWeek) {
		handleDate(isCurrentWeek);
	}

	private void handleDate(int isCurrentWeek) {
		LocalDate date;

		if (isCurrentWeek == 0) {
			date = new LocalDate(today);
		} else {
			date = new LocalDate(today).plusDays(7);
		}

		int dayOfTheWeek = date.getDayOfWeek();
		int weekOfTheYear = date.getWeekOfWeekyear();

		LocalDate firstDayOfTheWeek = date.minusDays(dayOfTheWeek - 1);
		int firstDay = firstDayOfTheWeek.getDayOfMonth();
		int firstDay_MonthOfTheYear = firstDayOfTheWeek.getMonthOfYear();

		LocalDate lastDayOfTheWeek = new LocalDate(firstDayOfTheWeek).plusDays(7);
		int lastDay = lastDayOfTheWeek.getDayOfMonth();
		int lastDay_MonthOfTheYear = lastDayOfTheWeek.getMonthOfYear();

		overview = weekOfTheYear + " / " + String.format("%02d", firstDay) + "-"
				+ String.format("%02d", firstDay_MonthOfTheYear) + " Till " + String.format("%02d", lastDay) + "-"
				+ String.format("%02d", lastDay_MonthOfTheYear);
	}

	public String getOverviewDate() {
		return overview;
	}

}
