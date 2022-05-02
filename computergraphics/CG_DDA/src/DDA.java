import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DDA extends JPanel{
	private final int pix = 5;
	private final int lx = 100;
	private final int ly = 150;
	private final int bx = pix * ly;
	private final int by = pix * lx;
	private int count = 0;
	private ArrayList<xypos> xy_arr;
	private gpanel gp;
	private xypos sub_xy;
	class xypos{
		public int x;
		public int y;
		public xypos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	class gpanel extends JPanel{
		public gpanel() {
			setPreferredSize(new Dimension(bx+1, by+1));
			addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					double x1,y1,x2,y2,dx,dy, m;
					int step;
					if(count == 0) {
						x1 = Math.floor((Math.floor(e.getX())/5));
						y1 = 100 - Math.floor(Math.floor(e.getY())/5)+1;
						sub_xy = new xypos((int)x1,(int)y1);
						xy_arr.add(sub_xy);
						gp.re_paint();
						count++;
					}
					else {
						x2 = Math.floor(Math.floor(e.getX()) / 5);
						y2 = 100 - Math.floor(Math.floor(e.getY()) / 5)+1;
						x1= sub_xy.x;
						y1= sub_xy.y;
						count = 0;
						
						dx = x2 - x1;
						dy = y2 - y1;
						m = dx/dy;
						//기울기가 0보다 작을 때
						if(Math.abs(dx) > Math.abs(dy))
							step = (int)Math.abs(dx);
						else
							step = (int)Math.abs(dy);
						dx /= step;
						dy /= step;
						for(int i = 0; i < step; i++) {
							x1 += dx;
							y1 += dy;
							xy_arr.add(new xypos((int)Math.round(x1), (int)Math.round(y1)));
						}
						
						gp.re_paint();
					}
					
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {}
				@Override
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseReleased(MouseEvent arg0) {}
				
			});
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			// 가로줄
			for(int i = 0; i < lx + 1; i++) {
				g.drawLine(0, i*pix, bx, i*pix);
			}
			// 세로줄
			for(int i = 0; i < ly + 1; i++) {
				g.drawLine(i*pix, 0, i*pix, by);
			}
			for(xypos p : xy_arr) {
				g.fillRect(p.x * 5, by - p.y* 5, pix, pix);
			}
		}
		public void re_paint() {
			repaint();
		}
	}
	class bpanel extends JPanel{
		JTextField x1;
		JTextField y1;
		JTextField x2;
		JTextField y2;
		JButton b;
		public bpanel() {
			setPreferredSize(new Dimension(250, 150));
			setLayout(new GridLayout(0, 4, 2, 2));
			add(new JLabel(""));
			add(new JLabel("X"));
			add(new JLabel("Y"));
			add(new JLabel(""));
			add(new JLabel("1"));
			x1 = new JTextField();
			y1 = new JTextField();
			add(x1);
			add(y1);
			b = new JButton("확인");
			b.addActionListener(e->{
				int step;
				double x,y,dx,dy;;
				double m;
				x = Double.parseDouble(x1.getText());
				y = Double.parseDouble(y1.getText());
				xy_arr.add(new xypos((int)x,(int)y));
				dx = (Double.parseDouble(x2.getText()) - Double.parseDouble(x1.getText()));
				dy = (Double.parseDouble(y2.getText()) - Double.parseDouble(y1.getText()));
				m = dx/dy;
				//기울기가 0보다 작을 때
				if(Math.abs(dx) > Math.abs(dy))
					step = (int)Math.abs(dx);
				else
					step = (int)Math.abs(dy);
				dx /= step;
				dy /= step;
				for(int i = 0; i < step; i++) {
					x += dx;
					y += dy;
					xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
				}
				
				gp.re_paint();
			});
			add(b);
			add(new JLabel("2"));
			x2 = new JTextField();
			y2 = new JTextField();
			add(x2);
			add(y2);
		}
	}

	
	public DDA() {
		// 더블버퍼링을 허용한다. - 깜박임을 방지한다.
		setDoubleBuffered(true);
		setLayout(new FlowLayout());
		gp = new gpanel();
		add(gp);
		add(new bpanel());
		xy_arr = new ArrayList<>();
	}
}
