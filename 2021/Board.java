
public class Board {
	Cell[][] values;
	private int row = 0;
	public Board() {
		values = new Cell[5][5];
	}
	public void add(String s) {
		String[] val = s.trim().split(" ");
		int count = 0;
		for(int i = 0; i < val.length; i++) {
			if(val[i].length() > 0) {
				values[row][count] = new Cell(Integer.parseInt(val[i]));
				count++;
			}
		}
		row++;
	}
	public void call(int num) {
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				if(values[i][j].value == num) {
					values[i][j].selected = true;
				}
			}
		}
	}
	public boolean winning() {
		for(int i = 0; i < values.length; i++) {
			boolean yes = true;
			for(int j = 0; j < values[i].length; j++) {
				if(!values[i][j].selected) {
					yes = false;
				}
			}
			if(yes) return true;
		}
		for(int i = 0; i < values.length; i++) {
			boolean yes = true;
			for(int j = 0; j < values[i].length; j++) {
				if(!values[j][i].selected) {
					yes = false;
				}
			}
			if(yes) return true;
		}
		return false;
	}
}
