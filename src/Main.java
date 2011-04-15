import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 4648172894076113183L;

    Board board;

    //Helps define where different components are -JJ
   // private BorderLayout LayoutMGR=new BorderLayout();
//    private FlowLayout LayoutMGR = new FlowLayout();
    
    public Main() {
	
    	this.setLayout(null);
    	
	Board board= new Board();
	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,650);
        setLocationRelativeTo(null);
        setTitle("Trashed");
        setResizable(false);
     
        board.setBounds(0, 0, 600, 650);
        board.setPreferredSize(new Dimension(600,650));
        board.setMaximumSize(new Dimension(600,650));
        
        JPanel menu=new Menu(board);
        menu.setSize(300, 550);
        
       
       menu.setBounds(600, 0, 300, 550);
       menu.setPreferredSize(new Dimension(300,550));
       menu.setMaximumSize(new Dimension(300,550));
       add(menu);
        add(board);
       
        
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}
