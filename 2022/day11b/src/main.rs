use std::fs;
use num::BigUint;


#[derive(Debug)]
struct Monkey {
	items: Vec<BigUint>,
	operation: String,
	change: String,
	test: u128,
	onTrue: usize,
	onFalse: usize,
	throws: u128,
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

	let test = lines[3].split(' ').nth(3).unwrap().parse::<u128>().unwrap();

	let onTrue = lines[4].split(' ').nth(5).unwrap().parse::<usize>().unwrap();
	let onFalse = lines[5].split(' ').nth(5).unwrap().parse::<usize>().unwrap();

	let throws = 0;

	Monkey {items, operation, change, test, onTrue, onFalse, throws}

}

fn change(value: BigUint, operation: &str, change: &str, base: u128) -> BigUint {
	match (operation, change) {
		("+", "old") => {
			(value.clone() + value.clone()) % base
		},
		("*", "old") => {
			(value.clone() * value.clone()) % base
		},
		("+", num) => {
			(value + num.parse::<u128>().unwrap()) % base
		},
		("*", num) => {
			(value * num.parse::<u128>().unwrap()) % base
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

	let base = monkeys.iter().map(|x| x.test).product::<u128>();

	for count in 0..10000 {
		for i in 0..monkeys.len() {
			while monkeys[i].items.len() > 0 {
				let num = monkeys[i].items[0].clone();
				let newNum = change(num, &monkeys[i].operation, &monkeys[i].change, base);
				monkeys[i].items[0] = newNum;
				monkeys[i].throws += 1;
				if monkeys[i].items[0].clone() % monkeys[i].test == BigUint::from(0 as u128) {
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
