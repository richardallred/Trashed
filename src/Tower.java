import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Tower{
   
    private Image towerPic;
    TowerType type;
    private double x, y, width, height;
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
	
	if(fireCounter==0){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"1.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==1){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"2.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==2){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"3.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==3){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"4.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==4){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"5.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==5){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"6.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==6){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"7.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==7){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"8.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==8){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"9.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==9){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"10.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==10){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"9.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==11){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"8.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==12){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"7.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==13){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"6.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==14){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"5.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==15){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"4.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==16){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"3.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==17){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"2.png"));
	    towerPic=ii.getImage();
	    
	}else if(fireCounter==18){
	    ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"1.png"));
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
    
    
    public double getX(){
	return x;
    }
    
    public void setY(int newY){
	y=newY;
    }
    
    public double getY(){
	return y;
    }
        
    
    public double getWidth(){
	return width;
    }
    
    public double getHeight(){
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
