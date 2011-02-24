import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Trash{
   
    private Image trashPic;
    TrashType type;
    private double x, y,speed;
    int curPath;
    enum TrashType {paper, plastic, metal,food};
    private boolean beenKilled=false;

    
    Trash(int initX, int initY, double trashSpeed, TrashType type){
	
	if(type==TrashType.paper){
	    ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Trash/paper.png"));
	    trashPic = ii.getImage();
	}else if(type==TrashType.plastic){
	    ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Trash/plastic.png"));
	    trashPic = ii.getImage();
	}else{
	    ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Trash/paper.png"));
	    trashPic = ii.getImage();
	}
	 this.type=type;
	 x=initX;
	 y=initY;
	 speed=trashSpeed;
    }
    
    
    public Image getImage(){
	return trashPic;
    }
    
    public TrashType getType(){
	return type;
    }
    
    public double getX(){
	return x;
    }
    
    public double getY(){
	return y;
    }
    
    public void setType(TrashType newType){
	this.type=newType;
    }
    
    public void followPath(ArrayList<Integer> X, ArrayList<Integer> Y){
	
	
	double distX, distY,angle;
	
	distX=X.get(curPath)-x;
	distY=Y.get(curPath)-y;
	
	if(Math.abs(distX)+Math.abs(distY)<speed){
	    curPath++;
	    if(curPath>=X.size()){
		curPath=0;
	    }
	}
	
	angle=Math.atan2(distY, distX);
	x=x+speed*Math.cos(angle);
	y=y+speed*Math.sin(angle);
	
	
    }
    
    public boolean detectCollisions(Tower tower){
	
	
	   
	    Tower curTower= tower;
	    
	    int towerMidX=(int)(curTower.getX()+curTower.getWidth()/2);
	    int towerMidY=(int)(curTower.getY()+curTower.getHeight()/2);
	    
	    int trashMidX=(int)(x+15);
	    int trashMidY=(int)(y+15);
	    
	    if((towerMidX-curTower.getRange()<trashMidX && towerMidX+curTower.getRange()>trashMidX && 
		    towerMidY-curTower.getRange()<trashMidY && towerMidY+curTower.getRange()>trashMidY)){
		
		if(curTower.getType()==Tower.TowerType.recycle && type==TrashType.plastic){
		    return true;
		}else if(curTower.getType()==Tower.TowerType.incenerator && type==TrashType.paper){
		    return true;
		}
		
	    }
	return false;
	
    }
    public boolean isKilled(){
	return beenKilled;
    }
    
    public void setKilled(){
	beenKilled=true;
    }
}
