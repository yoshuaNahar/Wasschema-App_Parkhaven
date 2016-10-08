package nl.parkhaven.model.entity;

public class Appointment {

	private int day;
	private int time;
	private String machinenummer;
	private int huisnummer;
	private final int default_vaule = 0;

	public int calculateRowNumber() {
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

	public String getMachinenummer() {
		return machinenummer;
	}

	public void setMachinenummer(String machinenummer) {
		this.machinenummer = machinenummer;
	}

	public int getHuisnummer() {
		return huisnummer;
	}

	public void setHuisnummer(String huisnummer) {
		if (huisnummer != null && !huisnummer.trim().equals("")) {
			this.huisnummer = Integer.parseInt(huisnummer);
		} else {
			this.huisnummer = default_vaule;
		}
	}
}
