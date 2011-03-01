import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Menu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5237335232850181080L;
	private GridLayout layoutMGR =new GridLayout(0,2,10,10);
	private Board gameBoard;
	private boolean addRecycleTower=false;
	private boolean addInceneratorTower=false;

	public Menu(Board board){
		gameBoard=board;
		board.addMouseListener(new MouseTest());
		setLayout(layoutMGR);
		//ActionListener AL=new ActionListener();
		JButton recycleButton=new JButton("Add Recycling");
		recycleButton.addActionListener(new RecycleButtonListener());
		
		JButton inceneratorButton=new JButton("Add Incenerator");
		inceneratorButton.addActionListener(new InceneratorButtonListener());
		add(recycleButton);
		add(inceneratorButton);
		//add(new JButton("Button3"));
		//add(new JButton("Button4"));
		//add(new JButton("Button5"));


	}
	private class RecycleButtonListener implements ActionListener{
	

		@Override
		public void actionPerformed(ActionEvent e) {
			addRecycleTower=!addRecycleTower;
			//System.out.println(e.getSource().toString());
			// TODO Auto-generated method stub
		    //Tower newTower= new Tower(200,200,1,25,Tower.TowerType.recycle);
		    //gameBoard.addTower(newTower);
			//System.exit(1);

		}
	}
	private class InceneratorButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			addInceneratorTower=!addInceneratorTower;
		}
		
	}
	private class MouseTest implements MouseListener{

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
			// TODO Auto-generated method stub
			if(addRecycleTower==true){
				int mouseX=e.getPoint().x;
				int mouseY=e.getPoint().y;
				gameBoard.addTower(new Tower(mouseX,mouseY,1,25,Tower.TowerType.recycle));
			}else if(addInceneratorTower==true)
			{
				int mouseX=e.getPoint().x;
				int mouseY=e.getPoint().y;
				gameBoard.addTower(new Tower(mouseX,mouseY,1,25,Tower.TowerType.incenerator));
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
	}

}
