package chp02;

import java.io.File;
import java.io.IOException;

public class Chp02aMain {

  public static void main(String[] args) throws IOException {
    File file = new File("build/conf/cheese.txt");
    try {
      file.canWrite();
      System.out.println("We can write to cheese.txt");
    } catch (SecurityException e) {
      System.out.println("We can NOT write to cheese.txt");
    }
  }
}