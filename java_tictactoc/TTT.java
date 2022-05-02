package TTT;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

 

public class MyFrame extends JFrame {

	class mybutton extends JButton {
		int type;
		public int getType() {
			return type;}
		public void setType(int type) {
			this.type = type;}
		public mybutton() { // mybutton 생성자
			super();
			type = 0;
		}
	}

	int a[][] = {{2,4,6,0,0,0},
			{0,1,2,0,0,0},
			{3,4,5,0,0,0},
			{6,7,8,0,0,0},
			{0,4,8,0,0,0},
			{2,5,8,0,0,0},
			{1,4,7,0,0,0},
			{0,3,6,0,0,0}};
	JTextField tf;
	ArrayList<mybutton> arr = new ArrayList<>();

	public MyFrame() {
		super("Tic Tac Toc");
		setSize(300,300);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(3,3,2,2));
		for(int i = 0; i<9;i++) {
			mybutton b = new mybutton();
			b.addActionListener(e->{
				if(b.type == 0) {
					b.setText("O");
					b.type = 1;
					check_ttt(); // 게임상태 체크
					ttt_process(); // 컴퓨터 작동
					check_ttt(); // 게임상태 체크
					b.setEnabled(false);
				}
			});
			arr.add(b);
			panel1.add(arr.get(i));
		}

		JPanel panel2 = new JPanel();
		tf = new JTextField();
		tf.setEditable(false);
		tf.setText("게임을 시작합니다.");
		panel2.add(tf);
		add(panel1);
		add(panel2, BorderLayout.SOUTH);		

		setVisible(true);
	}

	void ttt_process() { // 현재 상태에 따라 컴퓨터가 최적의 위치에 놓는 메소드
		if(arr.get(4).getType() == 0) { // 가운데가 비었다면 가운데로
			arr.get(4).setText("X");
			arr.get(4).type = 2;
			arr.get(4).setEnabled(false);
		}
		else { // 아닐경우
			int move = 0;
			int i,j;
            for(i =0; i<8;i++){ // X가 두 개인 곳, 컴퓨터가 승리할 수 있는 라인 찾기
            	if(a[i][3] == 2 && a[i][4] == 2 && a[i][5] == 2){
            		move = 1; // 게임이 끝날 수도 있는 상황
            		break;
            	}
            }
            if(move == 0) { // 컴퓨터가 패배할 수도 있는 라인 찾기
                for(i =0; i<8;i++){
                	if(a[i][4] == 2 && a[i][5] == 2 ){
                		move = 1; // 게임이 끝날 수도 있는 상황
                		break;
                	}
                }
            }
            if(move == 1){
				for(j = 0; j<3; j++)
					if(arr.get(a[i][j]).type == 0){ // 2개가 놓인 곳 빈칸찾기
						arr.get(a[i][j]).type = 2;
						arr.get(a[i][j]).setText("X");
						arr.get(a[i][j]).setEnabled(false);
						break;
				}
			}
            else{ // 빈 대각선 검사
            	if(arr.get(0).type == 0){
            		arr.get(0).type = 2;
            		arr.get(0).setText("X");
            		arr.get(0).setEnabled(false);
            	}
            	else if(arr.get(2).type == 0){
            		arr.get(2).type = 2;
            		arr.get(2).setText("X");
            		arr.get(2).setEnabled(false);
				}
            	else if(arr.get(6).type == 0){
            		arr.get(6).type = 2;
            		arr.get(6).setText("X");
            		arr.get(6).setEnabled(false);
            	}
            	else if(arr.get(8).type == 0){
            		arr.get(8).type = 2;
            		arr.get(8).setText("X");
            		arr.get(8).setEnabled(false);
            	}

				else{
					if(arr.get(1).type == 0){
						arr.get(1).type = 2;
						arr.get(1).setText("X");
						arr.get(1).setEnabled(false);
					}
					else if(arr.get(3).type == 0){
						arr.get(3).type = 2;			
						arr.get(3).setText("X");	
						arr.get(3).setEnabled(false);
					}
					else if(arr.get(5).type == 0){
						arr.get(5).type = 2;
						arr.get(5).setText("X");
						arr.get(5).setEnabled(false);
					}
				
					else if(arr.get(7).type == 0){					
						arr.get(7).type = 2;			
						arr.get(7).setText("X");
						arr.get(7).setEnabled(false);
					}
				}
            }

		}

	}

	void check_ttt() { // 게임상태를 체크해주는 메소드
		int o = 0;
		int x = 0;
		int i,j;
		int over=0;
		int game_check = 0;

		for(i = 0; i<8; i++) {
			if(a[i][5] == 3){
				over++;
			}
			for(j = 0; j<3; j++) // 라인 별 o와 x 개수 카운트
				if(arr.get(a[i][j]).type != 0) {
					if(arr.get(a[i][j]).type == 1)
						o++;
					else
						x++;
                    a[i][5] = o+x; // count
				}
            if(o>x){ // o가 클 경우 o의 개수 기입
            	a[i][3] = 1;
                a[i][4] = o;
            }
            if(x>o){ // x가 클 경우 x의 개수 기입
            	a[i][3] = 2;
                a[i][4] = x;
            }
			if(a[i][4] == 3){
				game_check = 1;
				break;
			}
            x=0;
            o=0;
		}
		if(over == 8)
			game_check = 2;
		System.out.println("게임체크 : "+ game_check);//////////////////////////////////////////////////////////
		if(game_check == 1) { // 누군가 승리했을 경우
			// 사실 컴퓨터가 안져서 사용자가 승리할 경우가 없음.
			tf.setText("패배하였습니다.");
			for(i = 0; i<9;i++) // 빈칸 사용불가 만들기
				arr.get(i).setEnabled(false);
		}
		if(game_check == 2) // 무승부 했을 경우.
			tf.setText("무승부입니다.");
	}
}


package TTT;

public class ttt {
	public static void main(String args[]) {
		MyFrame a = new MyFrame();
	}
}
