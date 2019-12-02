package programmingTeam.sources;


public class AddReverseNumbersShorter {
   public static void main(String[] args) {
      new java.io.BufferedReader(new java.io.InputStreamReader(System.in))
       .lines().filter(s -> s.split(" ").length > 1)
       .map(s -> {for(char i : s.toCharArray()) s = i + s; return s.substring(0, s.length()/2).split(" ");})
       .map(s -> new java.math.BigInteger(s[0]).add(new java.math.BigInteger(s[1])).toString())
       .forEach(s -> {for(char i : s.toCharArray()) s = i + s; System.out.println(s.substring(0, s.length()/2).replaceFirst("0*", ""));});
   }
}