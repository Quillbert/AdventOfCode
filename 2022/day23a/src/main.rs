use std::fs;
use std::collections::HashMap;

#[derive(Copy, Clone, Debug)]
enum Direction {
	North,
	South,
	West,
	East,
}

#[derive(Debug)]
struct Elf {
	x: i32,
	y: i32,
	planned: Option<Direction>,
}

impl Elf {
	fn new(x: i32, y: i32) -> Self {
		Elf { x, y, planned: None}
	}

	fn alone(&self, map: &HashMap<(i32, i32), Elf>) -> bool {
		!map.contains_key(&(self.x-1, self.y-1)) &&
			!map.contains_key(&(self.x, self.y-1)) &&
			!map.contains_key(&(self.x+1, self.y-1)) &&
			!map.contains_key(&(self.x+1, self.y)) &&
			!map.contains_key(&(self.x+1, self.y+1)) &&
			!map.contains_key(&(self.x, self.y+1)) &&
			!map.contains_key(&(self.x-1, self.y+1)) &&
			!map.contains_key(&(self.x-1, self.y))
	}
	
	fn check(&mut self, direction: Direction, map: &HashMap<(i32, i32), Elf>) {
		if self.alone(map) {
			return;
		}
		if let Some(_) = self.planned {
			return;
		}
		match direction {
			Direction::North => {
				if !map.contains_key(&(self.x, self.y-1)) &&
					!map.contains_key(&(self.x-1, self.y-1)) &&
					!map.contains_key(&(self.x+1, self.y-1)) {
					self.planned = Some(Direction::North);	
				}
			},
			Direction::South => {
				if !map.contains_key(&(self.x, self.y+1)) &&
					!map.contains_key(&(self.x-1, self.y+1)) &&
					!map.contains_key(&(self.x+1, self.y+1)) {
					self.planned = Some(Direction::South);	
				}
			},
			Direction::West => {
				if !map.contains_key(&(self.x-1, self.y)) &&
					!map.contains_key(&(self.x-1, self.y-1)) &&
					!map.contains_key(&(self.x-1, self.y+1)) {
					self.planned = Some(Direction::West);	
				}
			},
			Direction::East => {
				if !map.contains_key(&(self.x+1, self.y)) &&
					!map.contains_key(&(self.x+1, self.y-1)) &&
					!map.contains_key(&(self.x+1, self.y+1)) {
					self.planned = Some(Direction::East);	
				}
			},
		}
	}
}

fn print_map(map: &HashMap<(i32, i32), Elf>) {
	let min_x = map.values().map(|x| x.x).min().unwrap();
	let max_x = map.values().map(|x| x.x).max().unwrap();
	let min_y = map.values().map(|x| x.y).min().unwrap();
	let max_y = map.values().map(|x| x.y).max().unwrap();

	for y in min_y..=max_y {
		for x in min_x..=max_x {
			if map.contains_key(&(x, y)) {
				print!("#");
			} else {
				print!(".");
			}
		}
		println!();
	}
	println!();
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	let mut map = HashMap::new();

	for (i, line) in input.lines().enumerate() {
		for (j, c) in line.chars().enumerate() {
			if c == '#' {
				let i = i as i32;
				let j = j as i32;
				let elf = Elf::new(j, i);	
				map.insert((j, i), elf);
			}
		}
	}

	let mut orders = vec![Direction::North, Direction::South, Direction::West, Direction::East];

	for _debug in 0..10 {
		for order in &orders {
			let key_list: Vec<_> = map.keys().cloned().collect();
			for key in key_list {
				let mut elf = map.remove(&key).unwrap();
				elf.check(*order, &map);
				map.insert(key, elf);
			}
		}

		let mut plans = HashMap::new();

		for elf in map.values() {
			match elf.planned {
				None => continue,
				Some(Direction::North) => {
					let key = (elf.x, elf.y - 1);
					let up = *plans.get(&key).unwrap_or(&0);
					plans.insert(key, up + 1);
				},
				Some(Direction::South) => {
					let key = (elf.x, elf.y + 1);
					let up = *plans.get(&key).unwrap_or(&0);
					plans.insert(key, up + 1);
				},
				Some(Direction::West) => {
					let key = (elf.x - 1, elf.y);
					let up = *plans.get(&key).unwrap_or(&0);
					plans.insert(key, up + 1);
				},
				Some(Direction::East) => {
					let key = (elf.x + 1, elf.y);
					let up = *plans.get(&key).unwrap_or(&0);
					plans.insert(key, up + 1);
				},
			}
		}

		let elves: Vec<_> = map.drain().map(|x| x.1).collect();

		for mut elf in elves {
			let key = match elf.planned {
				None => (elf.x, elf.y),
				Some(Direction::North) => (elf.x, elf.y - 1),
				Some(Direction::South) => (elf.x, elf.y + 1),
				Some(Direction::West) => (elf.x - 1, elf.y),
				Some(Direction::East) => (elf.x + 1, elf.y),
			};
			if *plans.get(&key).unwrap_or(&0) > 1 {
				let key = (elf.x, elf.y);
				elf.planned = None;
				map.insert(key, elf);
				continue;
			}
			elf.planned = None;
			elf.x = key.0;
			elf.y = key.1;
			map.insert(key, elf);
		}

		let m = orders.remove(0);
		orders.push(m);

		//print_map(&map);
	}

	let min_x = map.values().map(|x| x.x).min().unwrap();
	let max_x = map.values().map(|x| x.x).max().unwrap();
	let min_y = map.values().map(|x| x.y).min().unwrap();
	let max_y = map.values().map(|x| x.y).max().unwrap();

	let area= ((max_x - min_x)+1) * ((max_y - min_y)+1);

    println!("{}", area - map.len() as i32);
}
