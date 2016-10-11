package nl.parkhaven.model.entity;

public class Appointment {

	private int day;
	private int time;
	private String wasmachine;
	private int gebruiker_id;
	private final int default_vaule = 0;

	public int week_dag_tijd_id() {
		return day + time;
	}

	public void setDay(String day) {
		if (day != null && !day.trim().equals("")) {
			this.day = Integer.parseInt(day);
		} else {
			this.day = default_vaule;
		}
	}

	public void setTime(String time) {
		if (time != null && !time.trim().equals("")) {
			this.time = Integer.parseInt(time);
		} else {
			this.time = default_vaule;
		}
	}

	public String getWasmachine() {
		return wasmachine;
	}

	public void setWasmachine(String machinenummer) {
		this.wasmachine = machinenummer;
	}

	public int getGebruiker_id() {
		return gebruiker_id;
	}

	public void setGebruiker_id(String gebruiker_id) {
		if (gebruiker_id != null && !gebruiker_id.trim().equals("")) {
			this.gebruiker_id = Integer.parseInt(gebruiker_id);
		} else {
			this.gebruiker_id = default_vaule;
		}
	}

	public void setGebruiker_id(int gebruiker_id) {
		this.gebruiker_id = gebruiker_id;
	}
}
