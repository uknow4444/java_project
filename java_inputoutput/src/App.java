import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class App {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write("입력 : ");
        //System.out.print("입력 : ");
        bw.flush(); // 출력

        int num = Integer.parseInt(br.readLine());    

        bw.write(num+"");
        //bw.newLine();
        bw.flush();
        //System.out.println(num);

        br.close();
        bw.close();
    }
}
