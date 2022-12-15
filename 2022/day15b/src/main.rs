use std::fs;

#[derive(Debug)]
struct Range {
	start: i32,
	end: i32,
}

impl Range {
	fn new(start: i32, end: i32) -> Self {
		Range {start, end}
	}
	
	fn size(&self) -> u32 {
		(self.end - self.start + 1).try_into().unwrap()
	}

	fn overlapping(&self, other: &Self) -> bool {
		(self.start >= other.start && self.start <= other.end) ||
		(self.end >= other.start && self.end <= other.end) ||
		(other.start >= self.start && other.start <= self.end) ||
		(other.end >= self.start && other.end <= self.end)
	}

	fn merge(&self, other: &Self) -> Self {
		Range {start: self.start.min(other.start), end: self.end.max(other.end)}
	}

	fn cap(&self, min: i32, max: i32) -> Self {
		Range {start: self.start.max(min), end: self.end.min(max)}
	}
}

struct Sensor {
	location: (i32, i32),
	beacon: (i32, i32),
}

impl Sensor {
	fn new(lx: i32, ly: i32, bx: i32, by: i32) -> Self {
		let location = (lx, ly);
		let beacon = (bx, by);
		Sensor { location, beacon }
	}
	
	fn impossible(&self, row: i32) -> Option<Range> {
		let (lx, ly) = self.location;
		let (bx, by) = self.beacon;

		
		let distance = (bx-lx).abs() + (by-ly).abs();

		if distance < (row - ly).abs() {
			return None;
		}

		let impossible = 2 * distance - 2 * (row-ly).abs() + 1;

		let left = lx - (impossible / 2);
		let right = lx + (impossible / 2);

		Some(Range::new(left, right))
	}
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut sensors = Vec::new();

	for line in input.lines() {
		let parts: Vec<_> = line.split(' ').collect();
		let lx = parts[2].split('=').last().unwrap();
		let lx = lx[0..lx.len()-1].parse::<i32>().unwrap();
		let ly = parts[3].split('=').last().unwrap();
		let ly = ly[0..ly.len()-1].parse::<i32>().unwrap();
		let bx = parts[8].split('=').last().unwrap();
		let bx = bx[0..bx.len()-1].parse::<i32>().unwrap();
		let by = parts[9].split('=').last().unwrap().parse::<i32>().unwrap();

		sensors.push(Sensor::new(lx, ly, bx, by));
	}

	for pos in 0..=4000000 {

		let mut ranges: Vec<_> = sensors.iter()
			.map(|x| x.impossible(pos))
			.filter(|x| x.is_some())
			.map(|x| x.unwrap())
			.collect();
		
		let mut i = ranges.len()-1;

		loop {
			for j in (i+1..ranges.len()).rev() {
				if ranges[i].overlapping(&ranges[j]) {
					let range2 = ranges.remove(j);
					let range1 = ranges.remove(i);
					let new_range = range1.merge(&range2);
					ranges.insert(i, new_range);
				}
			}
			if i == 0 {
				break;
			}
			i -= 1;
		}

		let ranges: Vec<_> = ranges.iter().map(|x| x.cap(0, 4000000)).collect();
		
		let num = ranges.iter().map(|x| x.size()).sum::<u32>();

		if num < 4000001 {
			println!("{:?}", ranges);
			println!("{}", pos);
		}
	}
}
