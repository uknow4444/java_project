public class Mssprite extends Sprite{
	private Space space_board;
	//생성자 정의
	public Mssprite(int x, int y) {
		super("C:\\Users\\dbsgh\\Desktop\\mis.png");
		//이미지 크기 지정
		setIx(20);
		setIy(30);
		//이미지 좌표 지정
		setX(x);
		setY(y);
		//미사일은 쭉 올라가기 때문에 움직일 이동량을 지정
		setDy(-3);
		space_board = Space.getSpace();
	}
	@Override
	public void move() {
		super.move();
		if(y < 300) {// y좌표를 넘어갈 때 해당 객체를 삭제한다.
			space_board.item_boom(getX(), getY());
			space_board.remove(this);
		}
	}
}
