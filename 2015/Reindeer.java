
public class Reindeer {
	public int speed;
	public int restTime;
	public int moveTime;
	public int moveLeft;
	public int restLeft;
	public int distance;
	private boolean moving;
	
	public Reindeer(int s, int m, int r) {
		speed = s;
		restTime = r;
		moveTime = m;
		moveLeft = m;
		restLeft = 0;
		distance = 0;
		moving = true;
	}
	
	public void move() {
		if(moving) {
			if(moveLeft > 0) {
				distance += speed;
				moveLeft--;
			}
			if(moveLeft == 0) {
				moving = false;
				restLeft = restTime;
			}
		} else {
			if(restLeft > 0) {
				restLeft--;
			}
			if(restLeft == 0) {
				moving = true;
				moveLeft = moveTime;
			}
		}
	}
	
}
