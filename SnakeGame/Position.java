
public class Position {
	
	private int x;
	private int y;
	
	Position(){
		
	}
	
	Position(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public void setPos(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	
}
