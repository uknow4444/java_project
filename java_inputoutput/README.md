java 입출력 관련.

System.in과 System.out을 좀 더 빠르게 동작시키는 방식.


입력은 상관없으나,

출력 시에는 bw에 문자열(string)을 넣어준 뒤

flush 또는 close를 실행해줘야 출력이 됨.

단, close 시에는 해당 객체 소멸.