/**Reference Code taken from
 * https://opendsa-server.cs.vt.edu/ODSA/Books/Everything/html/Huffman.html
 */

/** A Huffman coding tree */
public class HuffTree implements Comparable<HuffTree> {
  private HuffNode root;

  /** Constructors */
  HuffTree(char el, int wt)
    { root = new HuffNode(el, wt); }
  HuffTree(HuffNode l, HuffNode r, int wt)
    { root = new HuffNode(l, r, wt); }

  public HuffNode root() { return root; }
  public int weight() // Weight of tree is weight of root
    { return root.weight(); }

  public int compareTo(HuffTree that) {
    if (root.weight() < that.weight()) return -1;
    else if (root.weight() == that.weight()) return 0;
    else return 1;
  }
}
