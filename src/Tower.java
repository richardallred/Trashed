import java.awt.Image;


import javax.swing.ImageIcon;

public class Tower{
   
    private Image base,arm;
    Util.TowerType type;
    String dir;
    private int x, y,armX,armY, width, height;
    private boolean isFiring;
    private int rate, range;
    private int fireCounter=0;
    int curPath;
    
    

    
    Tower(int initX, int initY, int fireRate, int towerRange, Util.TowerType type, boolean isValid, String dir){
	
	ImageIcon ii;
	
	//Get image for the base
	ii=getBaseImageIcon(type,dir,isValid);
	base = ii.getImage();
	
	this.dir=dir;
	width=ii.getIconWidth();
	height=ii.getIconHeight();
	x=initX;
	y=initY;
	armX=initX;
	armY=initY;
	range=towerRange;
	rate=fireRate;
	isFiring=false;
	
	//Get image for the arm
	if(type!=Util.TowerType.windmill && isValid){
	    ii=getArmImageIcon(type,dir);
	    arm = ii.getImage();
	}
		
    }
    
    public ImageIcon getArmImageIcon(Util.TowerType type, String dir){
	
	ImageIcon ii;
	
	if(type==Util.TowerType.incenerator){
	    
	    
	    ii= new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/arm"+dir+".png"));
	    
	    
	}else if(type==Util.TowerType.compactor){
	    
	    
	    ii= new ImageIcon(this.getClass().getResource("pics/Towers/Compactor/arm"+dir+".png"));
	
	    
	}else if(type==Util.TowerType.recycle){

	    ii= new ImageIcon(this.getClass().getResource("pics/Towers/Recycle/arm"+dir+".png"));
	    
	}else{
	    ii = new ImageIcon(this.getClass().getResource("pics/Trash/paper.png"));
	}
	
	return ii;
	
    }
	    
    public ImageIcon getBaseImageIcon(Util.TowerType type, String dir,boolean valid){
	
	ImageIcon ii;
	
	if(type==Util.TowerType.incenerator){
	    
	    if(valid){
		ii= new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/base"+dir+".png"));
	    }else{
		ii= new ImageIcon(this.getClass().getResource("pics/Towers/Incenerator/base"+dir+"Invalid.png"));
	    }
	    
	}else if(type==Util.TowerType.compactor){
	    
	    if(valid){
		 ii= new ImageIcon(this.getClass().getResource("pics/Towers/Compactor/base"+dir+".png"));
	    }else{
		 ii= new ImageIcon(this.getClass().getResource("pics/Towers/Compactor/base"+dir+"Invalid.png"));
	    }
	    
	}else if(type==Util.TowerType.recycle){
	    
	    if(valid){
		     ii= new ImageIcon(this.getClass().getResource("pics/Towers/Recycle/base"+dir+".png"));
		    }else{
		     ii= new ImageIcon(this.getClass().getResource("pics/Towers/Recycle/base"+dir+"Invalid.png"));
	    }
	
	//Windmills don't rotate    
	}else if(type==Util.TowerType.windmill){
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
	    dir="East";
        }else if(dir.equalsIgnoreCase("South")){
            dir="West";
	    
	}else if(dir.equalsIgnoreCase("East")){
	    dir="South";
	    
	}else if(dir.equalsIgnoreCase("West")){
	    dir="North";
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
    
    public int getArmX(){
	return armX;
    }
    
    public int getArmY(){
	return armY;
    }
    
    public void extendArm(int dist){
	
	if(dir.equalsIgnoreCase("North")){
	    armY-=dist;
	}else if(dir.equalsIgnoreCase("Sorth")){
	    armY+=dist;
	}else if(dir.equalsIgnoreCase("East")){
	    armX-=dist;
	}else if(dir.equalsIgnoreCase("West")){
	    armY+=dist;
	}
    }
    
    
   
    public void fire(int speed){
	
		
    }
    
    public boolean getFiring(){
	return isFiring;
    }
    
    public void setFiring(boolean newFire){
	isFiring=newFire;
    }
    
    public Image getBaseImage(){
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
    
    public void setType(Util.TowerType newType){
	this.type=newType;
    }
    
    public Util.TowerType getType(){
	return type;
    }
    
    public int getRate(){
	return rate;
    }
    
    public int getFireCounter(){
	return fireCounter;
    }
    
    public Image getArmImage(){
	return arm;
    }
    
    
    public String getDirection(){
	return dir;
    }
    public void resetTower(){
	
	//Windmills don't need reseting
	if(this.type!=Util.TowerType.windmill){
	    
	    String fileString="pics/Towers/";
        	
	    if(this.type==Util.TowerType.incenerator){
        	 fileString+="Incenerator/";
	    }else if(this.type==Util.TowerType.recycle){
        	 fileString+="Recycle/";
	    }
        	
    	ImageIcon ii=new ImageIcon(this.getClass().getResource(fileString+"base.png"));
    	base=ii.getImage();
	
	}
    }
}
