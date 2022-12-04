use std::fs;
use std::collections::HashSet;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut sum: u128 = 0;

	let lines: Vec<_> = input.lines().collect();

	for i in (0..lines.len()).into_iter().step_by(3) {
		let i = i as usize;


		let a: HashSet<char> = lines[i].chars().collect();
		let b: HashSet<char> = lines[i + 1].chars().collect();
		let c: HashSet<char> = lines[i + 2].chars().collect();
				
		let union1: HashSet<&char> = a.intersection(&b).collect();

		let union2: HashSet<&char> = a.intersection(&c).collect();

		let union3: HashSet<&&char> = union1.intersection(&union2).collect();

		for ch in union3 {
			let mut cha = ch as u128;
			println!("{}", ch);
			if cha < 96 {
				cha = cha - (65-27);
				sum = sum + cha;
			} else {
				cha = cha - 96;
				sum = sum + cha;
			}

		}

	}
	

    println!("{}", sum);
}
