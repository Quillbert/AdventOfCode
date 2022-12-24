use std::fs;
use std::collections::HashMap;

fn solve(monkeys: &HashMap<String, String>, root: &str) -> i128 {
	let parts: Vec<_> = monkeys.get(root).unwrap().split(' ').collect();
	if parts.len() == 1 {
		return parts[0].parse::<i128>().unwrap();
	}
	if parts[1] == "+" {
		return solve(monkeys, parts[0]) + solve(monkeys, parts[2]);
	}
	if parts[1] == "-" {
		return solve(monkeys, parts[0]) - solve(monkeys, parts[2]);
	}
	if parts[1] == "*" {
		return solve(monkeys, parts[0]) * solve(monkeys, parts[2]);
	}
	if parts[1] == "/" {
		return solve(monkeys, parts[0]) / solve(monkeys, parts[2]);
	}

	0	
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut monkeys = HashMap::new();

	for line in input.lines() {
		let parts: Vec<_> = line.split(": ").collect();
		monkeys.insert(String::from(parts[0]), String::from(parts[1]));
	}

	let parts: Vec<_> = monkeys.get("root").unwrap().split(' ').map(|x| String::from(x)).collect();

	let mut minimum: i128 = 0;
	let mut maximum: i128 = 1000000000000000;
	loop {
		let half = (minimum + maximum) / 2;
		monkeys.insert(String::from("humn"), format!("{}", half));
		let a = solve(&monkeys, &parts[0]);
		let b = solve(&monkeys, &parts[2]);
		if a == b {
			println!("{}", half);
			break;
		}
		if a < b {
			maximum = half - 1;
		} else {
			minimum = half + 1;
		}
	}
}
