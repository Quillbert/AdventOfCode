import java.util.*;

public class Day18P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		//String value = s.nextLine().trim();

		//TreeNode root = generate(value);

		ArrayList<String> lines = new ArrayList<String>();
		
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine().trim();
			
		}
		
		int max = 0;
		
		for(int i = 0; i < lines.size(); i++) {
			for(int j = 0; j < lines.size(); j++) {
				if(i != j) {
					TreeNode left = generate(lines.get(i));
					TreeNode right = generate(lines.get(j));
					TreeNode root = new TreeNode();
					root.right = right;
					root.left = left;
					left.parent = root;
					right.parent = root;
					
					while(depth(root) > 5 || canSplit(root)) {
						while(depth(root) > 5) {
							fix(root, 0);
						}
						if(canSplit(root)) {
							split(root);
						}
					}
					
					int mag = magnitude(root);
					if(mag > max) {
						max = mag;
					}
				}
			}
		}
		
		System.out.println(max);
		
		/*while(str.length() > 0) {
			TreeNode right = generate(str);
			TreeNode newRoot = new TreeNode();
			newRoot.right = right;
			newRoot.left = root;
			right.parent = newRoot;
			root.parent = newRoot;
			root = newRoot;

			while(depth(root) > 5 || canSplit(root)) {
				while(depth(root) > 5) {
					fix(root, 0);
				}
				if(canSplit(root)) {
					split(root);
				}
			}

			str = s.nextLine().trim();
		}

		System.out.println(root);
		System.out.println(magnitude(root));*/
	}

	public static TreeNode generate(String value) {
		TreeNode root = new TreeNode();

		if(value.charAt(0) == '[') {
			value = value.substring(1, value.length()-1);
		}

		int layer = 0;
		int middle = -1;
		for(int i = 0; i < value.length(); i++) {
			if(value.charAt(i) == '[') {
				layer++;
			} else if(value.charAt(i) == ']') {
				layer--;
			} else if(value.charAt(i) == ',' && layer == 0) {
				middle = i;
				break;
			}
		}

		if(middle == -1) {
			root.value = Integer.parseInt(value);
			return root;
		}

		String left = value.substring(0, middle);
		String right = value.substring(middle+1);

		root.left = generate(left);
		root.right = generate(right);
		root.left.parent = root;
		root.right.parent = root;

		return root;
	}

	public static int depth(TreeNode root) {
		if(root == null) {
			return 0;
		}
		return 1+Math.max(depth(root.left), depth(root.right));
	}

	public static boolean canSplit(TreeNode root) {
		if(root == null) {
			return false;
		}
		if(root.value == null) {
			return canSplit(root.left) || canSplit(root.right);
		}
		return (int)root.value > 9;
	}

	public static boolean fix(TreeNode root, int depth) {
		if(root == null) {
			return false;
		}

		if(depth == 4 && (root.left != null)) {

			TreeNode parent = root.parent;

			if(parent.left.left == null) {
				parent.left.value = (int)parent.left.value+(int)root.left.value;
			} else {
				TreeNode p = parent;
				TreeNode prev = root;
				while(p.parent != null && p.left == prev) {
					prev = p;
					p = p.parent;
				}

				if(p.left != prev) {
					p = p.left;
					while(p.value == null) {
						p = p.right;
					}
				}
				if (p.value != null) {
					p.value = (int)p.value+(int)root.left.value;
				} else if(p.left.value != null) {
					p.left.value = (int)p.left.value+(int)root.left.value;
				}
				if(parent.left == root) {
					parent.left.value = 0;

				}
			}
			if(parent.right.left == null) {
				parent.right.value = (int)parent.right.value+(int)root.right.value;
			} else {
				TreeNode p = parent;
				TreeNode prev = root;
				while(p.parent != null && p.right == prev) {
					prev = p;
					p = p.parent;
				}

				if(p.right != prev) {
					p = p.right;
					while(p.value == null) {
						p = p.left;
					}
				}

				if(p.value != null) {
					p.value = (int)p.value+(int)root.right.value;
				} else if(p.right.value != null) {
					p.right.value = (int)p.right.value+(int)root.right.value;
				}
				if(parent.right == root) {
					parent.right.value = 0;
				}
			}

			root.left = null;
			root.right = null;

			return true;
		}

		if(fix(root.left, depth+1)) return true;
		return fix(root.right, depth+1);
	}
	
	public static boolean split(TreeNode root) {
		if(root == null) {
			return false;
		}
		
		if(root.value != null && (int)root.value > 9) {
			TreeNode left = new TreeNode();
			left.parent = root;
			root.left = left;
			left.value = (int)Math.floor((int)(root.value)/2.0);

			TreeNode right = new TreeNode();
			right.parent = root;
			root.right = right;
			right.value = (int)Math.ceil((int)(root.value)/2.0);

			root.value = null;

			return true;
		}
		if(split(root.left)) return true;
		return split(root.right);
	}
	
	public static int magnitude(TreeNode root) {
		if(root.value == null) {
			return 3*magnitude(root.left) + 2*magnitude(root.right);
		}
		return (int)root.value;
	}

}
