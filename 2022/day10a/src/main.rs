use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut x = 1;

	let mut record = Vec::new();

	record.push(x);

	for line in input.lines() {
		let parts: Vec<_> = line.split(' ').collect();
		if parts[0] == "addx" {
			record.push(x);
			x += parts[1].parse::<i32>().unwrap();
		}
		record.push(x);
	}

	let out = 20 * record[19] + 60 * record[59] + 100 * record[99] + 140 * record[139]
		+ 180 * record[179] + 220 * record[219];

    println!("{}", out);
}
