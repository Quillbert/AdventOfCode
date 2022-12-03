use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut sum: u128 = 0;

	for line in input.lines() {
		let line: Vec<_> = line.chars().collect();
		let l = line.len();
		'outer: for i in 0..l/2 {
			for j in l/2..l {
				let i = i as usize;
				let j = j as usize;
				if line[i] == line[j] {
					let mut c = line[i] as u128;
					if c < 96 {
						c = c - (65-27);
						sum = sum + c;
					} else {
						c = c - 96;
						sum = sum + c;
					}
					break 'outer;
				}
			}
		}

	}

    println!("{}", sum);
}
