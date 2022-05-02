public class Triangle extends Shape {	
	public Triangle(xypos xy4, xypos xy5, xypos xy6){
		size = 3;
		arr = new xypos[size];
		arr[0] = xy4;
		arr[1] = xy5;
		arr[2] = xy6;
		}
	public xypos[] setdraw() {
		xypos[] sub1_arr = BH_Algorithm(arr[0].x, arr[0].y, arr[1].x, arr[1].y);
		xypos[] sub2_arr = BH_Algorithm(arr[1].x, arr[1].y, arr[2].
				x, arr[2].y);
		xypos[] sub3_arr = BH_Algorithm(arr[0].x, arr[0].y, arr[2].x, arr[2].y);
		int sub_size = sub1_arr.length
				+ sub2_arr.length
				+ sub3_arr.length;
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
		
		return re_arr;
	}
}
