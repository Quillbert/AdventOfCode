public class TreeNode {
	public Object value = null;
	public TreeNode left = null;
	public TreeNode right = null;
	public TreeNode parent = null;

	public String toString() {
		String out = "";
		if(left != null) {
			out += "[" + left.toString() + ",";
		}
		if(right != null) {
			out += right.toString() + "]";
		}
		if(value != null) {
			out += value;
		}
		return out;
	}
}
