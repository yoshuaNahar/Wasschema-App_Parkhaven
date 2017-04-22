package nl.parkhaven.wasschema.modules.user;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String houseNumber;
	private String email;
	private String password;
	private boolean admin;
	private String sharedPassword;
	private boolean rememberLaundryRoomChecked;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String FirstName) {
		this.firstName = FirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber.toUpperCase();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getSharedPassword() {
		return sharedPassword;
	}

	public void setSharedPassword(String sharedPassword) {
		this.sharedPassword = sharedPassword;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", houseNumber=" + houseNumber
				+ ", email=" + email + ", password=" + password + ", admin=" + admin + ", sharedPassword="
				+ sharedPassword + "]";
	}


	public boolean isRememberLaundryRoomChecked() {
		return rememberLaundryRoomChecked;
	}

	public void setRememberLaundryRoomChecked(boolean rememberLaundryRoomChecked) {
		this.rememberLaundryRoomChecked = rememberLaundryRoomChecked;
	}
}
