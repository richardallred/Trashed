import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;
import javax.swing.text.JTextComponent;

public class Menu extends JPanel implements Runnable {

	/**
* 
*/
	private static final long serialVersionUID = 5237335232850181080L;
	private Thread menu;
	private final int DELAY = 50;
	private GridLayout layoutMGR = new GridLayout(8, 1, 10, 10);
	private Board gameBoard;
	static private boolean addRecycleTower = false;
	static private boolean addInceneratorTower = false;
	static private boolean addWindmillTower = false;
	static private boolean addMetalTower = false;
	private boolean startWave = false;
	private String currentTowerDirection = "South";
	private JLabel info = new JLabel("");

	public Menu(Board board) {
		setDoubleBuffered(true);
		gameBoard = board;
		board.addMouseListener(new Mouse());
		board.addMouseMotionListener(new Mouse());
		setLayout(null);
		
		
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds(0,0, 300, 150);
		
		
		JButton recycleButton = new JButton("Recycling-$200");
		recycleButton.addActionListener(new RecycleButtonListener());
		recycleButton.setBounds(0, 150, 150, 50);
		
		JButton inceneratorButton = new JButton("Incenerator-$100");
		inceneratorButton.addActionListener(new InceneratorButtonListener());
		inceneratorButton.setBounds(150, 150, 150, 50);
		
		JButton metalButton = new JButton("Scrap Metal-$150");
		metalButton.addActionListener(new MetalButtonListener());
		metalButton.setBounds(0, 200, 150, 50);
		
		JButton windmillButton = new JButton("Windmill-$300");
		windmillButton.addActionListener(new WindmillButtonListener());
		windmillButton.setBounds(150, 200, 150, 50);
		
		JButton startWaveButton = new JButton("Send Next Wave");
		startWaveButton.addActionListener(new StartWaveButtonListener());
		startWaveButton.setBounds(0, 250, 300, 50);
		
		JButton muteButton = new JButton("Mute");
		muteButton.addActionListener(new MuteButtonListener());
		muteButton.setBounds(0, 300, 100, 50);
		
		
		add(info);
		add(recycleButton);
		add(inceneratorButton);
		add(metalButton);
		add(windmillButton);
		add(startWaveButton);
		add(muteButton);

	}

	private class StartWaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			startWave = !startWave;
			gameBoard.startWave();
		}
	}

	private class MuteButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (gameBoard.isMuted()) {
				gameBoard.startMusic();
			} else {
				gameBoard.stopMusic();
			}

		}
	}

	private class MetalButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (gameBoard.getBudget() >= getCost(Util.TowerType.metal)) {
				addMetalTower = !addMetalTower;
				info.setText("This tower is for picking up scrap metal");
				addInceneratorTower = false;
				addRecycleTower = false;
				addWindmillTower = false;
				gameBoard.pendingTower = null;
			}
		}
	}

	private class WindmillButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (gameBoard.getBudget() >= getCost(Util.TowerType.windmill)) {
				addWindmillTower = !addWindmillTower;
				info.setText("Windmills help to reduce energy costs in your town and earn you money throughout the rounds");
				addMetalTower = false;
				addInceneratorTower = false;
				addRecycleTower = false;
				gameBoard.pendingTower = null;
			}
		}
	}

	private class RecycleButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (gameBoard.getBudget() >= getCost(Util.TowerType.recycle)) {
				addRecycleTower = !addRecycleTower;
				info.setText("The recycle tower helps to properly dispose of the recycleable trash that is produced in the city.  While more expensive than the incenerator, it has less of an imact on the enviorment");
				addInceneratorTower = false;
				addMetalTower = false;
				addWindmillTower = false;
				gameBoard.pendingTower = null;
			}
		}
	}

	private class InceneratorButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (gameBoard.getBudget() >= getCost(Util.TowerType.incenerator)) {
				addInceneratorTower = !addInceneratorTower;
				addRecycleTower = false;
				addWindmillTower = false;
				addMetalTower = false;
				gameBoard.pendingTower = null;
			}
		}
	}

	private class Mouse implements MouseInputListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {

			int mouseX = e.getPoint().x;
			int mouseY = e.getPoint().y;

			//if (!Board.inBetweenLevels) {

				int adjX = mouseX - 20;
				int adjY = mouseY - 20;

				// Detect a left click
				if (e.getButton() == 1) {
					Util.TowerType type = null;

					if (addInceneratorTower) {
						type = Util.TowerType.incenerator;

					} else if ((addRecycleTower)) {
						type = Util.TowerType.recycle;

					} else if ((addWindmillTower)) {

						type = Util.TowerType.windmill;

					} else if ((addMetalTower)) {

						type = Util.TowerType.metal;
					}

					boolean isValid = validTower(mouseX, mouseY, type);

					if (type != null && isValid) {
						gameBoard.addTower(new Tower(adjX, adjY, 1, 25, type,
								isValid, currentTowerDirection));
						gameBoard.removeMoney(getCost(type));
						gameBoard.pendingTower = null;
						resetButtons();
					}

					// Detect a right click
				} else if (e.getButton() == 3) {
					currentTowerDirection = gameBoard.pendingTower.rotateDir();
					Util.TowerType type = null;

					if (addInceneratorTower) {
						type = Util.TowerType.incenerator;

					} else if ((addRecycleTower)) {
						type = Util.TowerType.recycle;

					} else if ((addWindmillTower)) {

						type = Util.TowerType.windmill;

					} else if ((addMetalTower)) {
						type = Util.TowerType.metal;
					}

					boolean isValid = validTower(mouseX, mouseY, type);
					gameBoard.pendingTower = new Tower(adjX, adjY, 1, 25, type,
							isValid, currentTowerDirection);
				}
			//}
			
			if(Board.inBetweenLevels&&Board.messages.size()!=0)
			{
				Board.messages.remove(0);
			}


		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Util.TowerType type = null;

			int mouseX = e.getPoint().x;
			int mouseY = e.getPoint().y;

			int adjX = mouseX - 20;
			int adjY = mouseY - 20;

			if (addInceneratorTower) {
				type = Util.TowerType.incenerator;
				boolean isValid = validTower(mouseX, mouseY, type);
				gameBoard.pendingTower = new Tower(adjX, adjY, 1, 30, type,
						isValid, currentTowerDirection);

			} else if ((addRecycleTower)) {

				type = Util.TowerType.recycle;
				boolean isValid = validTower(mouseX, mouseY, type);
				gameBoard.pendingTower = new Tower(adjX, adjY, 1, 30, type,
						isValid, currentTowerDirection);

			} else if ((addWindmillTower)) {

				type = Util.TowerType.windmill;
				boolean isValid = validTower(mouseX, mouseY, type);
				gameBoard.pendingTower = new Tower(adjX, adjY, 1, 30, type,
						isValid, currentTowerDirection);
			} else if ((addMetalTower)) {

				type = Util.TowerType.metal;
				boolean isValid = validTower(mouseX, mouseY, type);
				gameBoard.pendingTower = new Tower(adjX, adjY, 1, 30, type,
						isValid, currentTowerDirection);

			}

		}
	}

	private void resetButtons() {
		addMetalTower = false;
		addInceneratorTower = false;
		addRecycleTower = false;
		addWindmillTower = false;
		info.setText("");
	}

	public boolean validTower(int x, int y, Util.TowerType type) {

		if (gameBoard.inPath(x, y)) {
			return false;
		}

		if (gameBoard.getBudget() < getCost(type)) {
			return false;
		}

		return true;
	}

	public int getCost(Util.TowerType type) {
		if (type == Util.TowerType.recycle) {
			return 200;
		} else if (type == Util.TowerType.incenerator) {
			return 100;
		} else if (type == Util.TowerType.windmill) {
			return 300;
		} else if (type == Util.TowerType.metal) {
			return 150;
		} else {
			return 1000;
		}
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {

			long pause = 0;
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

	public void addNotify() {
		super.addNotify();
		menu = new Thread(this);
		menu.start();
	}

}
