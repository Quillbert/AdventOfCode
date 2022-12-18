// This only works on the assumption that not every valve can be opened
// by one entity
// For one that works on smaller inputs, see old2.rs

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

struct Data {
	num: u32,
	state: HashMap<String, bool>,
}

impl Clone for Data {
    fn clone(&self) -> Self {
        Data {
            state: self.state.clone(),
            num: self.num,
        }
    }
}

fn solve(cache: &mut HashMap<String, Data>, pos: &str, time: u8,
	cooldown: u8, state: HashMap<String, bool>, connections: &HashMap<String, Vec<Connection>>, 
	weights: &HashMap<String, u32>) -> Data {

	if cache.len() % 1000 == 0 {
		println!("{}", cache.len());
	}

	let mut state_key: Vec<_> = state.keys().collect();
	state_key.sort();
	let key = format!("{}~{}~{}~{:?}", pos, time, cooldown,
		state_key);

	if cache.contains_key(&key) {
		return cache.get(&key).unwrap().clone();
	}

	if time == 0 {
		return Data {num: 0, state: state.clone()};
	}
	
	if state.len() == weights.len() {
		let flow = flow_rate(&state, weights) * time as u32;
		return Data {num: flow, state};
	}

	let flow = flow_rate(&state, weights);

	let mut nums = Vec::new();

	if cooldown > 0 {
		let n = solve(cache, pos, time - 1, cooldown - 1, state.clone(), connections, weights);
		let data = Data{num: n.num + flow, state: n.state};
		cache.insert(key, data.clone());
		return data;
	}

	if !*state.get(pos).unwrap_or(&false) {
		let mut new_state = state.clone();
		new_state.insert(String::from(pos), true);
		let n = solve(cache, pos, time - 1, 0, new_state, connections, weights);
		let data = Data{num: n.num + flow, state: n.state};
		nums.push(data);
	}

	for c in connections.get(pos).unwrap() {
		let new_pos = &c.vertex;
		let distance = c.distance;
		if *state.get(new_pos).unwrap_or(&false) {
			continue;
		}
		let n = solve(cache, new_pos, time - 1, distance - 1, state.clone(), connections, weights);
		let data = Data{num: n.num + flow, state: n.state};
		nums.push(data);
	}

	if nums.len() == 0 {
		let n = solve(cache, pos, time - 1, 0, state.clone(), connections, weights);
		let data = Data{num: n.num + flow, state: n.state};
		nums.push(data);
	}

	nums.sort_by(|a, b| a.num.partial_cmp(&b.num).unwrap());
	let out = nums.into_iter().last().unwrap();
	cache.insert(key, out.clone());
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

	let mut connections = new_connections;

	println!("{:?}", connections);

    let mut cache = HashMap::new();
    let state = HashMap::new();

    let out = solve(&mut cache, "AA", 26, 0, state, &connections, &weights);
    println!("{}", out.num);

	let used: HashSet<_> = out.state.keys().collect();
	
	for u in &used {
		connections.remove(&String::from(*u));
		weights.remove(&String::from(*u));
	}

	let mut new_connections = HashMap::new();
	for (key, value) in connections {
		let mut conn = Vec::new();
		for c in value {
			if !used.contains(&c.vertex) {
				conn.push(c.clone());
			}
		}
		new_connections.insert(String::from(key), conn);
	}
	
	let connections = new_connections;

	let mut cache = HashMap::new();
	let out2 = solve(&mut cache, "AA", 26, 0, HashMap::new(), &connections, &weights);
	println!("{}", out.num + out2.num);

}
