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
    
    public Main() {
	
    	this.setLayout(null);
    	
	    Board board= new Board();
	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,650);
        setLocationRelativeTo(null);
        setTitle("Trashed");
        setResizable(false);
        board.setBounds(0, 0, 600, 600);
       
        
        JPanel menu=new Menu(board);
        menu.setBounds(600, 0, 300, 600);
        
        JPanel scoreBoard=new Scoreboard(board);
        scoreBoard.setBounds(0,600,900, 50);
        

        add(menu);
        add(board);
        add(scoreBoard);
       
        
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}
