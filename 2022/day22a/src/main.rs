use std::fs;

#[derive(Clone, Copy)]
enum Space {
	Empty,
	Wall,
	Path,
}

fn can_move(map: &Vec<Vec<Space>>, x: i32, y: i32, facing: i32) -> bool {
	let y = y as usize % map.len();
	let x = x as usize % map[y].len();
	if facing == 0 {
		let mut pos: usize = x + 1;
		loop {
			if let Space::Path = map[y][pos % map[y].len()] {
				return true;
			} else if let Space::Wall = map[y][pos % map[y].len()] {
				return false;
			}
			pos += 1;
		}
	} else if facing == 1 {
		let mut pos: usize = y + 1;
		loop {
			if x >= map[pos % map.len()].len() {
				pos += 1;
				continue;
			}
			if let Space::Path = map[pos % map.len()][x] {
				return true;
			} else if let Space::Wall = map[pos % map.len()][x] {
				return false;
			}
			pos += 1;
		}
	} else if facing == 2 {
		let mut pos: usize = x + map[y].len() - 1;
		loop {
			if let Space::Path = map[y][pos % map[y].len()] {
				return true;
			} else if let Space::Wall = map[y][pos % map[y].len()] {
				return false;
			}
			pos -= 1;
		}
	} else if facing == 3 {
		let mut pos: usize = y + map.len() - 1;
		loop {
			if x >= map[pos % map.len()].len() {
				pos += 1;
				continue;
			}
			if let Space::Path = map[pos % map.len()][x] {
				return true;
			} else if let Space::Wall = map[pos % map.len()][x] {
				return false;
			}
			pos -= 1;
		}
	}
	false
}

fn go(map: &Vec<Vec<Space>>, x: &mut i32, y: &mut i32, facing: i32, distance: i32) {
	*y %= map.len() as i32;
	*x %= map[*y as usize].len() as i32;
	let mut py = *y;
	let mut px = *x;
	for _ in 0..distance {
		if let Space::Wall = map[*y as usize][*x as usize] {
			*x = px;
			*y = py;
			break;
		} else {
			px = *x;
			py = *y;
		}
		if can_move(map, *x, *y, facing) {
			if facing == 0 {
				*x += 1;
				*x %= map[*y as usize].len() as i32;
				while let Space::Empty = map[*y as usize][*x as usize] {
					*x += 1;
				}
			} else if facing == 1 {
				*y += 1;
				*y %= map.len() as i32;
				loop {
					if *x >= map[*y as usize].len() as i32 {
						*y += 1;
						*y %= map.len() as i32;
						continue;
					}
					if let Space::Empty = map[*y as usize][*x as usize] {
						*y += 1;
						*y %= map.len() as i32;
					} else {
						break;
					}
				}
			} else if facing == 2 {
				if *x == 0 {
					*x = map[*y as usize].len() as i32;
				}
				*x -= 1;
				while let Space::Empty = map[*y as usize][*x as usize] {
					*x -= 1;
					if *x == 0 {
						*x = map[*y as usize].len() as i32 - 1;
					}
				}
			} else {
				if *y == 0 {
					*y = map.len() as i32 - 1;
				} else {
					*y -= 1;
				}
				loop {
					if *y < 0 {
						*y += map.len() as i32;
					}
					if *x >= map[*y as usize].len() as i32 {
						*y -= 1; 
						continue;
					}
					if let Space::Empty = map[*y as usize][*x as usize] {
						*y -= 1;
					} else {
						break;
					}
				}

			}
		}
	}
	if *y < 0 {
		*y += map.len() as i32;
	}
	*y %= map.len() as i32;
	*x %= map[*y as usize].len() as i32;
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	let parts: Vec<_> = input.split("\r\n\r\n").collect();

	let map_key: Vec<_> = parts[0].lines().collect();

	let mut map = Vec::new();

	for i in 0..map_key.len() {
		let ch: Vec<_> = map_key[i].chars().collect();
		let mut add = Vec::new();
		for j in 0..ch.len() {
			match ch[j] {
				' ' => {
					add.push(Space::Empty);
				},
				'.' => {
					add.push(Space::Path);
				},
				'#' => {
					add.push(Space::Wall);
				},
				_ => unreachable!(),
			}
		}
		map.push(add);
	}

	let mut y = 0;
	let mut x = map[0].iter().position(|x| matches!(x, Space::Path)).unwrap() as i32;

	let mut facing = 0;

	let instructions: Vec<_> = parts[1].chars().collect();

	let mut number = String::new();

	for c in instructions {
		if c.is_numeric() {
			number.push(c);
		} else {
			if number.len() > 0 {
				let forward = number.parse::<i32>().unwrap();
				number = String::new();
				go(&map, &mut x, &mut y, facing, forward);
			}
			if c == 'L' {
				facing -= 1;
			} else if c == 'R' {
				facing += 1;
			}
			facing = facing % 4;
			if facing < 0 {
				facing += 4;
			}
		}
	}
	if number.len() > 0 {
		let forward = number.parse::<i32>().unwrap();
		number = String::new();
		go(&map, &mut x, &mut y, facing, forward);
	}



	println!("{}, {}, {}", x, y, facing);
    println!("{}", 1000 * (y + 1) + 4 * (x + 1) + facing);
}
