import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Stack;
import org.objectweb.asm.commons.*;
import org.objectweb.asm.tree.*;

public class CFG {
    Set<Node> nodes = new HashSet<Node>();
    Map<Node, Set<Node>> edges = new HashMap<Node, Set<Node>>();

    static class Node {
		int position;
		MethodNode method;
		ClassNode clazz;

		Node(int p, MethodNode m, ClassNode c) {
	    	position = p; method = m; clazz = c;
		}

		public boolean equals(Object o) {
	    	if (!(o instanceof Node)) return false;
			Node n = (Node)o;
	    	return (position == n.position) &&
			method.equals(n.method) && clazz.equals(n.clazz);
		}

		public int hashCode() {
	    return position + method.hashCode() + clazz.hashCode();
	}

		public String toString() {
	    	return clazz.name + "." +
					method.name + method.signature + ": " + position;
		}
    }

    public void addNode(int p, MethodNode m, ClassNode c) {
		Node newNode = new Node(p,m,c);
		nodes.add(newNode);
    }

    public void addEdge(int p1, MethodNode m1, ClassNode c1,
			int p2, MethodNode m2, ClassNode c2) {
		Node node1 = new Node(p1,m1,c1);
		Node node2 = new Node(p2,m2,c2);
		if(!nodes.contains(node1)){
			nodes.add(node1);
		}
		if(!nodes.contains(node2)){
			nodes.add(node2);
		}
		if(!edges.containsKey(node1)){
			edges.put(node1, new HashSet<>());
		}
//		if(!edges.containsKey(node2)){
//			edges.put(node2, new HashSet<>());
//		}
		edges.get(node1).add(node2);
    }
	
	public void deleteNode(int p, MethodNode m, ClassNode c) {
		Node removeNode = new Node(p,m,c);
		nodes.remove(removeNode);
		edges.remove(removeNode);
		for(Node node : edges.keySet()){
			edges.get(node).remove(removeNode);
		}
    }
	
    public void deleteEdge(int p1, MethodNode m1, ClassNode c1,
						int p2, MethodNode m2, ClassNode c2) {
		Node node1 = new Node(p1,m1,c1);
		Node node2 = new Node(p2,m2,c2);
		if(edges.containsKey(node1)) {
			edges.get(node1).remove(node2);
		}
    }
	

    public boolean isReachable(int p1, MethodNode m1, ClassNode c1,
			       int p2, MethodNode m2, ClassNode c2) {
		Node node1 = new Node(p1,m1,c1);
		Node node2 = new Node(p2,m2,c2);
		boolean found = false;
		HashMap<Node, Boolean> visited = new HashMap<>();
		Stack<Node> stack = new Stack<>();
		for(Node node : nodes){
			visited.put(node,new Boolean(false));
		}
		if(nodes.contains(node1)){
			visited.put(node1,new Boolean(true));
			stack.push(node1);
		}else{
			return found;
		}
		while(!stack.empty()){
			Node top = stack.pop();
			if(edges.containsKey(top)) {
				for (Node node : edges.get(top)) {
					if (node.equals(node2)) {
						found = true;
						break;
					}
					if (visited.get(node) == false) {
						visited.put(node, true);
						stack.push(node);
					}
				}
			}
		}
		return found;
//		Node node1 = new Node(p1,m1,c1);
//		Node node2 = new Node(p2,m2,c2);
//		if(!nodes.contains(node1) || !nodes.contains(node2)) {
//			return false;
//		}
//		Map<Node,Boolean> visited = new HashMap<>();
//		for(Node node : nodes){
//			visited.put(node,false);
//		}
//		return isReachable(node1,node2,visited);
    }

}
