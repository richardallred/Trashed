import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.MouseInputListener;



public class Menu extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5237335232850181080L;
	private Thread menu;
	private final int DELAY = 50;
	private GridLayout layoutMGR =new GridLayout(8,1,10,10);
	private Board gameBoard;
	static private boolean addRecycleTower=false;
	static private boolean addInceneratorTower=false;
	static private boolean addWindmillTower=false;
	private boolean startWave=false;
	private String currentTowerDirection="south";

	public Menu(Board board){
		gameBoard=board;
		board.addMouseListener(new Mouse());
		board.addMouseMotionListener(new Mouse());
		
		setLayout(layoutMGR);
		//ActionListener AL=new ActionListener();
		JButton recycleButton=new JButton("Add Recycling-$200");
		recycleButton.addActionListener(new RecycleButtonListener());
		
		JButton inceneratorButton=new JButton("Add Incenerator-$100");
		inceneratorButton.addActionListener(new InceneratorButtonListener());
		
		JButton muteButton=new JButton("Mute");
		muteButton.addActionListener(new MuteButtonListener());
		
		
		JButton startWaveButton=new JButton("Send Next Wave");
		startWaveButton.addActionListener(new StartWaveButtonListener());
		
		JButton buyWindmillButton= new JButton("Add Windmill-$300");
		buyWindmillButton.addActionListener(new WindmillButtonListener());
		
		add(recycleButton);
		add(inceneratorButton);
		add(buyWindmillButton);
		add(startWaveButton);
		add(muteButton);


	}
	private class StartWaveButtonListener implements ActionListener{
		

		@Override
		public void actionPerformed(ActionEvent e) {
		    	
			startWave=!startWave;
			gameBoard.startWave();
		}
	}
	private class MuteButtonListener implements ActionListener{
		

		@Override
		public void actionPerformed(ActionEvent e) {
		    if(gameBoard.isMuted()){
			gameBoard.startMusic();
		    }else{
			gameBoard.stopMusic();
		    }
		    
		}
	}
	
	private class WindmillButtonListener implements ActionListener{
		

		@Override
		public void actionPerformed(ActionEvent e) {
		    if(gameBoard.getBudget()>=getCost(Tower.TowerType.windmill)){
			addWindmillTower=!addWindmillTower;
			addInceneratorTower=false;
			addRecycleTower=false;
		    }
		}
	}
	
	private class RecycleButtonListener implements ActionListener{
	

		@Override
		public void actionPerformed(ActionEvent e) {
		    if(gameBoard.getBudget()>=getCost(Tower.TowerType.recycle)){
			addRecycleTower=!addRecycleTower;
			addInceneratorTower=false;
			addWindmillTower=false;
		    }
		}
	}
	private class InceneratorButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
		    if(gameBoard.getBudget()>=getCost(Tower.TowerType.incenerator)){
			addInceneratorTower=!addInceneratorTower;
			addRecycleTower=false;
			addWindmillTower=false;
			
		    }
		}
	}
	private class Mouse implements MouseInputListener{

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
		public  void mousePressed(MouseEvent e) {
		    
		    //Detect a left click
		    if(e.getButton()==1){
			
        		    int mouseX=e.getPoint().x;
        		    int mouseY=e.getPoint().y;
        		    
        			if(addRecycleTower==true && gameBoard.getBudget()>=getCost(Tower.TowerType.recycle) && !gameBoard.inPath(mouseX, mouseY)){
        				
        				gameBoard.addTower(new Tower(mouseX-15,mouseY-15,1,25,Tower.TowerType.recycle,true,currentTowerDirection));
        				gameBoard.removeMoney(getCost(Tower.TowerType.recycle));
        				Board.pendingTower=null;
        				addRecycleTower=false;
        
        			}else if(addInceneratorTower==true  && gameBoard.getBudget()>=getCost(Tower.TowerType.incenerator)&& !gameBoard.inPath(mouseX, mouseY))
        			{
        				
        				gameBoard.addTower(new Tower(mouseX-15,mouseY-15,1,25,Tower.TowerType.incenerator,true,currentTowerDirection));
        				gameBoard.removeMoney(getCost(Tower.TowerType.incenerator));
        				Board.pendingTower=null;
        				addInceneratorTower=false;
        	
        			}else if(addWindmillTower==true && gameBoard.getBudget()>=getCost(Tower.TowerType.windmill)&& !gameBoard.inPath(mouseX, mouseY)){
        			    	gameBoard.addTower(new Tower(mouseX-15,mouseY-15,1,25,Tower.TowerType.windmill,true,currentTowerDirection));
    					gameBoard.removeMoney(getCost(Tower.TowerType.incenerator));
    					Board.pendingTower=null;
    					addWindmillTower=false;
        			}
        			
        			
		    }else if(e.getButton()==3){
			currentTowerDirection=gameBoard.pendingTower.rotateDir();
			
			if(addInceneratorTower)
			{
			    if(!gameBoard.inPath(e.getX(), e.getY())){
				 gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.incenerator,true, currentTowerDirection);
			    }else{
				gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.incenerator,false, currentTowerDirection);
			    }
			}else if((addRecycleTower))
			{ 
			    
			    if(!gameBoard.inPath(e.getX(), e.getY())){
				gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.recycle,true, currentTowerDirection);
			    }else{
				 gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.recycle,false, currentTowerDirection);
			    }
			}
				
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
			
		    	if(addInceneratorTower)
			{
			    if(!gameBoard.inPath(e.getX(), e.getY())){
				 gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.incenerator,true,currentTowerDirection);
			    }else{
				gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.incenerator,false,currentTowerDirection);
			    }
			}
			else if((addRecycleTower)){ 
			    
			    if(!gameBoard.inPath(e.getX(), e.getY())){
				gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.recycle,true,currentTowerDirection);
			    }else{
				 gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.recycle,false,currentTowerDirection);
			    }
			}else if((addWindmillTower)){ 
			    
			    if(!gameBoard.inPath(e.getX(), e.getY())){
				gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.windmill,true,currentTowerDirection);
			    }else{
				 gameBoard.pendingTower=new Tower(e.getX()-15,e.getY()-15,1,30,Tower.TowerType.windmill,false,currentTowerDirection);
			    }
			}
				
		}
	}
	
	public int getCost(Tower.TowerType type){
		if(type==Tower.TowerType.recycle){
		    return 200;
		}else if(type==Tower.TowerType.incenerator){
		    return 100;
		}else if(type==Tower.TowerType.windmill){
		    return 300;
		}else{
		    return 0;
		}
	    }

	@Override
	public void run() {
	    long beforeTime, timeDiff, sleep;
            
	        beforeTime = System.currentTimeMillis();
	        
	           
	        
	        
	        while (true) {
	            
	            long pause=0;
	        	
	            
	          
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
