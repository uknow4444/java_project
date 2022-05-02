public class Gold extends Sprite{
	//생성자 정의
	public Gold() {
		super();
	}
	
	@Override
	void move(char c) { // 호출시 좌표 랜덤 변환
		this.x = (int)(Math.random()*19+1);
		this.y = (int)(Math.random()*9+1);
	}
}
