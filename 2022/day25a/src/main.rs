use std::fs;

fn snafu_to_decimal(input: &str) -> i128 {
	let mut out = 0;
	let mut base = 1;
	
	for c in input.chars().rev() {
		match c {
			'2' => out += 2*base,
			'1' => out += base,
			'-' => out += -1 * base,
			'=' => out += -2 * base,
			_ => (),
		}
		base *= 5;
	}
	out
}

fn decimal_to_snafu(input: i128) -> String {
	let mut out = String::new();
	let mut num = input;

	while num != 0 {
		match num % 5 {
			2 => out.insert(0, '2'),
			1 => out.insert(0, '1'),
			0 => out.insert(0, '0'),
			3 => {
				out.insert(0, '=');
				num += 5;
			},
			4 => {
				out.insert(0, '-');
				num += 5;
			},
			_ => unreachable!(),
		}
		num /= 5;
	}
	out
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let value: i128 = input.lines().map(|x| snafu_to_decimal(x)).sum();

    println!("{}", decimal_to_snafu(value));
}
