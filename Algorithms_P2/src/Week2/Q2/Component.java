package Week2.Q2;

import java.util.ArrayList;
import java.util.List;

import Week2.Q1.Vertex;

public class Component {
	List<Vertex> nodes = new ArrayList<Vertex>();
	Vertex nodeLeader;
	public Component(Vertex node) {
		super();
		nodes.add(node);
		nodeLeader = node;
	}
	
	public Integer getSize(){ return nodes.size(); }
	
	public void mergeTo(Component c){
		c.renameLeader(nodeLeader);
		this.nodes.addAll(c.nodes);
	}

	public void renameLeader(Vertex newName) {
		for (Vertex n:nodes){
			n.leader = newName;
		}
	}

	public Vertex getName() {
		return nodeLeader;
	}
}
