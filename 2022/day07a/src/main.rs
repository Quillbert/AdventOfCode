use std::fs;
use std::rc::Rc;
use std::cell::RefCell;

#[derive(Debug)]
struct TreeNode {
	subfolders: Vec<Rc<RefCell<TreeNode>>>,
	size: u32,
	name: String,
}

fn build_tree(input: Vec<&str>) -> Rc<RefCell<TreeNode>> {
	let top = Rc::new(RefCell::new(TreeNode{subfolders: Vec::new(), size: 0, name: String::from("")}));
	let mut stack: Vec<Rc<RefCell<TreeNode>>> = Vec::new();

	let mut current = top.clone();
	for line in input {
		let parts: Vec<_> = line.split(' ').collect();

		if parts[0] == "$" {
			if parts[1] == "cd" {
				if parts[2] == ".." {
					current = stack.pop().unwrap().clone();
				} else {
					let new = Rc::new(RefCell::new(TreeNode{subfolders: Vec::new(), size: 0, name: parts[2].to_string()}));
					current.borrow_mut().subfolders.push(new.clone());
					stack.push(current.clone());
					current = new.clone();
				}
			}
			continue;
		}
		if parts[0].parse::<u32>().is_ok() {
			let size = parts[0].parse::<u32>().unwrap();
			let new = Rc::new(RefCell::new(TreeNode{subfolders: Vec::new(), size, name: parts[1].to_string()}));
			current.borrow_mut().subfolders.push(new.clone());
		}
	}
	top
}

fn update_size(root: &mut Rc<RefCell<TreeNode>>) {
	let mut sum = 0;
	
	let mut a = root.borrow_mut();

	for mut child in &mut a.subfolders {
		update_size(&mut child);
		sum += child.borrow().size;
	}
	if a.size == 0 {
		a.size = sum;
	}
}

fn get_size(root: Rc<RefCell<TreeNode>>) -> u32 {
	let mut sum = 0;

	if root.borrow().size <= 100000 && root.borrow().subfolders.len() > 0 {
		sum += root.borrow().size;
	}
	for child in &root.borrow().subfolders {
		sum += get_size(child.clone());
	}

	sum
}

fn main() {
	let input = fs::read_to_string("input.txt").unwrap();

	let lines: Vec<_> = input.lines().collect();

	let mut parent = build_tree(lines);
	update_size(&mut parent);

    println!("{}", get_size(parent.clone()));
}
