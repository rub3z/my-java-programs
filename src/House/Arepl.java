//CECS 277 Project 4: 
// Mystery House
//By Ruben Baerga ID #010366978


package House;

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
