use std::fs;
use num::BigUint;


#[derive(Debug)]
struct Monkey {
	items: Vec<BigUint>,
	operation: String,
	change: String,
	test: u32,
	onTrue: usize,
	onFalse: usize,
	throws: u32,
}

fn build_monkey(input: &str) -> Monkey {
	let lines: Vec<_> = input.lines().map(|x| x.trim()).collect();
	let items: Vec<BigUint> = lines[1].replace(",", "").split(' ')
		.filter(|x| x.parse::<u128>().is_ok())
		.map(|x| BigUint::from(x.parse::<u128>().unwrap()))
		.collect();

	let opParts: Vec<_> = lines[2].split(' ').collect();

	let operation = String::from(opParts[4]);
	let change = String::from(opParts[5]);

	let test = lines[3].split(' ').nth(3).unwrap().parse::<u32>().unwrap();

	let onTrue = lines[4].split(' ').nth(5).unwrap().parse::<usize>().unwrap();
	let onFalse = lines[5].split(' ').nth(5).unwrap().parse::<usize>().unwrap();

	let throws = 0;

	Monkey {items, operation, change, test, onTrue, onFalse, throws}

}

fn change(value: BigUint, operation: &str, change: &str) -> BigUint {
	match (operation, change) {
		("+", "old") => {
			value.clone() + value.clone()
		},
		("*", "old") => {
			value.clone() * value.clone()
		},
		("+", num) => {
			value + num.parse::<u128>().unwrap()
		},
		("*", num) => {
			value * num.parse::<u128>().unwrap()
		},
		_ => unreachable!(),
	}
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let monkeyTexts: Vec<_> = input.split("\r\n\r\n").collect();

	let mut monkeys = Vec::new();
	
	for m in monkeyTexts {
		monkeys.push(build_monkey(m));
	}

	for _ in 0..20 {
		for i in 0..monkeys.len() {
			while monkeys[i].items.len() > 0 {
				let num = monkeys[i].items[0].clone();
				let newNum = change(num, &monkeys[i].operation, &monkeys[i].change);
				monkeys[i].items[0] = newNum/3 as u32;
				monkeys[i].throws += 1;
				if monkeys[i].items[0].clone() % monkeys[i].test == BigUint::from(0 as u32) {
					let mov = monkeys[i].items.remove(0);
					let spot = monkeys[i].onTrue;
					monkeys[spot].items.push(mov);
				} else {
					let mov = monkeys[i].items.remove(0);
					let spot = monkeys[i].onFalse;
					monkeys[spot].items.push(mov);
				}
			}
		}
	}

	monkeys.sort_by_key(|m| m.throws);

	let monkeys: Vec<_> = monkeys.iter().rev().collect();

	println!("{}", monkeys[0].throws * monkeys[1].throws);



}
