import java.util.Random; 

public class Apple {
	Position apple_pos;
	Random rand = new Random();
	int rand_x;
	int rand_y;
	Apple(){
		rand_x = rand.nextInt(17);
		rand_y = rand.nextInt(10);
		apple_pos = new Position(50 + rand_x*31 ,50 + rand_y*31);
	}
	
	public void PrintApplePos() {
		System.out.println("apple_pos:" + apple_pos.getX() + ", " + apple_pos.getY());
	}
	
	public void resetApple(){
		rand_x = rand.nextInt(17);
		rand_y = rand.nextInt(10);
		apple_pos = new Position(50 + rand_x*31 ,50 + rand_y*31);
	}
	
}
