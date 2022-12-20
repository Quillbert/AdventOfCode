use std::fs;

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
				if !grid[i][j][k] {
					continue;
				}
				if i > 0 && !grid[i-1][j][k] {
					surface_area += 1;
				} else if i == 0 {
					surface_area += 1;
				}
				if i < grid.len() - 1 && !grid[i+1][j][k] {
					surface_area += 1;
				} else if i == grid.len() - 1 {
					surface_area += 1;
				}
				if j > 0 && !grid[i][j-1][k] {
					surface_area += 1;
				} else if j == 0 {
					surface_area += 1;
				}
				if j < grid[i].len() - 1 && !grid[i][j+1][k] {
					surface_area += 1;
				} else if j == grid[i].len() - 1 {
					surface_area += 1;
				}
				if k > 0 && !grid[i][j][k-1] {
					surface_area += 1;
				} else if k == 0 {
					surface_area += 1;
				}
				if k < grid[i][j].len() - 1 && !grid[i][j][k+1] {
					surface_area += 1;
				} else if k == grid[i][j].len() - 1 {
					surface_area += 1;
				}
			}
		}
	}

    println!("{}", surface_area);
}
