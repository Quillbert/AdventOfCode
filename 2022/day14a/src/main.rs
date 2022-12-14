use std::fs;

#[derive(Copy, Clone, Debug)]
enum Cell {
	Air,
	Sand,
	Rock,
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut cave = [[Cell::Air; 600]; 600];

	let mut lowest = 0;

	let lines: Vec<_> = input.lines().collect();

	for line in lines {
		let instructions: Vec<_> = line.split(" -> ").collect();
		for i in 0..instructions.len()-1 {
			let pair1: Vec<usize> = instructions[i].split(',').map(|x| x.parse().unwrap()).collect();
			let pair2: Vec<usize> = instructions[i+1].split(',').map(|x| x.parse().unwrap()).collect();
			for j in pair1[0].min(pair2[0])..=pair1[0].max(pair2[0]) {
				for k in pair1[1].min(pair2[1])..=pair1[1].max(pair2[1]) {
					cave[j][k] = Cell::Rock;
				}
			}
			if pair1[1] > lowest {
				lowest = pair1[1];
			}
			if pair2[1] > lowest {
				lowest = pair2[1];
			}
		}
	}

	let mut count = 0;

	'outer: loop {
		let mut x = 500;
		let mut y = 0;
		loop {
			if y > lowest {
				break 'outer;				
			}
			if let Cell::Air = cave[x][y+1]	{
				y += 1;
				continue;
			} else if let Cell::Air = cave[x-1][y+1] {
				x -= 1;
				y += 1;
				continue;
			} else if let Cell::Air = cave[x+1][y+1] {
				x += 1;
				y += 1;
				continue;
			}
			cave[x][y] = Cell::Sand;	
			count += 1;
			break;
		}
	}

	println!("{}", count);

}
