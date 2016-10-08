package nl.parkhaven.mysql;

public class WeekDayTimeTable {

	private final int _3weken = 21;
	private final int _tijdAantal = 13;

	public static void main(String[] args) {
		new WeekDayTimeTable().createQuery();
	}

	private void createQuery() {

		for (int i = 1; i <= _3weken; i++) {
			for (int j = 1; j <= _tijdAantal; j++) {
				System.out
						.println("INSERT INTO week_dag_tijd (`week_dag_id`, `tijd_id`) VALUES (" + i + ", " + j + ");");
			}
		}
	}
}
