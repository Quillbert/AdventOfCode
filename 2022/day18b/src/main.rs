use std::fs;
use std::collections::{VecDeque, HashSet};

fn is_edge(grid: &[[[bool; 20]; 20]; 20], coords: (usize, usize, usize)) -> bool {
	let mut queue = VecDeque::new();
	let mut visited = HashSet::new();
	let start = coords;
	queue.push_back(start);
	visited.insert(start);
	while !queue.is_empty() {
		let current = queue.pop_front().unwrap();	
		if current.0 == 0 || current.1 == 0 || current.2 == 0 ||
			current.0 == grid.len() - 1 || current.1 == grid[current.0].len() - 1 ||
			current.2 == grid[current.0][current.1].len() - 1 {
			
			return true;
		}
		let neighbors = [(current.0 - 1, current.1, current.2), (current.0 + 1, current.1, current.2),
			(current.0, current.1 - 1, current.2), (current.0, current.1 + 1, current.2),
			(current.0, current.1, current.2 - 1), (current.0, current.1, current.2 + 1)];
		for n in neighbors {
			if !visited.contains(&n) && !grid[n.0][n.1][n.2] {
				queue.push_back(n);
				visited.insert(n);
			}
		}
	}
	return false;
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut grid = [[[false; 20]; 20]; 20];

	for line in input.lines() {
		let coords: Vec<_> = line.split(',').map(|x| x.parse::<usize>().unwrap()).collect();
		grid[coords[0]][coords[1]][coords[2]] = true;
	}

	let mut surface_area = 0;


	for i in 0..grid.len() {
		for j in 0..grid[i].len() {
			for k in 0..grid[i][j].len() {
				let mut change = 1;
				let mut trigger = false;
				if !grid[i][j][k] {
					change = -1;
					trigger = true;
				}
				if is_edge(&grid, (i, j, k)) && !grid[i][j][k] {
					continue;
				}
				if i > 0 && grid[i-1][j][k] == trigger {
					surface_area += change;
				} else if i == 0 {
					surface_area += change;
				}
				if i < grid.len() - 1 && grid[i+1][j][k] == trigger {
					surface_area += change;
				} else if i == grid.len() - 1 {
					surface_area += change;
				}
				if j > 0 && grid[i][j-1][k] == trigger {
					surface_area += change;
				} else if j == 0 {
					surface_area += change;
				}
				if j < grid[i].len() - 1 && grid[i][j+1][k] == trigger {
					surface_area += change;
				} else if j == grid[i].len() - 1 {
					surface_area += change;
				}
				if k > 0 && grid[i][j][k-1] == trigger {
					surface_area += change;
				} else if k == 0 {
					surface_area += change;
				}
				if k < grid[i][j].len() - 1 && grid[i][j][k+1] == trigger {
					surface_area += change;
				} else if k == grid[i][j].len() - 1 {
					surface_area += change;
				}
			}
		}
	}

    println!("{}", surface_area);
}
