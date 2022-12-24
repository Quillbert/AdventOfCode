use std::fs;

fn m(a: isize, b: usize) -> usize {
	let b = b as isize;
	(((a % b) + b) % b).try_into().unwrap()
}

fn swap(tape: &mut Vec<isize>, moved: &mut Vec<bool>, i: isize, j: isize) -> bool {
	let out = i < 0 || i >= tape.len() as isize;
	let i = m(i, tape.len());
	let j = m(j, tape.len());
	let temp = tape[i];
	tape[i] = tape[j];
	tape[j] = temp;
	let temp = moved[i];
	moved[i] = moved[j];
	moved[j] = temp;
	out
}

fn rf(tape: &mut Vec<isize>, moved: &mut Vec<bool>) {
	for i in 0..tape.len()-1 {
		swap(tape, moved, i as isize, (i + 1) as isize);
	}
}

fn rb(tape: &mut Vec<isize>, moved: &mut Vec<bool>) {
	for i in 0..tape.len()-1 {
		swap(tape, moved, i as isize, i as isize - 1);
	}
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let mut tape: Vec<_> = input.lines()
		.map(|x| x.parse::<isize>().unwrap())
		.collect();

	let mut moved = vec![false; tape.len()];

	for i in 0..tape.len() {
		println!("{}", i);
		let index = moved.iter().position(|x| !x).unwrap();
		moved[index] = true;
		let a = tape.remove(index);
		moved.remove(index);
		let new_spot = m(index as isize + a, tape.len());
		tape.insert(new_spot, a);
		moved.insert(new_spot, true);
		//println!("{:?}", tape);
	}


	let zero = tape.iter().position(|x| *x == 0).unwrap() as isize;

	let out = tape[m(zero + 1000, tape.len())] + tape[m(zero + 2000, tape.len())] +
		tape[m(zero + 3000, tape.len())];

	println!("{}", out);
}
