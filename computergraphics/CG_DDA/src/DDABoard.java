import javax.swing.JFrame;

public class DDABoard extends JFrame{
	
	public DDABoard() {
		add(new DDA());
		setTitle("DDA Algorithm");
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		DDABoard ddab = new DDABoard();
	}
}
