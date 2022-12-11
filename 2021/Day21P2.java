import java.util.*;

public class Day21P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int place1 = Integer.parseInt(s.nextLine().trim().split(" ")[4]);
		int place2 = Integer.parseInt(s.nextLine().trim().split(" ")[4]);
		
		
		long[] out = {0,0};
		
		int score1 = 0;
		int score2 = 0;
		boolean turn1 = false;
		
		long[] part1 = score(place1, place2, score1, score2, 3, !turn1, 1);
		long[] part2 = score(place1, place2, score1, score2, 4, !turn1, 3);
		long[] part3 = score(place1, place2, score1, score2, 5, !turn1, 6);
		long[] part4 = score(place1, place2, score1, score2, 6, !turn1, 7);
		long[] part5 = score(place1, place2, score1, score2, 7, !turn1, 6);
		long[] part6 = score(place1, place2, score1, score2, 8, !turn1, 3);
		long[] part7 = score(place1, place2, score1, score2, 9, !turn1, 1);
		
		out[0] += part1[0];
		out[0] += part2[0];
		out[0] += part3[0];
		out[0] += part4[0];
		out[0] += part5[0];
		out[0] += part6[0];
		out[0] += part7[0];
		out[1] += part1[1];
		out[1] += part2[1];
		out[1] += part3[1];
		out[1] += part4[1];
		out[1] += part5[1];
		out[1] += part6[1];
		out[1] += part7[1];
		
		System.out.println(Arrays.toString(out));
	}
	
	public static long[] score(int place1, int place2, int score1, int score2, int roll, boolean turn1, long multiplier) {
		long[] out = {0, 0};
		
		if(turn1) {
			place1 += roll;
			if(place1 > 10) {
				place1 -= 10;
			}
			score1 += place1;
		} else {
			place2 += roll;
			if(place2 > 10) {
				place2 -= 10;
			}
			score2 += place2;
		}
		
		if(score1 >= 21) {
			out[0] = multiplier;
			return out;
		}
		if(score2 >= 21) {
			out[1] = multiplier;
			return out;
		}
		
		long[] part1 = score(place1, place2, score1, score2, 3, !turn1, multiplier*1);
		long[] part2 = score(place1, place2, score1, score2, 4, !turn1, multiplier*3);
		long[] part3 = score(place1, place2, score1, score2, 5, !turn1, multiplier*6);
		long[] part4 = score(place1, place2, score1, score2, 6, !turn1, multiplier*7);
		long[] part5 = score(place1, place2, score1, score2, 7, !turn1, multiplier*6);
		long[] part6 = score(place1, place2, score1, score2, 8, !turn1, multiplier*3);
		long[] part7 = score(place1, place2, score1, score2, 9, !turn1, multiplier*1);
		
		out[0] += part1[0];
		out[0] += part2[0];
		out[0] += part3[0];
		out[0] += part4[0];
		out[0] += part5[0];
		out[0] += part6[0];
		out[0] += part7[0];
		out[1] += part1[1];
		out[1] += part2[1];
		out[1] += part3[1];
		out[1] += part4[1];
		out[1] += part5[1];
		out[1] += part6[1];
		out[1] += part7[1];
		
		return out;
	}

}
