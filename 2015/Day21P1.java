
public class Day21P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Item[] weapons = {
			new Item(8, 4, 0),
			new Item(10, 5, 0),
			new Item(25, 6, 0),
			new Item(40, 7, 0),
			new Item(74, 8, 0)
		};
		
		Item[] armor = {
			new Item(13, 0, 1),
			new Item(31, 0, 2),
			new Item(53, 0, 3),
			new Item(75, 0, 4),
			new Item(102, 0, 5)
		};
		
		Item[] rings = {
			new Item(0, 0, 0),
			new Item(0, 0, 0),
			new Item(25, 1, 0),
			new Item(50, 2, 0),
			new Item(100, 3, 0),
			new Item(20, 0, 1),
			new Item(40, 0, 2),
			new Item(80, 0, 3)
		};
		
		int min = Integer.MAX_VALUE;
		
		for(int a = 0; a < weapons.length; a++) {
			for(int b = 0; b < armor.length; b++) {
				for(int c = 0; c < rings.length-1; c++) {
					for(int d = c+1; d < rings.length; d++) {
						int attack = weapons[a].attack + rings[c].attack + rings[d].attack;
						int defense = armor[b].defense + rings[c].defense + rings[d].defense;
						int cost = weapons[a].cost + armor[b].cost + rings[c].cost + rings[d].cost;
						
						if(cost < min && willWin(attack, defense)) {
							min = cost;
						}
					}
				}
			}
		}
		
		System.out.println(min);
	}
	
	public static boolean willWin(int attack, int defense) {
		int pHealth = 100;
		int bHealth = 103;
		
		while(pHealth > 0 && bHealth > 0) {
			bHealth -= Math.max((attack-2), 1);
			if(bHealth <= 0) break;
			pHealth -= Math.max((9-defense), 1);
		}
		return pHealth > 0;
	}

}
