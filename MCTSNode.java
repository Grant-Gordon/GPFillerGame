package main;
import java.util.*;
//Credit TO: https://github.com/eugenp/tutorials/blob/master/algorithms-modules/algorithms-searching/src/main/java/com/baeldung/algorithms/mcts/tree/Node.java
public class MCTSNode {
	MCTSState state;
	MCTSNode parent;
	List<MCTSNode> childArray;
	
	
	public MCTSNode() {
		this.state = new MCTSState();
		childArray= new ArrayList<>();
		
	}
	
	public MCTSNode(MCTSState s) {
		this.state = s;
		childArray = new ArrayList<>();
	}
	public MCTSNode(MCTSState s, MCTSNode p) {
		this.state = s;
		childArray = new ArrayList<>();
		parent = p;
	}
	
	public MCTSNode(MCTSState state, MCTSNode parent, List<MCTSNode> childArray) {
        this.state = state;
        this.parent = parent;
        this.childArray = childArray;
    }
	
	 public MCTSNode(MCTSNode node) {
	        this.childArray = new ArrayList<>();
	        this.state = new MCTSState(node.getState());
	        if (node.getParent() != null)
	            this.parent = node.getParent();
	        List<MCTSNode> childArray = node.getChildArray();
	        for (MCTSNode child : childArray) {
	            this.childArray.add(new MCTSNode(child));
	        }
	    }

    public MCTSState getState() {
        return state;
    }
    public MCTSNode getParent() {
        return parent;
    }
    public List<MCTSNode> getChildArray() {
        return childArray;
    }
    public MCTSNode getRandomChildNode() {
        int noOfPossibleMoves = this.childArray.size();
        int selectRandom = (int) (Math.random() * noOfPossibleMoves);
        return this.childArray.get(selectRandom);
    }
    public MCTSNode getChildWithMaxScore() {
        return Collections.max(this.childArray, Comparator.comparing(c -> {
            return c.getState().getVisitCount();
        }));
    }
    
    
    public void setState(MCTSState state) {
        this.state = state;
    }
    public void setParent(MCTSNode parent) {
        this.parent = parent;
    }
    public void setChildArray(List<MCTSNode> childArray) {
        this.childArray = childArray;
    }
    
}
