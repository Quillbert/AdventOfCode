use std::fs;
use std::collections::{HashMap, HashSet, BinaryHeap};
use std::cmp::Ordering;
use std::hash::{Hash, Hasher};

fn flow_rate(state: &HashMap<String, bool>, weights: &HashMap<String, u32>) -> u32 {
	let mut out = 0;
	for (pos, open) in state {
		if *open {
			out += weights.get(pos).unwrap();
		}
	}
	out
}

#[derive(Eq, Debug)]
struct Moment {
	position: String,
	state: HashMap<String, bool>,
	possible: u32,
	gained: u32,
	time: u8,
}

impl PartialOrd for Moment {
	fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
		Some(self.cmp(other))
	}
}

impl Ord for Moment {
	fn cmp(&self, other: &Self) -> Ordering {
		self.possible.cmp(&other.possible)
	}
}

impl PartialEq for Moment {
	fn eq(&self, other: &Self) -> bool {
		self.position == other.position &&
		self.state == other.state &&
		self.possible == other.possible &&
		self.time == other.time &&
		self.gained == other.gained
	}	
}

#[derive(Eq)]
struct Place {
	position: String,
	state: HashMap<String, bool>,
}

impl Hash for Place {
	fn hash<H: Hasher>(&self, state: &mut H) {
		let s = format!("{}~{:?}", self.position, self.state);
		s.hash(state);
	}
}

impl PartialEq for Place {
	fn eq(&self, other: &Self) -> bool {
		self.position == other.position &&
		self.state == other.state
	}
}

#[derive(Debug)]
struct Connection {
	vertex: String,
	distance: u8,
}

impl Clone for Connection {
	fn clone(&self) -> Self {
		Connection {vertex: self.vertex.clone(), distance: self.distance}
	}
}

fn possible_and_gained(state: &HashMap<String, bool>, weights: &HashMap<String, u32>, 
	time: u8, gained: u32, distance: u8) -> (u32, u32) {
	
	let mut gained = gained;

	gained += flow_rate(state, weights) * distance as u32;

	let possible = weights.values().sum::<u32>() * time as u32 + gained;		

	(possible, gained)
		
}

fn solve(connections: &HashMap<String, Vec<Connection>>, weights: &HashMap<String, u32>, pos: &str,
	possible: u32, time: u8) -> u32 {
	let mut queue = BinaryHeap::new();
	let mut visited = HashSet::new();
	let start = Moment {position: String::from(pos), state: HashMap::new(), possible, gained: 0, time}; 
	queue.push(start);
	let place = Place {position: String::from(pos), state: HashMap::new()};
	visited.insert(place);
	while !queue.is_empty() {
		if visited.len() % 100 == 0 {
			println!("{}", visited.len());
		}
		let current = queue.pop().unwrap();
		if current.time == 0 {
			return current.gained;
		}
		if !*current.state.get(&current.position).unwrap_or(&false) && 
			*weights.get(&current.position).unwrap() != 0 {
			let mut new_state = current.state.clone();
			new_state.insert(current.position.clone(), true);
			let (possible, gained) = possible_and_gained(&new_state, weights, current.time - 1, current.gained, 1);
			let new_moment = Moment {position: current.position.clone(), state: new_state, possible, gained,
				time: current.time - 1};
			let new_place = Place {position: String::from(&new_moment.position), state: new_moment.state.clone()};
			if !visited.contains(&new_place) {
				visited.insert(new_place);
				queue.push(new_moment);
			}
		}

		for pos in connections.get(&current.position).unwrap() {
			let mut distance = pos.distance;
			let pos = &pos.vertex;
			if current.time < distance {
				distance = current.time;
			}
			let (possible, gained) = possible_and_gained(&current.state, weights, current.time - distance,
				current.gained, distance);
			let new_moment = Moment {position: String::from(pos), state: current.state.clone(), possible,
				gained, time: current.time-distance};
			let new_place = Place {position: String::from(pos), state: current.state.clone()};
			if !visited.contains(&new_place) {
				visited.insert(new_place);
				queue.push(new_moment);
			}
		}
		if queue.is_empty() {
			return current.possible;
		}
	}
	0
}

fn main() {
	let input = fs::read_to_string("test.txt").unwrap();

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

	let possible = weights.values().sum::<u32>() * 30;

	let out = solve(&connections, &weights, "AA", possible, 29);
    println!("{}", out);
}
