public class beam2 extends Sprite{
	private Space space_board;
	//생성자 정의
	public beam2(int x, int y){
		super("C:\\Users\\dbsgh\\Desktop\\beam2.png");
		//이미지 크기 지정
		setIx(10);
		setIy(10);
		//이미지 좌표 지정
		setX(x);
		setY(y);
		dy = 5;
	}
	public void move() {
		space_board = Space.getSpace();
		super.move();
		if(y>750)
			space_board.remove(this);
	}
}
