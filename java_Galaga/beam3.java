public class beam3 extends Sprite {
	private Space space_board;
	//생성자 정의
	public beam3(int x, int y){
		super("C:\\Users\\dbsgh\\Desktop\\beam3.png");
		//이미지 크기 지정
		setIx(30);
		setIy(30);
		//이미지 좌표 지정
		setX(x-5);
		setY(y);
		space_board = Space.getSpace();
	}
	@Override
	public void move() {
		setIx(getIx()+10);
		setIy(getIy()+10);
		setX(getX()-5);
		setY(getY()-5);
		if(getIy() > 500) {
			space_board.remove(this);
		}
	}
	
	@Override
	public void handleCollision(Sprite other) {
		if(other instanceof Mobsprite) {
			space_board.score +=100;
			space_board.remove(other);
		}
		if(other instanceof beam2) {
			space_board.score +=100;
			space_board.remove(other);
		}
	}
}
