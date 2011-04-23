import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;

public class Menu extends JPanel implements Runnable {

	/**
* 
*/
	private static final long serialVersionUID = 5237335232850181080L;
	private Thread menu;
	private final int DELAY = 50;
	private Board gameBoard;
	static private boolean addRecycleTower = false;
	static private boolean addInceneratorTower = false;
	static private boolean addWindmillTower = false;
	static private boolean addMetalTower = false;
	static private boolean addCompostTower = false;
	private boolean startWave = false;
	private String currentTowerDirection = "South";
	private JLabel info = new JLabel("");
	private JButton sellButton;
	private Tower clickedTower;

	public Menu(Board board) {
		setDoubleBuffered(true);
		gameBoard = board;
		board.addMouseListener(new Mouse());
		board.addMouseMotionListener(new Mouse());
		setLayout(null);
		
		
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds(5,0, 290, 100);
		
		
		
		JButton inceneratorButton = new JButton("Incenerator-$100");
		inceneratorButton.addActionListener(new TowerButtonListener(Util.TowerType.incenerator));
		inceneratorButton.setBounds(0, 150, 150, 50);
		
		JButton recycleButton = new JButton("Recycling-$200");
		recycleButton.addActionListener(new TowerButtonListener(Util.TowerType.recycle));
		recycleButton.setBounds(150, 150, 150, 50);
		
		JButton metalButton = new JButton("Scrap Metal-$250");
		metalButton.addActionListener(new TowerButtonListener(Util.TowerType.metal));
		metalButton.setBounds(0, 200, 150, 50);
		
		JButton compostButton = new JButton("Compost-$250");
		compostButton.addActionListener(new TowerButtonListener(Util.TowerType.compost));
		compostButton.setBounds(150, 200, 150, 50);
		
		JButton windmillButton = new JButton("Windmill-$300");
		windmillButton.addActionListener(new TowerButtonListener(Util.TowerType.windmill));
		windmillButton.setBounds(0,250,150, 50);
		
		JButton startWaveButton = new JButton("Send Next Wave");
		startWaveButton.addActionListener(new StartWaveButtonListener());
		startWaveButton.setBounds(0, 300, 300, 50);
		
		JButton muteButton = new JButton("Mute");
		muteButton.addActionListener(new MuteButtonListener());
		muteButton.setBounds(0, 350, 100, 50);
		
		
		add(info);
		add(recycleButton);
		add(inceneratorButton);
		add(metalButton);
		add(windmillButton);
		add(compostButton);
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

	private class SellTowerButtonListener implements ActionListener {
		
		Tower toBeSold;
		int moneyBack=0;
		
		public SellTowerButtonListener(Tower t, int cost){
			toBeSold=t;
			moneyBack=cost;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			gameBoard.sellTower(toBeSold, moneyBack);
			remove(sellButton);
			info.setText("");
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
	
	private void fixBooleans(Util.TowerType type){
		switch(type){
			case metal:
				addMetalTower = !addMetalTower;
				addInceneratorTower = false;
				addRecycleTower = false;
				addWindmillTower = false;
				addCompostTower = false;
				break;
			case recycle:
				addRecycleTower = !addRecycleTower;
				addInceneratorTower = false;
				addMetalTower = false;
				addCompostTower = false;
				addWindmillTower = false;
				break;
			case windmill:
				addWindmillTower = !addWindmillTower;
				addMetalTower = false;
				addInceneratorTower = false;
				addRecycleTower = false;
				addCompostTower = false;
				break;
			case incenerator:
				addInceneratorTower = !addInceneratorTower;
				addRecycleTower = false;
				addWindmillTower = false;
				addMetalTower = false;	
				addCompostTower = false;
				break;
			case compost:
				addCompostTower = !addCompostTower;
				addInceneratorTower = false;
				addRecycleTower = false;
				addWindmillTower = false;
				addMetalTower = false;	
				break;
				
		}
	}
	
	private class TowerButtonListener implements ActionListener {
		
		Util.TowerType thisType;
		
		public TowerButtonListener(Util.TowerType type){
			thisType=type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if(sellButton != null){
				remove(sellButton);
				if(clickedTower != null){
					clickedTower.setHighLight(false);
				}
			}
			if (gameBoard.getBudget() >= getCost(thisType)) {
				fixBooleans(thisType);
				gameBoard.pendingTower = null;
				setInfoText(thisType);
			}
		}
	}
	
	private class Mouse implements MouseInputListener {

		

		@Override
		public void mousePressed(MouseEvent e) {

			int mouseX = e.getPoint().x;
			int mouseY = e.getPoint().y;

			//if (!Board.inBetweenLevels) {

				int adjX = mouseX - 20;
				int adjY = mouseY - 20;

				// Detect a left click
				if (e.getButton() == 1) {
					
					//None of the buttons have been pressed
					if(!(addCompostTower || addInceneratorTower || addMetalTower || addRecycleTower || addWindmillTower )){
						//User clicks on a tower on the board, and hasn't previously pressed any buttons
						if(gameBoard.onTower(mouseX, mouseY)){
							//Change to new clicked tower
							if(clickedTower != null){
								clickedTower.setHighLight(false);
								remove(sellButton);
							}
							clickedTower=gameBoard.onTowerReturn(mouseX, mouseY);
							int cost=(int)(getCost(clickedTower.type)*.75);
							sellButton = new JButton("Sell Tower $"+cost);
							sellButton.setBounds(15, 75, 135, 50);
							sellButton.addActionListener(new SellTowerButtonListener(clickedTower,cost));
							add(sellButton);
							setInfoText(clickedTower.type);
							clickedTower.setHighLight(true);
						}else{
							info.setText("");
							if(sellButton !=null){
								remove(sellButton);
								clickedTower.setHighLight(false);
							}
							clickedTower=null;
							
						}
						
					}else{
						Util.TowerType type = null;
	
						if (addInceneratorTower) {
							type = Util.TowerType.incenerator;
	
						} else if ((addRecycleTower)) {
							type = Util.TowerType.recycle;
	
						} else if ((addWindmillTower)) {
	
							type = Util.TowerType.windmill;
	
						} else if ((addMetalTower)) {
	
							type = Util.TowerType.metal;
						} else if ((addCompostTower)) {
							
							type = Util.TowerType.compost;
							System.out.println("Compost");
						}
	
						boolean isValid = validTower(mouseX, mouseY, type);
	
						if (type != null && isValid) {
							gameBoard.addTower(new Tower(adjX, adjY, 1, 25, type,
									isValid, currentTowerDirection));
							gameBoard.removeMoney(getCost(type));
							gameBoard.pendingTower = null;
							resetButtons();
						}
					
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
					} else if ((addCompostTower)) {
						type = Util.TowerType.compost;
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
			} else if ((addCompostTower)) {

				type = Util.TowerType.compost;
				boolean isValid = validTower(mouseX, mouseY, type);
				gameBoard.pendingTower = new Tower(adjX, adjY, 1, 30, type,
						isValid, currentTowerDirection);
	
			}

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}

	private void resetButtons() {
		addMetalTower = false;
		addInceneratorTower = false;
		addRecycleTower = false;
		addWindmillTower = false;
		addCompostTower = false;
		info.setText("");
	}

	public boolean validTower(int x, int y, Util.TowerType type) {

		if (gameBoard.inPath(x, y) || gameBoard.onTower(x, y)) {
			return false;
		}

		if (gameBoard.getBudget() < getCost(type)) {
			return false;
		}

		return true;
	}

	public int getCost(Util.TowerType type) {
		
		switch(type){
			case recycle:
				return 200;
			case incenerator:
				return 100;
			case windmill:
				return 300;
			case metal:
				return 250;
			case compost:
				return 200;
			case compactor:
				return 500;
			}
		return 0;
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
	public void setInfoText(Util.TowerType type){
		switch(type){
			case incenerator: info.setText("<html>Incenerators can handle many types of household trash, but have negative effects on air quality</html>"); break;
			case metal:  info.setText("<html>The magnet is for picking up scrap metal</html>"); break;
			case recycle: info.setText("<html>Recycle Bins are able to recycle paper, plastic, and aluminum and help to improve air quality</html>"); break;
			case windmill: info.setText("<html>Windmills help to create clean energy for your town, therefore saving you money each round on energy costs</html>"); break;
			case compost: info.setText("<html>Compost has the ability to properly dispose of food trash that comes through the level and provides more money than using another type of tower on this trash</html>"); break;
		}
	}

	public void addNotify() {
		super.addNotify();
		menu = new Thread(this);
		menu.start();
	}

}
