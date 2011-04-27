import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

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
	static private boolean addNuclearTower = false;
	private boolean startWave = false;
	private String currentTowerDirection = "South";
	private JLabel info = new JLabel("");
	private JLabel towerInfo = new JLabel("");
	private JLabel fact = new JLabel("");
	private JButton sellButton,cancelButton,muteButton,effectMuteButton,upgradeButton;
	private Tower clickedTower;
	private ArrayList<String> facts= new ArrayList<String>();
	Random generator = new Random();
	

	public Menu(Board board) {
		setDoubleBuffered(true);
		gameBoard = board;
		board.addMouseListener(new Mouse());
		board.addMouseMotionListener(new Mouse());
		setLayout(null);
		
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setBounds(5,0, 290, 100);
		
		towerInfo.setHorizontalAlignment(SwingConstants.CENTER);
		towerInfo.setVerticalAlignment(SwingConstants.CENTER);
		towerInfo.setBounds(5, 170, 290, 50);
		
		JButton inceneratorButton = new JButton("<html><center>Incenerator $100</center></html>");
		inceneratorButton.addActionListener(new TowerButtonListener(Util.TowerType.incenerator));
		inceneratorButton.setBounds(0, 250, 150, 50);
		
		JButton recycleButton = new JButton("<html><center>Recycling $200</center></html>");
		recycleButton.addActionListener(new TowerButtonListener(Util.TowerType.recycle));
		recycleButton.setBounds(150, 250, 150, 50);
		
		JButton metalButton = new JButton("<html><center>Scrap Metal $250</center></html>");
		metalButton.addActionListener(new TowerButtonListener(Util.TowerType.metal));
		metalButton.setBounds(0, 300, 150, 50);
		
		JButton compostButton = new JButton("<html><center>Compost $250</center></html>");
		compostButton.addActionListener(new TowerButtonListener(Util.TowerType.compost));
		compostButton.setBounds(150, 300, 150, 50);
		
		JButton windmillButton = new JButton("<html><center>Windmill $300</center></html>");
		windmillButton.addActionListener(new TowerButtonListener(Util.TowerType.windmill));
		windmillButton.setBounds(0,350,150, 50);
		
		JButton nuclearButton = new JButton("<html><center>Nuclear $400</center></html>");
		nuclearButton.addActionListener(new TowerButtonListener(Util.TowerType.nuclear));
		nuclearButton.setBounds(150,350,150, 50);
		
		JButton startWaveButton = new JButton("<html><center>Send Next Wave</center></html>");
		startWaveButton.addActionListener(new StartWaveButtonListener());
		startWaveButton.setBounds(0, 400, 300, 50);
		
		fact.setBounds(5, 450, 290, 100);
		
		muteButton = new JButton("<html><center>Mute Music</center></html>");
		muteButton.addActionListener(new MuteButtonListener());
		muteButton.setBounds(0, 550, 150, 50);
		
		effectMuteButton = new JButton("<html><center>Mute Effects</center></html>");
		effectMuteButton.addActionListener(new EffectMuteButtonListener());
		effectMuteButton.setBounds(150,550, 150, 50);
		
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
		
		
		//Set up facts array
		
		facts.add("<html><center>To produce each week's Sunday newspapers, 500,000 trees must be cut down.</center></html>");
		facts.add("<html><center>Recycling a single run of the Sunday <i>New York Times</i> would save 75,000 trees.</center></html>");
		facts.add("<html><center>If all our newspaper was recycled, we could save about 250,000,000 trees each year!</center></html>");
		facts.add("<html><center>If every American recycled just one-tenth of their newspapers, we would save about 25,000,000 trees a year.</center></html>");
		facts.add("<html><center>If you had a 15-year-old tree and made it into paper grocery bags, you'd get about 700 of them. A busy supermarket could use all of them in under an hour! This means in one year, one supermarket can go through over 6 million paper bags! Imagine how many supermarkets there are just in the United States!!!</center></html>"); 
		facts.add("<html><center>The average American uses seven trees a year in paper, wood, and other products made from trees. This amounts to about 2,000,000,000 trees per year!</center></html>");
		facts.add("<html><center>The amount of wood and paper we throw away each year is enough to heat 50,000,000 homes for 20 years. </center></html>");
		facts.add("<html><center>Approximately 1 billion trees worth of paper are thrown away every year in the U.S.</center></html>");
		facts.add("<html><center>Americans use 85,000,000 tons of paper a year; about 680 pounds per person.</center></html>");
		facts.add("<html><center>The average household throws away 13,000 separate pieces of paper each year. Most is packaging and junk mail.</center></html>");
		facts.add("<html><center>In 1993, U.S. paper recovery saved more than 90,000,000 cubic yards of landfill space.</center></html>");
		facts.add("<html><center>Each ton (2000 pounds) of recycled paper can save 17 trees, 380 gallons of oil, three cubic yards of landfill space, 4000 kilowatts of energy, and 7000 gallons of water. This represents a 64% energy savings, a 58% water savings, and 60 pounds less of air pollution!</center></html>");
		facts.add("<html><center>The 17 trees saved (above) can absorb a total of 250 pounds of carbon dioxide from the air each year. Burning that same ton of paper would <i>create</i> 1500 pounds of carbon dioxide.</center></html>"); 
		facts.add("<html><center>The construction costs of a paper mill designed to use waste paper is 50 to 80% less than the cost of a mill using new pulp.</center></html>"); 
		facts.add("<html><center>Americans use 2,500,000 plastic bottles every hour! Most of them are thrown away!</center></html>");
		facts.add("<html><center>Plastic bags and other plastic garbage thrown into the ocean kill as many as 1,000,000 sea creatures every year!</center></html>");
		facts.add("<html><center>Recycling plastic saves twice as much energy as burning it in an incinerator.</center></html>"); 
		facts.add("<html><center>Americans throw away 25,000,000,000 Styrofoam coffee cups every year.</center></html>"); 
		facts.add("<html><center>Every month, we throw out enough glass bottles and jars to fill up a giant skyscraper. All of these jars are recyclable!</center></html>");
		facts.add("<html><center>The energy saved from recycling one glass bottle can run a 100-watt light bulb for four hours or a compact fluorescent bulb for 20 hours. It also causes 20% less air pollution and 50% less water pollution than when a new bottle is made from raw materials.</center></html>");
		facts.add("<html><center>A modern glass bottle would take 4000 years or more to decompose -- and even longer if it's in the landfill.</center></html>");
		facts.add("<html><center>Mining and transporting raw materials for glass produces about 385 pounds of waste for every ton of glass that is made. If recycled glass is substituted for half of the raw materials, the waste is cut by more than 80%.</center></html>"); 
		facts.add("<html><center>About one-third of an average dump is made up of packaging material!</center></html>");
		facts.add("<html><center>Every year, each American throws out about 1,200 pounds of organic garbage that can be composted.</center></html>");
		facts.add("<html><center>The U.S. is the #1 trash-producing country in the world at 1,609 pounds per person per year. This means that 5% of the world's people generate 40% of the world's waste.</center></html>");
		facts.add("<html><center>The highest point in Hamilton County, Ohio (near Cincinnati) is 'Mount Rumpke'. It is actually a mountain of trash at the Rumpke sanitary landfill towering 1045 ft. above sea level.</center></html>");
		facts.add("<html><center>The US population discards each year 16,000,000,000 diapers, 1,600,000,000 pens, 2,000,000,000 razor blades, 220,000,000 car tires, and enough aluminum to rebuild the US commercial air fleet four times over.</center></html>"); 
		facts.add("<html><center>Out of every $10 spent buying things, $1 (10%) goes for packaging that is thrown away. Packaging represents about 65% of household trash.</center></html>");
		facts.add("<html><center>On average, it costs $30 per ton to recycle trash, $50 to send it to the landfill, and $65 to $75 to incinerate it.</center></html>");
		facts.add("<html><center>An estimated 80,000,000 Hershey's Kisses are wrapped each day, using enough aluminum foil to cover over 50 acres of space -- that's almost 40 football fields. All that foil is recyclable, but not many people realize it.</center></html>");
		facts.add("<html><center>Rainforests are being cut down at the rate of 100 acres per minute!</center></html>");
		facts.add("<html><center>A single quart of motor oil, if disposed of improperly, can contaminate up to 2,000,000 gallons of fresh water.</center></html>");
		facts.add("<html><center>Motor oil never wears out, it just gets dirty. Oil can be recycled, re-refined and used again, reducing our reliance on imported oil.</center></html>");
		facts.add("<html><center>On average, each one of us produces 4.4 pounds of solid waste each day. This adds up to almost a ton of trash per person, per year.</center></html>");
		facts.add("<html><center>A typical family consumes 182 gallons of soda, 29 gallons of juice, 104 gallons of milk, and 26 gallons of bottled water a year. That's a lot of containers -- make sure they're recycled!</center></html>");

	}

	private class CancelButtonListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			resetButtons();
			gameBoard.pendingTower=null;
			remove(cancelButton);
		}
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
			remove(upgradeButton);
			sellButton=null;
			upgradeButton=null;
			info.setText("");
			towerInfo.setText("");
			clickedTower=null;
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
				upgradeButton= new JButton("<html><center>Upgrade $"+clickedTower.getUpgradeCost()+"</center></html>");
				upgradeButton.setBounds(150,135,135,50);
				upgradeButton.addActionListener(new UpgradeTowerButtonListener(clickedTower, clickedTower.getUpgradeCost()));
				add(upgradeButton);
			}
		}
	}
	
	private class EffectMuteButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (gameBoard.effectMute) {
				gameBoard.effectMute=false;
				effectMuteButton.setBackground(null);
				effectMuteButton.setForeground(null);
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
				muteButton.setBackground(null);
				muteButton.setForeground(null);
			} else {
				gameBoard.stopMusic();
				muteButton.setBackground(Color.red);
				muteButton.setForeground(Color.black);
			}

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

			if(sellButton != null){
				remove(sellButton);
				sellButton=null;
				remove(upgradeButton);
				sellButton=null;
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
				}
				add(cancelButton);
				
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
							int cost=(int)(getCost(clickedTower.type)*.75);
							
							sellButton = new JButton("<html><center>Sell $"+cost+"</center></html>");
							sellButton.setBounds(15, 135, 135, 50);
							sellButton.addActionListener(new SellTowerButtonListener(clickedTower,cost));
							
							
							upgradeButton= new JButton("<html><center>Upgrade $"+clickedTower.getUpgradeCost()+"</center></html>");
							upgradeButton.setBounds(150,135,135,50);
							upgradeButton.addActionListener(new UpgradeTowerButtonListener(clickedTower, clickedTower.getUpgradeCost()));
							
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
								remove(upgradeButton);
								if(clickedTower != null){
									clickedTower.setHighLight(false);
								}
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
					} else if ((addNuclearTower)) {
						
						type = Util.TowerType.nuclear;
						
					}

					boolean isValid = validTower(mouseX, mouseY, type);
					gameBoard.pendingTower = new Tower(adjX, adjY, 1, 25, type,
							isValid, currentTowerDirection);
				}
			//}
			
			if(Board.inBetweenLevels&&Board.messages.size()!=0)
			{
				Board.messages.remove(0);
				Board.inBetweenLevels=false;
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
				return 200;
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
			
			long pause = 0;
			if(clickedTower!=null){
				setTowerInfoText(clickedTower);
			}
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
			towerInfo.setText("<html><center>Fire-Rate:" + tower.getRate()+" | Kills:" + tower.getKillCount()+ "</center></html>");
		}else{
			towerInfo.setText("<html><center>Current Bonus: $"+tower.getWindmillBonus()+" each round</center></html>");
		}
	}
	
	public void setInfoText(Util.TowerType type){
		switch(type){
			case incenerator: info.setText("<html><center>Incenerators can handle many types of household trash, but have negative effects on air quality</center></html>"); break;
			case metal:  info.setText("<html><center>The magnet is for picking up scrap metal</center></html>"); break;
			case recycle: info.setText("<html><center>Recycle Bins are able to recycle paper, plastic, and aluminum and help to improve air quality</center></html>"); break;
			case windmill: info.setText("<html><center>Windmills help to create clean energy for your town, therefore saving you money each round on energy costs</center></html>"); break;
			case compost: info.setText("<html><center>Compost has the ability to properly dispose of food trash that comes through the level and provides more money than using another type of tower on this trash</center></html>"); break;
			case nuclear: info.setText("<html><center>Nuclear waste centers are for disposing of nuclear waste before it reaches the landfill, they are the only towers that can handle this type of waste</center></html>"); break;
		}
	}

	public void addNotify() {
		super.addNotify();
		menu = new Thread(this);
		menu.start();
	}
	
	private String getFact(){
		
		
		int rand=generator.nextInt(facts.size());
		
		System.out.println(rand);
		
		return facts.get(rand);
		
		
	}

}
