import java.util.*;

public class spritegame {
	public static void main(String []arg) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Sprite> mob = new ArrayList<>();
		mob.add(new Monster());
		mob.add(new Monster());
		mob.add(new Monster());
		
		
		int score = 0;
		Player user = new Player();
		Gold gold = new Gold();
		
		char input;
		boolean gameover = true;
		while(gameover) { // 게임이 끝나지 않았다면
			// 게임 화면 출력
			for(int y = 0; y<13 ; y++) {
				for(int x = 0;x<23;x++) {
					if(y == 0)
						System.out.print("#");
					else if (y == 12)
						System.out.print("#");
					else {
						if (x == 0)
							System.out.print("#");
						else if (x == 22)
							System.out.print("#");
						else {
							if((mob.get(0).getX() == x) && (mob.get(0).getY() == y))
								System.out.print("M");
							else if((mob.get(1).getX() == x) && (mob.get(1).getY() == y))
								System.out.print("M");
							else if((mob.get(2).getX() == x) && (mob.get(2).getY() == y))
								System.out.print("M");
							else if((user.getX() == x) && (user.getY() == y))
								System.out.print("@");
							else if((gold.getX() == x) && (gold.getY() == y))
								System.out.print("G");
							else
								System.out.print(" ");
						}
					}
				}
				System.out.print("\n");
			}
			
			//게임이 끝나는 조건
			for(Sprite monster : mob) { // 몬스터와 같은 좌표에 있는지 확인
				if (monster.getX() == user.getX() && monster.getY() == user.getY()) {
					System.out.println("플레이어가 사망하였습니다.");
					System.out.println("최종 점수 : "+ score);
					gameover = false;
					break;
				}
				monster.move('v'); // 같은 좌표가 아니라면 몬스터 좌표 변경
			}
			if (gold.getX() == user.getX() && gold.getY() == user.getY()) { // 골드와 같은 좌표시
				score++; // 점수 추가
				System.out.println("골드 획득! 현재 점수 : " + score);
				gold.move('v'); // 골드 위치 변경
			}
			else { // 골드와 같은 위치가 아닐 경우 플레이어 이동
				System.out.print("\n");
				System.out.print("왼쪽(h), 위쪽(j), 아래쪽(k). 오른쪽(l) : ");
				input = sc.next().charAt(0); // 입력받은 한개의 문자를 input에 저장.
				user.move(input);
			}
		
		}
		sc.close();
	}
}
