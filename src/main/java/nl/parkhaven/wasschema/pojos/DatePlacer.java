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
		LocalDate todayDate;

		if (isCurrentWeek == 0) {
			todayDate = new LocalDate(today);
		} else {
			todayDate = new LocalDate(today).plusDays(7);
		}

		int dayOfWeek = todayDate.getDayOfWeek();
		int weekOfYear = todayDate.getWeekOfWeekyear();
		int monthOfYear = todayDate.getMonthOfYear();

		LocalDate firstDayOfWeekx = todayDate.minusDays(dayOfWeek - 1);
		int firstDayOfWeek = firstDayOfWeekx.getDayOfMonth();

		LocalDate lastDayOfWeekx = new LocalDate(firstDayOfWeekx).plusDays(7);
		int lastDayMonthOfYear = lastDayOfWeekx.getMonthOfYear();
		int lastDayOfWeek = lastDayOfWeekx.getDayOfMonth();

		overview = weekOfYear + " / " + String.format("%02d", firstDayOfWeek) + "-" + String.format("%02d", monthOfYear)
				+ " Till " + String.format("%02d", lastDayOfWeek) + "-" + String.format("%02d", lastDayMonthOfYear);
	}

	public String getOverviewDate() {
		return overview;
	}

}
