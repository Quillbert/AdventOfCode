import java.util.HashMap;

public class Day22P2 {

	public static HashMap<String, Integer> cache = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(cost(true, 50, 500, 55, 0, 0, 0, 0));
	}
	
	public static int cost(boolean playerTurn, int playerHealth, int manna, int bossHealth, int shieldTimer, int poisonTimer, int rechargeTimer, int depth) {
		String key = playerTurn + " " + playerHealth + " " + manna + " " + bossHealth + " " + shieldTimer + " " + poisonTimer + " " + rechargeTimer;
		if(cache.containsKey(key)) {
			return cache.get(key);
		}
		if(playerTurn) {
			playerHealth--;
		}
		if(depth > 100) {
			cache.put(key, 1000000);
			return 1000000;
		}
		if(manna < 0) {
			cache.put(key, 1000000);
			return 1000000;
		}
		if(poisonTimer > 0) {
			bossHealth -= 3;
		}
		if(playerHealth <= 0) {
			cache.put(key, 1000000);
			return 1000000;
		}
		if(bossHealth <= 0) {
			cache.put(key, 0);
			return 0;
		}
		
		int damage = shieldTimer > 0 ? 1 : 8;
		if(rechargeTimer > 0) {
			manna += 101;
		}
		
		if(!playerTurn) {
			int t = cost(true, playerHealth-damage, manna, bossHealth, Math.max(shieldTimer-1, 0), Math.max(poisonTimer-1, 0), Math.max(rechargeTimer-1, 0), depth+1);
			cache.put(key, t);
			return t;
		}
		
		int min = Integer.MAX_VALUE;
		
		if(bossHealth <= 4) {
			min = 53;
		} else {
			int mm = cost(false, playerHealth, manna-53, bossHealth-4, Math.max(shieldTimer-1, 0), Math.max(poisonTimer-1, 0), Math.max(rechargeTimer-1, 0), depth+1);
			if(mm < min) {
				min = mm+53;
			}
		}
		int d = cost(false, playerHealth+2, manna-73, bossHealth-2, Math.max(shieldTimer-1, 0), Math.max(poisonTimer-1, 0), Math.max(rechargeTimer-1, 0), depth+1);
		if(d+73 < min) {
			min = d+73;
		}
		
		if(shieldTimer <= 1) {
			int s = cost(false, playerHealth, manna-113, bossHealth, 6, Math.max(poisonTimer-1, 0), Math.max(rechargeTimer-1, 0), depth+1);
			if(s+113 < min) {
				min = s+113;
			}
		}
		
		if(poisonTimer <= 1) {
			int p = cost(false, playerHealth, manna-173, bossHealth, Math.max(shieldTimer-1, 0), 6, Math.max(rechargeTimer-1, 0), depth+1);
			if(p+173 < min) {
				min = p+173;
			}
		}
		
		if(rechargeTimer <= 1) {
			int r = cost(false, playerHealth, manna-229, bossHealth, Math.max(shieldTimer-1, 0), Math.max(poisonTimer-1, 0), 5, depth+1);
			if(r+229 < min) {
				min = r+229;
			}
		}
		
		cache.put(key, min);
		return min;
	}

}
