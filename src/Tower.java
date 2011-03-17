import java.awt.Image;


import javax.swing.ImageIcon;

public class Tower{
   
    private Image base,arm;
    TowerType type;
    String dir;
    private int x, y, width, height;
    private boolean isFiring;
    private int rate, range;
    private int fireCounter=0;
    int curPath;
    enum TowerType {incenerator, compactor, recycle,windmill};
    

    //Different orientation
    Tower(int initX, int initY, int fireRate, int towerRange, TowerType type, boolean isValid, String dir){
	
	ImageIcon ii;
	
	//Get image for the base
	ii=getBaseImageIcon(type,dir,isValid);
	base = ii.getImage();
	
	width=ii.getIconWidth();
	height=ii.getIconHeight();
	x=initX;
	y=initY;
	range=towerRange;
	rate=fireRate;
	isFiring=false;
	
	//Get image for the arm
	ii=getArmImageIcon(type,dir);
	arm = ii.getImage();
	
		
    }
    
    public ImageIcon getArmImageIcon(TowerType type, String dir){
	
	ImageIcon ii;
	
	if(type==TowerType.incenerator){
	    
	    
	    ii= new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/arm"+dir+".png"));
	    
	    
	}else if(type==TowerType.compactor){
	    
	    
	    ii= new ImageIcon(this.getClass().getResource("pics/Towers/Compactor/arm"+dir+".png"));
	
	    
	}else if(type==TowerType.recycle){

	    ii= new ImageIcon(this.getClass().getResource("pics/Towers/Recycle/arm"+dir+".png"));
	    
	}else if(type==TowerType.windmill){
	   
	    ii= new ImageIcon(this.getClass().getResource("pics/Towers/Windmill/arm"+dir+".png"));

	}else{
	    ii = new ImageIcon(this.getClass().getResource("pics/Trash/paper.png"));
	}
	
	return ii;
	
    }
	    
    public ImageIcon getBaseImageIcon(TowerType type, String dir,boolean valid){
	
	ImageIcon ii;
	
	if(type==TowerType.incenerator){
	    
	    if(valid){
		ii= new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/base"+dir+".png"));
	    }else{
		ii= new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/base"+dir+"Invalid.png"));
	    }
	    
	}else if(type==TowerType.compactor){
	    
	    if(valid){
		 ii= new ImageIcon(this.getClass().getResource("pics/Towers/Compactor/base"+dir+".png"));
	    }else{
		 ii= new ImageIcon(this.getClass().getResource("pics/Towers/Compactor/base"+dir+"Invalid.png"));
	    }
	    
	}else if(type==TowerType.recycle){
	    
	    if(valid){
		     ii= new ImageIcon(this.getClass().getResource("pics/Towers/Recycle/base"+dir+".png"));
		    }else{
		     ii= new ImageIcon(this.getClass().getResource("pics/Towers/Recycle/base"+dir+"Invalid.png"));
	    }
	
	//Windmills don't rotate    
	}else if(type==TowerType.windmill){
	    if(valid){
		     ii= new ImageIcon(this.getClass().getResource("pics/Towers/Windmill/base.png"));
		    }else{
		     ii= new ImageIcon(this.getClass().getResource("pics/Towers/Windmill/baseInvalid.png"));
		    }
	}else{
	    ii = new ImageIcon(this.getClass().getResource("pics/Trash/paper.png"));
	}
	
	return ii;
	
    }
    
    public String rotateDir(){
	
	//Find the direction that we need to rotate to
	if(dir.equalsIgnoreCase("north")){
	    
	    dir="east";
	
        }else if(dir.equalsIgnoreCase("south")){
            dir="west";
	    
	}else if(dir.equalsIgnoreCase("east")){
	    dir="south";
	    
	}else if(dir.equalsIgnoreCase("west")){
	    dir="north";
	}
	
	
	ImageIcon ii=null;
	
	//Get updated image for base
	ii=getBaseImageIcon(type,dir,true);
	base = ii.getImage();
	
	//Get updated image for arm
	ii=getArmImageIcon(type,dir);
	arm = ii.getImage();
	
	
	return dir;
	
    }
    
    
   
    public void fire(int speed){
		
    }
    
    public boolean getFiring(){
	return isFiring;
    }
    
    public void setFiring(boolean newFire){
	isFiring=newFire;
    }
    
    public Image getImage(){
	return base;
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
    
    
    public void resetTower(){
	
	//Windmills don't need reseting
	if(this.type!=TowerType.windmill){
	    
	    String fileString="pics/Towers/";
        	
	    if(this.type==TowerType.incenerator){
        	 fileString+="Incenerator/";
	    }else if(this.type==TowerType.recycle){
        	 fileString+="Recycle/";
	    }
        	
    	ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"base.png"));
    	base=ii.getImage();
	
	}
    }
}
