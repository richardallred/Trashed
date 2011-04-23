import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import sun.audio.AudioPlayer;


public class Scoreboard extends JPanel {
	
	Board gameBoard;
	private Font smallfont = new Font("Comic Sans", Font.BOLD, 16);
	
	public Scoreboard(Board gameBoard){
		this.gameBoard=gameBoard;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setFont(smallfont);
		g2d.setColor(Color.RED);
		g2d.drawString("Air Quality: " + gameBoard.airQual.toString() + " |  Budget: $"
				+ gameBoard.getBudget().toString() + "  | Level: " + gameBoard.getLevel().toString()
				+ " | Trash Left: " + gameBoard.trash.size()*100 + " lbs" + " | Landfill "
				+ gameBoard.getLandFillScore().toString() + "% Full", 5, 0);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

}
