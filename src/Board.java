import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5438534791450927749L;

	// Global Variables

	private final int DELAY = 50;

	// Thread for the Board Class to run in separately from everything else
	private Thread animator;

	Tower pendingTower;

	Util.TrashType[] types = { Util.TrashType.paper, Util.TrashType.plastic };

	// Images
	private Image background;
	private Image landFill;
	private Image outline;

	// Fonts
	private Font bigfont = new Font("Georgia", Font.BOLD, 30);
	private Font smallfont = new Font("Georgia", Font.BOLD, 20);

	// Audio Player
	AudioInputStream as;
	static Boolean muted = false;
	static Boolean effectMute = false;
	private Clip clip;


	/* Game State Variables */

	// Game State Booleans
	public static boolean inBetweenLevels = true;
	boolean restart =false;
	private boolean ingame = true;
	boolean wonGame=false;

	// Game State Variables
	private Integer budget = 200;
	Double airQual = 1000.0;
	private Integer level = 1;
	private Integer landFillScore = 0;
	private Integer escapedTrash =0;
	private Integer moneyEarned = 0;
	private Integer bonus = 0;


	// Game State Lists
	static ArrayList<ImageIcon> messages=new ArrayList<ImageIcon>();
	ArrayList<Trash> trash = new ArrayList<Trash>();
	ArrayList<Tower> towers = new ArrayList<Tower>(); // not sure if this should be static but am trying to add towers on button press
	ArrayList<Integer> pathX = new ArrayList<Integer>();
	ArrayList<Integer> pathY = new ArrayList<Integer>();
	
	//Wavegen
	WaveGen Wave = new WaveGen(this);
	
	
	public Board() {

		setDoubleBuffered(true);

		makeBoard(pathX, pathY);

		// My computer wants Board capitalized. -JJ
		ImageIcon ii = new ImageIcon(this.getClass().getResource(
		"pics/Board.png"));
		background = ii.getImage();

		ii = new ImageIcon(this.getClass().getResource("pics/landfill.png"));
		landFill = ii.getImage();

		ii = new ImageIcon(this.getClass().getResource(
		"pics/outline.png"));
		outline = ii.getImage();



	}

	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}
	
	public void setMusic(){
		String path = "audio/Song2.wav";
		if (level%2==0 && clip != null){
			stopMusic();
			clip.close();
			path= "audio/Menu.wav";
		}else if (level !=1 && clip != null) {
			stopMusic();
			clip.close();
		}
		try {
			InputStream in = getClass().getResourceAsStream(path);
			as = AudioSystem.getAudioInputStream(in);
			clip = AudioSystem.getClip();
			clip.open(as);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void makeBoard(ArrayList<Integer> pathX, ArrayList<Integer> pathY) {

		pathX.add(0);
		pathY.add(Util.pathPad);

		pathX.add(Util.boardWidth - Util.pathPad - Util.pathWidth);
		pathY.add(Util.pathPad);

		pathX.add(Util.boardWidth - Util.pathPad - Util.pathWidth);
		pathY.add(Util.pathPad + Util.pathHeight + Util.gapPad);

		pathX.add(Util.pathPad);
		pathY.add(Util.pathPad + Util.pathHeight + Util.gapPad);

		pathX.add(Util.pathPad);
		pathY.add(Util.pathPad + Util.pathHeight * 2 + Util.gapPad * 2);

		pathX.add(Util.boardWidth - Util.pathPad - Util.pathWidth);
		pathY.add(Util.pathPad + Util.pathHeight * 2 + Util.gapPad * 2);

		pathX.add(Util.boardWidth - Util.pathPad - Util.pathWidth);
		pathY.add(Util.boardHeight + Util.pathHeight);

		pathX.add(-Util.pathWidth);
		pathY.add(Util.boardHeight + Util.pathHeight);

		pathX.add(-Util.pathWidth);
		pathY.add(Util.pathPad);

		
	}

	public void paint(Graphics g) {
		Tower highLightTower=null;
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, 0, 0, this);

		if (ingame) {
			
			//direction variables for adjusting arrow image
			int addArrowX=0;
			int addArrowY=0;

			g2d.drawImage(landFill, 0, 0, this);
			// Draw each piece of trash in our trash array onto the frame
			for (int i = 0; i < trash.size(); i++) {
				Trash curTrash = trash.get(i);
				g2d.drawImage(curTrash.getImage(), (int) curTrash.getX(),
						(int) curTrash.getY(), this);
			}
			//Draw all of the towers onto the board
			for (int i = 0; i < towers.size(); i++) {
				Tower curTower = towers.get(i);
				
				
				
				g2d.drawImage(curTower.getArmImage(), (int) curTower.getArmX(),
						(int) curTower.getArmY(), this);
				g2d.drawImage(curTower.getBaseImage(), (int) curTower.getX(),
						(int) curTower.getY(), this);
				if(curTower.isHighLight()){
					highLightTower=curTower;
					
				}

			}
			if(highLightTower!=null){
				g2d.drawImage(outline,(int) highLightTower.getX()-10,
						(int) highLightTower.getY()-10, this);
			}
			
			
			
			// JJ
			if (pendingTower != null && (pendingTower.getX() != Integer.MIN_VALUE || pendingTower.getY() != Integer.MIN_VALUE)) {
				String dir=pendingTower.dir;
				if(dir.equalsIgnoreCase("east")){
					addArrowX+=40;
				}else if(dir.equalsIgnoreCase("west")){
					addArrowX-=40;
				}else if(dir.equalsIgnoreCase("north")){
					addArrowY-=40;
				}else if(dir.equalsIgnoreCase("south")){
					addArrowY+=40;
				}
				g2d.drawImage(pendingTower.getBaseImage(),(int) pendingTower.getX(), (int) pendingTower.getY(),this);
				g2d.drawImage(pendingTower.getArrowImage(),(int)pendingTower.getX()+addArrowX,(int)pendingTower.getY()+addArrowY, this);
			}



			if(inBetweenLevels)
			{
			
				
				if(messages.size()!=0){
					g2d.drawImage(messages.get(0).getImage(),135,135
							,this);
				}
				g2d.setFont(bigfont);
				g2d.setColor(Color.WHITE);
				g2d.drawString("Wave "+level, 300, 550);
				
				if(level>1){
					g2d.setFont(smallfont);
					g2d.setColor(Color.WHITE);
					g2d.drawString("Last Wave - Profit: $"+moneyEarned+"  Bonus: $"+ bonus, 50, 580);
				}
			}

		} else {
			if(!wonGame){
				g2d.setFont(bigfont);
				g2d.drawString("GAME OVER", 250, 300);
				g2d.drawString("Final Score "+getFinalScore().toString()+" points", 100, 350);
				
			}else{
				ImageIcon jj= new ImageIcon(this.getClass().getResource(
				"pics/welcomeFinish.png"));
				Image gameWon= jj.getImage();
				g2d.drawImage(gameWon,135,135,this);
			}
			
			
		}
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
		highLightTower=null;


	}
	
	
	
	public void resetGame(){
		budget = 200;
		airQual = 1000.0;
		level = 1;
		landFillScore = 0;
		escapedTrash =0;
		moneyEarned = 0;
		bonus = 0;
		towers.removeAll(towers);
		trash.removeAll(trash);
		towers= new ArrayList<Tower>();
		trash= new ArrayList<Trash>();
		inBetweenLevels = true;
		ingame = true;
		wonGame=false;
		restart=false;
		stopMusic();
	}

	public static void startWave() {
		inBetweenLevels = false;
	}

	public void removeMoney(int cost) {

		budget -= cost;
	}

	public Integer getBudget() {
		return budget;
	}
	public void setBudget(int aBudget){
		budget = aBudget;
	}

	public void startMusic() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		muted = false;
	}

	public boolean isMuted() {
		return muted;
	}

	public void stopMusic() {
		clip.stop();
		muted = true;
	}

	public void run() {
		
		

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		int counter = 0;
		
		//SWEET HACKZ
		//level=25;
	    //budget=10000000;
	
		int oldBudget = budget;

		ingame = true;

		while (true) {
			
			restart=false;
			
			if (inBetweenLevels  && !muted){
				setMusic();
				stopMusic();
				
				startMusic();
			}
			
			trash = Wave.getWave(level);
			messages=Wave.getMessages(level);
			
			while (trash.size() > 0) {
				
				counter++;
				
				while (inBetweenLevels) {
					repaint();
				}

				for (int i = 0; i < trash.size(); i++) {

					trash.get(i).followPath(pathX, pathY);

					for (int j = 0; j < towers.size(); j++) {
						
						//System.out.println("Trash Killed:"+trash.get(i).isKilled() +" Tower Firing:"+ towers.get(j).getFiring());
						if (towers.get(j).getType()!=Util.TowerType.windmill && !trash.get(i).isKilled() && !towers.get(j).getFiring() && trash.get(i).detectCollisions(towers.get(j),pathX, pathY)) {
							//System.out.println(" Tower: " + j + " Trash: "+ i +"  --"+counter);
							calculateScore(towers.get(j),trash.get(i));
							towers.get(j).setFiring(true,trash.get(i));
							trash.get(i).setKilled();
						}



					}

					if(trash.get(i).getImage()==null){
						trash.remove(i);
					}
					// Check to make sure trash hasn't exited the board
					else if (trash.get(i).getY() + 30 > Util.boardHeight) {
						trash.remove(i);
						landFillScore += 1;
						escapedTrash +=1;
						//System.out.println(landFillScore);
					}

				}

				for(int i=0; i<towers.size();i++){
					if (towers.get(i).getFiring()){
						towers.get(i).fire();
					}
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
				if (airQual <= 0 || landFillScore>=100) {
					break;
				}
			}
			

			// Wave has now ended
			resetTowers();

			// Check for windmills
			for (int g = 0; g < towers.size(); g++) {
				if (towers.get(g).getType() == Util.TowerType.windmill) {
					budget += towers.get(g).getWindmillBonus();
					towers.get(g).windmillBonus();
				}
			}

			repaint();
			level++;
			calculateBonus();
			
			moneyEarned=budget-oldBudget;
			oldBudget=budget;

			inBetweenLevels = true;

			if (airQual <= 0 || landFillScore>=100) {
				restart=true;
				ingame = false;
			}
			if(level==26 && ingame){
				wonGame=true;
				restart=true;
				ingame = false;
			}
			
			if(restart){
				while(restart){
					//System.out.println(restart);
					repaint();
				}
				repaint();
			}

		}

		
		
		

	}
	private void calculateBonus(){
		double multiplier = 1;
		multiplier = (airQual/1000) * multiplier;
		multiplier = (level/4) + multiplier; 
		multiplier = multiplier - (escapedTrash/100);
		bonus = (int)(multiplier*150);
		budget += (int)(multiplier*150);

	}

	private void calculateScore(Tower tower, Trash trash){

		switch(tower.type){
			case incenerator: 
				budget+=15; airQual-=15; break;
			case recycle: budget +=25; airQual+=10; break;
			case windmill: airQual+=30; break;
			case compost: budget+=30; airQual+=5; break;
			case metal: budget+=35; break;
			case nuclear: budget+=25; break;
				
		}
		


	}

	private void resetTowers() {
		for (int i = 0; i < towers.size(); i++) {
			towers.get(i).resetTower();
		}
	}

	public Tower onTowerReturn(int x, int y){
		for(int i=0; i<towers.size(); i++){
			int tX=towers.get(i).getX();
			int tY=towers.get(i).getY();
			int tW=towers.get(i).getWidth();
			int tH=towers.get(i).getHeight();

			if(tX<=x && tX+tW>=x && tY<=y && tY+tH>=y){
				return towers.get(i);
			}
		}
		return null;
	}

	public boolean onTower(int x, int y){
		for(int i=0; i<towers.size(); i++){
			int tX=towers.get(i).getX();
			int tY=towers.get(i).getY();
			int tW=towers.get(i).getWidth();
			int tH=towers.get(i).getHeight();

			if(tX<=x && tX+tW>=x && tY<=y && tY+tH>=y){
				return true;
			}
		}
		return false;
	}
	// Method to determine if the user is adding the tower on top of the path
	public boolean inPath(int x, int y) {

		// Inside actual game board
		if ((x > 0 && x > Util.boardWidth) || (y > 0 && y > Util.boardHeight)) {
			return true;
		}

		// First row
		if ((x > 0 && x < Util.boardWidth - Util.pathPad
				+ (Util.towerWidth / 2))
				&& (y > Util.pathPad - (Util.towerWidth / 2) && y < Util.pathPad
						+ Util.pathWidth + (Util.towerWidth / 2))) {
			return true;
			// First down
		} else if ((x < Util.boardWidth - Util.pathPad + (Util.towerWidth / 2) && x > (Util.boardWidth
				- Util.pathPad - Util.pathWidth - (Util.towerWidth / 2)))
				&& (y > Util.pathPad - (Util.towerWidth / 2) && (y < Util.pathPad
						+ Util.gapPad + (Util.towerWidth / 2)))) {
			return true;
			// First Switch Back
		} else if (x < Util.boardWidth - Util.pathPad + (Util.towerWidth / 2)
				&& x > Util.pathPad - (Util.towerWidth / 2)
				&& y > Util.pathPad + Util.pathWidth + Util.gapPad
				- (Util.towerWidth / 2)
				&& y < Util.pathPad + (2 * Util.pathWidth) + Util.gapPad
				+ (Util.towerWidth / 2)) {
			return true;
			// Second Down
		} else if (x > Util.pathPad - (Util.towerWidth / 2)
				&& x < Util.pathPad + Util.pathWidth + (Util.towerWidth / 2)
				&& y > Util.pathPad + Util.pathWidth + Util.gapPad
				- (Util.towerWidth / 2)
				&& y < Util.pathPad + 2 * Util.pathWidth + 2 * Util.gapPad
				+ (Util.towerWidth / 2)) {
			return true;
			// Second Switch Back
		} else if (x > Util.pathPad - (Util.towerWidth / 2)
				&& x < Util.boardWidth - Util.pathPad + (Util.towerWidth / 2)
				&& y > Util.pathPad + 2 * Util.pathWidth + 2 * Util.gapPad
				- (Util.towerWidth / 2)
				&& y < Util.pathPad + (3 * Util.pathWidth) + 2 * Util.gapPad
				+ (Util.towerWidth / 2)) {
			return true;
			// Third Down
		} else if ((x < Util.boardWidth - Util.pathPad + (Util.towerWidth / 2) && x > (Util.boardWidth
				- Util.pathPad - Util.pathWidth - (Util.towerWidth / 2)))
				&& y > Util.pathPad + (3 * Util.pathWidth) + 2 * Util.gapPad
				+ (Util.towerWidth / 2)) {
			return true;
		}

		return false;
	}

	// JJ
	public Tower addTower(Tower t) {
		towers.add(t);
		Collections.sort(towers,new TowerComparator());
		return t;
	}

	public void addPendingTower(Tower t) {
		pendingTower = t;
	}

	public void removePendingTower() {
		pendingTower = null;
	}

	public Integer getLevel() {
		return level;
	}

	public void sellTower(Tower t, int cost){
		budget+=cost;
		towers.remove(t);
	}

	public void upgradeTower(Tower t, int cost){
		budget-=cost;
		t.upgrade();
	}

	public Integer getLandFillScore() {
		return landFillScore;
	}
	
	public Integer getFinalScore(){
		
		Integer score=0;
		score+=airQual.intValue()-1000;
		score+=budget-200;
		score*=(100-landFillScore/10000);
		if(score<0){
			score=0;
		}
		
		return score;
		
	}
	
	private class TowerComparator implements Comparator{
		   
	    public int compare(Object t1, Object t2){
	  	       
	        int t1x = ((Tower)t1).getX();        
	        int t1y = ((Tower)t2).getY();
	        
	        int t2x = ((Tower)t2).getX();
	        int t2y = ((Tower)t2).getY();
	        
	        //Tower 1 is above Tower 2
	        if(t1y-t2y>Util.gapPad){
	        	return 1;
	        //Tower 2 is above Tower 1
	        }else if(t2y-t1y>Util.gapPad){
	        	return -1;
	        //Towers are on same switchback and it is middle
	        }else if(t1y>Util.pathPad+Util.pathHeight && t1y<Util.pathPad+Util.pathHeight*2+Util.gapPad*2 && t2y>Util.pathPad+Util.pathHeight && t2y<Util.pathPad+Util.pathHeight*2+Util.gapPad*2){
	        	if(t1x<t2x){
	        		return 1;
	        	}else{
	        		return -1;
	        	}
	    	}else{
	    		if(t1x>t2x){
	        		return 1;
	        	}else{
	        		return -1;
	        	}
	    	}
	          
	    }
	   
	}
	

}
