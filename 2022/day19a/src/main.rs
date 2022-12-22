use std::fs;

fn solve(costs: &Vec<u32>, robots: Vec<u32>, materials: Vec<u32>, time: u8, min: u32) -> u32 {
	if time == 1 {
		return materials[3] + robots[3];
	}

	let part1 = materials[3] as f64;
	let part2 = robots[3] as f64 * 2.0 + time as f64;
	let part3 = time as f64 / 2.0;
	let potential = part1 + part2*part3;

	if potential < min.into() {
		return 0;
	}

	let max_ore = costs[0].max(costs[1]).max(costs[2]).max(costs[4]);
	let max_clay = costs[3];
	let max_obsidian = costs[5];

	let mut min = min;
	let mut results = Vec::new();
	if materials[0] >= costs[4] && materials[2] >= costs[5] {
		let mut materials = materials.clone();
		for i in 0..robots.len() {
			materials[i] += robots[i];
		}
		let mut new_mat = materials.clone();
		new_mat[0] -= costs[4];
		new_mat[2] -= costs[5];
		let mut new_rob = robots.clone();
		new_rob[3] += 1;
		let n = solve(costs, new_rob, new_mat, time - 1, min);
		if n > min {
			min = n;
		}
		results.push(n);

	}
	if materials[0] >= costs[2] && materials[1] >= costs[3] && robots[2] < max_obsidian {
		let mut materials = materials.clone();
		for i in 0..robots.len() {
			materials[i] += robots[i];
		}
		let mut new_mat = materials.clone();
		new_mat[0] -= costs[2];
		new_mat[1] -= costs[3];
		let mut new_rob = robots.clone();
		new_rob[2] += 1;
		let n = solve(costs, new_rob, new_mat, time - 1, min);
		if n > min {
			min = n;
		}
		results.push(n);
	}
	if materials[0] >= costs[1] && robots[1] < max_clay {
		let mut materials = materials.clone();
		for i in 0..robots.len() {
			materials[i] += robots[i];
		}
		let mut new_mat = materials.clone();
		new_mat[0] -= costs[1];
		let mut new_rob = robots.clone();
		new_rob[1] += 1;
		let n = solve(costs, new_rob, new_mat, time - 1, min);
		if n > min {
			min = n;
		}
		results.push(n);
	}
	if materials[0] >= costs[0] && robots[0] < max_ore {
		let mut materials = materials.clone();
		for i in 0..robots.len() {
			materials[i] += robots[i];
		}
		let mut new_mat = materials.clone();
		new_mat[0] -= costs[0];
		let mut new_rob = robots.clone();
		new_rob[0] += 1;
		let n = solve(costs, new_rob, new_mat, time - 1, min);
		if n > min {
			min = n;
		}
		results.push(n);
	}
	let mut materials = materials.clone();
	for i in 0..robots.len() {
		materials[i] += robots[i];
	}
	let n = solve(costs, robots.clone(), materials.clone(), time - 1, min);
	results.push(n);

	*results.iter().max().unwrap()
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();
	
	let mut out = 0;

	for (num, line) in input.lines().enumerate() {
		let parts: Vec<_> = line.split(' ').collect();

		let id = num + 1;
		let costs = vec![parts[6].parse::<u32>().unwrap(), parts[12].parse::<u32>().unwrap(),
			parts[18].parse::<u32>().unwrap(), parts[21].parse::<u32>().unwrap(),
			parts[27].parse::<u32>().unwrap(), parts[30].parse::<u32>().unwrap()];

		let n = solve(&costs, vec![1, 0, 0, 0], vec![0, 0, 0, 0], 24, 0);
		println!("{}", n);
		out += id * n as usize;
	}

    println!("Result: {}", out);
}
