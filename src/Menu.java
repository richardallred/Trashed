import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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
	static private boolean addNuclearTower = false;
	private boolean errorclick=false;
	private boolean startWave = false;
	private String currentTowerDirection = "South";
	private JLabel info = new JLabel("");
	private JLabel towerInfo = new JLabel("");
	private JLabel fact = new JLabel("");
	private JLabel error = new JLabel("");
	private JButton sellButton,cancelButton,muteButton,effectMuteButton,upgradeButton;
	//StartOver Button
	private JButton startOver;
	private Tower clickedTower;
	private ArrayList<String> facts= new ArrayList<String>();
	Random generator = new Random();
	Font menuFont=new Font("Georgia",Font.BOLD, 12);
	Font factFont=new Font("Georgia",Font.PLAIN,13);
	int intBlue = Integer.parseInt( "a0c0f0",16);
	int intGreen= Integer.parseInt( "406020",16);
	Color blue = new Color( intBlue );
	Color green= new Color( intGreen);
	int intBrown= Integer.parseInt( "604020",16);
	int colorNum=Integer.parseInt("FFFFFF", 16);
	Color light= new Color(colorNum);
	Color brown= new Color(intBrown);
	Border thickBorder = new LineBorder(brown, 2);
	Border thinBorder = new LineBorder(brown, 2);
	

	public Menu(Board board) {
		
		int col = Integer.parseInt("c0c060",16);
		Color back= new Color(col);
		this.setBackground(back);
		
		
		
		
		setDoubleBuffered(true);
		gameBoard = board;
		board.addMouseListener(new Mouse());
		board.addMouseMotionListener(new Mouse());
		setLayout(null);
		
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds(5,0, 290, 100);
		info.setFont(factFont);
		info.setForeground(brown);
		
		error.setBounds(5, 100, 290, 40);
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setVerticalAlignment(SwingConstants.CENTER);
		error.setFont(factFont);
		
		towerInfo.setHorizontalAlignment(SwingConstants.CENTER);
		towerInfo.setVerticalAlignment(SwingConstants.CENTER);
		towerInfo.setBounds(5, 185, 290, 50);
		towerInfo.setFont(factFont);
		towerInfo.setForeground(brown);
		
		ImageIcon incen= new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/baseSouth.png"));
		JButton inceneratorButton = new JButton("<html><center><font color=\"#604020\">Incinerator $100</font></center></html>");
		inceneratorButton.addActionListener(new TowerButtonListener(Util.TowerType.incenerator));
		inceneratorButton.setBounds(5, 235, 142, 50);
		inceneratorButton.setFont(menuFont);
		inceneratorButton.setIcon(incen);
		inceneratorButton.setForeground(Color.darkGray);
		inceneratorButton.setBackground(blue);
		inceneratorButton.setBorder(thinBorder);
		
		ImageIcon rec= new ImageIcon(this.getClass().getResource("pics/Towers/Recycle/baseSouth.png"));
		JButton recycleButton = new JButton("<html><center><font color=\"#604020\">Recycling $200</font</center></html>");
		recycleButton.addActionListener(new TowerButtonListener(Util.TowerType.recycle));
		recycleButton.setBounds(150, 235, 142, 50);
		recycleButton.setFont(menuFont);
		recycleButton.setIcon(rec);
		recycleButton.setForeground(Color.darkGray);
		recycleButton.setBackground(blue);
		recycleButton.setBorder(thinBorder);
		
		ImageIcon met= new ImageIcon(this.getClass().getResource("pics/Towers/Metal/baseSouth.png"));
		JButton metalButton = new JButton("<html><center><font color=\"#604020\">Scrap Metal $250</font></center></html>");
		metalButton.addActionListener(new TowerButtonListener(Util.TowerType.metal));
		metalButton.setBounds(5, 290, 142, 50);
		metalButton.setFont(menuFont);
		metalButton.setIcon(met);
		metalButton.setForeground(Color.DARK_GRAY);
		metalButton.setBackground(blue);
		metalButton.setBorder(thinBorder);
		
		ImageIcon comp= new ImageIcon(this.getClass().getResource("pics/Towers/Compost/baseSouth.png"));
		JButton compostButton = new JButton("<html><center><font color=\"#604020\">Compost $250</font</center></html>");
		compostButton.addActionListener(new TowerButtonListener(Util.TowerType.compost));
		compostButton.setBounds(150, 290, 142, 50);
		compostButton.setFont(menuFont);
		compostButton.setIcon(comp);
		compostButton.setBackground(blue);
		compostButton.setForeground(Color.darkGray);
		compostButton.setBorder(thinBorder);
		
		ImageIcon wind= new ImageIcon(this.getClass().getResource("pics/Towers/Windmill/base.png"));
		JButton windmillButton = new JButton("<html><center><font color=\"#604020\">Windmill $300</font></center></html>");
		windmillButton.addActionListener(new TowerButtonListener(Util.TowerType.windmill));
		windmillButton.setBounds(5,345,142, 50);
		windmillButton.setFont(menuFont);
		windmillButton.setIcon(wind);
		windmillButton.setBackground(blue);
		windmillButton.setForeground(Color.DARK_GRAY);
		windmillButton.setBorder(thinBorder);
		
		ImageIcon nuc= new ImageIcon(this.getClass().getResource("pics/Towers/Nuclear/baseSouth.png"));
		JButton nuclearButton = new JButton("<html><center><font color=\"#604020\">Nuclear $400</font></center></html>");
		nuclearButton.addActionListener(new TowerButtonListener(Util.TowerType.nuclear));
		nuclearButton.setBounds(150,345,142, 50);
		nuclearButton.setFont(menuFont);
		nuclearButton.setForeground(Color.DARK_GRAY);
		nuclearButton.setBackground(blue);
		nuclearButton.setIcon(nuc);
		nuclearButton.setBorder(thinBorder);
		
		JButton startWaveButton = new JButton("<html><center>Send Next Wave</center></html>");
		startWaveButton.addActionListener(new StartWaveButtonListener());
		startWaveButton.setBounds(5, 400, 287, 50);
		startWaveButton.setFont(menuFont);
		startWaveButton.setBorder(thickBorder);

		startWaveButton.setBackground(light);
		startWaveButton.setForeground(Color.DARK_GRAY);
		
		fact.setBounds(5, 450, 290, 100);
		fact.setForeground(green);
		fact.setFont(factFont);
		
		muteButton = new JButton("<html><center>Mute Music</center></html>");
		muteButton.addActionListener(new MuteButtonListener());
		muteButton.setBounds(5, 550, 142, 50);
		muteButton.setFont(menuFont);
		muteButton.setForeground(brown);
		muteButton.setBackground(light);
		muteButton.setBorder(thickBorder);

		
		effectMuteButton = new JButton("<html><center>Mute Effects</center></html>");
		effectMuteButton.addActionListener(new EffectMuteButtonListener());
		effectMuteButton.setBounds(150,550, 140, 50);
		effectMuteButton.setFont(menuFont);
		effectMuteButton.setForeground(brown);
		effectMuteButton.setBackground(light);
		effectMuteButton.setBorder(thickBorder);
		
		add(info);
		add(towerInfo);
		add(recycleButton);
		add(inceneratorButton);
		add(metalButton);
		add(windmillButton);
		add(compostButton);
		add(nuclearButton);
		add(startWaveButton);
		add(muteButton);
		add(effectMuteButton);
		add(fact);
		try {
			readFacts();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private class StartOverListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			gameBoard.resetGame();
			remove(startOver);
			startOver=null;
			errorclick=false;
			
		}
		
	}
	
	private void readFacts() throws IOException{
		  InputStream is = getClass().getResourceAsStream("facts.txt");
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
		
		while(br.readLine()!=null){
			facts.add("<html><center>"+br.readLine()+"</center></html>");
		}
	}

	private class CancelButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			resetButtons();
			gameBoard.pendingTower=null;
			remove(cancelButton);
			errorclick=false;
		}
	}
	private class StartWaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			startWave = !startWave;
			gameBoard.startWave();
			errorclick=false;
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
			remove(upgradeButton);
			sellButton=null;
			upgradeButton=null;
			info.setText("");
			towerInfo.setText("");
			clickedTower=null;
			errorclick=false;
		}
	}
	
private class UpgradeTowerButtonListener implements ActionListener {
		
		Tower toBeUpgraded;
		int cost=0;
		
		public UpgradeTowerButtonListener(Tower t, int cost){
			toBeUpgraded=t;
			this.cost=cost;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(gameBoard.getBudget()>=cost){
				gameBoard.upgradeTower(toBeUpgraded, cost);
				setTowerInfoText(toBeUpgraded);
				remove(upgradeButton);
				upgradeButton=null;
				upgradeButton= new JButton("<html><center>Upgrade $"+clickedTower.getUpgradeCost()+"</center></html>");
				upgradeButton.setBounds(150,135,135,50);
				upgradeButton.addActionListener(new UpgradeTowerButtonListener(clickedTower, clickedTower.getUpgradeCost()));
				upgradeButton.setFont(menuFont);
				add(upgradeButton);
				upgradeButton.setBackground(light);
				upgradeButton.setForeground(Color.DARK_GRAY);
				upgradeButton.setBorder(thickBorder);
				remove(sellButton);
				sellButton=null;
				
				int value=Math.max((int)(getCost(clickedTower.type)*.75),clickedTower.getCost());
				sellButton = new JButton("<html><center>Sell $"+value+"</center></html>");
				sellButton.setBounds(15, 135, 135, 50);
				sellButton.addActionListener(new SellTowerButtonListener(clickedTower,value));
				sellButton.setFont(menuFont);
				sellButton.setBackground(light);
				sellButton.setForeground(Color.DARK_GRAY);
				sellButton.setBorder(thickBorder);
				add(sellButton);
				
				
			}else{
				error.setText("<html><center>Your budget is inadequate for this purchase!</center></html>");
				error.setForeground(Color.red);
				error.repaint();
				errorclick=true;
			}
		}
	}
	
	private class EffectMuteButtonListener implements ActionListener {

		@Override
		public  void actionPerformed(ActionEvent e) {
			if (gameBoard.effectMute) {
				gameBoard.effectMute=false;
				effectMuteButton.setBackground(light);
				effectMuteButton.setForeground(Color.darkGray);
			} else {
				gameBoard.effectMute=true;
				effectMuteButton.setBackground(Color.red);
				effectMuteButton.setForeground(Color.black);
			}

		}
	}
	
	
	
	private class MuteButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (gameBoard.muted) {
				gameBoard.startMusic();
				muteButton.setBackground(light);
				muteButton.setForeground(Color.DARK_GRAY);
			} else {
				gameBoard.stopMusic();
				muteButton.setBackground(Color.red);
				muteButton.setForeground(Color.black);
			}
			errorclick=false;

		}
	}
		
	private void fixBooleans(Util.TowerType type){
		switch(type){
			case metal:
				addMetalTower = true;
				addInceneratorTower = false;
				addRecycleTower = false;
				addWindmillTower = false;
				addCompostTower = false;
				addNuclearTower = false;
				break;
			case recycle:
				addRecycleTower = true;
				addInceneratorTower = false;
				addMetalTower = false;
				addCompostTower = false;
				addWindmillTower = false;
				addNuclearTower = false;
				break;
			case windmill:
				addWindmillTower = true;
				addMetalTower = false;
				addInceneratorTower = false;
				addRecycleTower = false;
				addCompostTower = false;
				addNuclearTower = false;
				break;
			case incenerator:
				addInceneratorTower = true;
				addRecycleTower = false;
				addWindmillTower = false;
				addMetalTower = false;	
				addCompostTower = false;
				addNuclearTower = false;
				break;
			case compost:
				addCompostTower = true;
				addInceneratorTower = false;
				addRecycleTower = false;
				addWindmillTower = false;
				addMetalTower = false;	
				addNuclearTower = false;
				break;
			case nuclear:
				addNuclearTower = true;
				addInceneratorTower = false;
				addRecycleTower = false;
				addWindmillTower = false;
				addMetalTower = false;	
				addCompostTower= false;
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
			if (!gameBoard.wonGame) {
				if(sellButton != null){
					remove(sellButton);
					sellButton=null;
					remove(upgradeButton);
					upgradeButton=null;
					info.setText("");
					towerInfo.setText("");
					if(clickedTower != null){
						clickedTower.setHighLight(false);
						clickedTower=null;
					}
				}
				if (gameBoard.getBudget() >= getCost(thisType)) {
					fixBooleans(thisType);
					gameBoard.pendingTower = null;
					setInfoText(thisType);
					if(cancelButton==null){
						cancelButton = new JButton("<html><center>Cancel Purchase</center></html>");
						cancelButton.setBounds(50, 135, 200, 50);
						cancelButton.addActionListener(new CancelButtonListener());
						cancelButton.setFont(menuFont);
						cancelButton.setBackground(light);
						cancelButton.setForeground(Color.DARK_GRAY);
						cancelButton.setBorder(thickBorder);
						
					}
					add(cancelButton);
					errorclick=false;
					
				}else{
					error.setText("<html><center>Your budget is inadequate for this purchase!</center></html>");
					error.setForeground(Color.red);
					error.repaint();
					errorclick=true;
				}
			}
		}
	}
	
	private class Mouse implements MouseInputListener {

		

		@Override
		public void mousePressed(MouseEvent e) {

			int mouseX = e.getPoint().x;
			int mouseY = e.getPoint().y;

			if (!gameBoard.wonGame) {

				int adjX = mouseX - 20;
				int adjY = mouseY - 20;

				// Detect a left click
				if (e.getButton() == 1) {
					
					//None of the buttons have been pressed
					if(!(addCompostTower || addInceneratorTower || addMetalTower || addRecycleTower || addWindmillTower || addNuclearTower )){
						//User clicks on a tower on the board, and hasn't previously pressed any buttons
						if(gameBoard.onTower(mouseX, mouseY)){
							//Change to new clicked tower
							if(clickedTower != null){
								clickedTower.setHighLight(false);
								if(upgradeButton != null){
									remove(upgradeButton);
									upgradeButton=null;
								}
								if(sellButton != null){
									remove(sellButton);
									sellButton=null;
								}
							}
							clickedTower=gameBoard.onTowerReturn(mouseX, mouseY);
							int cost=Math.max((int)(getCost(clickedTower.type)*.75),clickedTower.getCost());
							
							sellButton = new JButton("<html><center>Sell $"+(int)(cost*(.75))+"</center></html>");
							sellButton.setBounds(15, 135, 132, 50);
							sellButton.addActionListener(new SellTowerButtonListener(clickedTower,(int)(cost*(.75))));
							sellButton.setFont(menuFont);
							sellButton.setBackground(light);
							sellButton.setForeground(Color.DARK_GRAY);
							sellButton.setBorder(thickBorder);
							
							upgradeButton= new JButton("<html><center>Upgrade $"+clickedTower.getUpgradeCost()+"</center></html>");
							upgradeButton.setBounds(150,135,132,50);
							upgradeButton.addActionListener(new UpgradeTowerButtonListener(clickedTower, clickedTower.getUpgradeCost()));
							upgradeButton.setFont(menuFont);
							upgradeButton.setBackground(light);
							upgradeButton.setForeground(Color.DARK_GRAY);
							upgradeButton.setBorder(thickBorder);
							
							add(upgradeButton);
							add(sellButton);
							
							setInfoText(clickedTower.type);
							setTowerInfoText(clickedTower);
							
							clickedTower.setHighLight(true);
							
						}else{
							//Remove all focus from any previously clicked towers
							info.setText("");
							towerInfo.setText("");
							if(sellButton !=null){
								remove(sellButton);
								sellButton=null;
								remove(upgradeButton);
								upgradeButton=null;
								if(clickedTower != null){
									clickedTower.setHighLight(false);
								}
							}
							clickedTower=null;
							
						}
						if(errorclick){
							errorclick=false;
						}else{
						error.setText("");
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
							
						} else if ((addNuclearTower)) {
							
							type = Util.TowerType.nuclear;
							
						}
	
						boolean isValid = validTower(mouseX, mouseY, type);
	
						if (type != null && isValid) {
							gameBoard.addTower(new Tower(adjX, adjY, 1, 25, type,
									isValid, currentTowerDirection));
							
							gameBoard.removeMoney(getCost(type));
							gameBoard.pendingTower = null;
							resetButtons();
							remove(cancelButton);
							
						}
					
					}

					// Detect a right click
				} else if (e.getButton() == 3 && gameBoard.pendingTower!=null) {
					
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
					} else if ((addNuclearTower)) {
						
						type = Util.TowerType.nuclear;
						
					}

					boolean isValid = validTower(mouseX, mouseY, type);
					gameBoard.pendingTower = new Tower(adjX, adjY, 1, 25, type,
							isValid, currentTowerDirection);
				}
			}
			
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
	
			} else if ((addNuclearTower)) {

				type = Util.TowerType.nuclear;
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
		addNuclearTower= false;
		info.setText("");
		towerInfo.setText("");
		clickedTower=null;
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
				return 250;
			case compactor:
				return 500;
			case nuclear:
				return 400;
			}
		return 0;
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();
		int curLevel=0;
		int oldLevel=0;

		while (true) {
			
			curLevel=gameBoard.getLevel();
			if(curLevel>oldLevel){
				fact.setText(getFact());
				fact.repaint();
				oldLevel=curLevel;
			}
			
			if(errorclick){
				add(error);
			}else{
				error.setText("");
				remove(error);
			}
			
			if(gameBoard.restart && startOver==null){
				
				if(sellButton!=null){
					remove(sellButton);
					sellButton=null;
					
				}
				if(cancelButton!=null){
					remove(cancelButton);
					cancelButton=null;
				}
				if(upgradeButton!=null){
					remove(upgradeButton);
					upgradeButton=null;
				}
				towerInfo.setText("");
				info.setText("");
				startOver = new JButton("Start Over");
				startOver.addActionListener(new StartOverListener());
				startOver.setBounds(50, 135, 200, 50);
				startOver.setFont(menuFont);
				add(startOver);
				
			}else{
				if(clickedTower!=null && !gameBoard.restart){
					setTowerInfoText(clickedTower);
				}
			}
			
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
	public void setTowerInfoText(Tower tower){
		if(tower.type!=Util.TowerType.windmill){
			towerInfo.setText("<html><center>Fire Rate: <b><font color=\"#406020\">" + tower.getRate()+"0</b></font> ft/sec <br /> Kills: <font color=\"#406020\"><b>" + tower.getKillCount()*100+"</b></font> pounds of trash</center></html>");
		}else{
			towerInfo.setText("<html><center>Current Bonus: <font color=\"#406020\">$"+tower.getWindmillBonus()+"</font> each round<br/>Total Money Earned: <b><font color=\"#406020\">$" +tower.getTotalWindmill()+"</font></b></center></html>");
		}
	}
	
	public void setInfoText(Util.TowerType type){
		switch(type){
			case incenerator: info.setText("<html><center>Incinerators can handle paper, plastic, and styrofoam, but will have negative effect on air quality</center></html>"); break;
			case metal:  info.setText("<html><center>The magnet is for picking up scrap metal</center></html>"); break;
			case recycle: info.setText("<html><center>Recycle Bins are able to recycle paper, plastic, and aluminum and help to improve air quality</center></html>"); break;
			case windmill: info.setText("<html><center>Windmills help to create clean energy for your town, therefore saving you  money each round  on energy costs and  improving air quality</center></html>"); break;
			case compost: info.setText("<html><center>Compost has the ability to properly dispose of food trash that comes through the level and provides  more money than using another type of tower on this trash</center></html>"); break;
			case nuclear: info.setText("<html><center>Nuclear waste centers are for disposing of nuclear waste before it reaches the landfill, they are the only towers that can handle this type of waste</center></html>"); break;
		}
	}

	public void addNotify() {
		super.addNotify();
		menu = new Thread(this);
		menu.start();
	}
	
	private String getFact(){
		
		
		int rand=generator.nextInt(facts.size()-1);
		
		//System.out.println(rand);
		
		return facts.get(rand);
		
		
	}

}
