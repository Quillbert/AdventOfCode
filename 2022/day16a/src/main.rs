use std::fs;
use std::collections::HashMap;

struct Connection {
	vertex: String,
	distance: u8,
}

impl Clone for Connection {
	fn clone(&self) -> Self {
		Connection {vertex: self.vertex.clone(), distance: self.distance}
	}
}

fn flow_rate(state: &HashMap<String, bool>, weights: &HashMap<String, u32>) -> u32 {
	let mut out = 0;
	for (pos, open) in state {
		if *open {
			out += weights.get(pos).unwrap();
		}
	}
	out
}

fn solve(cache: &mut HashMap<String, u32>, places: &mut HashMap<String, u32>, pos: &str, 
	connections: &HashMap<String, Vec<Connection>>, weights: &HashMap<String, u32>, 
	state: HashMap<String, bool>, open_count: u32, time: u8, cooldown: u8) -> u32 {

	if cache.len() % 1000 == 0 {
		println!("{}", cache.len());
	}

	let key = format!("{}~{:?}~{}~{}", pos, state, time, cooldown);
	let place = format!("{}~{:?}", pos, state);

	if cache.contains_key(&key) {
		return *cache.get(&key).unwrap();
	}

	if time <= 0 {
		cache.insert(key, 0);
		return 0;
	}

	let flow = flow_rate(&state, weights);

	if cooldown > 0 {
		let n = solve(cache, places, pos, connections, weights, state, open_count, time - 1, cooldown - 1);
		cache.insert(key, n + flow);
		return n + flow;
	}

	if open_count as usize == weights.len() {
		let n = solve(cache, places, pos, connections, weights, state, open_count, time - 1, 0);
		cache.insert(key, n + flow);
		return n + flow;
	}

	if *places.get(&place).unwrap_or(&0) > time.into() {
		cache.insert(key, 0);
		return 0;
	} else {
		places.insert(place, time.into());
	}

	let mut totals = Vec::new();

	if !*state.get(pos).unwrap_or(&false) {
		let mut new_state = state.clone();
		new_state.insert(String::from(pos), true);
		let n = solve(cache, places, pos, connections, weights, new_state, open_count + 1, time - 1, 0);
		totals.push(n + flow);
	}

	for new_pos in connections.get(pos).unwrap() {
		let distance = new_pos.distance;
		let new_pos = &new_pos.vertex;
		let n = solve(cache, places, new_pos, connections, weights, state.clone(), open_count, time - 1, distance - 1);
		totals.push(n + flow);
	}

	let out = totals.iter().max().unwrap();
	cache.insert(key, *out);
	*out
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut connections = HashMap::new();
	let mut weights = HashMap::new();

	for line in input.lines() {
		let parts: Vec<_> = line.splitn(10, ' ').collect();

		let rooms: Vec<_> = parts[9].split(", ")
			.map(|x| Connection {vertex: String::from(x), distance: 1})
			.collect();

		connections.insert(String::from(parts[1]), rooms);

		let weight = parts[4][5..parts[4].len()-1].parse::<u32>().unwrap();

		weights.insert(String::from(parts[1]), weight);
	}

	let places: Vec<_> = connections.keys().cloned().collect();

	for place in places {
		if place != "AA" && *weights.get(&place).unwrap() == 0 &&
			connections.get(&place).unwrap().len() == 2 {
			let to = connections.remove(&place).unwrap();	
			let c1 = Connection{vertex: to[1].vertex.clone(), distance: to[1].distance + to[0].distance};
			let c2 = Connection{vertex: to[0].vertex.clone(), distance: to[0].distance + to[1].distance};
			let mut p1 = connections.get(&to[0].vertex).unwrap().clone();
			for i in 0..p1.len() {
				if p1[i].vertex == place {
					p1.remove(i);
					p1.push(c1);
					break;
				}
			}
			connections.insert(String::from(&to[0].vertex), p1.to_vec());
			let mut p2 = connections.get(&to[1].vertex).unwrap().clone();
			for i in 0..p2.len() {
				if p2[i].vertex == place {
					p2.remove(i);
					p2.push(c2);
					break;
				}
			}
			connections.insert(String::from(&to[1].vertex), p2.to_vec());
		}
	}

	let mut cache = HashMap::new();
	let mut places = HashMap::new();
	let state = HashMap::new();

	let out = solve(&mut cache, &mut places, "AA", &connections, &weights, state, 0, 30, 0);
    println!("{}", out);
}
