use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	let mut biggest = 0;
	let mut sum = 0;
	for line in input.lines() {
		if line.len() == 0 {
			if sum > biggest {
				biggest = sum;
			}
			sum = 0;
		} else {
			sum += line.parse::<i32>().unwrap();
		}
	}
	println!("{}", biggest);
}
