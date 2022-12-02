use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	let mut score = 0;
	for line in input.lines() {
		let parts = line.split(" ").collect::<Vec<&str>>();
		match parts[1]  {
			"X" => {
				score += 1;
				if parts[0] == "A" {
					score += 3;
				} else if parts[0] == "C" {
					score += 6;
				}
			},
			"Y" => {
				score += 2;
				if parts[0] == "B" {
					score += 3;
				} else if parts[0] == "A" {
					score += 6;
				}
			},
			"Z" => {
				score += 3;
				if parts[0] == "C" {
					score += 3;
				} else if parts[0] == "B" {
					score += 6;
				}
			},
			_ => unreachable!(),
		}
		//println!("{}", score);
	}

    println!("{}", score);
}
