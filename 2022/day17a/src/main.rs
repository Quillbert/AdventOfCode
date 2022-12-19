use std::fs;

#[derive(Copy, Clone)]
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
	let mut change_index = 0;

	for i in 0..2022 {
		let block = &blocks[i % 5];
		for _ in 0..block.len() {
			space.push([Block::Empty; 7]);
		}
		let mut x = 2;
		let mut y = space.len() - block.len();
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
				'outer: for y1 in 0..block.len() {
					for x1 in 0..block[0].len() {
						if let Block::Moving = block[y1][x1] {
							if let Block::Still = space[y1+y][x1+x] {
								collision = true;
								y += 1;
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
				if y + block.len() > top {
					top = y + block.len();
				}
				while top + 3 < space.len() {
					space.remove(space.len() - 1);
				}
				y = 0;
				break;
			}
		}
	}
	
    println!("{}", top);
}
