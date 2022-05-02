
public class Circle extends Shape {
	int radius;
	public Circle(xypos xy3, int r){
		size  = 1;
		radius = r;
		arr = new xypos[size];
		arr[0] = xy3;
		}
	
	public xypos[] setdraw() {
		return Circle_Algorithm(arr[0].x, arr[0].y, radius);
	}
	public void Scaling_matrix(int sx){
		float sx_f = (float)sx / 100;
		radius = Math.round(radius * sx_f);
	}
}
