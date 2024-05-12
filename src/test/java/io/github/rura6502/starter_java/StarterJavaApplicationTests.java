package io.github.rura6502.starter_java;

import org.junit.jupiter.api.Test;

class StarterJavaApplicationTests {

  public static void log(String message, Object... objects) {
    var filledMessage = String.format(message, objects);

  }

  @Test
  void contextLoads() {
  }

}
