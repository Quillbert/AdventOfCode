use std::fs;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut sum: u128 = 0;

	let lines: Vec<_> = input.lines().collect();

	for i in (0..lines.len()).into_iter().step_by(3) {
		let i = i as usize;


		let a: Vec<char> = lines[i].chars().collect();
		let b: Vec<char> = lines[i + 1].chars().collect();
		let c: Vec<char> = lines[i + 2].chars().collect();

		let mut union = Vec::default();

		for ch in a {
			if b.contains(&ch) && c.contains(&ch) {
				union.push(ch);
			}
		}
				
		let mut cha = union[0] as u128;
		if cha < 96 {
			cha = cha - (65-27);
			sum = sum + cha;
		} else {
			cha = cha - 96;
			sum = sum + cha;
		}


	}
	

    println!("{}", sum);
}
