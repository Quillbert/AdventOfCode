use std::collections::{HashMap, HashSet};
use std::fs;

#[derive(Debug)]
struct Connection {
    vertex: String,
    distance: u8,
}

impl Clone for Connection {
    fn clone(&self) -> Self {
        Connection {
            vertex: self.vertex.clone(),
            distance: self.distance,
        }
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

fn solve(cache: &mut HashMap<String, u32>, pos: &str, epos: &str, time: u8, etime: u8,
	cooldown: u8, ecooldown: u8, state: HashMap<String, bool>,
	connections: &HashMap<String, Vec<Connection>>, weights: &HashMap<String, u32>,
	places: HashSet<String>, eplaces: HashSet<String>) -> u32 {

	if cache.len() % 1000 == 0 {
		println!("{}", cache.len());
	}

	let mut state_key: Vec<_> = state.keys().collect();
	state_key.sort();
	let key = format!("{}~{}~{}~{}~{}~{}~{:?}", pos, epos, time, etime, cooldown, ecooldown, 
		state_key);
	let place = format!("{}~{:?}~{}", pos, state_key, cooldown);
	let eplace = format!("{}~{:?}~{}", epos, state_key, ecooldown);

	if cache.contains_key(&key) {
		return *cache.get(&key).unwrap();
	}

	if time == 0 && etime == 0 {
		return 0;
	}
	
	if (time as i16 - etime as i16).abs() > 1 {
		return 0;
	}

	if state.len() == weights.len() {
		let flow = flow_rate(&state, weights) * (time.min(etime)) as u32;
		return flow;
	}

	if places.contains(&place) {
		return 0;
	}

	if eplaces.contains(&eplace) {
		return 0;
	}

	let flow = if time == etime {flow_rate(&state, weights)} else {0};

	let mut nums = Vec::new();

	if time > 0 {
		if cooldown > 0 {
			if *state.get(pos).unwrap_or(&false) {
				return 0;
			}
			let mut places = places.clone();
			places.insert(place.clone());
			let n = solve(cache, pos, epos, time - 1, etime, cooldown - 1, ecooldown, state.clone(),
				connections, weights, places, eplaces.clone());
			nums.push(n + flow);
		} else {
			if !*state.get(pos).unwrap_or(&false) && pos != "AA" {
				let mut places = places.clone();
				places.insert(place.clone());
				let mut new_state = state.clone();
				new_state.insert(String::from(pos), true);
				let n = solve(cache, pos, epos, time - 1, etime, 0, ecooldown, new_state,
					connections, weights, places, eplaces.clone());
				nums.push(n + flow);
			} else {
				for c in connections.get(pos).unwrap() {
					let mut places = places.clone();
					places.insert(place.clone());
					let new_pos = &c.vertex;
					let distance = c.distance;
					if *state.get(new_pos).unwrap_or(&false) {
						continue;
					}
					let n = solve(cache, new_pos, epos, time - 1, etime, distance - 1, ecooldown,
						state.clone(), connections, weights, places, eplaces.clone());
					nums.push(n + flow);
				}
			}
		}
	}

	if etime > 0 {
		if ecooldown > 0 {
			if *state.get(epos).unwrap_or(&false) {
				return 0;
			}
			let mut eplaces = eplaces.clone();
			eplaces.insert(eplace.clone());
			let n = solve(cache, pos, epos, time, etime - 1, cooldown, ecooldown - 1, state.clone(),
				connections, weights, places.clone(), eplaces);
			nums.push(n + flow);
		} else {
			if !*state.get(epos).unwrap_or(&false) && epos != "AA" {
				let mut eplaces = eplaces.clone();
				eplaces.insert(eplace.clone());
				let mut new_state = state.clone();
				new_state.insert(String::from(epos), true);
				let n = solve(cache, pos, epos, time, etime - 1, cooldown, 0, new_state,
					connections, weights, places.clone(), eplaces);
				nums.push(n + flow);
			} else {
				for c in connections.get(epos).unwrap() {
					let mut eplaces = eplaces.clone();
					eplaces.insert(eplace.clone());
					let new_pos = &c.vertex;
					let distance = c.distance;
					if *state.get(new_pos).unwrap_or(&false) {
						continue;
					}
					let n = solve(cache, pos, new_pos, time, etime - 1, cooldown, distance - 1,
						state.clone(), connections, weights, places.clone(), eplaces);
					nums.push(n + flow);
				}
			}
		}
	}

	if nums.len() == 0 {
		if time > 0 {
			let n = solve(cache, pos, epos, time - 1, etime, cooldown, ecooldown, state.clone(), connections,
				weights, places.clone(), eplaces.clone());
			nums.push(n + flow);
		}
		if etime > 0 {
			let n = solve(cache, pos, epos, time, etime - 1, cooldown, ecooldown, state.clone(), connections,
				weights, places.clone(), eplaces.clone());
			nums.push(n + flow);
		}
	}

	let out = *nums.iter().max().unwrap_or(&0);
	cache.insert(key, out);
	out
}

fn main() {
    let input = fs::read_to_string("input.txt").unwrap();

    let mut connections = HashMap::new();
    let mut weights = HashMap::new();

    for line in input.lines() {
        let parts: Vec<_> = line.splitn(10, ' ').collect();

        let rooms: Vec<_> = parts[9]
            .split(", ")
            .map(|x| Connection {
                vertex: String::from(x),
                distance: 1,
            })
            .collect();

        connections.insert(String::from(parts[1]), rooms);

        let weight = parts[4][5..parts[4].len() - 1].parse::<u32>().unwrap();

        weights.insert(String::from(parts[1]), weight);
    }

    let places: Vec<_> = connections.keys().cloned().collect();

    for place in places {
        if place != "AA"
            && *weights.get(&place).unwrap() == 0
            && connections.get(&place).unwrap().len() == 2
        {
            let to = connections.remove(&place).unwrap();
            let c1 = Connection {
                vertex: to[1].vertex.clone(),
                distance: to[1].distance + to[0].distance,
            };
            let c2 = Connection {
                vertex: to[0].vertex.clone(),
                distance: to[0].distance + to[1].distance,
            };
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

	let vertices: Vec<_> = connections.keys().collect();

	let mut matrix = vec![vec![u16::MAX/2; vertices.len()]; vertices.len()];

	for i in 0..vertices.len() {
		matrix[i][i] = 0;
	}

	for (key, value) in &connections {
		for c in value {
			let indexfrom = vertices.iter().position(|&r| &r == &key).unwrap();
			let indexto = vertices.iter().position(|&r| r == &c.vertex).unwrap();
			matrix[indexfrom][indexto] = c.distance as u16;
		}
	}

	for k in 0..vertices.len() {
		for i in 0..vertices.len() {
			for j in 0..vertices.len() {
				if matrix[i][j] > matrix[i][k] + matrix[k][j] {
					matrix[i][j] = matrix[i][k] + matrix[k][j];
				}
			}
		}
	}

	let mut new_connections = HashMap::new();
	for i in 0..vertices.len() {
		let mut conn_list = Vec::new();
		for j in 0..vertices.len() {
			if i != j {
				let conn = Connection {vertex: String::from(vertices[j]), distance: matrix[i][j] as u8};
				conn_list.push(conn);
			}	
		}
		new_connections.insert(String::from(vertices[i]), conn_list);
	}

	let connections = new_connections;

	println!("{:?}", connections);

    let mut cache = HashMap::new();
	let places = HashSet::new();
	let eplaces = HashSet::new();
    let state = HashMap::new();

    let out = solve(&mut cache, "AA", "AA", 26, 26, 0, 0, state, &connections, &weights, places, eplaces);
    println!("{}", out);
}
