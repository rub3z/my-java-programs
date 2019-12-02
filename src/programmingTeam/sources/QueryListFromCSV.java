package programmingTeam.sources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QueryListFromCSV {
   public static void main(String[] args) throws IOException {
      BufferedReader br
       = new BufferedReader(new FileReader("daily_mail_ix.csv"));


      br.lines().forEach(line -> {
         String[] cells = line.split(",");
         ArrayList<String> cellsList = new ArrayList<>();
         for (String s : cells) {
            cellsList.add(s);
         }

         cellsList.stream()
          .filter(c -> c.contains("OR") || c.contains("AND")
                     || c.contains("NOT") || c.contains("\"\"")
                     || c.startsWith("\"\"\"") || c.startsWith("("))
          .map(c -> c.replaceAll("\"\"","\""))
          .map(c -> c.replaceAll("\"\"","\""))
          .map(c -> c.replaceAll("\\s+OR\\s+", " "))
          .map(c -> c.replaceAll("\\s+AND\\s+", " +"))
          .map(c -> c.replaceAll("\\s+NOT\\s+", " -"))
          .map(c -> "title:" + c)
          .forEach(System.out::println);
      });
   }
}
