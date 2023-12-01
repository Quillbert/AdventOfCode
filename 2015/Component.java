import java.util.*;

public class Component {
	public int value = -1;
	public ArrayList<Component> parents = new ArrayList<Component>();
	public String operation = "";
	
	public void eval() {
		if(value >= 0) {
			return;
		}
		for(Component c : parents) {
			c.eval();
		}
		
		if(operation.length() <= 0) {
			value = parents.get(0).value;
		}
		
		if(operation.equals("AND")) {
			value = parents.get(0).value & parents.get(1).value;
		} else if(operation.equals("OR")) {
			value = parents.get(0).value | parents.get(1).value;
		} else if(operation.equals("NOT")) {
			value = ~parents.get(0).value;
		} else if(operation.equals("RSHIFT")) {
			value = parents.get(0).value >> parents.get(1).value;
		} else if(operation.equals("LSHIFT")) {
			value = parents.get(0).value << parents.get(1).value;
		} else {
			value = parents.get(0).value;
		}
	}
}
