abstract class Sprite {
	int x;
	int y;
	
	//설정자 및 접근자 정의
	public int getX() {
		return x;	}
	public void setX(int x) {
		this.x = x;	}
	public int getY() {
		return y;	}
	public void setY(int y) {
		this.y = y;	}
	
	
	//생성자 정의
	public Sprite() { // 생성시 필드 위 랜덤 위치로 생성
		this.x = (int)(Math.random()*21+1);
		this.y = (int)(Math.random()*11+1);
	}
	
	abstract void move(char c); // 추상 메소드
}
