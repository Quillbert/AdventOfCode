import java.util.*;
import java.math.*;

public class Day24P1 {

	public static HashMap<String, Bundle> cache = new HashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		ArrayList<Integer> packages = new ArrayList<>();
		
		while(s.hasNext()) {
			packages.add(s.nextInt());
		}
		
		//System.out.println(smallest(packages, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>()).qe);

		//System.out.println(qe(packages, new ArrayList<Integer>(), 6, 520)); // part 1
		System.out.println(qe(packages, new ArrayList<Integer>(), 4, 390)); // part 2
		
	}
	
	public static BigInteger q(ArrayList<Integer> list) {
		BigInteger out = new BigInteger("1");
		for(Integer i : list) {
			out = out.multiply(new BigInteger(i.toString()));
		}
		return out;
	}
	
	public static BigInteger qe(ArrayList<Integer> unused, ArrayList<Integer> list, int depth, int target) {
		if(depth == 0) {
			if(sum(list) == target) {
				return q(list);
			} else {
				return new BigInteger("999999999999999999999999999999999999999999999999999999999999999999999");
			}
		}
		
		BigInteger min = new BigInteger("999999999999999999999999999999999999999999999999999999999999999999999");
		for(int i = 0; i < unused.size(); i++) {
			ArrayList<Integer> copy = new ArrayList<>(unused);
			ArrayList<Integer> listCopy = new ArrayList<>(list);
			listCopy.add(copy.remove(i));
			BigInteger v = qe(copy, listCopy, depth-1, target);
			if(v.compareTo(min) < 0) {
				min = v;
			}
		}
		
		return min;
	}
	
	public static Bundle smallest(ArrayList<Integer> unused, ArrayList<Integer> g1, ArrayList<Integer> g2, ArrayList<Integer> g3) {
		String key = unused + "~" + g1 + "~" + g2 + "~" + g3;
		
		if(cache.containsKey(key)) {
			return cache.get(key);
		}
		
		if(unused.size() == 0) {
			int sum1 = sum(g1);
			int sum2 = sum(g2);
			int sum3 = sum(g3);
			if (sum1 == sum2 && sum2 == sum3) {
				Bundle b = new Bundle();
				b.qe = qe(g1);
				b.numPack = g1.size();
				cache.put(key, b);
				return b;
			} else {
				Bundle b = new Bundle();
				b.qe = Integer.MAX_VALUE;
				b.numPack = Integer.MAX_VALUE;
				cache.put(key, b);
				return b;
			}
		}
		
		Bundle b = null;
		
		int first = unused.remove(0);
		
		ArrayList<Integer> copy1 = new ArrayList<>(g1);
		copy1.add(first);
		ArrayList<Integer> copy2 = new ArrayList<>(g2);
		copy2.add(first);
		ArrayList<Integer> copy3 = new ArrayList<>(g3);
		copy3.add(first);
		
		b = smallest(new ArrayList<>(unused), new ArrayList<>(copy1), new ArrayList<>(g2), new ArrayList<>(g3));
		
		Bundle temp = smallest(new ArrayList<>(unused), new ArrayList<>(g1), new ArrayList<>(copy2), new ArrayList<>(g3));
		if(temp.numPack < b.numPack || (temp.numPack == b.numPack && temp.qe < b.qe)) {
			b = temp;
		}
		
		temp = smallest(new ArrayList<>(unused), new ArrayList<>(g1), new ArrayList<>(g2), new ArrayList<>(copy3));
		if(temp.numPack < b.numPack || (temp.numPack == b.numPack && temp.qe < b.qe)) {
			b = temp;
		}
		
		cache.put(key, b);
		return b;
	}
	
	public static int sum(ArrayList<Integer> list) {
		int sum = 0;
		for(Integer i : list) {
			sum += i;
		}
		return sum;
	}
	
	public static long qe(ArrayList<Integer> list) {
		int qe = 1;
		for(Integer i : list) {
			qe *= i;
		}
		return qe;
	}
	
	private static class Bundle {
		public long qe;
		public int numPack;
	}

}
