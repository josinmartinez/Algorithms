package Week2.Q1;

public class Vertex {
	public Integer name;
	public Vertex leader;
	
	public Vertex(Integer name) {
		super();
		this.name = name;
		leader = this;
	}
	
	public void setLeader(Vertex leader) {
		this.leader = leader;
	}

	public Integer getLeaderName(){
		return leader.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertex [" + (name != null ? "name=" + name + ", " : "")	+ (leader != null ? "leader=" + leader.name : "") + "]";
	}

}