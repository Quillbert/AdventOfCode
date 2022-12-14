use std::fs;

#[derive(Debug)]
enum Pair {
	Num(u8),
	List(Vec<Pair>),
}

enum Order {
	Correct,
	Maybe,
	Wrong,
}

fn build_pair(input: &str) -> Vec<Pair> {
	let mut out = Vec::new();

	let chars: Vec<_> = input.chars().collect();

	let mut buf = String::new();
	
	let mut i = 0;

	while i < chars.len() {
		if chars[i] == '[' {
			let mut height = 1;
			let mut j = i + 1;
			while height > 0 {
				if chars[j] == '[' {
					height += 1;
				} else if chars[j] == ']' {
					height -=1;
				}
				j += 1;
			}
			let layer: Vec<_> = build_pair(&chars[i+1..j-1].iter().cloned().collect::<String>());
			let new_pair = Pair::List(layer);
			out.push(new_pair);
			i = j
		} else if chars[i] == ',' {
			if buf.len() > 0 {
				let num = buf.parse::<u8>().unwrap();
				let new_pair = Pair::Num(num);
				out.push(new_pair);
			}
			buf.clear();
		} else if chars[i].is_numeric() {
			buf.push(chars[i].clone());
		}
		i += 1;
	}

	if buf.len() > 0 {
		let num = buf.parse::<u8>().unwrap();
		let new_pair = Pair::Num(num);
		out.push(new_pair);
	}


	out
}

fn less_than(packet1: &Pair, packet2: &Pair) -> Order {
	match (packet1, packet2) {
		(Pair::Num(x), Pair::Num(y)) => {
			if x < y {
				Order::Correct
			} else if x > y {
				Order::Wrong
			} else {
				Order::Maybe
			}
		},
		(Pair::List(vec1), Pair::List(vec2)) => {
			for i in 0..vec1.len() {
				if i >= vec2.len() {
					return Order::Wrong;
				}
				let order = less_than(&vec1[i], &vec2[i]);
				if let Order::Correct = order {
					return order;
				} else if let Order::Wrong = order {
					return order;
				}
			}
			if vec1.len() < vec2.len() {
				return Order::Correct;
			}
			Order::Maybe
		},
		(Pair::Num(x), vec) => {
			let new_pair = Pair::List(vec![Pair::Num(*x)]);
			less_than(&new_pair, vec)
		},
		(vec, Pair::Num(x)) => {
			let new_pair = Pair::List(vec![Pair::Num(*x)]);
			less_than(vec, &new_pair)
		},
	}
}

fn sort(mut list: Vec<Pair>) -> Vec<Pair>{
	for i in (0..list.len()-1).rev() {
		for j in i..list.len()-1 {
			if let Order::Wrong = less_than(&list[j], &list[j + 1]) {
				let p1 = list.remove(j);
				let p2 = list.remove(j);
				list.insert(j, p2);
				list.insert(j + 1, p1);
			}
		}
	}
	list
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut packets = Vec::new();

	for line in input.lines() {
		if line.len() > 0 {
			let packet = Pair::List(build_pair(&line));
			packets.push(packet);
		}
	}

	println!("{:?}", packets[0]);

	packets.push(Pair::List(vec![Pair::List(vec![Pair::List(vec![Pair::Num(2)])])]));
	packets.push(Pair::List(vec![Pair::List(vec![Pair::List(vec![Pair::Num(6)])])]));

	let check1 = Pair::List(vec![Pair::List(vec![Pair::List(vec![Pair::Num(2)])])]);
	let check2 = Pair::List(vec![Pair::List(vec![Pair::List(vec![Pair::Num(6)])])]);

	let packets = sort(packets);

	let mut value = 1;

	for (index, packet) in packets.iter().enumerate() {
		if let Order::Maybe = less_than(&packet, &check1) {
			value *= (index + 1);
		}
		if let Order::Maybe = less_than(&packet, &check2) {
			value *= (index + 1);
		}
	}
	println!("{}", value);
}
