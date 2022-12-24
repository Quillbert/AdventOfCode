import java.util.*;

public class Day21P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int place1 = Integer.parseInt(s.nextLine().trim().split(" ")[4]);
		int place2 = Integer.parseInt(s.nextLine().trim().split(" ")[4]);
		
		int score1 = 0;
		int score2 = 0;
		
		int dice = 0;
		int diceRolls = 0;
		boolean turn1 = true;
		
		while(score1 < 1000 & score2 < 1000) {
			int movement = 0;
			for(int i = 0; i < 3; i++) {
				dice++;
				diceRolls++;
				if(dice > 100) {
					dice -= 100;
				}
				movement += dice;
			}
			if(turn1) {
				place1 += movement;
				while(place1 > 10) {
					place1 -= 10;
				}
				score1 += place1;
			} else {
				place2 += movement;
				while(place2 > 10) {
					place2 -= 10;
				}
				score2 += place2;
			}
			turn1 = !turn1;
		}
		
		System.out.println(diceRolls*Math.min(score1, score2));

	}

}
