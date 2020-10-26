import java.util.ArrayList;

public class Snake {
	Position start_position;
	public int length = 2;
	private final int CELL_LGTH=31;
	
	ArrayList<Position> tail = new ArrayList<Position>();
	
	Snake(){
		start_position = new Position(112, 112);
		for(int i=0;i<=length;i++) {
			tail.add(new Position(start_position.getX() - (i*CELL_LGTH),start_position.getY()));
		}
	}
	
	
	void eat() {
		length++;
		tail.add(new Position());
	}
	
	
	void move(String dir){
		for(int i=length;i>0;i--) {
			tail.get(i).setPos(tail.get(i-1).getX(), tail.get(i-1).getY());
		}
		
		switch(dir) {
		
		case "up":
			tail.get(0).setPos(tail.get(0).getX(), tail.get(0).getY()-CELL_LGTH);
			break;
			
		case "down":
			tail.get(0).setPos(tail.get(0).getX(), tail.get(0).getY()+CELL_LGTH);
			break;
			
		case "left":
			tail.get(0).setPos(tail.get(0).getX()-CELL_LGTH, tail.get(0).getY());
			break;
			
		case "right":	
			tail.get(0).setPos(tail.get(0).getX()+CELL_LGTH, tail.get(0).getY());
			break;
		}
	}
}
