package nl.parkhaven.wasschema.modules.schema;

// implement this when adding disable-laundry-machine feature
public class LaundryMachine {

  private int id;
  private String name;
  private boolean active;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

}
