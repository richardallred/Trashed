
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import sun.audio.*;



import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;






public class Board extends JPanel implements Runnable{

    
    /**
     * 
     */
    private static final long serialVersionUID = -5438534791450927749L;

    
   
   
   
    //Global Variables
    
    private final int DELAY = 50;

    //Thread for the Board Class to run in seperately from everything else
    private Thread animator;


    static Tower pendingTower;
    

    
    //Board Dimensions used for making the path
    private final int boardWidth=600;
    private final int boardHeight=600;
    private final int pathWidth=30;
    private final int pathHeight=30;
    private final int pathPad=70;
    private final int gapPad=140;
    private final int towerWidth=30;
    
    Util.TrashType[] types= {Util.TrashType.paper,Util.TrashType.plastic};
    private int TRASH_SPEED=2;
    
    //Images
    private Image background;
    private Image landFill;
    
    //Fonts
    private Font bigfont = new Font("Helvetica", Font.BOLD, 25);
    private Font smallfont=new Font("Comic Sans", Font.BOLD, 16);
   
    
    //Audio Player
    AudioStream as;
    private static Boolean muted=true;
    
    /*Game State Variables*/
    
    //Game State Booleans
    private static boolean inBetweenLevels=true;
    private boolean ingame=true;
    
    //Game State Variables
    private Integer budget=300;
    private Double airQual=1000.0;
    private Integer level=1;
    private Integer landFillScore=0;
    
    //Game State Lists
    ArrayList<Trash> trash= new ArrayList<Trash>();
    static ArrayList<Tower> towers= new ArrayList<Tower>(); //not sure if this should be static but am trying to add towers on button press
    ArrayList<Integer> pathX=new ArrayList<Integer>();
    ArrayList<Integer> pathY=new ArrayList<Integer>();

    public Board() {
        
        setDoubleBuffered(true);
        
        makeBoard(pathX,pathY);
        
       //My computer wants Board capitalized.  -JJ
        ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Board.png"));
        background = ii.getImage();
        
        ii= new ImageIcon(this.getClass().getResource("pics/landfill.png"));
        landFill=ii.getImage(); 
                
        WaveGen Wave= new WaveGen(20,35,TRASH_SPEED,pathPad,types);
        
        trash=Wave.getWave();
       
    }

    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    
    public void makeBoard(ArrayList<Integer> pathX, ArrayList<Integer> pathY){
	
	pathX.add(0);
        pathY.add(pathPad);
        
        pathX.add(boardWidth-pathPad-pathWidth);
        pathY.add(pathPad);
        
        pathX.add(boardWidth-pathPad-pathWidth);
        pathY.add(pathPad+pathHeight+gapPad);
        
        pathX.add(pathPad);
        pathY.add(pathPad+pathHeight+gapPad);
        
        pathX.add(pathPad);
        pathY.add(pathPad+pathHeight*2+gapPad*2);
        
        pathX.add(boardWidth-pathPad-pathWidth);
        pathY.add(pathPad+pathHeight*2+gapPad*2);
        
        pathX.add(boardWidth-pathPad-pathWidth);
        pathY.add(boardHeight+pathHeight);
        
        pathX.add(-pathWidth);
        pathY.add(boardHeight+pathHeight);
        
        pathX.add(-pathWidth);
        pathY.add(pathPad);
    }
    

    public void paint(Graphics g) {
	super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(background,0,0,this);
       
       
	if(ingame){
	    
	    g2d.drawImage(landFill,0,0,this);
            //Draw each piece of trash in our trash array onto the frame
            for(int i=0; i<trash.size(); i++){
                Trash curTrash=trash.get(i);
                g2d.drawImage(curTrash.getImage(), (int)curTrash.getX(),(int) curTrash.getY(), this);
            }
            
            for(int i=0; i<towers.size(); i++){
                Tower curTower=towers.get(i);
                g2d.drawImage(curTower.getArmImage(), (int)curTower.getArmX(),(int) curTower.getArmY(), this);
                g2d.drawImage(curTower.getBaseImage(), (int)curTower.getX(),(int) curTower.getY(), this);
                
            }
            
            //JJ
            if(pendingTower!=null && (pendingTower.getX()!=Integer.MIN_VALUE|| pendingTower.getY()!=Integer.MIN_VALUE))
            {
            	g2d.drawImage(pendingTower.getBaseImage(),(int)pendingTower.getX(),(int)pendingTower.getY(),this);
            }
            

	}else{
	   g2d.setFont(bigfont);
	   g2d.drawString("GAME OVER", 250, 300);
            
	}
	g2d.setFont(smallfont);
	g2d.setColor(Color.RED);
	g2d.drawString("Air Quality: "+airQual.toString()+ " |  Budget: $"+budget.toString()+
		"  | Level: "+level.toString()+" | Trash Left: "+trash.size()+"00 lbs" + 
		" | Landfill "+ landFillScore.toString()+"% Full", 5, 615);
	
	Toolkit.getDefaultToolkit().sync();
        g.dispose();
        
        if(!AudioPlayer.player.isAlive()){
            startMusic();
        }
    }

    public static void startWave(){
	inBetweenLevels=false;
    }
    
    public void removeMoney(int cost){
	
	budget-=cost;
    }
    
    public int getBudget(){
	return budget;
    }
    
    public void startMusic(){
	String path = System.getProperty("user.dir");
        try {

            path += "/Resources/audio/Menu.au";
            InputStream in = new FileInputStream(path);
            as = new AudioStream(in);
            AudioPlayer.player.start(as);
            

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        muted=false;
    }
    
    public boolean isMuted(){
	return muted;
    }
    
    
    public void stopMusic(){
	AudioPlayer.player.stop((as));
	muted=true;
    }
    

    public void run() {

        long beforeTime, timeDiff, sleep;
                
        beforeTime = System.currentTimeMillis();
        
        int counter=0;
        
          
        //startMusic();
                 
		
        
        ingame=true;
        
        while (true){
        
            while (trash.size()>0) {
                counter++;
                
                long pause=0;
                while(inBetweenLevels){
                	pause++;
                	repaint();
                	if(pause>1000000){
                	    pause=0;
                	}
                }
                
                
            	
                for(int i=0; i<trash.size(); i++){
            	
            		trash.get(i).followPath(pathX, pathY);
            	
            		for(int j=0; j<towers.size(); j++){
            		    
            		    //Windmills don't need to check for collisions
            		    if(towers.get(j).getType()==Util.TowerType.windmill){
            			break;
            		    }
            		    	
            		    	if(!trash.get(i).isKilled() && !towers.get(j).getFiring() && trash.get(i).detectCollisions(towers.get(j),pathX,pathY) ){
                	    		
            		    	    	towers.get(j).setFiring(true);
            		    	    	trash.get(i).setKilled();
            
            		    	}
            	    
            		    	//Second case to avoid null point errors after removing the trash from the array
            		    	if(towers.get(j).getFiring() && (trash.get(i).isKilled() || towers.get(j).getFireCounter()>=9)  ){
            		
            		
            		
            		    	    //Only fire every other frame
            		    	    if(counter % TRASH_SPEED==0){
            		    
                    			towers.get(j).fire(TRASH_SPEED);
                    		
                    			if(towers.get(j).getFireCounter()>=9 && trash.get(i).isKilled() ){
                    			    	trash.remove(i);
                    			if(towers.get(j).getType()==Util.TowerType.incenerator){
                    			    	budget+=10;
                    			    	airQual-=15;
                    			}else if(towers.get(j).getType()==Util.TowerType.recycle){
                    			    	budget+=20;
                    			}
                    				break;
                    			}
            		    	    }
            		    	}
            		}
            		//Check to make sure trash hasn't exited the board
            		if(trash.get(i).getY()+30>boardHeight){
            		    trash.remove(i);
            		    landFillScore+=1;
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
            }
            
            //Wave has now ended
            resetTowers();
            
            //Check for windmills
            for(int g=0; g<towers.size(); g++){
        	if(towers.get(g).getType()==Util.TowerType.windmill){
        	    budget+=150;
        	}
            }
            
            repaint();
            level++;
            
            WaveGen Wave= new WaveGen(48,35,1,pathPad,types);
            trash=Wave.getWave();
            
            inBetweenLevels=true;
            
            
            if(level>5){
        	break;
            }
            
        }
        
        ingame=false;
        
    }
    
    private void resetTowers(){
    	for(int i=0; i<towers.size();i++){
    		towers.get(i).resetTower();
    	}
    }
    //Method to determine if the user is adding the tower on top of the path
    public boolean inPath(int x, int y){
	
	//Inside actual game board
	if((x>0 && x>boardWidth) || (y>0 && y>boardHeight)){
	    return true;
	}	
	
	//First row
	if( (x>0 && x<boardWidth-pathPad+(towerWidth/2))  && (y>pathPad-(towerWidth/2) && y<pathPad+pathWidth+(towerWidth/2))){
	    return true;
	//First down
	}else if( (x<boardWidth-pathPad+(towerWidth/2) && x>(boardWidth-pathPad-pathWidth-(towerWidth/2))) && (y>pathPad-(towerWidth/2) && (y<pathPad+gapPad+(towerWidth/2)))){
	    return true;
	//First Switch Back
	}else if(x<boardWidth-pathPad+(towerWidth/2) && x>pathPad-(towerWidth/2) && y>pathPad+pathWidth+gapPad-(towerWidth/2) && y<pathPad+(2*pathWidth)+gapPad+(towerWidth/2)){
	    return true;
	//Second Down
	}else if( x>pathPad-(towerWidth/2) && x<pathPad+pathWidth+(towerWidth/2) && y>pathPad+pathWidth+gapPad-(towerWidth/2) && y<pathPad+2*pathWidth+2*gapPad+(towerWidth/2) ){
	    return true;
	//Second Switch Back
	}else if( x>pathPad-(towerWidth/2) && x<boardWidth-pathPad+(towerWidth/2) && y>pathPad+2*pathWidth+2*gapPad-(towerWidth/2) && y<pathPad+(3*pathWidth)+2*gapPad+(towerWidth/2) ){
	    return true;
	//Third Down
	}else if( (x<boardWidth-pathPad+(towerWidth/2) && x>(boardWidth-pathPad-pathWidth-(towerWidth/2))) && y>pathPad+(3*pathWidth)+2*gapPad+(towerWidth/2) ){
	    return true;
	}
	
	return false;
    }
    
    
    //JJ
    public static void addTower(Tower t)
    {
	towers.add(t);	
    }
    public static void addPendingTower(Tower t)
    {
	pendingTower=t;
    }
    
    
    public static void removePendingTower()
    {
	pendingTower=null;
    }
    
    public int getLevel(){
	return level;
    }
}
