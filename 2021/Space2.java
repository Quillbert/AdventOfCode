import java.util.*;

public class Space2 {
	public char[][] values = new char[5][11];
	
	public boolean isWinning() {
		for(int i = 1; i < values.length; i++) {
			for(int j = 2; j < values[i].length; j += 2) {
				if(j == 2 && values[i][j] != 'A') {
					return false;
				}
				if(j == 4 && values[i][j] != 'B') {
					return false;
				}
				if(j == 6 && values[i][j] != 'C') {
					return false;
				}
				if(j == 8 && values[i][j] != 'D') {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Space2 copy() {
		Space2 out = new Space2();
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				out.values[i][j] = values[i][j];
			}
		}
		return out;
	}
	
	public ArrayList<Space2> moves() {
		ArrayList<Space2> out = new ArrayList<Space2>();

		for(int i = 0; i < values[0].length; i++) {
			for(int y = 1; y < values.length; y++) {
				for(int x = 2; x < values[0].length; x+= 2) {
					if(values[0][i] == '.' ^ values[y][x] == '.') {
						Space2 copy = copy();
						copy.values[0][i] = values[y][x];
						copy.values[y][x] = values[0][i];
						if(isValidMove(copy)) {
							out.add(copy);
						}
					}
				}
			}
		}

		return out;
	}
	
	public int cost(Space2 move) {
		int x1 = 0, y1 = 0;
		char letter = '\0';
		for(int i = 1; i < values.length; i++) {
			for(int j = 2; j < values[1].length; j += 2) {
				if(values[i][j] != move.values[i][j]) {
					x1 = j;
					y1 = i;
					if(values[i][j] != '.') {
						letter = values[i][j];
					} else {
						letter = move.values[i][j];
					}
				}
			}
		}

		int x2 = -1;

		for(int i = 0; i < values[0].length; i++) {
			if(values[0][i] != move.values[0][i]) {
				x2 = i;
				break;
			}
		}

		int dist = Math.abs(x2-x1) + y1;

		int multiplier = 1;

		switch(letter) {
		case 'B':
			multiplier = 10;
			break;
		case 'C':
			multiplier = 100;
			break;
		case 'D':
			multiplier = 1000;
			break;
		}

		return dist*multiplier;
	}
	
	public String stringify() {
		String out = "";
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				out += values[i][j];
			}
		}
		return out;
	}

	public boolean equals(Space2 other) {
		return stringify().equals(other.stringify());
	}

	public String toString() {
		String out = "";
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				out += values[i][j];
			}
			out += "\n";
		}
		return out;
	}
	
	public boolean isValidMove(Space2 move) {
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				if(values[i][j] == '#' && move.values[i][j] != '#') {
					return false;
				}
			}
		}
		
		if(move.values[0][2] != '.' ||
				move.values[0][4] != '.' ||
				move.values[0][6] != '.' ||
				move.values[0][8] != '.') {
			return false;
		}
		
		for(int i = 2; i < values[0].length; i += 2) {
			boolean empty = false;
			for(int j = values.length-1; j >= 1; j--) {
				if(values[j][i] == '.') {
					empty = true;
				} else {
					if(empty) {
						return false;
					}
				}
			}
		}
		
		int oldCubbies = 0;
		int newCubbies = 0;
		
		int x1 = -1;
		int y1 = -1;
		
		for(int i = 1; i < values.length; i++) {
			for(int j = 2; j < values[0].length; j += 2) {
				if(values[i][j] == '.') {
					oldCubbies++;
				}
				if(move.values[i][j] == '.') {
					newCubbies++;
				}
				if(values[i][j] != move.values[i][j]) {
					x1 = j;
					y1 = i;
					if(move.values[i][j] != '.') {
						for(int k = i+1; k < values.length; k++) {
							if(move.values[k][j] != move.values[i][j]) {
								return false;
							}
						}
						if((j == 2 && move.values[i][j] != 'A') ||
								(j == 4 && move.values[i][j] != 'B') ||
								(j == 6 && move.values[i][j] != 'C') ||
								(j == 8 && move.values[i][j] != 'D')) {
							return false;
						}
					} else {
						char right = 'A';
						switch(j) {
						case 4:
							right = 'B';
							break;
						case 6:
							right = 'C';
							break;
						case 8:
							right = 'D';
							break;
						}
						
						boolean bad = false;
						for(int k = i; k < values.length; k++) {
							if(values[k][j] != right) {
								bad = true;
							}
						}
						if(!bad) {
							return false;
						}
					}
				}
			}
		}
		
		if(oldCubbies == newCubbies) {
			return false;
		}
		
		if(Math.abs(oldCubbies-newCubbies) > 1) {
			return false;
		}
		
		int x2 = -1;
		
		for(int i = 0; i < values[0].length; i++) {
			if(values[0][i] != move.values[0][i]) {
				x2 = i;
				break;
			}
		}
		
		int count = 0;

		for(int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
			if(values[0][i] != '.') {
				count++;
			}
		}
		for(int i = 0; i <= y1; i++) {
			if(values[i][x1] != '.') {
				count++;
			}
		}
		if(count > 1) {
			return false;
		}
		
		return true;
	}
}
