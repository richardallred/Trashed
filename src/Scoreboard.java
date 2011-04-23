import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.audio.AudioPlayer;


public class Scoreboard extends JPanel  implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Board gameBoard;
	private Thread score;
	private Font smallfont = new Font("Georgia", Font.BOLD, 16);
	private final int DELAY = 50;
	private JLabel info = new JLabel("");

	
	public Scoreboard(Board gameBoard){
		setDoubleBuffered(true);
		this.gameBoard=gameBoard;
		info.setBounds(0, 0, 900, 50);
		info.setText("Level: " + gameBoard.getLevel().toString() + " |  Budget: $"
				+ gameBoard.getBudget().toString() + "  | Air Quality: " + gameBoard.airQual.toString()
				+ " | Trash Left: " + gameBoard.trash.size()*100 + " lbs" + " | Landfill "
				+ gameBoard.getLandFillScore().toString() + "% Full");
		info.setFont(smallfont);
		info.setForeground(Color.DARK_GRAY);
		add(info);
	}
	
	public void addNotify() {
		super.addNotify();
		score = new Thread(this);
		score.start();
	}
	
	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {

			long pause = 0;
			info.setText("Level: " + gameBoard.getLevel().toString() + " |  Budget: $"
					+ gameBoard.getBudget().toString() + "  | Air Quality: " + gameBoard.airQual.toString()
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
