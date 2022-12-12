use std::fs;
use std::collections::{HashMap, BinaryHeap, HashSet};
use std::cmp::*;

#[derive(Debug)]
struct Node {
	id: usize,
	height: u8,
}

#[derive(Eq)]
#[derive(Debug)]
struct Connection {
	vertex: usize,	
	distance: usize,
}

impl Ord for Connection {
	fn cmp(&self, other: &Self) -> Ordering {
		other.distance.cmp(&self.distance)
	}
}

impl PartialOrd for Connection {
	fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
		Some(self.cmp(other))
	}
}

impl PartialEq for Connection {
	fn eq(&self, other: &Self) -> bool {
		self.distance == other.distance && self.vertex == other.vertex
	}
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut vertices = Vec::new();
	let mut grid = Vec::new();

	let mut start = 0;
	let mut end = 0;

	for line in input.lines() {
		let mut row = Vec::new();
		for c in line.chars() {
			let id = vertices.len();
			if c == 'S' {
				start = id;
			} else if c == 'E' {
				end = id;
			}
			let height = match c {
				'S' => 0,
				'E' => 25,
				x => x as u32 - 97,
			} as u8;
			let vertex = Node {id, height};
			vertices.push(vertex);
			row.push(id);
		}
		grid.push(row);
	}

	let mut connections = HashMap::new();

	for i in 0..grid.len() {
		for j in 0..grid[i].len() {
			let mut list = Vec::new();
			if i > 0 {
				if vertices[grid[i][j]].height + 1 >= vertices[grid[i-1][j]].height {
					let connection = Connection {vertex: grid[i-1][j], distance: 1};
					list.push(connection);
				}
			}
			if j > 0 {
				if vertices[grid[i][j]].height + 1 >= vertices[grid[i][j-1]].height {
					let connection = Connection {vertex: grid[i][j-1], distance: 1};
					list.push(connection);
				}
			}
			if i < grid.len() - 1 {
				if vertices[grid[i][j]].height + 1 >= vertices[grid[i+1][j]].height {
					let connection = Connection {vertex: grid[i+1][j], distance: 1};
					list.push(connection);
				}
			}
			if j < grid[i].len() - 1 {
				if vertices[grid[i][j]].height + 1 >= vertices[grid[i][j+1]].height {
					let connection = Connection {vertex: grid[i][j+1], distance: 1};
					list.push(connection);
				}
			}
			if list.len() > 0 {
				connections.insert(grid[i][j], list);
			}	
		}
	}
	let mut distances = HashMap::new();
	let mut queue = BinaryHeap::new();

	for vertex in vertices {
		distances.insert(vertex.id, usize::MAX);
	}

	let mut visited = HashSet::new();

	queue.push(Connection { vertex: start, distance: 0 });

	let mut current = start;
	
	while current != end {
		let c = queue.pop().unwrap();
		current = c.vertex;
		let distance = c.distance;
		if !visited.contains(&current) {
			visited.insert(current);
			distances.insert(current, distance);
			for v in connections.get(&current).unwrap() {
				if !visited.contains(&v.vertex) {
					queue.push(Connection {vertex: v.vertex, distance: v.distance + distance});
				}
			}
		}
	}

	println!("{}", distances.get(&end).unwrap());
}
