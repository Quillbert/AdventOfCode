use std::fs;

#[derive(Clone, Copy, Debug)]
enum Space {
	Empty,
	Wall,
	Path,
}

fn region(x: i32, y: i32) -> i32 {
	match (x, y) {
		(0..=49, 100..=149) => 3,
		(0..=49, 150..=199) => 5,
		(50..=99, 0..=49) => 0,
		(50..=99, 50..=99) => 2,
		(50..=99, 100..=149) => 4,
		(100..=149, 0..=49) => 1,
		(x, y) => {
			-1
		},
	}
}

fn at_edge(x: i32, y: i32, facing: i32) -> bool {
	match (x, y, facing) {
		(49, _, 0) => true,
		(99, _, 0) => true,
		(149, _, 0) => true,
		(_, 49, 1) => true,
		(_, 99, 1) => true,
		(_, 149, 1) => true,
		(_, 199, 1) => true,
		(0, _, 2) => true,
		(50, _, 2) => true,
		(100, _, 2) => true,
		(_, 0, 3) => true,
		(_, 50, 3) => true,
		(_, 100, 3) => true,
		(_, 150, 3) => true,
		_ => false,
	}
}

fn go(map: &Vec<Vec<Space>>, x: &mut i32, y: &mut i32, facing: &mut i32, distance: i32) {
	let mut px = *x;
	let mut py = *y;
	let mut pf = *facing;
	for _ in 0..distance {
		if let Space::Wall = map[*y as usize][*x as usize] {
			*x = px;
			*y = py;
			*facing = pf;
			break;
		} else {
			px = *x;
			py = *y;
			pf = *facing;
		}
		if at_edge(*x, *y, *facing) {
			let region = region(*x, *y);
			match (region, *facing) {
				(0, 0) => {
					*x = 100;
				},
				(0, 1) => {
					*y = 50;
				},
				(0, 2) => {
					*facing = 0;
					*x = 0;
					*y = 149 - *y;
				},
				(0, 3) => {
					*facing = 0;
					*y = *x + 100;
					*x = 0;
				},
				(1, 0) => {
					*facing = 2;
					*y = 149 - *y;
					*x = 99;
				},
				(1, 1) => {
					*facing = 2;		
					*y = *x - 50;
					*x = 99;
				},
				(1, 2) => {
					*x = 99;	
				},
				(1, 3) => {
					*facing = 3;
					*x -= 100;
					*y = 199;
				},
				(2, 0) => {
					*facing = 3;
					*x = *y + 50;
					*y = 49;
				},
				(2, 1) => {
					*y = 100;
				},
				(2, 2) => {
					*facing = 1;
					*x = *y - 50;
					*y = 100;
				},
				(2, 3) => {
					*y = 49;	
				},
				(3, 0) => {
					*x = 50;	
				},
				(3, 1) => {
					*y = 150;
				},
				(3, 2) => {
					*facing = 0;	
					*y = 149 - *y;
					*x = 50;
				},
				(3, 3) => {
					*facing = 0;
					*y = *x + 50;
					*x = 50;
				},
				(4, 0) => {
					*facing = 2;
					*x = 149;
					*y = 149 - *y;
				},
				(4, 1) => {
					*facing = 2;
					*y = *x + 100;
					*x = 49;
				},
				(4, 2) => {
					*x = 49;	
				},
				(4, 3) => {
					*y = 100;	
				},
				(5, 0) => {
					*facing = 3;
					*x = *y - 100;
					*y = 149;
				},
				(5, 1) => {
					*facing = 1;
					*x += 100;
					*y = 0;
				},
				(5, 2) => {
					*facing = 1;
					*x = *y - 100;
					*y = 0;
				},
				(5, 3) => {
					*y = 149;
				},
				(x, y) => {
					println!("{}, {}", x, y);
					unreachable!();
				},
			}
		} else {
			match *facing {
				0 => *x += 1,
				1 => *y += 1,
				2 => *x -= 1,
				3 => *y -= 1,
				_ => unreachable!(),
			}
		}
		if region(*x, *y) < 0 {
			println!("{}, {}, {}, {}, {}", x, y, px, py, facing);
			panic!();
		}
	}
	if let Space::Wall = map[*y as usize][*x as usize] {
		*x = px;
		*y = py;
		*facing = pf;
	}
}

fn main() {
	let input = fs::read_to_string("test2.txt").unwrap();
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
				go(&map, &mut x, &mut y, &mut facing, forward);
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
		go(&map, &mut x, &mut y, &mut facing, forward);
	}
	facing = facing % 4;
	if facing < 0 {
		facing += 4;
	}



	println!("{}, {}, {}", x, y, facing);
    println!("{}", 1000 * (y + 1) + 4 * (x + 1) + facing);
}
