package nl.parkhaven.entity;

public class User {

	private String firstname;
	private String lastname;
	private String huisnummer;
	private String email;
	private String password;
	private String mobielnummer; // Not implemented yet

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHuisnummer() {
		return huisnummer;
	}
	public void setHuisnummer(String huisnummer) {
		this.huisnummer = huisnummer;
	}
	public String getMobielnummer() {
		return mobielnummer;
	}
	public void setMobielnummer(String mobielnummer) {
		this.mobielnummer = mobielnummer;
	}
}
