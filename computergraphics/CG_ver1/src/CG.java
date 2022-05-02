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

import com.sun.org.apache.xpath.internal.operations.And;


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
	
	class gpanel extends JPanel{
		public gpanel() {
			setPreferredSize(new Dimension(bx+1, by+1));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			for(Shape s: shape_arr)
				setpix(s.setdraw());
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
			System.out.println("����");
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
		xy_arr = new ArrayList<>();
		shape_arr = new ArrayList<>();
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
			xy_arr.clear();
			shape_arr.clear();
			gp.re_paint();
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
}
