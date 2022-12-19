use std::fs;
use std::collections::HashMap;

#[derive(Copy, Clone, Debug)]
enum Block {
	Empty,
	Moving, 
	Still,
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	let pattern: Vec<_> = input.trim().chars().collect();

	let mut blocks = Vec::new();

	let block1 = vec![vec![Block::Moving, Block::Moving, Block::Moving, Block::Moving]];
	blocks.push(block1);

	let block2 = vec![vec![Block::Empty, Block::Moving, Block::Empty],
		vec![Block::Moving, Block::Moving, Block::Moving],
		vec![Block::Empty, Block::Moving, Block::Empty]];
	blocks.push(block2);

	let block3 = vec![vec![Block::Moving, Block::Moving, Block::Moving],
		vec![Block::Empty, Block::Empty, Block::Moving],
		vec![Block::Empty, Block::Empty, Block::Moving]];
	blocks.push(block3);

	let block4 = vec![vec![Block::Moving], vec![Block::Moving], vec![Block::Moving], vec![Block::Moving]];
	blocks.push(block4);
	
	let block5 = vec![vec![Block::Moving, Block::Moving],
		vec![Block::Moving, Block::Moving]];
	blocks.push(block5);

	let mut space = vec![[Block::Empty; 7]; 3];
	let mut top = 0;
	let mut height = space.len();
	let mut change_index = 0;

	let mut cache = HashMap::new();
	let mut checked = false;

	let big = 1000000000000;

	let mut i = 0;

	while i < big {
		let block = &blocks[i % 5];
		for _ in 0..block.len() {
			space.push([Block::Empty; 7]);
			height += 1;
		}
		let mut x = 2;
		let mut y = space.len() - block.len();
		let mut yv = height-block.len();
		loop {
			let change = pattern[change_index % pattern.len()]; 	
			if change == '>' {
				if x + block[0].len() < 7 { 
					x += 1;
					'outer: for y1 in 0..block.len() {
						for x1 in 0..block[0].len() {
							if let Block::Moving = block[y1][x1] {
								if let Block::Still = space[y1+y][x1+x] {
									x -= 1;
									break 'outer;
								}
							}

						}
					}
				}
			}
			if change == '<' {
				if x > 0 {
					x -= 1;
					'outer: for y1 in 0..block.len() {
						for x1 in 0..block[0].len() {
							if let Block::Moving = block[y1][x1] {
								if let Block::Still = space[y1+y][x1+x] {
									x += 1;
									break 'outer;
								}
							}

						}
					}
				}
			}
			change_index += 1;
			let mut collision = false;
			if y > 0 {
				y -= 1;
				yv -= 1;
				'outer: for y1 in 0..block.len() {
					for x1 in 0..block[0].len() {
						if let Block::Moving = block[y1][x1] {
							if let Block::Still = space[y1+y][x1+x] {
								collision = true;
								y += 1;
								yv += 1;
								break 'outer;
							}
						}
					}
				}
			} else {
				collision = true;
			}
			if collision {
				for y1 in 0..block.len() {
					for x1 in 0..block[0].len() {
						if let Block::Moving = block[y1][x1] {
							space[y1+y][x1+x] = Block::Still;
						}
					}
				}
				if yv + block.len() > top {
					top = yv + block.len();
				}
				while top + 3 < height {
					space.remove(space.len() - 1);
					height -= 1;
				}

				let mut bottom = space.len();

				for x1 in 0..space[0].len() {
					let mut h: isize = (space.len() - 1).try_into().unwrap();
					while h >= 0 {
						if let Block::Empty = space[h as usize][x1] {
							h -= 1;
						} else {
							break;
						}
					}
					if h < 0 {
						bottom = 0;
						break;
					} else if h < bottom as isize {
						bottom = h as usize;
					}
				}

				for _ in 0..bottom {
					space.remove(0);
				}

				let key = format!("{}~{:?}~{}", change_index % pattern.len(), space, i % 5);
				if !checked && cache.contains_key(&key) {
					let (x1, y1) = cache.get(&key).unwrap();
					let (x1, y1) = (*x1, *y1);
					let (x2, y2) = (i, top);
					let rise = y2 - y1;
					let run = x2 - x1;
					let times = (big - i)/run;
					height += rise*times;
					i += run*times;
					checked = true;
				}
				cache.insert(key, (i, top));
				break;
			}
		}
		i += 1;
	}
	
    println!("{}", top);
}
