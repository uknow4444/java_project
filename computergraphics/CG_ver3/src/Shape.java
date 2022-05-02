import java.util.ArrayList;

public abstract class Shape{
   protected int size;
   protected xypos[] arr;
   
	public xypos[] DDA_Algorithm(int x0, int y0, int xe, int ye) {
		int dx = xe - x0;
		int dy = ye - y0;
		int step;
		float x = x0;
		float y = y0;
		float xIncrement, yIncrement;
		//기울기가 0보다 작을 때
		if(Math.abs(dx) > Math.abs(dy))
			step = (int)Math.abs(dx);
		else
			step = (int)Math.abs(dy);
		xypos[] re_arr = new xypos[step];
		xIncrement = (float)dx/(float)step;
		yIncrement = (float)dy/(float)step;
		re_arr[0] = new xypos((int)Math.round(x), (int)Math.round(y));
		for(int i = 1; i < step; i++) {
			x += xIncrement;
			y += yIncrement;
			re_arr[i] = new xypos((int)Math.round(x), (int)Math.round(y));
		}
		return re_arr;
	}
	
	public xypos[] BH_Algorithm(int x0, int y0, int xe, int ye) {
		double m, p;
		int count = 0;
		int x,y,dx,dy,subdx,subdy;
		xypos[] re_arr;
		dx = xe - x0;
		dy = ye - y0;

		m = (double)dy/(double)dx;
		dx = Math.abs(dx);
		dy = Math.abs(dy);
		p = 2*dy -dx;
		
		// x,y 기준에 따른 반환 배열 할당
		if(dx > dy) 
			re_arr = new xypos[dx+1];
		else 
			re_arr = new xypos[dy+1];
		
		
		if(m>0) {
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
				re_arr[count++] = new xypos((int)Math.round(x), (int)Math.round(y));
				while(x < xe) {
					x++;
					if(p<0) p += subdy;
					else {
						y++;
						p += subdx;
					}
					re_arr[count++] = new xypos((int)Math.round(x), (int)Math.round(y));
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
				re_arr[count++] = new xypos((int)Math.round(x), (int)Math.round(y));
				while(y < ye) {
					y++;
					if(p<0) p += subdx;
					else {
						x++;
						p += subdy;
					}
					re_arr[count++] = new xypos((int)Math.round(x), (int)Math.round(y));
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
				re_arr[count++] = new xypos((int)Math.round(x), (int)Math.round(y));
				while(x < xe) {
					x++;
					if(p<0) p += subdy;
					else {
						y--;
						p += subdx;
					}
					re_arr[count++] = new xypos((int)Math.round(x), (int)Math.round(y));
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
				re_arr[count++] = new xypos((int)Math.round(x), (int)Math.round(y));
				while(y < ye) {
					y++;
					if(p<0) p += subdx;
					else {
						x--;
						p += subdy;
					}
					re_arr[count++] = new xypos((int)Math.round(x), (int)Math.round(y));
				}
			}
		}
		return re_arr;
	}
	public xypos[] Circle_Algorithm(int x0, int y0, int radius) {
		ArrayList<xypos> sub_arr = new ArrayList<>();
		int p = 1 - radius;
		int x = 0;
		int y = radius;
		
		sub_arr.add(new xypos(x, y)); // 1
		while(x < y) {
			x++;
			if(p<0)
				p = p+(2*x)+1;
			else {
				y--;
				p = p+ (2*x) + 1 - (2 * y);
			}
			sub_arr.add(new xypos(x, y)); // 1
		}
		
		xypos[] re_arr = new xypos[sub_arr.size()*8];
		int count = 0;
		for(xypos sub_xy : sub_arr) {
			re_arr[count++] = new xypos(sub_xy.x + x0, sub_xy.y + y0); // 1
			re_arr[count++] = new xypos(sub_xy.x + x0, -(sub_xy.y) + y0); // 2
			re_arr[count++] = new xypos(-(sub_xy.x) + x0, sub_xy.y + y0); // 3
			re_arr[count++] = new xypos(-(sub_xy.x) + x0, -(sub_xy.y) + y0); // 4
			re_arr[count++] = new xypos(sub_xy.y + x0, sub_xy.x + y0); // 5
			re_arr[count++] = new xypos(sub_xy.y + x0, -(sub_xy.x) + y0); // 6
			re_arr[count++] = new xypos(-(sub_xy.y) + x0, sub_xy.x + y0); // 7
			re_arr[count++] = new xypos(-(sub_xy.y) + x0, -(sub_xy.x) + y0); // 8
			
		}
		return re_arr;
	}
	public void move(int sx, int sy) {
		for(xypos sub : arr) {
			sub.x += sx;
			sub.y += sy;
		}
	}
	public void Scaling_matrix(xypos fxy,int sx, int sy) {
		float sx_f = (float)sx/100;
		float sy_f = (float)sy/100;
		for(xypos sub : arr) {
			sub.x = Math.round((sub.x * sx_f) + fxy.x * (1 - sx_f));
			sub.y = Math.round((sub.y * sy_f) + fxy.y * (1 - sy_f));
		}
	}
	public void Rotate_matrix(xypos pxy, int rotate) {
		double degree = Math.toRadians((double)rotate);
		for(xypos sub : arr) {
			/*
			double rox = sub.x*Math.cos(degree)
					- sub.y * Math.sin(degree)
					- pxy.x * Math.cos(degree)
					+ pxy.y * Math.sin(degree)
					+ pxy.x;
			double roy = sub.x*Math.sin(degree)
					+ sub.y * Math.cos(degree)
					- pxy.x * Math.sin(degree)
					- pxy.y * Math.cos(degree)
					+ pxy.y;
			*/
			double rox = (sub.x-pxy.x)*Math.cos(degree)
					- (sub.y-pxy.y) * Math.sin(degree)
					+ pxy.x;
			double roy = (sub.x-pxy.x)*Math.sin(degree)
					+ (sub.y-pxy.y) * Math.cos(degree)
					+ pxy.y;
			sub.x = (int) Math.round(rox);
			sub.y = (int) Math.round(roy);
		}
	}
	
	public abstract xypos[] setdraw();
}