use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let lines: Vec<_> = input.lines().collect();

	let mut trees = Vec::new();

	for i in 0..lines.len() {
		let line: Vec<_> = lines[i].chars().collect();
		trees.push(Vec::new());
		for j in 0..line.len() {
			trees[i].push(line[j].to_digit(10).unwrap());
		}
	}

	let mut sum = 0;


	for i in 0..trees.len() {
		for j in 0..trees[i].len() {
			let height = trees[i][j];
			let mut left = true;
			let mut right = true;
			let mut top = true;
			let mut bottom = true;
			for k in 0..i {
				if trees[k][j] >= height {
					top = false;
				}
			}
			for k in 0..j {
				if trees[i][k] >= height {
					left = false;
				}
			}
			for k in i+1..trees.len() {
				if trees[k][j] >= height {
					bottom = false;
				}
			}
			for k in j+1..trees[i].len() {
				if trees[i][k] >= height {
					right = false;
				}
			}
			let visible = left || right || top || bottom;
			if visible {
				sum += 1;
			}
		}
	}



    println!("{}", sum);
}
