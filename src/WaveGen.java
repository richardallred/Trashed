import java.util.ArrayList;


public class WaveGen {
    
    private ArrayList<Trash> wave= new ArrayList<Trash>();
    
    
    public WaveGen(int num, int spacing, double speed, int initY, Trash.TrashType[] types){
	
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

}
