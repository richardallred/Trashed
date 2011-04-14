import java.util.ArrayList;
import javax.swing.ImageIcon;

import javax.swing.*;

//update
public class WaveGen {
    
    private ArrayList<Trash> wave= new ArrayList<Trash>();
    
    
    public WaveGen(int num, int spacing, double speed, int initY, Util.TrashType[] types){
	
	int curX=-30;
	
	for(int i=0; i<num; i++){
	    Trash curTrash=new Trash(curX-spacing, initY,speed,types[i%types.length]);
	    wave.add(curTrash);
	    curX-=spacing;
	}
	
	
	
    }
    
    
    public ArrayList<Trash> getWave(){
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
