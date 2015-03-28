
package ro.space.read.parsers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Parser {

  private String filesLocation;

  protected Parser(String filesLocation) {
    this.filesLocation = filesLocation;
  }

  protected ArrayList<String> readLines(String fileName) {

    ArrayList<String> lines = new ArrayList<>();

    String dataSource = filesLocation + fileName;

    try {
      FileInputStream dataStream = new FileInputStream(dataSource);

      InputStreamReader streamReader = new InputStreamReader(dataStream);

      BufferedReader bufferedReader = new BufferedReader(streamReader);

      String line = bufferedReader.readLine();

      while (line != null) {
        lines.add(line);
        line = bufferedReader.readLine();
      }

    } catch (Exception e) {
      System.out.println(e.toString());
    }
    
    return lines;
  }
}