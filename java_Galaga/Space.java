
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Space extends JPanel{
	//싱글톤 디자인패턴 구성을 위함
	private static Space space = new Space();
	//우주 크기 지정
	private final int b_x = 750;
	private final int b_y = 750;
	//우주선 좌표지정
	private final int s_x = b_x/2-30/2; // 중간 정렬
	private final int s_y = b_y - 100; // 하단에서 띄우기
	//게임 점수
	int score;
	
	//missile 스프라이트 모음
	private CopyOnWriteArrayList<Sprite> sprites_arr;
	private Sprite ship;
	private boolean checking;
	
	//싱글톤 디자인패턴 구성.
	public static Space getSpace() {
		return space;
	}
	
	public Space() {
		
		score = 0;
		ship = new Shipsprite(s_x, s_y);
		sprites_arr = new CopyOnWriteArrayList<Sprite>();
		sprites_arr.add(ship);
		checking = true;
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(b_x,b_y));
		setDoubleBuffered(true);
		
		
		
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent k) {
				// 키를 눌렀을 때 객체를 이동
				if(k.getKeyCode() == KeyEvent.VK_LEFT)
					ship.setDx(-3);
				if(k.getKeyCode() == KeyEvent.VK_RIGHT)
					ship.setDx(3);
				if(k.getKeyCode() == KeyEvent.VK_UP)
					ship.setDy(-3);
				if(k.getKeyCode() == KeyEvent.VK_DOWN)
					ship.setDy(3);
				//스페이스 키를 눌렀을 때 미사일 객체를 추가
				if(k.getKeyCode() == KeyEvent.VK_SPACE)
					fire_ms();
			}

			@Override
			public void keyReleased(KeyEvent k) {
				// 키를 땔 경우 이동량을 초기화
				if(k.getKeyCode() == KeyEvent.VK_LEFT)
					ship.setDx(0);
				if(k.getKeyCode() == KeyEvent.VK_RIGHT)
					ship.setDx(0);
				if(k.getKeyCode() == KeyEvent.VK_UP)
					ship.setDy(0);
				if(k.getKeyCode() == KeyEvent.VK_DOWN)
					ship.setDy(0);
			}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		setFocusable(true);
	}
	
	//미사일 객체를 추가하여 sprite 배열에 추가하는 함수.
	public void fire_ms() {
		sprites_arr.add(new Mssprite(ship.getX()+10, ship.getY()));
	}
	//자동으로 발사하는 beam1 객체를 배열에 추가해주는 함수
	public void fire_beam() {
		sprites_arr.add(new beam1(ship.getX()+10, ship.getY()));
	}
	// 상대 우주선의 공격빔 객체를 추가해주는 함수.
	public void fire_beam2(int x, int y) {
		sprites_arr.add(new beam2(x,y));
	}
	//특정 조건일 때 beam3 객체를 배열에 추가해주는 함수
	public void item_boom(int x, int y) {
		sprites_arr.add(new beam3(x,y));
	}
	
	//몬스터 객체를 배열에 추가해주는 함수
	public void mob_add(int x, int y, int t) {
		Mobsprite ms = new Mobsprite(x,y);
		ms.type = t;
		sprites_arr.add(ms);
	}
	
	//game over 시행
	public void game_over() {
		JOptionPane.showMessageDialog(null,"게임 오버");
		System.exit(0);
	}
	
	@Override
	public void paintComponent(Graphics g ) {
		super.paintComponent(g);
		//저장된 sprite 모두 그리기
		for(Sprite sp:sprites_arr)
			sp.draw(g);
		g.setColor(Color.white);
		g.drawString("SCORE : " + score,200,50);
	}
	
	
	//해당하는 객체를 sprite 배열에서 삭제해주는 함수
	public void remove(Sprite sp) {	
		sprites_arr.remove(sp);
	}
	
	//게임을 실행해주는 함수
	public void play_loop() {
		int b_cnt = 0;
		int m_cnt = 0;
		while(checking) {
				
			//저장된 sprite 모두 움직이기.
			for(Sprite sp: sprites_arr) {
				sp.move();
			}
			/* for문을 사용할 경우
			for(int i = 0; i< sprites_arr.size() ; i++) {
				Sprite sp = sprites_arr.get(i);
				sp.move();
			}
			*/
			// 충돌감지 및 충돌검사
			for(int i = 0; i< sprites_arr.size(); i++) {
				for(int j = i+1; j<sprites_arr.size(); j++) {
					Sprite me = sprites_arr.get(i);
					Sprite other = sprites_arr.get(j);
					if(me.checkCollision(other)) {
						me.handleCollision(other);
						other.handleCollision(me);
					}
				}
			}
			
			
			
			if(b_cnt == 30) {// 30번 화면 움직일 때마다 자동 발사
				fire_beam();
				b_cnt = 0;
			}
			// 특정 카운트에 몬스터를 추가함.
			if(m_cnt == 20) {
				mob_add(0,450, 1);
				m_cnt = 0;
			}
			if(m_cnt == 10) {
				mob_add(750,450,3);
			}
			repaint();
			m_cnt++;
			b_cnt++;
			try {
				Thread.sleep(15);
			} catch (Exception e) {}
					
		}
	}


}
