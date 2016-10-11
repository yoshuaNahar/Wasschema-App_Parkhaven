package nl.parkhaven.mysql;

import java.util.Date;

import org.joda.time.LocalDate;

class WeekDayTable {

	private final String weekArray[] = createWekenArray();

	public static void main(String[] args) {
		new WeekDayTable().createQuery();
	}

	private void createQuery() {

		for (int i = 0; i < weekArray.length; i++) {
			System.out.println("INSERT INTO week_dag (`week_dag`) VALUES ('" + weekArray[i] + "');");

		}
	}

	private String[] createWekenArray() {
		// Today date + 23 days Today was 02-10-16.
		// The plus 23 Days was because the array should start on 25-10-16.
		Date today = new Date();
		LocalDate localDate = new LocalDate(today);
		String[] wekenArray = new String[21];

		for (int i = 0; i < 21; i++) {
			wekenArray[i] = localDate.plusDays(23 + i).toString();
		}

		return wekenArray;
	}
}
