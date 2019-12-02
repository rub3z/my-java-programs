package littleFibonacci;

/**
 *
 * @author rubes
 */
public class weeFib {
   public static int fibonacci (int n) {
      return fibonacci(n, 0, 1, 0);
   }
   
   private static int fibonacci(int n, int i, int a, int b) {
      if (i == n) return a;
      return fibonacci(n, i + 1, a + b, a);
   }
   
   public static void main(String[] args) {
      System.out.println(fibonacci(2));
   }

   
}
