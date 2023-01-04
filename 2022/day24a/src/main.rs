use std::fs;
use std::collections::{VecDeque, HashSet};

#[derive(Copy, Clone, Debug)]
enum Mover {
	Left,
	Right,
	Up,
	Down,
}

#[derive(Clone, Debug)]
struct State {
	space: Vec<Vec<Vec<Mover>>>,
}

impl State {
	fn advance(&mut self) {
		let mut new_space = self.space.clone();	
		for row in &mut new_space {
			for spot in row {
				spot.clear();
			}
		}

		for i in 1..new_space.len()-1 {
			for j in 1..new_space[i].len()-1 {
				for blizzard in &self.space[i][j] {
					match blizzard {
						Mover::Up => {
							if i == 1 {
								let l = new_space.len()-2;
								new_space[l][j].push(Mover::Up);
							} else {
								new_space[i-1][j].push(Mover::Up);
							}
						},
						Mover::Down => {
							if i == new_space.len() - 2 {
								new_space[1][j].push(Mover::Down);
							} else {
								new_space[i+1][j].push(Mover::Down);
							}
						},
						Mover::Left => {
							if j == 1 {
								let l = new_space[i].len()-2;
								new_space[i][l].push(Mover::Left);
							} else {
								new_space[i][j-1].push(Mover::Left);
							}
						},
						Mover::Right => {
							if j == new_space[i].len() - 2 {
								new_space[i][1].push(Mover::Right);
							} else {
								new_space[i][j+1].push(Mover::Right);
							}
						},
					}
				}
			}
		}
		self.space = new_space;
	}
}

fn solve(state: State) -> usize {
	let mut queue = VecDeque::new();
	let mut visited = HashSet::new();

	let mut states = vec![state];
	queue.push_back(((1, 0), 0));

	while !queue.is_empty() {
		let (place, time) = queue.pop_front().unwrap();
		println!("{}", time);
		// if place.0 == 0 || place.1 == 0 || place.0 == states[0].space[0].len()-1
		// 	|| place.1 == states[0].space.len()-1 {
		// 	println!("{:?}", place);
		// 	if place != (1, 0) && place != (states[0].space[0].len()-2, states[0].space.len()-1) {
		// 		continue;
		// 	}
		// }
		while states.len() <= time {
			let mut s = states[states.len()-1].clone();
			s.advance();
			states.push(s);
		}
		let key = format!("{:?}~{:?}", place, states[time]);
		if visited.contains(&key) {
			continue;
		} else {
			visited.insert(key);
		}
		if place == (states[0].space[0].len()-2, states[0].space.len()-1) {
			return time;
		}
		if states[time].space[place.1][place.0].len() > 0 {
			continue;
		}
		queue.push_back((place, time + 1));
		if place.1 > 1 || place == (1, 1) {
			queue.push_back(((place.0, place.1 - 1), time + 1));
		}
		if place.1 < states[0].space.len()-2 || place == (states[0].space[0].len()-2, states[0].space.len()-2) {
			queue.push_back(((place.0, place.1 + 1), time + 1));
		}
		if place.0 > 1 {
			queue.push_back(((place.0-1, place.1), time + 1));
		}
		if place.0 < states[0].space[0].len()-2 && place.1 > 0 {
			queue.push_back(((place.0+1, place.1), time + 1));
		}
	}

	0
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut space = Vec::new();

	for line in input.lines() {
		let mut row = Vec::new();
		for c in line.chars() {
			let mut spot = Vec::new();
			match c {
				'<' => spot.push(Mover::Left),
				'>' => spot.push(Mover::Right),
				'v' => spot.push(Mover::Down),
				'^' => spot.push(Mover::Up),
				_ => (),
			}
			row.push(spot);
		}
		space.push(row);
	}

	let state = State{space};

    println!("{}", solve(state));
}
