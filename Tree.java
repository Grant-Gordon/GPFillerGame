package main;
//https://github.com/eugenp/tutorials/blob/master/algorithms-modules/algorithms-searching/src/main/java/com/baeldung/algorithms/mcts/tree/Tree.java
public class Tree {
	  MCTSNode root;

	    public Tree() {
	        root = new MCTSNode();
	    }

	    public Tree(MCTSNode root) {
	        this.root = root;
	    }

	    public MCTSNode getRoot() {
	        return root;
	    }

	    public void setRoot(MCTSNode root) {
	        this.root = root;
	    }

	    public void addChild(MCTSNode parent, MCTSNode child) {
	        parent.getChildArray().add(child);
	    }

}
