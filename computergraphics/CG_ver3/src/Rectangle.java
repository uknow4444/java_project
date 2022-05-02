
public class Rectangle extends Shape {
	public Rectangle() {
		size = 2;
		arr = new xypos[size];
		arr[0] = new xypos(0, 0);
		arr[1] = new xypos(0, 0);
	}
	
	public xypos[] setdraw() {
		xypos[] sub1_arr = BH_Algorithm(arr[0].x, arr[0].y, arr[1].x, arr[0].y);
		xypos[] sub2_arr = BH_Algorithm(arr[1].x, arr[0].y, arr[1].x, arr[1].y);
		xypos[] sub3_arr = BH_Algorithm(arr[1].x, arr[1].y, arr[0].x, arr[1].y);
		xypos[] sub4_arr = BH_Algorithm(arr[0].x, arr[1].y, arr[0].x, arr[0].y);
		int sub_size = sub1_arr.length
				+ sub2_arr.length
				+ sub3_arr.length
				+ sub4_arr.length;
		xypos[] re_arr = new xypos[sub_size];
		int count = 0;
		for(xypos sub : sub1_arr) {
			re_arr[count++] = sub;
		}
		for(xypos sub : sub2_arr) {
			re_arr[count++] = sub;
		}
		for(xypos sub : sub3_arr) {
			re_arr[count++] = sub;
		}
		for(xypos sub : sub4_arr) {
			re_arr[count++] = sub;
		}
		return re_arr;
	}
}
