//package programmingTeam.sources;
//
//import java.io.*;
//import java.lang.annotation.Documented;
//import java.net.URL;
//import java.util.ArrayList;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//public class GetPotDispensaries {
//   public static void main(String[] args) throws IOException {
//      URL weedHTML = new URL("https://potguide.com/florida/marijuana-dispensaries/");
//      BufferedReader in = new BufferedReader(
//       new InputStreamReader(weedHTML.openStream()));
//      ArrayList<String> dispensaryInfo = new ArrayList<>();
//
//      in.lines()
//       .filter(line -> line.matches(".*value\\=\"View.*"))
//       .map(line -> line.replaceAll("value\\=\\\"((?!View).)*", ""))
//       .map(line -> line.replaceAll("View", ""))
//       .map(line -> line.replaceAll("Dispensary\\\"\\/\\>", ""))
//       .map(line -> line.replaceAll("Dispensaries\\\"\\/\\>", ""))
//       .map(line -> line.trim())
//       .map(line -> line.replaceFirst("\\s", ","))
//       .map(line -> line.split(",")[1] + "," + line.split(",")[0])
//       .map(line -> line.trim())
//       .map(line -> line + "\n")
//       //.filter(line -> line.matches(".*/florida/marijuana-dispensaries/.*/.*"))
//       //.map(line -> line.replaceAll("\\\"((?!\\/florida\\/marijuana-dispensaries\\/.*\\/\\\").)*", ""))
//       //.filter(line -> line.contains("florida"))
//       //.map(line -> line.substring(line.indexOf("/")))
//       .distinct()
//       .forEach(dispensaryInfo::add);
//
//      dispensaryInfo.forEach(System.out::println);
//      Writer out = new BufferedWriter(new OutputStreamWriter(
//       new FileOutputStream("dispensaries.csv")));
//
//      out.write("City, Dispensaries\n");
//      for (String line : dispensaryInfo) {
//         out.write(line);
//      }
//      out.close();
//
////      Document potPage = Jsoup.connect("https://potguide.com/florida/marijuana-dispensaries/").get();
////      Elements es = potPage.getElementsMatchingText(".*View.*");
////
////      System.out.println(es.text());
//      //potPage.text();
////      potPage.outputSettings().prettyPrint(true).indentAmount(2);
////      System.out.println(potPage.text());
////      for (String s : weedLinks) {
////         System.out.println(s);
////      }
//
//
////      HttpGet request = null;
////
////      try {
////
////         String url = "https://potguide.com/florida/marijuana-dispensaries/";
////         HttpClient client = HttpClientBuilder.create().build();
////         request = new HttpGet(url);
////
////         request.addHeader("User-Agent", "Apache HTTPClient");
////         HttpResponse response = client.execute(request);
////
////         HttpEntity entity = response.getEntity();
////
////         String content = EntityUtils.toString(entity);
////
////         System.out.println(content);
////
////      } finally {
////
////         if (request != null) {
////
////            request.releaseConnection();
////         }
////      }
//      in.close();
//   }
//
//}
//
//
