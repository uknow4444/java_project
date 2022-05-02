import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class chatting_client extends JFrame implements Runnable{
    Socket socket;  //Server와 통신하기 위한 Socket
     DataInputStream in;  //Server로부터 데이터를 읽어들이기 위한 입력스트림
     DataOutputStream out; // server로 데이터를 전송하기 위한 출력 스트림
     JTextArea jta; // 메세지를 띄우는 객체
     JTextField jtf; // 메세지를 입력하는 객체
     JList<String> jl; // 접속자를 띄울 jlist 객체
     DefaultListModel<String> clients_names; // jlist와 연동하여 접속자를 저장할 객체
     boolean start; // 클라이언트 시작을 확인할 변수
     public chatting_client() {
        try {
             socket = new Socket("localhost", 13044);    //서버로 접속
             
             in = new DataInputStream(socket.getInputStream());            
             out = new DataOutputStream(socket.getOutputStream());
             start = true;
             //닉네임을 입력할 창을 만든다.
             JFrame nic = new JFrame();
             nic.setSize(400,100);
             nic.setTitle("닉네임을 입력해주세요.");
             JTextField nicjtf = new JTextField(20);
             nicjtf.setBackground(Color.YELLOW);
             nicjtf.addKeyListener(new KeyListener() {
             @Override
             public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == e.VK_ENTER)
                   if(nicjtf.getText() != null) {
                     try {
                        start = false;
                        nic.setVisible(false);
                     } catch (Exception e1) {}
                   }
             }
             @Override
             public void keyReleased(KeyEvent arg0) {}
             @Override
             public void keyTyped(KeyEvent arg0) {}
                 
              });
             nic.add(nicjtf);
             
             System.out.println("클라이언트 시스템 시작");
             
             
             
             setTitle("Chatting Client");
             
             setLayout(new BorderLayout());
             setSize(500, 500);
             jta = new JTextArea(10,30);
             jta.setBorder(new LineBorder(Color.BLACK));
             jta.setBackground(Color.YELLOW);

             jtf = new JTextField(30);
             jtf.setBorder(new LineBorder(Color.BLACK));
             jtf.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
               if(e.getKeyCode() == e.VK_ENTER)
                  if(jtf.getText() != null) {
                     sendmsg(jtf.getText());
                     jtf.setText("");
                  }
            }
            @Override
            public void keyReleased(KeyEvent arg0) {}
            @Override
            public void keyTyped(KeyEvent arg0) {}
                
             });
             
             clients_names = new DefaultListModel<>();
             clients_names.addElement("접속 유저 :");
             jl = new JList<>(clients_names);

             add(jta, BorderLayout.CENTER);
             add(jtf, BorderLayout.SOUTH);
             add(jl,BorderLayout.EAST);
             
             nic.setVisible(true);
             //채팅에 사용 할 닉네임을 입력받음
             while(start) {System.out.println("클라이언트 대기");}
             //서버로 닉네임을 전송
          sendmsg(nicjtf.getText());
             System.out.println("클라이언트 시작");
             setVisible(true);
             
             //접속자를 받아온다.
             String str_sub = in.readUTF();
             while(str_sub.contains("끝") == false) {
                clients_names.addElement(str_sub);
                str_sub = in.readUTF();
             }
             

             
             
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             //사용자가 채팅 내용을 입력 및 서버로 전송하기 위한 쓰레드 생성 및 시작
             Thread th = new Thread(this);
             th.start();
            
         }catch(Exception e) {}
     }
     public void run() { // thread를 위한 run 설정
      while(true)
        {
            try
            {
               // 입력받은 메세지에 대한 처리.
                String str2 = in.readUTF();
                jta.append(str2 + "\n");
                if(str2.contains("Server")) {
                   if(str2.contains("입장")) {
                      clients_names.addElement(str2.substring(9, str2.indexOf("님이 입장하셨습니다.")));
                   }
                   if(str2.contains("퇴장")) {
                      clients_names.removeElement(str2.substring(9, str2.indexOf("님이 퇴장하셨습니다.")));
                   }
                }
                
                System.out.println(str2);
            }catch(Exception e) {}
        }
   }
     
    //메세지 보내는 함수
     public void sendmsg(String str) {
        try {
        out.writeUTF(str); 
        }catch (Exception e) {};
     }
     
   public static void main(String[] arg)
    {
      chatting_client cc = new chatting_client();
    }
}
