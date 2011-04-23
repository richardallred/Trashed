import java.util.ArrayList;
import javax.swing.ImageIcon;

import javax.swing.*;

//update
public class WaveGen {
    Board theBoard;
    
	public WaveGen(Board aBoard){
		theBoard=aBoard;
	}

    private ArrayList<Trash> wave= new ArrayList<Trash>();

    
    public ArrayList<Trash> getWave(int level){
    	int num =0;
    	int spacing = 0;
    	int initY= Util.pathPad;
    	int speed= 0;
    	ArrayList<Util.TrashType> types = new ArrayList<Util.TrashType>();
    	if (level ==1){
    	num =10;
    	spacing = 135;
    	speed =2;
    	types.add(Util.TrashType.paper);
    		
    	}else if (level ==2){
    	
    	
    	num =20;
    	spacing = 85;
    	speed =3;
    	types.add(Util.TrashType.paper);
    	
    	}
    	int curX=-30;
    	
    	for(int i=0; i<num; i++){
    	    Trash curTrash=new Trash(curX-spacing, initY,speed,types.get(i%types.size()));
    	    wave.add(curTrash);
    	    curX-=spacing;
    	}
    
    	return wave;
    	
    	
    }

    public ArrayList<ImageIcon> getMessages(int level) {
		ArrayList<ImageIcon> temp = new ArrayList<ImageIcon>();
		// JJrandomShit
		if (level == 1)
			temp.add(new ImageIcon(this.getClass().getResource("pics/welcome1.png")));
		if (level == 2)
			temp.add(new ImageIcon(this.getClass().getResource("pics/welcome2.png")));
		return temp;
	}
}
