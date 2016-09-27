package nl.parkhaven.pojos;

import java.util.Date;

import org.joda.time.LocalDate;

public class DatePlacer {

	private Date today = new Date();
	private LocalDate todayDate = new LocalDate(today);
	private LocalDate plus7DaysDate = new LocalDate(today).plusDays(6);

	private int dayOfTheWeek = todayDate.getDayOfWeek();
	private int weekOfYear = todayDate.getWeekOfWeekyear();
	private int monthOfYear = todayDate.getMonthOfYear();
	private int dayOfMonth = todayDate.getDayOfMonth();

	private int monthOfYear_Plus7 = plus7DaysDate.getMonthOfYear();
	private int dayOfMonth_Plus7 = plus7DaysDate.getDayOfMonth();

	private String[] daysPresent = new String[7];
	private String Overview;


	public DatePlacer() {
		handleDaysName();
		Overview = handleDate();
	}


	private String handleDate() {
		return weekOfYear + " / " + String.format("%02d", dayOfMonth) + "-"
				+ String.format("%02d", monthOfYear) +
				" Till " + String.format("%02d", dayOfMonth_Plus7) + "-" +
				String.format("%02d", monthOfYear_Plus7);
	}

	private void handleDaysName() {
		final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

		dayOfTheWeek--;
		for(int i = 0; (dayOfTheWeek + i) < DAYS.length; i++) {
			daysPresent[i] = DAYS[dayOfTheWeek + i];
		
			if((dayOfTheWeek + i) == 6) {
				for(int ij = 0; ij < dayOfTheWeek; ij++) {
					daysPresent[ij + i + 1] = DAYS[ij];
				}
			}
		}
	}

	public String[] getDaysPresent() {
		return daysPresent;
	}
	public String getOverviewDate() {
		return Overview;
	}
}
