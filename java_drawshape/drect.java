import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class drect extends JFrame {
	ArrayList<xywh> list = new ArrayList<>(); // xywh 클래스형을 저장하는 arraylist 참조변수로 list를 선언하고 arraylist객체를 생성한다.
	int check = 0; // 사용자의 마우스 상태를 나타내줄 정수형 변수 check 를 선언하고 0으로 초기화한다.
	int count = 0; // 현재 list의 개수를 체크해줄 count를 선언하고 0으로 초기화한다.
	class xywh { // xywh 값을 저장할 내부 클래스 정의
		int x;
		int y;
		int w;
		int h;
	}
	class mypanel extends JPanel{ // rapaint를 시키기 위해 mypanel 클래스를 jpanel 클래스를 상속받아 정의
		protected void paintComponent(Graphics g){ // paintComponent를 재정의한다.
			g.setColor(Color.black); // g의 색을 검은색으로 지정하고
			for(xywh k : list) // for each문으로 list의 개수만큼 반복시킨다.
				g.drawRect(k.x, k.y, k.w, k.h); //list의 정보대로 사각형을 그린다.
		}
	}
	public drect() { // 생성자 정의
		super("demo of mouse event"); // 부모클래스 생성자 호출
		setSize(200,300); // 화면 크기 설정
		JButton b = new JButton("사각형 출력"); // 사각형 출력 버튼 생성
		b.addActionListener(e->{ // 람다식을 사용한 액션이벤트처리기 등록
			for(xywh k : list) // for each문을 사용하여 각각 값들을 출력
				System.out.println("x : "+k.x+" y : "+k.y+" w : "+k.w+" h : "+k.h);
		});
		add(b, BorderLayout.SOUTH); // 배치관리자 BorderLayout을 통해 남쪽에 버튼 추가
		mypanel mp = new mypanel(); // mypanel형 참조변수 mp를 선언하고 mypanel 객체를 생성한다.
		mp.addMouseMotionListener(new MouseMotionListener() { // mp에 mousemotionlistener 이벤트 처리기를 무명클래스로 등록한다.
			@Override
			public void mouseDragged(MouseEvent e) { // 드래그시
				if (check == 0) { // 한번만 실행하도록한다.
					xywh in = new xywh(); // 새로 xywh 객체를 생성하고,
					in.x = e.getX(); // 마우스 포인터의 x 값을 xywh 객체의 x값으로 넣는다.
					in.y = e.getY(); // 마우스 포인터의 y 값을 xywh 객체의 y값으로 넣는다.
					list.add(in); // list에 추가하고
					check = 1; // check를 1로 바꾼다. 드래그가 진행중인 동안엔 check는 1로 유지된다.
					count++; // 카운트를 1 추가한다.
				}
				if(check == 1) { // 드래그 중이라면
					list.get(count-1).w = e.getX() - list.get(count-1).x; // 추가된 list의 xywh 객체의 w값에 현재 마우스 x좌표값  - xywh의 x를 한다.(넓이를 측정하기 위함)
					list.get(count-1).h = e.getY() - list.get(count-1).y; // 추가된 list의 xywh 객체의 h값에 현재 마우스 y좌표값  - xywh의 y를 한다.(넓이를 측정하기 위함)
				}
				repaint(); // mypanel의 paintcomponent를 사용하여 panel을 새로 그린다.
			}
			@Override
			public void mouseMoved(MouseEvent e) { // 마우스가 움직이는 동안에는 
				check = 0; // check를 0으로 한다.
			}
		});
		add(mp); // 컨테이너에 center에 추가한다.
		setVisible(true); // 창이 보이도록한다.
	}
	public static void main(String args[]) { // main함수 정의
		drect  fr = new drect(); // drect클래스형 참조변수 fr에 drect객체 생성
	}
}
