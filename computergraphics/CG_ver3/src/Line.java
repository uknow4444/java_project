
public class Line extends Shape{
	public Line(xypos xy0, xypos xy1){
		size = 2;
		arr = new xypos[size];
		arr[0] = xy0;
		arr[1] = xy1;
		}
	public xypos[] DDA() {
		return DDA_Algorithm(arr[0].x, arr[0].y, arr[1].x, arr[1].y );
	}

	public xypos[] setdraw() {
		return BH_Algorithm(arr[0].x, arr[0].y, arr[1].x, arr[1].y);
	}
}
