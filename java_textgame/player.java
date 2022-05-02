public class Player extends Sprite{
	//생성자 정의
	public Player() {
		super();
	}
	
	@Override
	void move(char c) { // 입력받은 대로 좌표 이동
		switch(c) {
		case 'h':
			this.x -= 1;
			break;
		case 'j':
			this.y -= 1;
			break;
		case 'k':
			this.y += 1;
			break;
		case 'l':
			this.x += 1;
			break;
		}
	}
}
