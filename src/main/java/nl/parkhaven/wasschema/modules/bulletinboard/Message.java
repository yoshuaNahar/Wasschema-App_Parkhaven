package nl.parkhaven.wasschema.modules.bulletinboard;

public class Message {

    private int id;
    private int userId;
    private String userEmail;
    private String titleInput;
    private String bodyInput;
    private String titleOutput;
    private String bodyOutput;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
