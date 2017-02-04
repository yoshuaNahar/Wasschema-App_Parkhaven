package nl.parkhaven.wasschema.component.appointment;

public class Appointment {

	private int day;
	private int time;
	private int washingMachine;
	private int userId = -1;
	private static final int DEFAULT_VALUE = 0;
	private static final int DAY_MULTIPLIER = 13;

	public int week_day_time_id() {
		if (day == DEFAULT_VALUE || time < DEFAULT_VALUE) {
			return DEFAULT_VALUE;
		}

		return ((day - 1) * DAY_MULTIPLIER) + time + 1;
	}

	public void setDay(String day) {
		try {
			this.day = Integer.parseInt(day);
		} catch (NumberFormatException e) {
			this.day = DEFAULT_VALUE;
		}
	}

	public void setTime(String time) {
		try {
			this.time = Integer.parseInt(time);
		} catch (NumberFormatException e) {
			this.time = DEFAULT_VALUE;
		}
	}

	public int getWashingMachine() {
		return washingMachine;
	}

	public void setWashingMachine(String washingMachine) {
		try {
			this.washingMachine = Integer.parseInt(washingMachine);
		} catch (NumberFormatException e) {
			this.washingMachine = DEFAULT_VALUE;
		}
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		try {
			this.userId = Integer.parseInt(userId);
		} catch (NumberFormatException e) {
			this.userId = DEFAULT_VALUE;
		}
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
