/** Class HuffNode
 *  - Contains all data needed for each HuffNode, internal and leaf nodes.
 */

public class HuffNode {
    private int weight;
    private char element;
    private HuffNode left;
    private HuffNode right;
    private boolean leaf;

    //Constructor for leaf nodes
    HuffNode(char el, int wt){
        element = el;
        weight = wt;
        left = null;
        right = null;
        leaf = true;
    }

    //Constructor for internal nodes
    HuffNode(HuffNode l, HuffNode r, int wt){
        right = r;
        left = l;
        weight = wt;
        leaf = false;
    }

    public char value(){return element;} //returns char value at node

    public int weight(){return weight;} //returns int weight at node

    public HuffNode right(){return right;} //return right child node

    public HuffNode left(){return left;} //return left child node

    public boolean isLeaf(){return leaf;} //return true if isLeaf

}
