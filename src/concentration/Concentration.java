package concentration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Rub3z
 */
public class Concentration extends JFrame implements ActionListener {

// Callback style: callback-wrapper i  //   Consts
   //   Slots
   //  - class ZButton inner
   //  -- CTOR Press_me( ) public
   //  actionPerformed( ActionEvent rev ) public void  // Callback.
   //  add_w_panel( JComponent rpart ) void  // Add widget in its own panel.
   //  do_click( ActionEvent rev ) void  // Handle a click.
   //  do_press2( ) void  // Say hello loudly.
   //  do_press_me( ) void  // Say hello nicely.
   //  do_press_int( JButton bx ) void  // Announce the button index.
   //  setup_btn_row( int rcnt ) void // Setup a row of btns titled w index.
   //  setup_labal_fonts( Font rfont ) void  // Fontify the labels.
   //  setup_widgets_flow( ) void  // Setup widgets via FlowLayout.
   //  setup_widgets_border( ) void  // Setup widgets via BorderLayout.
   //  setup_widgets_grid( ) void  // Setup widgets via GridLayout.
   //  setup_widgets_box( ) void  // Setup widgets via BoxLayout + btn_row.
   //  to_capital( String rstr ) String  // Capitalize the string start.
   //  -- main static

   // ---------------------------------------------------- Consts ----
   final int K_FONT_SIZE = 16;
   final int K_PEBL_SIZE = 80;
   final int K_TBOX_SIZE = 20; // Textbos.
   final int K_WIN_X = 100;  // Custom Window.
   final int K_WIN_Y = 100;
   final int K_WIDTH = 1500;
   final int K_HEIGHT = 1500;
   final String K_FONT_NAME = "Arial";
   final String K_PRESS_ME = "Press for nice";
   final String K_PRESS2 = "Press for loud";
   
   // ---------------------------------------------------- Slots ----
   JPanel m_pnl_top = new JPanel(); // Top-level panel in frame.
   JButton m_btn_press_me = new JButton(K_PRESS_ME);
   JButton m_btn_press2 = new JButton(K_PRESS2);
   JLabel m_lbl_greeting = new JLabel(""); // Filled on btn press.
   JLabel m_lbl_hi = new JLabel("Hi!  Enter name & click button.");
   JLabel m_lbl_question = new JLabel("What is your name?");
   JTextField m_tbox_answer = new JTextField(K_TBOX_SIZE);
   
   // ----------------------------------------------------- ZButton ----
   class ZButton extends JButton // Inner class.
   {

      int m_val;

      ZButton(String rix) {
         super("" + rix);

      }
   ;

   }
   
  // ---------------------------------------------------- CTOR ----
  public Concentration() {
      super("Press_me");  // Call mom for frame title.

      setMinimumSize(new Dimension(K_WIDTH, K_HEIGHT));
      setLocation(K_WIN_X, K_WIN_Y);
      add(m_pnl_top);
      setup_labal_fonts(new Font(K_FONT_NAME, Font.BOLD, K_FONT_SIZE));
      { // Uncomment just one of these setups: each uses a diff layout mgr.
         //setup_widgets_flow();
         // setup_widgets_border( );
          setup_widgets_grid( );
         // setup_widgets_box( ); // Includes a button-row.
      }
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      m_btn_press_me.addActionListener(this); // Listen to btn 1.
      m_btn_press2.addActionListener(this); // Listen to btn 2.
   }
   // ---------------------------------------------------- actionPerformed ----
   // Our callback md.

   @Override
   public void actionPerformed(ActionEvent rev) {
      do_click(rev);
   }
   // ------------------------------------------------ add_w_panel ----
   // Add widget in its own panel.

   void add_w_panel(JComponent rpart) {
      JPanel pnlx = new JPanel();
      m_pnl_top.add(pnlx);
      pnlx.add(rpart);
   }
   // ------------------------------------------------ do_click ----
   // Handle a click.

   void do_click(ActionEvent rev) {
      // Get a button-type object from the event.
      JButton bx;
      Object ox = rev.getSource(); // Get event source.
      if (!(ox instanceof JButton)) {
         return; // Not our kind?  Punt!
      }
      bx = (JButton) ox; // Downcast to button type.
      // Dispatch to source button's doit md.
      if (bx.getText().equals(K_PRESS_ME)) {
         do_press_me();
      } else if (bx.getText().equals(K_PRESS2)) {
         do_press2();
      } else {
         do_press_int(bx);
      }
   }
   // ---------------------------------------------------- do_press2 ----
   // Say hello loudly.

   void do_press2() {
      String sbox = m_tbox_answer.getText(); // Get name.
      if (sbox.equals("")) {
         sbox = "unknown"; // Fix no name.
      }
      String sgreet = "Hello, " + sbox.toUpperCase(); // Be loud.
      m_lbl_greeting.setText(sgreet); // Show user.

      ////
      System.out.println(m_pnl_top.getComponentCount());
      //m_pnl_top.getComponent(0).setVisible(false);
      m_pnl_top.remove(0);
      System.out.println(m_pnl_top.getComponentCount());
      ////
   }
   // ---------------------------------------------------- do_press_me ----
   // Say hello nicely.

   void do_press_me() {
      String sbox = m_tbox_answer.getText(); // Get name.
      if (sbox.equals("")) {
         sbox = "unknown"; // Fix no name.
      }
      String sgreet = "Hello, " + to_capital(sbox); // Be nice.
      m_lbl_greeting.setText(sgreet); // Show user.
      ////
      m_pnl_top.setVisible(false);
      ////
   }
   // ---------------------------------------------------- do_press_int ----
   // Announce the button index.

   void do_press_int(JButton bx) {
      if (!(bx instanceof ZButton)) {
         return; // Not our kind?  Punt!
      }
      ZButton zbx = (ZButton) bx; // Downcast to button type.
      m_lbl_greeting.setText("I'm Button #" + zbx.m_val); // Show user.
      ////
      zbx.setVisible(false);
      System.out.println(zbx.getParent().getComponentCount() - 1);
      zbx.getParent().remove(zbx);

      ////
   }
   // ------------------------------------------------ setup_btn_row ----
   // Setup a row of buttons titled w their index.

   void setup_btn_row(int rcnt) {
      JPanel pnl_row = new JPanel();

      m_pnl_top.add(pnl_row);

      for (int ix = 0; ix < rcnt; ++ix) {
         ZButton bx = new ZButton("\u25CF");
         //pnl_row.setSize(bx.getWidth()*ix, bx.getHeight());
         pnl_row.add(bx);
         System.out.println(pnl_row.getComponentCount());
         bx.setFont(new Font(K_FONT_NAME, Font.BOLD, K_PEBL_SIZE));
         bx.setFocusPainted(false);
         bx.setContentAreaFilled(false);
         bx.setBorderPainted(false);

         bx.addActionListener(this); // Listen to new row btn.
      }
   }
   // ------------------------------------------------ setup_labal_fonts ----
   // Fontify the labels.

   void setup_labal_fonts(Font rfont) {
      m_lbl_hi.setFont(rfont);
      m_lbl_question.setFont(rfont);
      m_lbl_greeting.setFont(rfont);
   }
   // ------------------------------------------------ setup_widgets_grid ----
   // Setup widgets via GridLayout.

   void setup_widgets_grid() {
      LayoutManager layx = new GridLayout(3, 2); // rows,columns layout.
      m_pnl_top.setLayout(layx);

      // Grid layx adds parts, each into cell, filling one row from the left.
      m_pnl_top.add(m_lbl_hi);
      m_pnl_top.add(m_lbl_question);
      m_pnl_top.add(m_tbox_answer);
      m_pnl_top.add(m_lbl_greeting);
      m_pnl_top.add(m_btn_press_me);
      m_pnl_top.add(m_btn_press2);
      setup_btn_row(5);
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
   // ---------------------------------------------------- main ----

   public static void main(String[] args) {
      Concentration pmx = new Concentration(); // Get new custom window.
      pmx.setVisible(true); // Show window to user.
   }
}

