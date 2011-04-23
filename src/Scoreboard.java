import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.audio.AudioPlayer;


public class Scoreboard extends JPanel  implements Runnable{
	
	Board gameBoard;
	private Thread score;
	private Font smallfont = new Font("Comic Sans", Font.BOLD, 16);
	private final int DELAY = 50;
	private JLabel info = new JLabel("");

	
	public Scoreboard(Board gameBoard){
		setDoubleBuffered(true);
		this.gameBoard=gameBoard;
		info.setBounds(0, 0, 900, 50);
		info.setText("Air Quality: " + gameBoard.airQual.toString() + " |  Budget: $"
				+ gameBoard.getBudget().toString() + "  | Level: " + gameBoard.getLevel().toString()
				+ " | Trash Left: " + gameBoard.trash.size()*100 + " lbs" + " | Landfill "
				+ gameBoard.getLandFillScore().toString() + "% Full");
		add(info);
	}
	
	public void addNotify() {
		super.addNotify();
		score = new Thread(this);
		score.start();
	}
	/*
	public void paint(Graphics g){
		info.setText("Air Quality: " + gameBoard.airQual.toString() + " |  Budget: $"
				+ gameBoard.getBudget().toString() + "  | Level: " + gameBoard.getLevel().toString()
				+ " | Trash Left: " + gameBoard.trash.size()*100 + " lbs" + " | Landfill "
				+ gameBoard.getLandFillScore().toString() + "% Full");
	}*/
	

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {

			long pause = 0;
			info.setText("Air Quality: " + gameBoard.airQual.toString() + " |  Budget: $"
					+ gameBoard.getBudget().toString() + "  | Level: " + gameBoard.getLevel().toString()
					+ " | Trash Left: " + gameBoard.trash.size()*100 + " lbs" + " | Landfill "
					+ gameBoard.getLandFillScore().toString() + "% Full");
			repaint();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0)
				sleep = 2;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}

			beforeTime = System.currentTimeMillis();
		}

	}

}
