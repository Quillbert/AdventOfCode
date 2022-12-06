use std::fs;

use std::collections::HashSet;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let input: Vec<_> = input.chars().collect();

	for i in 0..input.len()-4 {
		let piece = &input[i..i+4];
		let set: HashSet<_> = piece.iter().collect();
		if set.len() == 4 {
			println!("{}", i + 4);
			break;
		}
	}

}
