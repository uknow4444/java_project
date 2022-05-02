import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;



class client_socket implements Runnable{ //  thread를 위해 runnable interface를 상속받는다.
   String name; //이름을 저장할 변수
    Socket socket; // 소켓을 저장할 변수
    DataInputStream in; // 데이터를 수신할 스트림
    DataOutputStream out; // 데이터를 전송할 스트림
    client_vector cv; // 클라이언트들을 모아 저장해둔 객체
    
    public client_socket(Socket socket, client_vector cv) throws Exception{
       this.socket = socket; // 소켓을 받는다.
       this.cv = cv; // 클라이언트들을 모아둔 객체를 받는다.
       in = new DataInputStream(socket.getInputStream()); // 데이터를 수신할 스트림 객체를 만든다.
       out = new DataOutputStream(socket.getOutputStream()); //데이터를 송신할 스트림 객체를 만든다.
       this.name = in.readUTF(); // 처음 사용자가 입력한 값을 닉네임으로 설정한다.
       cv.addclient(this); // 클라이언트를 추가한다.
    }
    
    public void run() // thread를 위해 run 함수를 재정의한다.
    {
        try
        {
            while(true)
            { //사용자가 입력한 것을 계속 받아 메세지를 전송한다.
                String msg = in.readUTF();
                cv.sendmsg(name, msg);
            }
        }catch(Exception e) {
            cv.removeclient(this.name); // 오류가 발생한다면 클라이언트 소켓을 종료한다.
        }        
    }
}
