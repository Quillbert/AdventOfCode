use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	let mut biggest = vec![];
	let mut sum = 0;
	for line in input.lines() {
		if line.len() == 0 {
			biggest.push(sum);
			sum = 0;
		} else {
			sum += line.parse::<i32>().unwrap();
		}
	}
	biggest.sort_by(|a, b| b.cmp(a));
	println!("{}", biggest[0] + biggest[1] + biggest[2]);
}
