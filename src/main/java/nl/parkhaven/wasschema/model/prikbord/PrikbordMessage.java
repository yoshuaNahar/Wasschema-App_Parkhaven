package nl.parkhaven.wasschema.model.prikbord;

public class PrikbordMessage {

	private int id;
	private int gebruikerId;
	private String gebruikerMail;
	private String titleInput;
	private String bodyInput;
	private String titleOutput;
	private String bodyOutput;

	public int getId() {
		return id;
	}

	public void setId(String id) {
		try {
			this.id = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGebruikerId() {
		return gebruikerId;
	}

	public void setGebruikerId(String gebruikerId) {
		this.gebruikerId = Integer.parseInt(gebruikerId);
	}

	public void setGebruikerId(int gebruikerId) {
		this.gebruikerId = gebruikerId;
	}

	public String getGebruikerMail() {
		return gebruikerMail;
	}

	public void setGebruikerMail(String gebruikerMail) {
		this.gebruikerMail = gebruikerMail;
	}

	public String getTitleInput() {
		return titleInput;
	}

	public void setTitleInput(String titleInput) {
		this.titleInput = titleInput;
	}

	public String getBodyInput() {
		return bodyInput;
	}

	public void setBodyInput(String bodyInput) {
		this.bodyInput = bodyInput;
	}

	public String getTitleOutput() {
		return titleOutput;
	}

	public void setTitleOutput(String titleOutput) {
		this.titleOutput = titleOutput;
	}

	public String getBodyOutput() {
		return bodyOutput;
	}

	public void setBodyOutput(String bodyOutput) {
		this.bodyOutput = bodyOutput;
	}
}
