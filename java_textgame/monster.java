public class Monster extends Sprite {
	//생성자 정의
	public Monster() {
		super();
	}
		
	@Override
	void move(char c) {
		boolean check = true; // 몬스터 랜덤이동에 쓰일 변수
		int M_move; // 몬스터 이동에 대한 걸 지정해줄 변수
		while(check) { // 값을 넘지 않는 선에서 몬스터 랜덤 이동.
			M_move = (int)(Math.random()*4);
			switch(M_move) {
			case 0:
				if(this.getX()>1) {
					this.x -= 1;
					check = false;
				}
				break;
			case 1:
				if(this.getY()>1) {
					this.y -= 1;
					check = false;
				}
				break;
			case 2:
				if(this.getY()<11) {
					this.y += 1;
					check = false;
				}
				break;
			case 3:
				if(this.getX()<21) {
					this.x += 1;
					check = false;
				}
				break;
			}
		}
	}
}
