import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Tower{
   
    private Image towerPic;
    TowerType type;
    private int x, y, width, height;
    private boolean isFiring;
    private int rate, range;
    private int fireCounter=0;
    int curPath;
    enum TowerType {incenerator, compactor, recycle};

    
    Tower(int initX, int initY, int fireRate, int towerRange, TowerType type){
	
	if(type==TowerType.incenerator){
	    ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/flame1.png"));
	    towerPic = ii.getImage();
	    width=ii.getIconWidth();
	    height=ii.getIconHeight();
	}else if(type==TowerType.compactor){
	    ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/flame1.png"));
	    towerPic = ii.getImage();
	    width=ii.getIconWidth();
	    height=ii.getIconHeight();
	}else if(type==TowerType.recycle){
	    ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Towers/Recycle/recycletower1.png"));
	    towerPic = ii.getImage();
	    width=ii.getIconWidth();
	    height=ii.getIconHeight();
	}else{
	    ImageIcon ii = new ImageIcon(this.getClass().getResource("pics/Trash/paper.png"));
	    towerPic = ii.getImage();
	    width=ii.getIconWidth();
	    height=ii.getIconHeight();
	}
	 this.type=type;
	 x=initX;
	 y=initY;
	 range=towerRange;
	 rate=fireRate;
	 isFiring=false;
    }
    
    public void fire(){
	
	String fileString="pics/Towers/";
	if(this.type==TowerType.incenerator){
	    fileString+="Incenerator/flame";
	}else if(this.type==TowerType.recycle){
	    fileString+="Recycle/recycletower";
	}
	
	if(fireCounter!=19){
	           ImageIcon ii;
	           switch(fireCounter){
	               case 18:
	               case 0: ii=new ImageIcon(this.getClass().getResource(fileString+"1.png")); break;
	               case 17:
	               case 1: ii=new ImageIcon(this.getClass().getResource(fileString+"2.png")); break;
	               case 16:
	               case 2: ii=new ImageIcon(this.getClass().getResource(fileString+"3.png")); break;
	               case 15:
	               case 3: ii=new ImageIcon(this.getClass().getResource(fileString+"4.png")); break;
	               case 14:
	               case 4: ii=new ImageIcon(this.getClass().getResource(fileString+"5.png")); break;
	               case 13:
	               case 5: ii=new ImageIcon(this.getClass().getResource(fileString+"6.png")); break;
	               case 12:
	               case 6: ii=new ImageIcon(this.getClass().getResource(fileString+"7.png")); break;
	               case 11:
	               case 7: ii=new ImageIcon(this.getClass().getResource(fileString+"8.png")); break;
	               case 10:
	               case 8: ii=new ImageIcon(this.getClass().getResource(fileString+"9.png")); break;
	               case 9: ii=new ImageIcon(this.getClass().getResource(fileString+"10.png")); break;
	               default: ii=new ImageIcon(this.getClass().getResource(fileString+"1.png")); break;
	           }
	           towerPic=ii.getImage();
	       }else if(fireCounter==19){
	           fireCounter=0;
	           isFiring=false;
	       }
	if(isFiring){
	    fireCounter++;
	}
	







    }
    
    public boolean getFiring(){
	return isFiring;
    }
    
    public void setFiring(boolean newFire){
	isFiring=newFire;
    }
    
    public Image getImage(){
	return towerPic;
    }
    
    public int getRange(){
	return range;
    }
    
    
    public void setX(int newX){
       x=newX;
    }
    
    
    public int getX(){
	return x;
    }
    
    public void setY(int newY){
	y=newY;
    }
    
    public int getY(){
	return y;
    }
    
    
        
    
    public int getWidth(){
	return width;
    }
    
    public int getHeight(){
	return height;
    }
    
    public void setType(TowerType newType){
	this.type=newType;
    }
    
    public TowerType getType(){
	return type;
    }
    
    public int getRate(){
	return rate;
    }
    public int getFireCounter(){
	return fireCounter;
    }
    
    
    
}
