import java.util.*;
import java.math.*;

public class Day22P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		ArrayList<String> lines = new ArrayList<String>();

		String str = s.nextLine().trim();

		while(str.length() > 0) {
			lines.add(str);

			str = s.nextLine().trim();
		}

		ArrayList<Cuboid> areas = new ArrayList<Cuboid>();

		for(int i = 0; i < lines.size(); i++) {
			String[] parts = lines.get(i).split(" ");

			boolean on = parts[0].equals("on");

			parts = parts[1].split(",");

			String xStr = parts[0].substring(2, parts[0].length());
			String yStr = parts[1].substring(2, parts[1].length());
			String zStr = parts[2].substring(2);



			parts = xStr.split("\\.\\.");

			int xMin = Integer.parseInt(parts[0]);
			int xMax = Integer.parseInt(parts[1]);

			parts = yStr.split("\\.\\.");

			int yMin = Integer.parseInt(parts[0]);
			int yMax = Integer.parseInt(parts[1]);

			parts = zStr.split("\\.\\.");

			int zMin = Integer.parseInt(parts[0]);
			int zMax = Integer.parseInt(parts[1]);

			areas.add(new Cuboid(on, xMin, xMax, yMin, yMax, zMin, zMax));
		}

		
		ArrayList<Cuboid> on = new ArrayList<Cuboid>();
		
		for(int i = 0; i < areas.size(); i++) {
			Cuboid c = areas.get(i);
			
			for(int j = on.size()-1; j >= 0; j--) {
				Cuboid o = c.overlap(on.get(j));
				if(o == null) {
					continue;
				}
				ArrayList<Cuboid> nCubes = on.get(j).cutout(o);
				on.remove(j);
				for(Cuboid cuboid : nCubes) {
					on.add(cuboid);
				}
			}
			if(c.on) {
				on.add(c);
			}
			
		}
		
		BigInteger total = new BigInteger("0");

		
		
		for(int i = 0; i < on.size(); i++) {
			total = total.add(on.get(i).area());
		}
		
		
		System.out.println(total);

	}

}
