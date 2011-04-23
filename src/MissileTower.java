import java.util.ArrayList;

public class MissileTower extends Tower{

	ArrayList<Trash> trash = new ArrayList<Trash>();
	ArrayList<Missile> missile = new ArrayList<Missile>();
	private double missileSpeed;
	MissileTower(int initX, int initY, int fireRate, int towerRange, Util.TowerType type, boolean isValid, String dir, double missileSpeed) {
		super(initX, initY, fireRate, towerRange, type, isValid, dir);
		// TODO Auto-generated constructor stub
		this.missileSpeed = missileSpeed;
	}

	public void fire() {
		for(int i=0; i<missile.size();i++){
			missile.get(i).move();
			if(missile.get(i).detectCollision()){
				trash.get(i).removeImage();
				trash.remove(i);
				missile.remove(i);
			}
		}
		if(super.fireCounter < super.rate){
			super.fireCounter++;
		}else{
			super.fireCounter = 0;
			super.isFiring = false;
		}
	}
	
	public void setFiring(boolean newFire, Trash aTrash) {
		trash.add(aTrash);
		missile.add(new Missile(super.getX(), super.getY(), aTrash.getFutureX(), aTrash.getFutureY(), missileSpeed));
		super.isFiring = newFire;
	}
}
