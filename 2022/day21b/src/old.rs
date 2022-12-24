use std::fs;
use std::collections::HashMap;

fn solve(monkeys: &HashMap<String, String>, root: &str) -> String {
	if root == "humn" {
		return String::from("humn");
	}
	let mut parts: Vec<_> = monkeys.get(root).unwrap().split(' ').collect();
	if parts.len() == 1 {
		return String::from(parts[0]);
	}
	if root == "root" {
		parts[1] = "=";
	}
	let part1 = solve(monkeys, parts[0]);
	let part2 = solve(monkeys, parts[2]);
	if part1.parse::<i128>().is_ok() && part2.parse::<i128>().is_ok() {
		let a = part1.parse::<i128>().unwrap();
		let b = part2.parse::<i128>().unwrap();
		if parts[1] == "+" {
			return format!("{}", a + b);
		}
		if parts[1] == "-" {
			return format!("{}", a - b);
		}
		if parts[1] == "*" {
			return format!("{}", a * b);
		}
		if parts[1] == "/" {
			return format!("{}", a / b);
		}
	}

	
	format!("({} {} {})", solve(monkeys, parts[0]), parts[1], solve(monkeys, parts[2]))
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut monkeys = HashMap::new();

	for line in input.lines() {
		let parts: Vec<_> = line.split(": ").collect();
		monkeys.insert(String::from(parts[0]), String::from(parts[1]));
	}

	let out = solve(&monkeys, "root");

    println!("{}", out);
}
