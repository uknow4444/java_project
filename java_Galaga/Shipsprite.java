public class Shipsprite extends Sprite {
	private Space space_board;
	public Shipsprite(int x, int y) {
		super("C:\\Users\\dbsgh\\Desktop\\ship.png");
		//이미지 크기 지정
		setIx(30);
		setIy(30);
		//이미지 좌표 지정
		setX(x);
		setY(y);
		space_board = Space.getSpace();
	}
	@Override
	public void move() {
		super.move();
		
		//만약 화면이 넘어갔다면 넘어가지 않도록 고정시킨다.
		if(getX()<0)
			setX(0);
		if(getX()>720)
			setX(720);
		if(getY()<0)
			setY(0);
		if(getY()>720)
			setY(720);
	}
	@Override
	public void handleCollision(Sprite other) {
		space_board = Space.getSpace();
		// 괴물 함선 or 괴물이 공격에 맞을 경우 게임 끝
		if (other instanceof Mobsprite) {
			space_board.game_over();
		}
		if(other instanceof beam2) {
			space_board.game_over();
		}
	}
}
