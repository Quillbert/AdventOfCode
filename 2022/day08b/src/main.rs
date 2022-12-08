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

	let mut highest = 0;


	for i in 0..trees.len() {
		for j in 0..trees[i].len() {
			let height = trees[i][j];
			let mut left = 0;
			let mut right = 0;
			let mut top = 0;
			let mut bottom = 0;
			for k in (0..i).rev() {
				top += 1;
				if trees[k][j] >= height {
					break;
				}
			}
			for k in (0..j).rev() {
				left += 1;
				if trees[i][k] >= height {
					break;
				}
			}
			for k in i+1..trees.len() {
				bottom += 1;
				if trees[k][j] >= height {
					break;
				}
			}
			for k in j+1..trees[i].len() {
				right += 1;
				if trees[i][k] >= height {
					break;
				}
			}
			let visible = left * right * top * bottom;
			if visible > highest {
				highest = visible;
			}
		}
	}



    println!("{}", highest);
}
