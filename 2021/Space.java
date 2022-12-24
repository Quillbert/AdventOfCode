import java.util.*;

public class Space {
	public char[][] values = new char[3][11];

	public boolean isWinning() {
		return values[1][2] == 'A' &&
				values[2][2] == 'A' &&
				values[1][4] == 'B' &&
				values[2][4] == 'B' &&
				values[1][6] == 'C' &&
				values[2][6] == 'C' &&
				values[1][8] == 'D' &&
				values[2][8] == 'D';
	}

	public Space copy() {
		Space out = new Space();
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				out.values[i][j] = values[i][j];
			}
		}
		return out;
	}

	public ArrayList<Space> moves() {
		ArrayList<Space> out = new ArrayList<Space>();

		for(int i = 0; i < values[0].length; i++) {
			for(int y = 1; y <= 2; y++) {
				for(int x = 2; x < values[0].length; x+= 2) {
					if(values[0][i] == '.' ^ values[y][x] == '.') {
						Space copy = copy();
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

	public boolean isValidMove(Space move) {
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
		if((move.values[1][2] != '.' && move.values[2][2] == '.') ||
				(move.values[1][4] != '.' && move.values[2][4] == '.') ||
				(move.values[1][6] != '.' && move.values[2][6] == '.') ||
				(move.values[1][8] != '.' && move.values[2][8] == '.')) {
			return false;
		}

		int oldCubbies = 0;
		int newCubbies = 0;

		int x1 = -1;
		int y1 = -1;

		for(int i = 1; i < values.length; i++) {
			for(int j = 0; j < values[1].length; j++) {
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
						if(i == 1 && move.values[1][j] != move.values[2][j]) {
							return false;
						}
						if((j == 2 && move.values[i][j] != 'A') ||
								(j == 4 && move.values[i][j] != 'B') ||
								(j == 6 && move.values[i][j] != 'C') ||
								(j == 8 && move.values[i][j] != 'D')) {
							return false;
						}
					} else {
						if(i == 2 || values[i][j] == values[i+1][j]) { 
							if((j == 2 && values[i][j] == 'A') ||
									(j == 4 && values[i][j] == 'B') ||
									(j == 6 && values[i][j] == 'C') ||
									(j == 8 && values[i][j] == 'D')) {
								return false;
							}
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

	public int cost(Space move) {
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

	public boolean equals(Space other) {
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
}
