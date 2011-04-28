import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;


public class Main extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 4648172894076113183L;

    Board board;
    Image instructions;
    boolean clicked=false;
    
    public Main() {
	
    	this.setLayout(null);
    	
	    Board board= new Board();
	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,655);
        setLocationRelativeTo(null);
        setTitle("Trashed");
        setResizable(false);
        board.setBounds(0, 0, 600, 600);
       
        
        JPanel menu=new Menu(board);
        menu.setBounds(600, 0, 300, 600);
        
        JPanel scoreBoard=new Scoreboard(board);
        scoreBoard.setBounds(0,600,900, 55);
        
        JPanel start = new Start();
        start.setBounds(0,0,900,655);
        
        add(start);
        setVisible(true);

        while(!clicked){
        	this.repaint();
        }
        remove(start);
        start=null;
        add(menu);
        add(board);
        add(scoreBoard);
        
       
        
        
    }
    
    private class Start extends JPanel{
    	
    	public Start(){
    		ImageIcon ii = new ImageIcon(this.getClass().getResource(
    		"pics/instructionswide.png"));
    		instructions = ii.getImage();
    		this.addMouseListener(new Mouse());
    	}
    	
    	@Override
    	public void paint(Graphics g){
    		super.paint(g);
    		Graphics2D g2d = (Graphics2D) g;
    		g2d.drawImage(instructions, 0, 0, this);
    	}
    }
    
    private class Mouse implements MouseInputListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			clicked=true;
			System.out.println("CLICK");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			clicked=true;
			System.out.println("Pressed");
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			clicked=true;
			System.out.println("released");
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    public static void main(String[] args) {
        new Main();
    }
}
