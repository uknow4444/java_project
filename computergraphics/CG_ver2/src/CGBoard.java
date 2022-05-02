import javax.swing.JFrame;
/*
 * 컴퓨터그래픽스 프로그램 짜기
 * 
 * 
 * CG : 통합하여 실행해주는 클래스
 * 
 * xy_pos :  좌표값을 저장하는데 사용하는 클래스
 * + 안티앨리어싱 적용이 필요하다면 추가적으로 rpg 값을 넣어주는 것도 좋을 듯.
 * 
 * Shape : 도형, 직선 등을 그리는 알고리즘을 추가해주는 각 도형들의 클래스.
 * 좌표를 저장하는 배열
 * 필요한 저장한 배열의 크기
 * 알고리즘이 적힘.
 *
 * Line
 * Circle
 * Triangle
 * 
 * 
 * 
 */
public class CGBoard extends JFrame{
	
	public CGBoard() {
		add(new CG());
		setSize(1300,900);
		setTitle("CG Algorithm");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	
	public static void main(String[] args) {
		CGBoard b = new CGBoard();
	}
}
