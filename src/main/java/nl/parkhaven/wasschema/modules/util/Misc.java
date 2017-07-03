package nl.parkhaven.wasschema.modules.util;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public final class Misc {

  public static final LocalTime[] TIMES_ARRAY = {LocalTime.of(5, 30, 0), LocalTime.of(7, 0, 0),
      LocalTime.of(8, 30, 0),
      LocalTime.of(10, 0, 0), LocalTime.of(11, 30, 0), LocalTime.of(13, 0, 0),
      LocalTime.of(14, 30, 0),
      LocalTime.of(16, 0, 0), LocalTime.of(17, 30, 0), LocalTime.of(19, 0, 0),
      LocalTime.of(20, 30, 0),
      LocalTime.of(22, 0, 0), LocalTime.of(23, 30, 0)};

  private Misc() {
  }

  public static String generateNewPassword() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&()=+[{]}<>/?";
    StringBuilder password = new StringBuilder();

    for (int i = 0; i < 9; i++) {
      int randomNum = ThreadLocalRandom.current().nextInt(0, 79 + 1);
      String c = Character.toString(characters.charAt(randomNum));
      password.append(c);
    }

    return password.toString();
  }

}
