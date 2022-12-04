use std::fs;

use std::collections::HashSet;

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut count = 0;

	for line in input.lines() {
		let parts: Vec<_> = line.split(",").collect();
		let mut a = HashSet::default();
		let mut b = HashSet::default();
		let part1: Vec<_>  = parts[0].split("-").collect();
		for i in part1[0].parse::<u32>().unwrap()..=part1[1].parse::<u32>().unwrap() {
			a.insert(i);
		}
		let part2: Vec<_>  = parts[1].split("-").collect();
		for i in part2[0].parse::<u32>().unwrap()..=part2[1].parse::<u32>().unwrap() {
			b.insert(i);
		}

		let union: HashSet<_> = a.union(&b).map(|l| *l).collect();

		if union == a || union == b {
			count += 1;
		}
	}

    println!("{}", count);
}
