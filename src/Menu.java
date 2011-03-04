import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;



public class Menu extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5237335232850181080L;
	private Thread menu;
	private final int DELAY = 50;
	private GridLayout layoutMGR =new GridLayout(4,4,10,10);
	private Board gameBoard;
	static private boolean addRecycleTower=false;
	static private boolean addInceneratorTower=false;
	private boolean startWave=false;

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
		
		
		JButton startWaveButton=new JButton("Send Next Wave");
		startWaveButton.addActionListener(new StartWaveButtonListener());
		
		add(recycleButton);
		add(inceneratorButton);
		add(startWaveButton);


	}
	private class StartWaveButtonListener implements ActionListener{
		

		@Override
		public void actionPerformed(ActionEvent e) {
			startWave=!startWave;
			gameBoard.startWave();
			System.out.println("SHIT");
		}
	}
	private class RecycleButtonListener implements ActionListener{
	

		@Override
		public void actionPerformed(ActionEvent e) {
			addRecycleTower=!addRecycleTower;
			addInceneratorTower=false;
			Board.pendingTower=new Tower(Integer.MIN_VALUE,Integer.MIN_VALUE,1,30,Tower.TowerType.recycle);
		}
	}
	private class InceneratorButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			addInceneratorTower=!addInceneratorTower;
			addRecycleTower=false;
			Board.pendingTower=new Tower(Integer.MIN_VALUE,Integer.MIN_VALUE,1,30,Tower.TowerType.incenerator);
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
			
			if(addRecycleTower==true && gameBoard.getBudget()>=getCost(Tower.TowerType.recycle)){
				int mouseX=e.getPoint().x;
				int mouseY=e.getPoint().y;
				gameBoard.addTower(new Tower(mouseX-15,mouseY-15,1,25,Tower.TowerType.recycle));
				gameBoard.removeMoney(200);

			}else if(addInceneratorTower==true  && gameBoard.getBudget()>=getCost(Tower.TowerType.incenerator))
			{
				int mouseX=e.getPoint().x;
				int mouseY=e.getPoint().y;
				gameBoard.addTower(new Tower(mouseX-15,mouseY-15,1,25,Tower.TowerType.incenerator));
				gameBoard.removeMoney(200);
	
			}else if(startWave==true){
				
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
			if(addInceneratorTower||addRecycleTower)
			{
				Board.pendingTower.setX(e.getX());
				Board.pendingTower.setY(e.getY());
			}
		}
	}
	
	public int getCost(Tower.TowerType type){
		if(type==Tower.TowerType.recycle){
		    return 200;
		}else if(type==Tower.TowerType.incenerator){
		    return 100;
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
