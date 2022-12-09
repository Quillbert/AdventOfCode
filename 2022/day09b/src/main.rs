use std::fs; 
use std::collections::HashSet;

#[derive(Copy)]
#[derive(Clone)]
#[derive(Debug)]
struct Point { 
	x: i32, 
	y: i32, 
} 

fn update_tail(head: &Point, t: &Point) -> Point {
	let mut tail = Point{x: t.x, y: t.y};
	if (head.x - tail.x).abs() > 1 && (head.y - tail.y).abs() > 1 {
		tail.x = if head.x > tail.x {tail.x + 1} else {tail.x - 1};
		tail.y = if head.y > tail.y {tail.y + 1} else {tail.y - 1};
		return tail;
	}
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
	tail
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut knots = [Point{x: 0, y: 0}; 10];

	let mut positions = HashSet::new();

	for line in input.lines() {
		let parts: Vec<_> = line.split(' ').collect();
		match (parts[0], parts[1].parse::<i32>().unwrap()) {
			("L", dis) => {
				for _ in 0..dis {
					knots[0].x -= 1;
					for i in 0..9 {
						let tail = update_tail(&knots[i], &knots[i+1]);
						knots[i+1] = tail;
					}
					positions.insert((knots[9].x, knots[9].y));
				}
			},
			("R", dis) => {
				for _ in 0..dis {
					knots[0].x += 1;
					for i in 0..9 {
						let tail = update_tail(&knots[i], &knots[i+1]);
						knots[i+1] = tail;
					}
					positions.insert((knots[9].x, knots[9].y));
				}
			},
			("U", dis) => {
				for _ in 0..dis {
					knots[0].y -= 1;
					for i in 0..9 {
						let tail = update_tail(&knots[i], &knots[i+1]);
						knots[i+1] = tail;
					}
					positions.insert((knots[9].x, knots[9].y));
				}
			},
			("D", dis) => {
				for _ in 0..dis {
					knots[0].y += 1;
					for i in 0..9 {
						let tail = update_tail(&knots[i], &knots[i+1]);
						knots[i+1] = tail;
					}
					positions.insert((knots[9].x, knots[9].y));
				}
			},
			_ => unreachable!(),
		}
	}
	
    println!("{}", positions.len());
}
