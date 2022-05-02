import javax.swing.JFrame;

public class CGBoard extends JFrame{
	
	public CGBoard() {
		add(new CG());
		setTitle("CG Algorithm");
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		CGBoard b = new CGBoard();
	}
}
