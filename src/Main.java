
import java.awt.BorderLayout;
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
    private BorderLayout LayoutMGR=new BorderLayout();
    
    public Main() {
	
    	this.setLayout(LayoutMGR);
    	
	Board board= new Board();
	add(board,BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,650);
        setLocationRelativeTo(null);
        setTitle("Trashed");
        setResizable(false);
        
        JPanel menu=new Menu(board);
        add(menu,BorderLayout.LINE_END);
        
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}