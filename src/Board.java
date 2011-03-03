
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import sun.audio.*;
import java.applet.*;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class Board extends JPanel implements Runnable{

    
    /**
     * 
     */
    private static final long serialVersionUID = -5438534791450927749L;

    private Thread animator;
    private boolean ingame=true;
    private Font bigfont = new Font("Helvetica", Font.BOLD, 25);
    private Font smallfont=new Font("Helvetica", Font.BOLD, 14);
    private Integer score=300;
    private Double airQual=1000.0;
   
    private final int DELAY = 50;
   
    ArrayList<Trash> trash= new ArrayList<Trash>();
    static ArrayList<Tower> towers= new ArrayList<Tower>(); //not sure if this should be static but am trying to add towers on button press
    ArrayList<Integer> pathX=new ArrayList<Integer>();
    ArrayList<Integer> pathY=new ArrayList<Integer>();
    
    //Board Dimensions used for making the path
    private final int boardWidth=600;
    private final int boardHeight=600;
    private final int pathWidth=30;
    private final int pathHeight=30;
    private final int pathPad=70;
    private final int gapPad=140;
    private Image background;
    private Image landFill;
    
    private static boolean inBetweenLevels=true;
 

    public Board() {
        
        setDoubleBuffered(true);
        
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
        
       //My computer wants Board capitalized.  -JJ
        ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Board.png"));
        background = ii.getImage();
        
        ii= new ImageIcon(this.getClass().getResource("pics/landfill.png"));
        landFill=ii.getImage(); 
        
        Trash.TrashType[] types= {Trash.TrashType.paper,Trash.TrashType.plastic};
        
        WaveGen Wave= new WaveGen(24,35,1,pathPad,types);
        
        trash=Wave.getWave();
       
    }

    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
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
                g2d.drawImage(curTower.getImage(), (int)curTower.getX(),(int) curTower.getY(), this);
            }
          
           
	}else{
	   g2d.setFont(bigfont);
	   g2d.drawString("GAME OVER", 250, 300);
            
	}
	g2d.setFont(smallfont);
	g2d.drawString("Current Budget: $"+score.toString(), 400, 615);
	g2d.drawString("Town Air Quality: "+airQual.toString(), 5, 615);
	g2d.setFont(bigfont);
	g2d.drawString(" | ", 163, 615);
	Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public static void startWave(){
	inBetweenLevels=false;
    }
    
    public void removeMoney(int cost){
	
	score-=cost;
    }
    
    public int getBudget(){
	return score;
    }
    

    public void run() {

        long beforeTime, timeDiff, sleep;
                
        beforeTime = System.currentTimeMillis();
        
        int counter=0;
        
       /*   
	try {
		
	    //JJ- commented this out becasue it throws a file not found exception
		//InputStream in = new FileInputStream("C:\\Users\\Richard\\workspace\\Trashed\\src\\Menu.au");
		InputStream in = new FileInputStream("//Trashed//Resources//audio//Menu.au");
		// InputStream in = new FileInputStream("/Users/zachg/Trashed/src/Menu.au");
	    
		AudioStream as = new AudioStream(in); 
	    AudioPlayer.player.start(as);
	    
	} catch (FileNotFoundException e1) {
	    e1.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
                 
	*/	
        
        ingame=true;
        
        
        
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
        	   
        	    if(trash.get(i).detectCollisions(towers.get(j)) &&
        		    !trash.get(i).isKilled() && !towers.get(j).getFiring()){
            	    		
        		towers.get(j).setFiring(true);
        		trash.get(i).setKilled();
        
        	    }
        	    
        	    //Second case to avoid null point errors after removing the trash from the array
        	    if(towers.get(j).getFiring() && (trash.get(i).isKilled() || towers.get(j).getFireCounter()>=9)  ){
        		
        		
        		
        		//Only fire every other frame
        		if(counter % 2==0){
        		    
                		towers.get(j).fire();
                		
                		
                		if(towers.get(j).getFireCounter()==9 && trash.get(i).isKilled() ){
                		    trash.remove(i);
                		    score+=100;
                		    break;
                		}
        		}
        	    }
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
        ingame=false;
        repaint();
        
    }
    
    //Method to determine if the user is adding the tower on top of the path
    public boolean inPath(int x, int y){
	
	return true;
    }
    
    
    //JJ
    public static void addTower(Tower t)
    {
    towers.add(t);	
    }
}