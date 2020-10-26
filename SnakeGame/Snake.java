import java.util.ArrayList;

public class Snake {
	Position head_position;
	Position real_position;
	String dir="right";
	public int length = 2;
	ArrayList<Position> tail = new ArrayList<Position>();
	
	Snake(){
		head_position = new Position(112, 112);
		real_position = new Position(0, 0);
		for(int i=0;i<=length;i++) {
			tail.add(new Position(head_position.getX() - (i*31),head_position.getY()));
		}
			
	}
	
	public void PrintHeadPos() {
		System.out.println("head_pos:" + tail.get(0).getX() + ", " + tail.get(0).getY());
	}
	
	void Feed(String dir) {
		
	}
	
	
	void Move(String dir){
		for(int i=length;i>0;i--) {
			tail.get(i).setPos(tail.get(i-1).getX(), tail.get(i-1).getY());
		}
		
		switch(dir) {
		
		case "up":
			head_position.setPos(head_position.getX(), head_position.getY()-31);
			tail.get(0).setPos(tail.get(0).getX(), tail.get(0).getY()-31);
			break;
			
		case "down":
			head_position.setPos(head_position.getX(), head_position.getY()+31);
			tail.get(0).setPos(tail.get(0).getX(), tail.get(0).getY()+31);
			break;
			
		case "left":
			head_position.setPos(head_position.getX()-31, head_position.getY());
			tail.get(0).setPos(tail.get(0).getX()-31, tail.get(0).getY());
			break;
			
		case "right":
			head_position.setPos(head_position.getX()+31, head_position.getY());
			tail.get(0).setPos(tail.get(0).getX()+31, tail.get(0).getY());
			break;
		}
		
	}
	
}
