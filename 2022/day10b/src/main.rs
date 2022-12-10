use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut x: i32 = 1;

	let mut grid = ['.'; 240];
	let mut cycle: usize = 0;

	for line in input.lines() {
		let parts: Vec<_> = line.split(' ').collect();
		if (x - (cycle % 40) as i32).abs() <= 1 {
			grid[cycle] = '#';
		}
		cycle += 1;
		if parts[0] == "addx" {
			if (x - (cycle % 40) as i32).abs() <= 1 {
				grid[cycle] = '#';
			}
			cycle += 1;
			x += parts[1].parse::<i32>().unwrap();
		}
	}

	for i in 0..6 {
		for j in 0..40 {
			print!("{}", grid[i * 40 + j]);
		}
		println!();
	}
}
