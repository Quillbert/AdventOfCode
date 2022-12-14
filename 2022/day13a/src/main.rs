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

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	let pairs: Vec<_> = input.split("\r\n\r\n").collect();

	let mut count = 0;

	for (index, pair) in pairs.iter().enumerate() {
		let packets: Vec<_> = pair.lines().collect();
		let packet1 = Pair::List(build_pair(&packets[0]));
		let packet2 = Pair::List(build_pair(&packets[1]));

		match less_than(&packet1, &packet2) {
			Order::Wrong => continue,
			_ => {
				count += index + 1;
			},
		}
	}

	println!("{}", count);

}
