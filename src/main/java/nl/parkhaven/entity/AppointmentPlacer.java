package nl.parkhaven.entity;

public class AppointmentPlacer {

	private static final int DEFAULT_VALUE = 0;

	private int day;
	private int time;
	private int machinenummer;
	private int huisnummer;

	public int calculateRowNumber() {
		return day + time;
	}

	public void setDay(String day) {
		if(day != null && day.trim().equals(""))
			this.day = Integer.parseInt(day);
		else
			this.day = DEFAULT_VALUE;
	}
	public void setTime(String time) {
		if(time != null && !time.trim().equals(""))
			this.time = Integer.parseInt(time);
		else
			this.time = DEFAULT_VALUE;
	}

	public int getMachinenummer() {
		return machinenummer;
	}
	public void setMachinenummer(String machinenummer) {
		if(machinenummer != null && !machinenummer.trim().equals(""))
			this.machinenummer = Integer.parseInt(machinenummer);
		else
			this.machinenummer = DEFAULT_VALUE;
	}

	public int getHuisnummer() {
		return huisnummer;
	}
	public void setHuisnummer(String huisnummer) {
		if(huisnummer != null && !huisnummer.trim().equals(""))
			this.huisnummer = Integer.parseInt(huisnummer);
		else
			this.huisnummer = DEFAULT_VALUE;
	}
}
