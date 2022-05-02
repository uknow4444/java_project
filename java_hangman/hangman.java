	import java.util.*;

public class hangman {
	public static void main(String arg[]) {
		String words[] = {"java","count", "school","book","student","programmer"};
		Scanner sc = new Scanner(System.in);
		
		int index = (new Random()).nextInt(words.length);
		String solution = words[index]; // 정답 저장
		int count = solution.length(); // 정답이 가진 글자 수
		StringBuffer answer = new StringBuffer(solution.length()); // 정답에 대한 정보를 출력해줄 문자열
		for(int i =0; i<solution.length();i++) // 정답이 가진 글자수만큼 * 추가
			answer.append("*");
		
		char input; // 문자를 입력받을 변수
		while(true) {
			do{
				System.out.println("현재 상태 : "+answer);
				System.out.print("문자를 입력하세요. : ");
				input = sc.next().charAt(0); //한개 문자만 받기
				for(int i = 0; i<solution.length(); i++) {
					if(solution.charAt(i) == input) {
						answer.setCharAt(i, input);
						count--;
					}
				}
			}while(count != 0);
			System.out.println("현재 상태 : "+answer);
			System.out.println("clear\n다시 시작하시겠습니까? y/n");
			input = sc.next().charAt(0); //한개 문자만 받기
			if(input != 'y') // 사용자가 재시작을 원하지 않으면 종료
				break;
			else { // 다시 시작
				index = (new Random()).nextInt(words.length);
				solution = words[index]; // 정답 저장
				count = solution.length(); // 정답이 가진 글자 수
				answer = new StringBuffer(solution.length()); // 정답에 대한 정보를 출력해줄 문자열
				for(int i =0; i<solution.length();i++) // 정답이 가진 글자수만큼 * 추가
					answer.append("*");
			}
		}
	}
}
