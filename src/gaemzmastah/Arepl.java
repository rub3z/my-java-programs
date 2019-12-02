package gaemzmastah;

/**
 *
 * @author Rub3z
 */
public abstract class Arepl implements IRepl {

   @Override
   public void repl() {
      setup();
      hello();
      do {
         listen();
         respond();   
      } while (endChk());
   }
}
