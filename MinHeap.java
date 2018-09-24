/**MinHeap generic class
 * PUBLIC
 * - getSize
 * - insert
 * - removeMin
 * - isEmpty
 * NOTE: very basic minHeap, maxSize only 1000
 * @param <T>
 */
public class MinHeap<T extends Comparable<T>>{
  private int maxSize = 1000;
  private int size;
  private T[] arrHeap;

  //Construct new heap of size 0
  public MinHeap(){
    this.size = 0;
    arrHeap = (T[]) new Comparable[maxSize];
  }

  //return size
  public int getSize(){
    return size;
  }

  //insert item to minHeap
  public void insert(T item){

    //Initialize variables and add new item to end.
    int i = size;
    T temp;
    size++;
    arrHeap[i] = item;
    int parent = parent(i);

    while (i > 0 && arrHeap[parent].compareTo(arrHeap[i]) > 0){
      //swap item with its parent
      temp = arrHeap[i];
      arrHeap[i] = arrHeap[parent];
      arrHeap[parent] = temp;

      //reconfigure i with new item and parent with i's parent.
      i = parent;
      parent = parent(i);
    }
  }

  //Removes min and maintains heap constraints
  public T removeMin(){
    //Throw error if empty
    if(isEmpty()){
      throw new ArrayIndexOutOfBoundsException("Heap is Empty.");
    }

    //If only one item, return that item.
    if(size == 1){
      T min = arrHeap[0];
      //arrHeap[0] = null;
      size--;
      return min;
    }

    //Otherwise remove last item, set as root, and then work it down
    //through the heap with heapify
    T min = arrHeap[0];
    T last = arrHeap[size-1];
    arrHeap[size-1] = null;
    size--;
    arrHeap[0] = last;
    heapify(0);

    return min;
  }

  //Check if empty
  public boolean isEmpty(){
    return (size==0);
  }

  //heapify data
  private void heapify(int i){
    //Initialize left right and smallest node
    int right = right(i);
    int left = left(i);
    int smallest = i;
    T temp;

    //Find the smallest node, either left right or parent.
    if (left < size && arrHeap[left].compareTo(arrHeap[i]) < 0){
      smallest = left;
    }
    if (right < size && arrHeap[right].compareTo(arrHeap[smallest]) < 0){
      smallest = right;
    }
    if (smallest != i){
      //Swap the smallest node wih its parent
      temp = arrHeap[i];
      arrHeap[i] = arrHeap[smallest];
      arrHeap[smallest] = temp;

      //recursively call
      heapify(smallest);
    }

  }

  //returns parent index of given index
  private int parent(int i){
    if (i%2 == 1){return i/2;}
    return (i - 1)/2;
  }

  //returns right child index of given index
  private int right(int i){return 2*i + 2;}

  //returns left child index of given index
  private int left(int i){return 2*i + 1;}



}
