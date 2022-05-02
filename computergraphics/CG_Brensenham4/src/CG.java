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


public class CG extends JPanel{
	private final int pix = 5;
	private final int lx = 100;
	private final int ly = 150;
	private final int bx = pix * ly;
	private final int by = pix * lx;
	private int count = 0;
	int cnt1 = 0;
	int cnt2 = 0;
	private ArrayList<xypos> xy_arr;
	private gpanel gp;
	private xypos sub_xy;
	private long t1, t2, tsub1, tsub2, t;
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
				g.fillRect(p.x * 5, by - p.y* 5 -5, pix, pix);
			}
			xy_arr.clear();
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
			b = new JButton("DDA");
			b.addActionListener(e->{
				DDA_Algorithm();
				gp.re_paint();
			});
			add(b);
			add(new JLabel("2"));
			x2 = new JTextField();
			y2 = new JTextField();
			add(x2);
			add(y2);
			b = new JButton("BH");
			b.addActionListener(e->{
				BH_Algorithm();
				gp.re_paint();
			});
			add(b);
		}
		
		public void DDA_Algorithm() {
			resettime();
			cnt1++;
			int dx = (Integer.parseInt(x2.getText()) - Integer.parseInt(x1.getText()));
			int dy = (Integer.parseInt(y2.getText()) - Integer.parseInt(y1.getText()));
			int step;
			float x = Integer.parseInt(x1.getText());
			float y = Integer.parseInt(y1.getText());
			float xIncrement, yIncrement;
			//기울기가 0보다 작을 때
			t1 = System.nanoTime();
			if(Math.abs(dx) > Math.abs(dy))
				step = (int)Math.abs(dx);
			else
				step = (int)Math.abs(dy);
			xIncrement = (float)dx/(float)step;
			yIncrement = (float)dy/(float)step;
			xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
			for(int i = 0; i < step; i++) {
				x += xIncrement;
				y += yIncrement;
				xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
			}
			t2 = System.nanoTime();
			t = t2 - t1;
			tsub1 += t;
			System.out.println("1   : " + tsub1/cnt1 + "   "+cnt1);
		}
		
		public void BH_Algorithm() {
			resettime();
			cnt2++;
			double m, p;
			int x,y,dx,dy,subdx,subdy,x0,y0,xe,ye;
			
			x0 = Integer.parseInt(x1.getText());
			y0 = Integer.parseInt(y1.getText());
			xe = Integer.parseInt(x2.getText());
			ye = Integer.parseInt(y2.getText());
			dx = xe - x0;
			dy = ye - y0;	
			m = (double)dy/(double)dx;
			dx = Math.abs(dx);
			dy = Math.abs(dy);
			p = 2*dy -dx;
			subdx = 2 * dx;
			subdy = 2 * dy;
			t1 = System.nanoTime();
			if(m>0) {
				if(dx > dy) {
					/*
					subdx = 2 * ( dy - dx );
					subdy = 2 * dy;
					*/
					p = 2*dy -dx;
					
					if( x0 > xe ) {
						x = xe;
						y = ye;
						xe = x0;
					}
					else {
						x = x0;
						y = y0;
					}
					xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
					while(x < xe) {
						x++;
						/*
						if(p<0) p += subdy;
						else {
							y++;
							p += subdx;
						}
						*/
						if(p>=0) {
							y++;
							p -= subdx;
						}
						p += subdy;
						xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
					}
				}
				else {
					subdx = 2 * dx;
					subdy = 2 * ( dx - dy );
					p = 2*dx -dy;
					
					if( y0 > ye ) {
						x = xe;
						y = ye;
						ye = y0;
					}
					else {
						x = x0;
						y = y0;
					}
					xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
					while(y < ye) {
						y++;
						if(p<0) p += subdx;
						else {
							x++;
							p += subdy;
						}
						xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
					}
				}
			}
			else {
				if(dx > dy) {
					subdx = 2 * ( dy - dx );
					subdy = 2 * dy;
					p = 2*dy -dx;
					
					if( x0 > xe ) {
						x = xe;
						y = ye;
						xe = x0;
					}
					else {
						x = x0;
						y = y0;
					}
					xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
					while(x < xe) {
						x++;
						if(p<0) p += subdy;
						else {
							y--;
							p += subdx;
						}
						xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
					}
				}
				else {
					subdx = 2 * dx;
					subdy = 2 * ( dx - dy );
					p = 2*dx -dy;
					
					if( y0 > ye ) {
						x = xe;
						y = ye;
						ye = y0;
					}
					else {
						x = x0;
						y = y0;
					}
					xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
					while(y < ye) {
						y++;
						if(p<0) p += subdx;
						else {
							x--;
							p += subdy;
						}
						xy_arr.add(new xypos((int)Math.round(x), (int)Math.round(y)));
					}
				}
			}
			t2 = System.nanoTime();
			t = t2 - t1;
			tsub2 += t;
			System.out.println("2   : "+tsub2/cnt2 + "    "+ cnt2);
		}
	}

	
	public CG() {
		setDoubleBuffered(true);
		setLayout(new FlowLayout());
		gp = new gpanel();
		add(gp);
		add(new bpanel());
		xy_arr = new ArrayList<>();
		resettime();
	}
	
	public void resettime() {
		t = 0;
		t1 = 0;
		t2 = 0;
		tsub1 = 0;
		tsub2 = 0;
	}

}
