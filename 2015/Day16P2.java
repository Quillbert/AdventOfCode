import java.util.ArrayList;
import java.util.Scanner;

public class Day16P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		ArrayList<Sue> aunts = new ArrayList<Sue>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			
			Sue sue = new Sue();
			
			String[] parts = str.split(": ", 2);
			
			parts = parts[1].split(",");
			
			for(int i = 0; i < parts.length; i++) {
				String[] words = parts[i].trim().split(": ");
				
				int value = Integer.parseInt(words[1]);
				
				switch(words[0]) {
				case "children":
					sue.children = value;
					break;
				case "cats":
					sue.cats = value;
					break;
				case "samoyeds":
					sue.samoyeds = value;
					break;
				case "pomeranians":
					sue.pomeranians = value;
					break;
				case "akitas":
					sue.akitas = value;
					break;
				case "vizslas":
					sue.vizslas = value;
					break;
				case "goldfish":
					sue.goldfish = value;
					break;
				case "trees":
					sue.trees = value;
					break;
				case "cars":
					sue.cars = value;
					break;
				case "perfumes":
					sue.perfumes = value;
					break;
				}
			}
			
			aunts.add(sue);
			
			str = s.nextLine().trim();
		}
		
		for(int i = 0; i < aunts.size(); i++) {
			Sue a = aunts.get(i);
			if((a.children == 3 || a.children < 0) &&
					(a.cats > 7 || a.cats < 0) &&
					(a.samoyeds == 2 || a.samoyeds < 0) &&
					(a.pomeranians < 3 || a.pomeranians < 0) &&
					(a.akitas == 0 || a.akitas < 0) &&
					(a.vizslas == 0 || a.vizslas < 0) &&
					(a.goldfish < 5 || a.goldfish < 0) &&
					(a.trees > 3 || a.trees < 0) &&
					(a.cars == 2 || a.cars < 0) &&
					(a.perfumes == 1 || a.perfumes < 0)) {
				System.out.println(i+1);
				break;
			}
		}
		
	}

}
