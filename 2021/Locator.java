import java.util.*;

public class Locator {
	public ArrayList<Beacon> origLocs  = new ArrayList<Beacon>();
	public int[] transform = new int[3];
	public int rotation = 0;
	public ArrayList<Beacon> movedLocs = new ArrayList<Beacon>();
	public ArrayList<Beacon> all;
	boolean solved = false;

	public void solve(ArrayList<Locator> scanners) {
		for(Locator s : scanners) {
			if(!s.solved) {
				continue;
			}
			for(int rotation = 0; rotation < 48; rotation++) {
				for(int frown = 0; frown < s.movedLocs.size()-11; frown++) {
					for(int i = 0; i < origLocs.size()-11; i++) {
						int[] t = new int[3];
						t[0] = s.movedLocs.get(frown).place[0]-origLocs.get(i).rotate(rotation).place[0];
						t[1] = s.movedLocs.get(frown).place[1]-origLocs.get(i).rotate(rotation).place[1];
						t[2] = s.movedLocs.get(frown).place[2]-origLocs.get(i).rotate(rotation).place[2];

						int matching = 0;

						for(int j = 0; j < origLocs.size(); j++) {
							if(contains(s.movedLocs, origLocs.get(j).rotate(rotation).transform(t[0], t[1], t[2]))) {
								matching++;
							}
						}

						if(matching >= 12) {
							solved = true;
							this.rotation = rotation;
							transform = t;
							for(int j = 0; j < origLocs.size(); j++) {
								movedLocs.add(origLocs.get(j).rotate(rotation).transform(t[0], t[1], t[2]));
								if(!contains(all, movedLocs.get(j))) {
									all.add(movedLocs.get(j));
								}
							}
							return;
						}

					}
				}
			}
		}
	}
	public boolean contains(ArrayList<Beacon> list, Beacon b) {
		for(Beacon beacon : list) {
			if(beacon.equals(b)) {
				return true;
			}
		}
		return false;
	}

}
