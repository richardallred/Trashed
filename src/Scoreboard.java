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
	int intBrown= Integer.parseInt( "604020",16);
	int colorNum=Integer.parseInt("c0c060", 16);
	Color light= new Color(colorNum);
	Color brown= new Color(intBrown);

	
	public Scoreboard(Board gameBoard){
		
		this.setBackground(light);
		info.setForeground(brown);
		setDoubleBuffered(true);
		this.gameBoard=gameBoard;
		info.setBounds(0, 0, 900, 50);
		info.setText("<html><center>Score: <b><font color=\"#406020\">"+gameBoard.getFinalScore()+"</font></b> | Wave: <b><font color=\"#406020\">" + gameBoard.getLevel().toString() + "</font></b> |  Budget: $<b><font color=\"#406020\">"
				+ gameBoard.getBudget().toString() + "</font></b> | Air Quality: <font color=\"#406020\">" + gameBoard.airQual.toString()
				+ "</font><b> | Trash Left: <font color=\"#406020\">" + gameBoard.trash.size()*100 + "</font></b> lbs" + " | Landfill <font color=\"#406020\">"
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

			
			info.setText("<html><center>Score: <b><font color=\"#406020\">"+gameBoard.getFinalScore()+"</font></b> | Wave: <b><font color=\"#406020\">" + gameBoard.getLevel().toString() + "</font></b> |  Budget: $<b><font color=\"#406020\">"
					+ gameBoard.getBudget().toString() + "</font></b> | Air Quality: <font color=\"#406020\">" + gameBoard.airQual.toString()
					+ "</font><b> | Trash Left: <font color=\"#406020\">" + gameBoard.trash.size()*100 + "</font></b> lbs" + " | Landfill <font color=\"#406020\">"
					+ gameBoard.getLandFillScore().toString() + "</font</b>% Full</center></html>");
			info.setForeground(brown);
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
