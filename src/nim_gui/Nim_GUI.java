package nim_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Ruben Baerga
 * Nim_GUI
 * 
 * Citation: This project is heavily adapted from Chuck Siska's 
 * own Press_me.java file he generously provided. It's a (mostly failed)
 * attempt at reverse-engineering and heavily modifying the 
 * Press_me.java class to turn it into a Nim game.
 * 
 * @author Rub3z
 */
public class Nim_GUI extends JFrame implements ActionListener {
   // ---------------------------------------------------- TOC ----
   //   Consts
   //   Slots
   //  - class Pebble inner
   //  -- CTOR Nim_GUI( ) public
   //  actionPerformed( ActionEvent rev ) public void  // Callback.
   //  addPanel( JComponent rpart ) void  // Add widget in its own panel.
   //  doClick( ActionEvent rev ) void  // Handle a click.
   //  pressButton( ) void  // Say hello nicely.
   //  pressPebble( JButton bx ) void  // Announce the button index.
   //  setupPebbleRow( int rcnt ) void // Setup a row of btns titled w index.
   //  setupLabels( Font rfont ) void  // Fontify the labels.
   //  setupWidgets( ) void  // Setup widgets via BoxLayout + btn_row.
   //  -- main static
   // ---------------------------------------------------- Consts ----
   final int NORMAL_FONT_SIZE = 16;
   final int TEXTBOX_SIZE = 20; // Textbos.
   final int PEBBLE_SIZE = 80;
   final int WINDOW_X_POS = 50;  // Custom Window.
   final int WINDOW_Y_POS = 100;
   final int WINDOW_WIDTH = 800;
   final int WINDOW_HEIGHT = 600;
   final String FONT_NAME = "Arial"; 
   final Font PEBBLE_FONT = new Font(FONT_NAME, Font.BOLD, NORMAL_FONT_SIZE);

   // ---------------------------------------------------- Slots ----
   private boolean done;
   private int rowNum;
   private int peblNum;
   private int numTurns;
   private Nim_Brain theBrain;
   private Nim_GUI theBoard;
   
   JPanel mTopPanel = new JPanel(); // Top-level panel in frame.
   JPanel[] mBoard;
   JLabel mWait = new JLabel("I shall move now...");
   JTextArea mGreeting = new JTextArea(""); // Filled on btn press.
   JButton mPressEnter = new JButton("Enter");
   
   public void setup() {
      if (numTurns == 0) {
         mBoard = new JPanel[(int)(Math.random() * 4) + 3];
         for (int i = 0; i < mBoard.length ; i++) {
            mBoard[i] = setupPebbleRow((int)(Math.random() * 6) + 3, i);
            mTopPanel.add(mBoard[i]);
         }
      }
      done = false;
      numTurns++;
   }
   
   // ----------------------------------------------------- Pebble ----
   // This inner class is now just a means of differentiating between
   // button presses and pebble presses.
   class Pebble extends JButton 
   {
      Pebble(String aPebble) {
         super(aPebble);
      }
   }
   
   // ---------------------------------------------------- CTOR ----
   public Nim_GUI() {
      super("NIM!!!");  // Call mom for frame title.
      setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
      setLocation(WINDOW_X_POS, WINDOW_Y_POS);
      add(mTopPanel);
      String sgreet = "Welcome, player.\n"
       + "Let's play... NIM!!!\n"
       + "The rules are as follows: \n"
       + "From any one of the rows shown, you may take 1 or more "
       + "pebbles - even the entire row if you so choose. "
       + "We shall take turns removing pebbles until one player takes the "
       + "last one... that player shall be the victor.\n"
       + "I shall allow you to go first.\n\n"
       + "PREPARE YOURSELF; HUMAN!!!";
      mGreeting.setText(sgreet); // Show user.
      mGreeting.setLineWrap(true);
      mGreeting.setWrapStyleWord(true);
      mGreeting.setEditable(false);
      mGreeting.setFont(new Font(FONT_NAME, Font.BOLD, NORMAL_FONT_SIZE));
      mGreeting.setBackground(new Color(240,240,240));
      
      mTopPanel.add(mGreeting);
      
      setupLabels(new Font(FONT_NAME, Font.BOLD, NORMAL_FONT_SIZE));
      { // Uncomment just one of these setups: each uses a diff layout mgr.
         // setup_widgets_flow( );
         // setup_widgets_grid( );
         setupWidgets();
      }
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mPressEnter.addActionListener(this); // Listen to btn 1.
      //mPressPlay.addActionListener(this); //Listen to btn 2.

   }
   // ---------------------------------------------------- actionPerformed ----
   // 
   @Override
   public void actionPerformed(ActionEvent rev) {
      doClick(rev);
   }
   // ------------------------------------------------ addPanel ----
   // Add widget in its own panel.

   void addPanel(JComponent rpart) {
      JPanel pnlx = new JPanel();
      mTopPanel.add(pnlx);
      pnlx.add(rpart);
   }
   
   // ------------------------------------------------ doClick ----
   // Handle a click.
   void doClick(ActionEvent rev) {
      // Get a button-type object from the event.
      JButton bx;
      Object ox = rev.getSource(); // Get event source.
      if (!(ox instanceof JButton)) {
         return; // Not our kind?  Punt!
      }
      bx = (JButton) ox; // Downcast to button type.
      // Dispatch to source button's doit md.
      if (bx.getText().equals("Enter")) {
         pressButton();
      } else {
         pressPebble(bx);
      }
   }
   
   // ---------------------------------------------------- pressButton ----
   void pressButton() {
      
      
      mPressEnter.setVisible(false);
      mGreeting.setVisible(false);
      mTopPanel.remove(mGreeting);
      mTopPanel.remove(mPressEnter);
      
      setup();
   }
   // ---------------------------------------------------- pressPebble ----
   // Announce the button index.

   void pressPebble(JButton bx) {
      if (!(bx instanceof Pebble)) {
         return; // Not our kind?  Punt!
      }
      Pebble zbx = (Pebble) bx; // Downcast to button type.
      
      int i = zbx.getParent().getComponentCount() - 1;
      
      while( i > zbx.getParent().getComponentZOrder(zbx)) {
         zbx.getParent().getComponent(i).setVisible(false);
         zbx.getParent().remove(i);
         i--;
      }

      zbx.setVisible(false);
      if(zbx.getParent().getComponentCount() == 2) {
         zbx.getParent().setVisible(false);
         mTopPanel.remove(zbx.getParent());
         System.out.println(mTopPanel.getComponentCount());
      }
      else
         zbx.getParent().remove(zbx);
      
      theBrain.makeAMove(this);
   }
   
   void PressPebble(int peblSelection) {
      
   }
   // ------------------------------------------------ setupPebbleRow ----
   // Setup a row of buttons titled w their index.
   // This took A LOT of work.
   // It includes creating buttons without borders with giant bulletpoints
   // to represent the pebbles.
   JPanel setupPebbleRow(int peblCount, int rowNum) {
      JPanel peblRow = new JPanel();
      FlowLayout thisRow = new FlowLayout(FlowLayout.LEFT);
      thisRow.setHgap(0);
      peblRow.setLayout(thisRow);
      JLabel rowLabel = new JLabel("Row " + (rowNum + 1));
      rowLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
      rowLabel.setFont(PEBBLE_FONT);
      peblRow.add(rowLabel);
      
      
      for (int ix = 0; ix < peblCount; ++ix) {
         Pebble bx = new Pebble("\u25CF");
         peblRow.setMinimumSize(new Dimension(bx.getWidth()*ix, 100));
         // I found 100 to  be the minimum size without squishing the pebbles.
         peblRow.add(bx);
         System.out.println(peblRow.getComponentCount());
         bx.setFont(new Font(FONT_NAME, Font.BOLD, PEBBLE_SIZE));
         bx.setFocusPainted(false);
         bx.setContentAreaFilled(false);
         bx.setBorderPainted(false);
         bx.addActionListener(this); 
         // Listen to the pebbles.
         // Lean in close enough, you can here them speak.
      }
      
      return peblRow;
   }
   // ------------------------------------------------ setupLabels ----
   // Fontify the labels.

   void setupLabels(Font rfont) {
      mGreeting.setFont(rfont);
   }
   // ------------------------------------------------- setup_widgets_flow ----
   // Setup widgets via FlowLayout.

   void setup_widgets_flow() {
      LayoutManager layx = new FlowLayout(FlowLayout.LEFT); // Auto-wrap row of widgets.
      mTopPanel.setLayout(layx);

      mTopPanel.add(mGreeting);
      mTopPanel.add(mPressEnter);
   }  
   // ------------------------------------------------ setup_widgets_grid ----
   // Setup widgets via GridLayout.

   void setup_widgets_grid() {
      LayoutManager layx = new GridLayout(3, 2); // rows,columns layout.
      mTopPanel.setLayout(layx);
      

      // Grid layx adds parts, each into cell, filling one row from the left.
      mTopPanel.add(mGreeting);
      mTopPanel.add(mPressEnter);
   }
   // ------------------------------------------------ setupWidgets ----
   // Setup widgets via BoxLayout + btn_row.

   void setupWidgets() {
      LayoutManager layx = new BoxLayout(mTopPanel, BoxLayout.Y_AXIS);
      mTopPanel.setLayout(layx);
      
      
      mTopPanel.add(mPressEnter);
      mTopPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Blank box.
//      
//      addPanel(mGreeting);
//      //addPanel(mPressPlay);
//      JPanel pnl_row_bot = new JPanel();
//      mTopPanel.add(pnl_row_bot);
//      pnl_row_bot.add(mPressEnter);
      }
   // ------------------------------------------------------- to_capital ----
   // Capitalize the string start.

   String to_capital(String rstr) {
      if (null == rstr) {
         return rstr; // No string.
      }
      if (rstr.equals("")) {
         return rstr; // Empty string.
      }
      Character c0 = Character.toUpperCase(rstr.charAt(0)); // Cap 1st letter.
      return c0 + rstr.substring(1, rstr.length());
   }
   
   
   public static void main(String[] args) {
      Nim_GUI nGUI = new Nim_GUI(); // Get new custom window.
      nGUI.setVisible(true); // Show window to user.
   }
   
   
   public class Nim_Brain {

      private int brainRow;
      private int brainPebl;

      public void makeAMove(Nim_GUI gameState) {
         int numRows = mBoard.length;
         int choose = 1337;
         while (brainRow != choose) {
            choose = (int) (Math.random() * numRows);
            if (mBoard[choose] != null) {
               brainRow = choose;
            }
         }

         brainPebl
          = (int) (Math.random() * mBoard[brainRow].getComponentCount()) + 1;

         int i = mBoard[brainRow].getComponentCount();

         while (i > brainPebl) {
            mBoard[brainRow].getComponent(i).setVisible(false);
            mBoard[brainRow].remove(i);
            i--;
         }

         mBoard[brainRow].getComponent(brainPebl).setVisible(false);
         if (mBoard[brainRow].getComponentCount() == 2) {
            mBoard[brainRow].setVisible(false);
            mTopPanel.remove(mBoard[brainRow]);
            System.out.println(mTopPanel.getComponentCount());
         } else {
            gameState.mBoard[brainRow].remove(brainPebl);
         }

      }
   }

}
