import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;



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
		this.setBackground(Color.gray);
		info.setForeground(Color.red);
		setDoubleBuffered(true);
		this.gameBoard=gameBoard;
		info.setBounds(0, 0, 900, 50);
		info.setText("<html><center>Score: <b><font color=\"#CC0000\">"+gameBoard.getFinalScore()+"</font></b> | Wave: <b><font color=\"#CC0000\">" + gameBoard.getLevel().toString() + "</font></b> |  Budget: $<b><font color=\"#CC0000\">"
				+ gameBoard.getBudget().toString() + "</font></b> | Air Quality: <font color=\"#CC0000\">" + gameBoard.airQual.toString()
				+ "</font><b> | Trash Left: <font color=\"#CC0000\">" + gameBoard.trash.size()*100 + "</font></b> lbs" + " | Landfill <font color=\"#CC0000\">"
				+ gameBoard.getLandFillScore().toString() + "</font</b>% Full</center></html>");
		info.setFont(smallfont);
		//info.setForeground(Color.DARK_GRAY);
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

			
			info.setText("<html><center>Score: <b><font color=\"#CC0000\">"+gameBoard.getFinalScore()+"</font></b> | Wave: <b><font color=\"#CC0000\">" + gameBoard.getLevel().toString() + "</font></b> |  Budget: $<b><font color=\"#CC0000\">"
					+ gameBoard.getBudget().toString() + "</font></b> | Air Quality: <font color=\"#CC0000\">" + gameBoard.airQual.toString()
					+ "</font><b> | Trash Left: <font color=\"#CC0000\">" + gameBoard.trash.size()*100 + "</font></b> lbs" + " | Landfill <font color=\"#CC0000\">"
					+ gameBoard.getLandFillScore().toString() + "</font</b>% Full</center></html>");
			info.setForeground(Color.white);
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
