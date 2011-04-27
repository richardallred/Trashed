import java.util.ArrayList;

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
			speed =2;     //high for testing purposes, should be 2
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);


		}else if (level ==2){
			num =20;
			spacing = 85;
			speed =3;
			types.add(Util.TrashType.paper);

		}else if (level ==3){
			num = 25;
			spacing = 85;
			speed =3;
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==4){
			num = 30;
			spacing= 80;
			speed = 4;
			types.add(Util.TrashType.plastic);
			types.add(Util.TrashType.aluminum);
		}else if (level ==5){
			num = 30;
			spacing= 60;
			speed = 5;
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.plastic);
			types.add(Util.TrashType.aluminum);
		}else if (level ==6){
			num = 35;
			spacing= 50;
			speed = 6;
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
		}else if (level ==7){
			num = 40;
			spacing= 46;
			speed = 7;
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.aluminum);
		}else if (level ==8){
			num = 35;
			spacing= 50;
			speed = 6;
			types.add(Util.TrashType.aluminum);
			types.add(Util.TrashType.metal);

		}else if (level ==9){
			num = 45;
			spacing= 50;
			speed = 7;
			types.add(Util.TrashType.plastic);
			types.add(Util.TrashType.styrofoam);
		}else if (level ==10){
			num = 48;
			spacing= 45;
			speed = 7;
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.plastic);
			types.add(Util.TrashType.aluminum);
		}else if (level ==11){
			num = 48;
			spacing= 40;
			speed = 7;
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.plastic);
			types.add(Util.TrashType.aluminum);
			types.add(Util.TrashType.paper);
		}
		else if (level ==12){
			num = 20;
			spacing= 40;
			speed = 7;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.paper);
		}else if (level ==13){
			num = 40;
			spacing= 37;
			speed = 7;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.aluminum);
		}else if (level ==14){
			num = 40;
			spacing= 37;
			speed = 8;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
		}else if (level ==15){
			num = 40;
			spacing= 34;
			speed = 8;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.aluminum);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
		}else if (level ==16){
			num = 20;
			spacing= 34;
			speed = 8;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
		}else if (level ==17){
			num = 50;
			spacing= 30;
			speed = 9;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==18){
			num = 50;
			spacing= 27;
			speed = 9;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==19){
			num = 50;
			spacing= 23;
			speed = 10;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==20){
			num = 60;
			spacing= 25;
			speed = 10;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==21){
			num = 65;
			spacing= 21;
			speed = 11;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==22){
			num = 65;
			spacing= 17;
			speed = 12;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==23){
			num = 65;
			spacing= 14;
			speed = 13;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==24){
			num = 65;
			spacing= 14;
			speed = 13;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}else if (level ==25){
			num = 65;
			spacing= 10;
			speed = 14;
			types.add(Util.TrashType.food);
			types.add(Util.TrashType.nuclear);
			types.add(Util.TrashType.metal);
			types.add(Util.TrashType.styrofoam);
			types.add(Util.TrashType.paper);
			types.add(Util.TrashType.plastic);
		}



		int curX=-30;


		for(int i=0; i<num; i++){
			Trash curTrash=new Trash(curX-spacing, initY,speed,types.get((int)(Math.random()*types.size())));
			wave.add(curTrash);
			curX-=spacing;
		}


		return wave;




	}

	public ArrayList<ImageIcon> getMessages(int level) {
		ArrayList<ImageIcon> temp = new ArrayList<ImageIcon>();
		// JJrandomShit
		if (level == 1)
			temp.add(new ImageIcon(this.getClass().getResource(
			"pics/welcome1.png")));
		if (level == 2)
			temp.add(new ImageIcon(this.getClass().getResource(
			"pics/welcome2.png")));
		if (level == 3)
			temp.add(new ImageIcon(this.getClass().getResource(
			"pics/welcome3.png")));
		return temp;
	}
}

