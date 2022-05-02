import javax.swing.JFrame;

public class Mainframe extends JFrame{
	private Space spaceboard;
	public Mainframe() {
		setTitle("Space WAR");
		spaceboard = Space.getSpace(); 
		add(spaceboard);
		pack();
		setVisible(true);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		Mainframe mf = new Mainframe(); 
		mf.spaceboard.play_loop();
	}
}
