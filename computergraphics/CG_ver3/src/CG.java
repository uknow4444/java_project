import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import com.sun.java.swing.plaf.windows.resources.windows;


@SuppressWarnings("serial")
public class CG extends JPanel{
	private final int pix = 4;
	private final int lx = 200;
	private final int ly = 200;
	private final int sx = lx/2;
	private final int sy = ly/2;
	private final int bx = pix * ly;
	private final int by = pix * lx;
	
	private ArrayList<xypos> xy_arr;
	private ArrayList<Shape> shape_arr;
	private ArrayList<Shape> window_arr;
	
	private gpanel gp; // �׸��� �׸��� �г�
	private JPanel Transp; // Ʈ������ �˰��� ��ư �г�
	private JPanel lp; // ������ �׸��� �г�
	private JPanel cp; // ���� �׸��� �г�
	private JPanel tp; // �ﰢ���� �׸��� �г�
	
	private JButton Line_button;
	private JButton Circle_button;
	private JButton Triangle_button;
	
	// ��ǥ �Է� ���� ��ü��
	private JTextField x1, y1, x2, y2, x3, y3;
	private JTextField x4, y4, x5, y5, x6, y6; // �ﰢ����
	// ������ �������� �Է¹��� ����
	private JTextField radius;
	// x,y�� �����ϸ��� �̿��� ������
	private JTextField x_scale, y_scale;
	// ȸ�� �߽��� ��ǥ ���� �� ȸ�� �� ����
	private JTextField x_rotate, y_rotate, rotate;
	// �̵��� ��Ű�� ����
	private JTextField x_move, y_move;
	
	// ���� �׸��� ���� Ÿ��
	// 0 = line, 1 = circle, 2 = triangle
	private int shape_type;
	
	//������ window ���� �ϴ� ����
	private Rectangle rect;
	//window�� �׸��� ���� Ȯ���ϴ� ����.
	private boolean window_check;
	private boolean checking = true;
	
	class gpanel extends JPanel{
		public gpanel() {
			setPreferredSize(new Dimension(bx+1, by+1));
			addMouseMotionListener(new MouseMotionListener(){	
				@Override
				public void mouseDragged(MouseEvent e) {
					if(checking) {
						final double startx = Math.floor(e.getX()/pix)-sx-1;
						final double starty = sy - Math.floor(e.getY()/pix);
						rect = new Rectangle();
						rect.arr[0].x = (int) startx;
						rect.arr[0].y = (int) starty;
						rect.arr[1].x = (int) startx;
						rect.arr[1].y = (int) starty;
						window_check = true;
						window_arr.add(rect);
						checking = false;
					}
					else {
						rect.arr[1].x = (int) Math.floor(e.getX()/pix)-sx-1;
						rect.arr[1].y = sy - (int) Math.floor(e.getY()/pix);
					}
					gp.re_paint();
				}
				@Override
				public void mouseMoved(MouseEvent e) {
					checking = true;
				}
			});
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			if(window_check) { // window  �׸���
				window_arr.removeIf(e->(e instanceof Line));
				ArrayList<Shape> sub_rect = new ArrayList<>();
				for(Shape s: window_arr)
					if(s instanceof Rectangle) {
						sub_rect.add(s);
					}
				for(Shape s: sub_rect)
					setwindow(s);
				for(Shape s: window_arr)
					setpix(s.setdraw());
			}
			else {
				for(Shape s: shape_arr)
					setpix(s.setdraw());
			}
			// ������
			for(int i = 0; i < lx + 1; i++) {
				g.drawLine(0, i*pix, bx, i*pix);
			}
			// ������
			for(int i = 0; i < ly + 1; i++) {
				g.drawLine(i*pix, 0, i*pix, by);
			}
			for(xypos p : xy_arr) {
				g.fillRect((p.x + sx) * pix, by - ( p.y + sy )* pix - pix, pix, pix);
			}
			xy_arr.clear();
		}
		public void re_paint() {
			repaint();
		}
	}
	
	// ���׸��� �г�
	class Linepanel extends JPanel{
		JButton b;
		public Linepanel() {
			setPreferredSize(new Dimension(250, 150));
			setLayout(new GridLayout(0, 4, 2, 2));
			add(new JLabel(""));
			add(new JLabel("X"));
			add(new JLabel("Y"));
			add(new JLabel(""));
			add(new JLabel("1"));
			add(x1);
			add(y1);
			b = new JButton("DDA");
			b.addActionListener(e->{
				xypos xy0 = new xypos(Integer.parseInt(x1.getText()),Integer.parseInt(y1.getText())); 
				xypos xy1 = new xypos(Integer.parseInt(x2.getText()),Integer.parseInt(y2.getText()));
				Line test = new Line(xy0,xy1);
				shape_arr.add(test);
				setpix(test.DDA());
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
				xypos xy0 = new xypos(Integer.parseInt(x1.getText()),Integer.parseInt(y1.getText())); 
				xypos xy1 = new xypos(Integer.parseInt(x2.getText()),Integer.parseInt(y2.getText()));
				shape_arr.add(new Line(xy0,xy1));
				x_rotate.setText(x1.getText());
				y_rotate.setText(y1.getText());
				gp.re_paint();
			});
			add(b);
		}
	}
	
	// �� �׸��� �г�
	class Circlepanel extends JPanel{
		public Circlepanel() {
			System.out.println("����");
			setPreferredSize(new Dimension(250, 150));
			setLayout(new GridLayout(0, 3, 2, 2));
			add(new JLabel(""));
			add(new JLabel("X"));
			add(new JLabel("Y"));
			add(new JLabel("��ǥ"));
			add(x3);
			add(y3);
			add(new JLabel("������"));
			add(radius);
			JButton b = new JButton("�� �׸���");
			b.addActionListener(e->{
				xypos xy3 = new xypos(Integer.parseInt(x3.getText()),Integer.parseInt(y3.getText()));
				int r = Integer.parseInt(radius.getText());
				shape_arr.add(new Circle(xy3, r));
				x_rotate.setText(x3.getText());
				y_rotate.setText(y3.getText());
				gp.re_paint();
			});
			add(b);
		}
	}

	// �ﰢ�� �׸��� �г�
	class Trianglepanel extends JPanel{
		public Trianglepanel() {
			setPreferredSize(new Dimension(250, 150));
			setLayout(new GridLayout(0, 3, 2, 2));
			add(new JLabel(""));
			add(new JLabel("X"));
			add(new JLabel("Y"));
			add(new JLabel("��ǥ 1"));
			add(x4);
			add(y4);
			add(new JLabel("��ǥ 2"));
			add(x5);
			add(y5);
			add(new JLabel("��ǥ 3"));
			add(x6);
			add(y6);
			add(new JLabel(""));
			JButton b = new JButton("�ﰢ�� �׸���");
			b.addActionListener(e->{
				xypos xy4 = new xypos(Integer.parseInt(x4.getText()),Integer.parseInt(y4.getText()));
				xypos xy5 = new xypos(Integer.parseInt(x5.getText()),Integer.parseInt(y5.getText()));
				xypos xy6 = new xypos(Integer.parseInt(x6.getText()),Integer.parseInt(y6.getText()));
				shape_arr.add(new Triangle(xy4, xy5, xy6));
				x_rotate.setText(x4.getText());
				y_rotate.setText(y4.getText());
				gp.re_paint();
			});
			add(b);
		}
	}
	
	class Transformpanel extends JPanel{
		public Transformpanel() {
			System.out.println("����");
			setPreferredSize(new Dimension(250, 150));
			setLayout(new GridLayout(0, 4, 2, 2));
			add(new JLabel(""));
			add(new JLabel("X"));
			add(new JLabel("Y"));
			add(new JLabel(""));
			add(new JLabel("scaling"));
			add(x_scale);
			add(y_scale);
			JButton sc = new JButton("scaling");
			sc.addActionListener(e->{
				switch (shape_type) {
				case 0: // ������ �׷��� ���
					xypos scxy0 = new xypos(Integer.parseInt(x1.getText()),Integer.parseInt(y1.getText())); 
					xypos scxy1 = new xypos(Integer.parseInt(x2.getText()),Integer.parseInt(y2.getText()));
					Line s0 = new Line(scxy0, scxy1);
					int sx0 = Integer.parseInt(x_scale.getText());
					int sy0 = Integer.parseInt(y_scale.getText());
					s0.Scaling_matrix(new xypos(Integer.parseInt(x_rotate.getText()),
							Integer.parseInt(y_rotate.getText())
							), sx0, sy0);
					shape_arr.add(s0);
					gp.re_paint();
					break;
				case 1: // ���� �׷��� ���
					xypos scxy3 = new xypos(Integer.parseInt(x3.getText()),Integer.parseInt(y3.getText())); 
					int scr = Integer.parseInt(radius.getText());
					Circle s1 = new Circle(scxy3, scr);
					int sx1 = Integer.parseInt(x_scale.getText());
					s1.Scaling_matrix(sx1);
					shape_arr.add(s1);
					gp.re_paint();
					break;
				case 2:
					xypos scxy4 = new xypos(Integer.parseInt(x4.getText()),Integer.parseInt(y4.getText()));
					xypos scxy5 = new xypos(Integer.parseInt(x5.getText()),Integer.parseInt(y5.getText()));
					xypos scxy6 = new xypos(Integer.parseInt(x6.getText()),Integer.parseInt(y6.getText()));
					Triangle s2 = new Triangle(scxy4, scxy5, scxy6);
					int sx2 = Integer.parseInt(x_scale.getText());
					int sy2 = Integer.parseInt(y_scale.getText());
					s2.Scaling_matrix(new xypos(Integer.parseInt(x_rotate.getText()),
												Integer.parseInt(y_rotate.getText())
												), sx2, sy2);
					//x4.setText(s2.arr[0].x+"");
					//y4.setText(s2.arr[0].y+"");
					//x5.setText(s2.arr[1].x+"");
					//y5.setText(s2.arr[1].y+"");
					//x6.setText(s2.arr[2].x+"");
					//y6.setText(s2.arr[2].y+"");
					shape_arr.add(s2);
					gp.re_paint();
					break;
				}
			});
			add(sc);
			add(new JLabel("����"));
			add(x_rotate);
			add(y_rotate);
			JButton ro = new JButton("rotate");
			ro.addActionListener(e->{
				switch (shape_type) {
				case 0: // ������ �׷��� ���
					xypos scxy0 = new xypos(Integer.parseInt(x1.getText()),Integer.parseInt(y1.getText())); 
					xypos scxy1 = new xypos(Integer.parseInt(x2.getText()),Integer.parseInt(y2.getText()));
					Line s0 = new Line(scxy0, scxy1);
					int ro0 = Integer.parseInt(rotate.getText());
					s0.Rotate_matrix(new xypos(Integer.parseInt(x_rotate.getText()),
												Integer.parseInt(y_rotate.getText())), ro0);
					shape_arr.add(s0);
					gp.re_paint();
					break;
				case 1: // ���� �׷��� ���
					xypos scxy3 = new xypos(Integer.parseInt(x3.getText()),Integer.parseInt(y3.getText())); 
					int scr = Integer.parseInt(radius.getText());
					Circle s1 = new Circle(scxy3, scr);
					int ro1 = Integer.parseInt(rotate.getText());
					s1.Rotate_matrix(new xypos(Integer.parseInt(x_rotate.getText()),
												Integer.parseInt(y_rotate.getText())), ro1);
					shape_arr.add(s1);
					gp.re_paint();
					break;
				case 2:
					xypos scxy4 = new xypos(Integer.parseInt(x4.getText()),Integer.parseInt(y4.getText()));
					xypos scxy5 = new xypos(Integer.parseInt(x5.getText()),Integer.parseInt(y5.getText()));
					xypos scxy6 = new xypos(Integer.parseInt(x6.getText()),Integer.parseInt(y6.getText()));
					Triangle s2 = new Triangle(scxy4, scxy5, scxy6);
					int ro2 = Integer.parseInt(rotate.getText());
					s2.Rotate_matrix(new xypos(Integer.parseInt(x_rotate.getText()),
												Integer.parseInt(y_rotate.getText())), ro2);
					x4.setText(s2.arr[0].x+"");
					y4.setText(s2.arr[0].y+"");
					x5.setText(s2.arr[1].x+"");
					y5.setText(s2.arr[1].y+"");
					x6.setText(s2.arr[2].x+"");
					y6.setText(s2.arr[2].y+"");
					shape_arr.add(s2);
					gp.re_paint();
					break;
				}
			});
			add(ro);
			add(new JLabel("ȸ������"));
			add(new JLabel(""));
			add(rotate);
			JButton cb = new JButton("center");
			cb.addActionListener(e->{
				int sub_x = Integer.parseInt(x4.getText())+
							Integer.parseInt(x5.getText())+
							Integer.parseInt(x6.getText());
				int sub_y = Integer.parseInt(y4.getText())+
							Integer.parseInt(y5.getText())+
							Integer.parseInt(y6.getText());
				x_rotate.setText(sub_x/3+"");
				y_rotate.setText(sub_y/3+"");
			});
			add(cb);
			add(new JLabel("�̵���"));
			add(x_move);
			add(y_move);
			JButton mb = new JButton("move");
			mb.addActionListener(e->{
				switch (shape_type) {
				case 0: // ������ �׷��� ���
					xypos scxy0 = new xypos(Integer.parseInt(x1.getText()),Integer.parseInt(y1.getText())); 
					xypos scxy1 = new xypos(Integer.parseInt(x2.getText()),Integer.parseInt(y2.getText()));
					Line s0 = new Line(scxy0, scxy1);
					int dx0 = Integer.parseInt(x_move.getText());
					int dy0 = Integer.parseInt(y_move.getText());
					s0.move(dx0,dy0);
					shape_arr.add(s0);
					gp.re_paint();
					break;
				case 1: // ���� �׷��� ���
					xypos scxy3 = new xypos(Integer.parseInt(x3.getText()),Integer.parseInt(y3.getText())); 
					int scr = Integer.parseInt(radius.getText());
					Circle s1 = new Circle(scxy3, scr);
					int dx1 = Integer.parseInt(x_move.getText());
					int dy1 = Integer.parseInt(y_move.getText());
					s1.move(dx1,dy1);
					shape_arr.add(s1);
					gp.re_paint();
					break;
				case 2:
					xypos scxy4 = new xypos(Integer.parseInt(x4.getText()),Integer.parseInt(y4.getText()));
					xypos scxy5 = new xypos(Integer.parseInt(x5.getText()),Integer.parseInt(y5.getText()));
					xypos scxy6 = new xypos(Integer.parseInt(x6.getText()),Integer.parseInt(y6.getText()));
					Triangle s2 = new Triangle(scxy4, scxy5, scxy6);
					int dx2 = Integer.parseInt(x_move.getText());
					int dy2 = Integer.parseInt(y_move.getText());
					s2.move(dx2,dy2);
					//x4.setText(s2.arr[0].x+"");
					//y4.setText(s2.arr[0].y+"");
					//x5.setText(s2.arr[1].x+"");
					//y5.setText(s2.arr[1].y+"");
					//x6.setText(s2.arr[2].x+"");
					//y6.setText(s2.arr[2].y+"");
					shape_arr.add(s2);
					gp.re_paint();
					break;
				}
			});
			add(mb);
		}
	}
	
	public CG() {
		shape_type = 0;
		window_check = false;
		xy_arr = new ArrayList<>();
		shape_arr = new ArrayList<>();
		window_arr = new ArrayList<>();
		// ���� �뵵
		x1 = new JTextField();
		y1 = new JTextField();
		x2 = new JTextField();
		y2 = new JTextField();
		// �� �뵵
		x3 = new JTextField();
		y3 = new JTextField();
		radius = new JTextField();
		// �ﰢ�� �뵵
		x4 = new JTextField();
		y4 = new JTextField();
		x5 = new JTextField();
		y5 = new JTextField();
		x6 = new JTextField();
		y6 = new JTextField();
		// �����ϸ� �뵵
		x_scale = new JTextField();
		y_scale = new JTextField();
		x_scale.setText("100");
		y_scale.setText("100");
		// ȸ�� �뵵
		x_rotate = new JTextField();
		y_rotate = new JTextField();
		x_rotate.setText("0");
		y_rotate.setText("0");
		rotate = new JTextField();
		rotate.setText("0");
		// �̵� �뵵
		x_move = new JTextField();
		y_move = new JTextField();
	
		setSize(bx+400,by+400);
		setDoubleBuffered(true);
		setLayout(null);
		// �׸��׸��� �г� �߰�
		gp = new gpanel();
		gp.setBounds(25, 25, bx+1, by+1);
		add(gp);
		// Ʈ������ �г� ����
		Transp = new Transformpanel();
		Transp.setBounds(bx+100, 300, 350, 200);
		add(Transp);
		// �����г� ����
		lp = new Linepanel();
		lp.setBounds(bx+100, 50, 350, 150);
		// �� �г� ����
		cp = new Circlepanel();
		cp.setBounds(bx+100, 50, 350, 150);
		// �ﰢ�� �г� ����
		tp = new Trianglepanel();
		tp.setBounds(bx+100, 50, 350, 200);
		
		//�ʱ� �г� �߰�
		add(tp);
		add(cp);
		add(lp);
		cp.setVisible(false);
		tp.setVisible(false);
		
		
		// ��� ���� ��ư �߰�
		JButton del = new JButton("��� �����");
		del.addActionListener(e->{
			if(window_check) {
				window_arr.clear();
				xy_arr.clear();
				window_check = false;
				gp.re_paint();
			}
			else {
				xy_arr.clear();
				shape_arr.clear();
				gp.re_paint();
			}
		});
		del.setBounds(bx+100, 605, 300, 50);
		add(del);
		//���� �׸��� �гη� ����
		Line_button = new JButton("�� �׸���");
		Line_button.addActionListener(e->{
			shape_type = 0;
			lp.setVisible(true);
			cp.setVisible(false);
			tp.setVisible(false);
		});
		Line_button.setBounds(bx+100, 670, 300, 50);
		add(Line_button);
		
		// �� �׸��� �гη� ����
		Circle_button = new JButton("�� �׸���");
		Circle_button.addActionListener(e->{
			shape_type = 1;
			lp.setVisible(false);
			cp.setVisible(true);
			tp.setVisible(false);
		});
		add(Circle_button);
		Circle_button.setBounds(bx+100, 735, 300, 50);
		
		// �ﰢ�� �׸��� �гη� ����
		Triangle_button = new JButton("�ﰢ�� �׸���");
		Triangle_button.addActionListener(e->{
			shape_type = 2;
			lp.setVisible(false);
			cp.setVisible(false);
			tp.setVisible(true);
		});
		Triangle_button.setBounds(bx+100, 800, 300, 50);
		add(Triangle_button);
	
		
	}
	
	public void setpix(xypos[] set_arr) {
		for(xypos s :set_arr) 
			xy_arr.add(s);
	}
	
	public void setwindow() { // ������ �׸���
		int xmin, xmax, ymin, ymax;
		window_arr.removeIf(e->(e instanceof Line));
		// window�� �ּ�, �ִ� x,y�� ���ϱ�.
		if (rect.arr[0].x < rect.arr[1].x) {
			xmin = rect.arr[0].x;
			xmax = rect.arr[1].x;
		}
		else {
			xmin = rect.arr[1].x;
			xmax = rect.arr[0].x;
		}
		if (rect.arr[0].y < rect.arr[1].y) {
			ymin = rect.arr[0].y;
			ymax = rect.arr[1].y;
		}
		else {
			ymin = rect.arr[1].y;
			ymax = rect.arr[0].y;
		}
		for(Shape shape : shape_arr) {
			// ���� ���� �ڸ���.
			if(shape instanceof Line ) {
				/*
				int p1 = -(shape.arr[1].x - shape.arr[0].x);
				int p2 = shape.arr[1].x - shape.arr[0].x;
				int p3 = -(shape.arr[1].y - shape.arr[0].y);
				int p4 = shape.arr[1].y - shape.arr[0].y;
				int q1 = shape.arr[0].x - xmin;
				int q2 = xmax - shape.arr[0].x;
				int q3 = shape.arr[0].y - ymin;
				int q4 = ymax - shape.arr[0].y;
				double u1 = (double)p1/q1;
				double u2 = (double)p2/q2;
				double u3 = (double)p3/q3;
				double u4 = (double)p4/q4;
				*/
				clip_liangbrasky(xmin, xmax, ymin, ymax, shape.arr[0], shape.arr[1]);
			}
			if(shape instanceof Triangle ) {
				clip_liangbrasky(xmin, xmax, ymin, ymax, shape.arr[0], shape.arr[1]);
				clip_liangbrasky(xmin, xmax, ymin, ymax, shape.arr[1], shape.arr[2]);
				clip_liangbrasky(xmin, xmax, ymin, ymax, shape.arr[0], shape.arr[2]);
			}
		}
	}
	public void setwindow(Shape rects) { // ������ �׸���
		int xmin, xmax, ymin, ymax;
		// window�� �ּ�, �ִ� x,y�� ���ϱ�.
		if (rects.arr[0].x < rects.arr[1].x) {
			xmin = rects.arr[0].x;
			xmax = rects.arr[1].x;
		}
		else {
			xmin = rects.arr[1].x;
			xmax = rects.arr[0].x;
		}
		if (rects.arr[0].y < rects.arr[1].y) {
			ymin = rects.arr[0].y;
			ymax = rects.arr[1].y;
		}
		else {
			ymin = rects.arr[1].y;
			ymax = rects.arr[0].y;
		}
		for(Shape shape : shape_arr) {
			// ���� ���� �ڸ���.
			if(shape instanceof Line ) {
				clip_liangbrasky(xmin, xmax, ymin, ymax, shape.arr[0], shape.arr[1]);
			}
			if(shape instanceof Triangle ) {
				clip_liangbrasky(xmin, xmax, ymin, ymax, shape.arr[0], shape.arr[1]);
				clip_liangbrasky(xmin, xmax, ymin, ymax, shape.arr[1], shape.arr[2]);
				clip_liangbrasky(xmin, xmax, ymin, ymax, shape.arr[0], shape.arr[2]);
			}
		}
	}
	
	public boolean clip_check(float p, float q, float[] u) {
		float r;
		boolean return_value = true;
		if(p < 0.0f) {
			r = q/p;
			if(r > u[1])
				return_value = false;
			else if(r > u[0])
					u[0] = r;
		}
		else {
			if(p > 0.0f) {
				r= q/p;
				if(r<u[0])
					return_value = false;
				else if(r < u[1])
					u[1] = r;
			}
			else
				if(q < 0.0)
					return_value = false;
		}
		return return_value;
	}
	
	public void clip_liangbrasky(int xmin, int xmax, int ymin, int ymax, 
								xypos _point1, xypos _point2) {
		//������ �Ѽ����� �ʱ� ���� ��ǥ�� �߰�.
		xypos point1 = new xypos(_point1.x, _point1.y);
		xypos point2 = new xypos(_point2.x, _point2.y);
		float[] u = {0.0f, 1.0f};
		int dx = point2.x - point1.x;
		int dy;
		
		if(clip_check(-dx, point1.x - xmin, u))
			if(clip_check(dx, xmax - point1.x, u)) {
				dy = point2.y - point1.y;
				if(clip_check(-dy, point1.y - ymin, u))
					if(clip_check(dy, ymax - point1.y, u)) {
						if(u[1] < 1.0) point2.setxy(Math.round(point1.x + u[1]*dx), Math.round(point1.y + u[1]*dy));
						if(u[0] > 0.0) point1.setxy(Math.round(point1.x + u[0]*dx), Math.round(point1.y + u[0]*dy));
						window_arr.add(new Line(point1, point2));
					}
			}
	}
}
