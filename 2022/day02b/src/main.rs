use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	let mut score = 0;
	for line in input.lines() {
		let parts = line.split(" ").collect::<Vec<&str>>();
		match parts[1]  {
			"X" => {
				if parts[0] == "A" {
					score += 3;
				} else if parts[0] == "C" {
					score += 2;
				} else {
					score += 1;
				}
			},
			"Y" => {
				score += 3;
				if parts[0] == "B" {
					score += 2;
				} else if parts[0] == "A" {
					score += 1;
				} else {
					score += 3;
				}
			},
			"Z" => {
				score += 6;
				if parts[0] == "C" {
					score += 1;
				} else if parts[0] == "B" {
					score += 3;
				} else {
					score += 2;
				}
			},
			_ => unreachable!(),
		}
		//println!("{}", score);
	}

    println!("{}", score);
}
