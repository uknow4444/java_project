import java.util.Vector;

import javax.swing.DefaultListModel;

public class client_vector { // 클라이언트를 모아두는 클래스를 정의
   Vector<client_socket> clients; // 클라이언트들을 모아둔 벡터 변수 선언
   DefaultListModel<String> clients_names; // 클라이언트들의 이름만 따로 모아둬 jlist에 이용할 수 있는 defaultlistmodel 변수 선언
   
   public client_vector() {
      clients = new Vector<>(); // 벡터 객체를 생성한다.
      clients_names = new DefaultListModel<>(); // defaultlistmodel 객체를 생성한다.
      clients_names.addElement("접속 유저 : "); // 첫줄에 띄울 접속 유저란 단어를 추가한다.
   }
   
   public void addclient(client_socket cs){ // 클라이언트를 추가하는 함수
      try {
         clients.add(cs); // 벡터에 클라이언트 객체를 추가한다.
         //현재 저장되어있는 객체들의 이름을 새로 생성된 클라이언트에 전송한다.
         for(int i = 1; i < clients_names.getSize(); i++) {
            cs.out.writeUTF(clients_names.get(i));
         }
         cs.out.writeUTF("끝"); // 클라이언트 이름을 모두 전송했음을 알리는 메세지
         clients_names.addElement(cs.name); //현재 객체의 이름을 추가한다.
         sendmsg("Server", cs.name + "님이 입장하셨습니다."); // 접속했다는 메세지를 보낸다.
      }catch(Exception e) {}
   }
   
   public void removeclient(String name){ // 삭제하는 메소드
      try {
         clients.removeIf(e -> e.name == name); // 지워지는 소켓과 이름이 같은 클라이언트 소켓을 삭제한다.
         clients_names.removeElement(name); // 지워진 클라이언트 소켓의 이름을 삭제한다.
         sendmsg("Server" , name + "님이 퇴장하셨습니다."); // 퇴장했음을 알린다.
      }catch(Exception e) {}
   }
   
   public void sendmsg(String name, String msg) throws Exception { // 메세지를 전송하는 함수
         for(client_socket cs : clients) // 모든 클라이언트 객체들에게 메세지를 전송한다.
            cs.out.writeUTF(name + " : "+ msg);
   }
}
