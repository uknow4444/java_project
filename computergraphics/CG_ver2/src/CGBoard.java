import javax.swing.JFrame;
/*
 * ��ǻ�ͱ׷��Ƚ� ���α׷� ¥��
 * 
 * 
 * CG : �����Ͽ� �������ִ� Ŭ����
 * 
 * xy_pos :  ��ǥ���� �����ϴµ� ����ϴ� Ŭ����
 * + ��Ƽ�ٸ���� ������ �ʿ��ϴٸ� �߰������� rpg ���� �־��ִ� �͵� ���� ��.
 * 
 * Shape : ����, ���� ���� �׸��� �˰����� �߰����ִ� �� �������� Ŭ����.
 * ��ǥ�� �����ϴ� �迭
 * �ʿ��� ������ �迭�� ũ��
 * �˰����� ����.
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
