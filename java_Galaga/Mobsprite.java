
public class Mobsprite extends Sprite {
	private Space space_board;
	int fps_cnt;
	int type;
	//생성자 정의
	public Mobsprite(int x, int y){
		super("C:\\Users\\dbsgh\\Desktop\\mob.png");
		//이미지 크기 지정
		setIx(30);
		setIy(30);
		//이미지 좌표 지정
		setX(x);
		setY(y);
		type = 1;
		fps_cnt = 360;
	}
	
	public void circle1() {
		// 괄호 첫 값이 반지름의 크기
		float speedx = (150 *(float)Math.sin(Math.toRadians(360-fps_cnt)));
		float speedy = (150 *(float)Math.cos(Math.toRadians(360-fps_cnt)));
		// + 에 넣는 값이 기준 좌표 값
		x = (int)speedx+350-100;
		y = (int)speedy+450-150;
		
		if(fps_cnt == 0)
			fps_cnt = 360;
		else
			fps_cnt--;
	}
	public void circle2() {
		// 괄호 첫 값이 반지름의 크기
		float speedx = (150 *(float)Math.sin(Math.toRadians(360-fps_cnt)));
		float speedy = (150 *(float)Math.cos(Math.toRadians(360-fps_cnt)));
		// + 에 넣는 값이 기준 좌표 값
		x = (int)speedx+500-100;
		y = (int)speedy+450-150;
		
		if(fps_cnt == 360)
			fps_cnt = 0;
		else
			fps_cnt++;
	}
	@Override
	public void move() {
		space_board = Space.getSpace();
		if(Math.random()>0.999)//0.001% 확률로 미사일 발사
			space_board.fire_beam2(x, y);
		switch(type) {
		case 1:
			dx = 5;
			super.move();
			if(x > 250)
				type = 2;
			break;
		case 2:
			circle1();
			break;
		case 3:
			dx = -5;
			super.move();
			if(x < 400){
				fps_cnt = 0;
				type = 4;
			}
			break;
		case 4:
			circle2();
			break;
		}
	}
}
