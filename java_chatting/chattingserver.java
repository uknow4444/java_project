import java.awt.BorderLayout;
import java.awt.Color;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;



public class chattingserver extends JFrame{
   Socket socket; // 소켓을 저장할 변수      
   client_vector cv; // 클라이언트를 모아둘 객체
   ServerSocket server_socket; // 서버 소켓을 저장할 변수
   JList<String> jl; // jlist 객체를 저장할 변수
   int count = 0; // 현재 열린 클라이언트의 개수
   Thread thread[]= new Thread[10]; // 쓰레드를 저장할 배열, 최대 클라이언트 개수를 10개로 제한
   //생성자 정의
   public chattingserver(){
       try {
               server_socket = new ServerSocket(13044); // 포트번호 13044로 접속
               cv = new client_vector(); // 클라이언트를 모아둘 객체를 생성
               System.out.println("서버 시스템 시작"); // 시스템 메세지 출력
               setSize(500,500); // 크기지정
               setTitle("ChattingServer"); // 제목지정
               jl = new JList<>(cv.clients_names); // jlist 생성
               setLayout(new BorderLayout()); // 레이아웃 설정
               jl.setBorder(new LineBorder(Color.BLACK)); // 겉의 선 설정
               jl.setBackground(Color.YELLOW); // 배경 설정
               add(jl); // jlist추가

               setVisible(true); // 창을 보이게 설정
               setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼 클릭시 모두 종료
               while(true) // 계속 반복
               {
                   socket = server_socket.accept(); // 접속한 클라이언트가 있을 경우
                   System.out.println("클라이언트 추가"); // 클라이언트가 추가되었음을 알리고
                   thread[count] = new Thread(new client_socket(socket, cv)); // 쓰레드에 추가
                   thread[count].start();
                   count++;
               }
       }catch(Exception e) {};
      
   }

   
   public static void main(String[] args)  {
      chattingserver chatting = new chattingserver();
   }

}
