public class beam1 extends Sprite {
	private Space space_board;
	//생성자 정의
	public beam1(int x, int y){
		super("C:\\Users\\dbsgh\\Desktop\\beam1.png");
		//이미지 크기 지정
		setIx(10);
		setIy(10);
		//이미지 좌표 지정
		setX(x);
		setY(y);
		setDy(-5);
		space_board = Space.getSpace();
	}
	@Override
	public void move() {
		super.move();
		if(y < 0) // y좌표를 넘어갈 때 해당 객체를 삭제한다.
			space_board.remove(this);
	}
	
	@Override
	public void handleCollision(Sprite other) {
		//자동발사 미사일과 적이 충돌하였다면 둘 다 삭제
		if(other instanceof Mobsprite) {
			space_board.score +=10;
			space_board.remove(other);
			space_board.remove(this);
		}
	}
}
