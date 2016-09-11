package nl.parkhaven.entity;

public class Appointment {

	private int day;
	private int time;
	private int machinenummer;
	private int huisnummer;
	private final int default_vaule = 0;

	public int calculateRowNumber() {
		return day + time;
	}

	public void setDay(String day) {
		if (day != null && day.trim().equals(""))
			this.day = Integer.parseInt(day);
		else
			this.day = default_vaule;
	}

	public void setTime(String time) {
		if (time != null && !time.trim().equals(""))
			this.time = Integer.parseInt(time);
		else
			this.time = default_vaule;
	}

	public int getMachinenummer() {
		return machinenummer;
	}

	public void setMachinenummer(String machinenummer) {
		if (machinenummer != null && !machinenummer.trim().equals(""))
			this.machinenummer = Integer.parseInt(machinenummer);
		else
			this.machinenummer = default_vaule;
	}

	public int getHuisnummer() {
		return huisnummer;
	}

	public void setHuisnummer(String huisnummer) {
		if (huisnummer != null && !huisnummer.trim().equals(""))
			this.huisnummer = Integer.parseInt(huisnummer);
		else
			this.huisnummer = default_vaule;
	}
}
