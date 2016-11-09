package nl.parkhaven.wasschema.model.appointment;

public class Appointment {

	private int day;
	private int time;
	private int wasmachine;
	private int gebruiker_id;
	private static final int DEFAULT_VALUE = 0;
	private static final int DAY_MULTIPLIER = 13;

	public int week_dag_tijd_id() {
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

	public int getWasmachine() {
		return wasmachine;
	}

	public void setWasmachine(String wasmachine) {
		try {
			this.wasmachine = Integer.parseInt(wasmachine);
		} catch (NumberFormatException e) {
			this.wasmachine = DEFAULT_VALUE;
		}
	}

	public int getGebruiker_id() {
		return gebruiker_id;
	}

	public void setGebruiker_id(String gebruiker_id) {
		try {
			this.gebruiker_id = Integer.parseInt(gebruiker_id);
		} catch (NumberFormatException e) {
			this.gebruiker_id = DEFAULT_VALUE;
		}
	}

	public void setGebruiker_id(int gebruiker_id) {
		this.gebruiker_id = gebruiker_id;
	}
}
