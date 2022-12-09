use std::fs; 
use std::collections::HashSet;

struct Point { 
	x: i32, 
	y: i32, 
} 

fn update_tail(head: &Point, tail: &mut Point) {
	if head.x - tail.x < -1 {
		tail.y = head.y;
		tail.x = head.x + 1;
	} else if head.x - tail.x > 1 {
		tail.y = head.y;
		tail.x = head.x - 1;
	} else if head.y - tail.y < -1 {
		tail.x = head.x;
		tail.y = head.y + 1;
	} else if head.y - tail.y > 1 {
		tail.x = head.x;
		tail.y = head.y - 1;
	}
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut head = Point {x: 0, y: 0};
	let mut tail = Point {x: 0, y: 0};

	let mut positions = HashSet::new();

	for line in input.lines() {
		let parts: Vec<_> = line.split(' ').collect();
		match (parts[0], parts[1].parse::<i32>().unwrap()) {
			("L", dis) => {
				for _ in 0..dis {
					head.x -= 1;
					update_tail(&head, &mut tail);
					positions.insert((tail.x, tail.y));
				}
			},
			("R", dis) => {
				for _ in 0..dis {
					head.x += 1;
					update_tail(&head, &mut tail);
					positions.insert((tail.x, tail.y));
				}
			},
			("U", dis) => {
				for _ in 0..dis {
					head.y -= 1;
					update_tail(&head, &mut tail);
					positions.insert((tail.x, tail.y));
				}
			},
			("D", dis) => {
				for _ in 0..dis {
					head.y += 1;
					update_tail(&head, &mut tail);
					positions.insert((tail.x, tail.y));
				}
			},
			_ => unreachable!(),
		}
	}
	
    println!("{}", positions.len());
}
