import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;


public class HuffmanEncoder implements HuffmanCoding{
    private int sizeTable =127;
    private int frequencyTable[];
    private Hashtable<Character, String> codeTable;

    //Constructs new frequencyTable and codeTable
    public HuffmanEncoder(){
        frequencyTable = new int[sizeTable];
        codeTable = new Hashtable<>();
    }

    /** getFrequencies prints the characters and frequencies */
    public String getFrequencies(File inputFile) throws FileNotFoundException{
        //initialize stringbuilder and freq array
        StringBuilder sb = new StringBuilder(1500);
        for (int i=0; i<sizeTable; i++){
            frequencyTable[i] = 0;
        }

        //scan text file
        try{
            Scanner sc = new Scanner(inputFile);
            while(sc.hasNext()){
                //create char array for each line of text
                char lineArr[] = sc.nextLine().toCharArray();
                for (char c : lineArr){
                    //add 1 for each frequency to its ascii value in array
                    frequencyTable[c] = frequencyTable[c] + 1;
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }

        //append frequencies to stringbuilder
        for (int i=0; i<sizeTable; i++){
            if (frequencyTable[i] != 0){
                sb.append((char)i);
                sb.append(" ");
                sb.append(frequencyTable[i]);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /** buildTree takes file and creates Huffman Tree */
    public HuffTree buildTree(File inputFile) throws FileNotFoundException, Exception{
        //Initialize frequency table, minheap, hufftree temp
        getFrequencies(inputFile);
        MinHeap<HuffTree> mh = new MinHeap<>();
        HuffTree temp;

        //Check if char appears, if it does put single node hufftree in minheap
        for (int i=32; i<127; i++){
            if (frequencyTable[i] !=0){
                temp = new HuffTree((char)i, frequencyTable[i]);
                mh.insert(temp);
            }
        }

        //call buildHuffman to create tree from hufftree minheap
        return buildHuffman(mh);
    }

    /** encodeFile encodes file to a string of 1's and 0's using input hufftree*/
    public String encodeFile(File inputFile, HuffTree huffTree) throws FileNotFoundException{
        //initialize stringbuilder, clear hashtable.
        //reload hashtable with given hufftree
        StringBuilder sb = new StringBuilder(10000);
        codeTable.clear();
        loadCodeTable(huffTree.root(), "");

        //scan each line and append each chars code
        try{
            Scanner sc = new Scanner(inputFile);
            while(sc.hasNext()){
                char lineArr[] = sc.nextLine().toCharArray();
                for (char c : lineArr){
                    sb.append(codeTable.get(c));
                }
                sb.append("\n");
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return sb.toString();
    }


    /**decodeFile decodes string using input hufftree*/
    public String decodeFile(String code, HuffTree huffTree) throws Exception{
        //Create new stringbuilder object and tempnode
        StringBuilder sb = new StringBuilder(100000);
        HuffNode tempNode = huffTree.root();

        //convert line to character array
        Scanner sc = new Scanner(code);
        while(sc.hasNext()){
            char lineArr[] = sc.nextLine().toCharArray();
            //for 1 or 0, traverse huffman tree until a leaf is reached.
            for ( char c : lineArr){
                if (c == '1'){
                    tempNode = tempNode.right();
                }
                else {
                    tempNode = tempNode.left();
                }
                if (tempNode.left() == null && tempNode.right() == null){
                    sb.append(tempNode.value());
                    tempNode = huffTree.root();
                }
            }
            //sb.append(tempNode.value());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**traverseHuffmanTree prints character and its code*/
    public String traverseHuffmanTree(HuffTree huffTree) throws Exception{

        //Load codetable, initialize stringbuilder.
        loadCodeTable(huffTree.root(), "");
        StringBuilder sb = new StringBuilder();

        //check if key exists, print key and code
        for(int i=0; i<sizeTable; i++){
            if(codeTable.containsKey((char)i)){
                sb.append((char)i);
                sb.append(" ");
                sb.append(codeTable.get((char)i));
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**loadCodeTable loads huffman tree into a hashtable in preorder*/
    private void loadCodeTable(HuffNode hbn, String str){
        //If given node is leaf, load char and str into hashtable
        if (hbn.left() == null && hbn.right() == null) {
            codeTable.put(hbn.value(), str);
        }
        //if left append "0"
        if (hbn.left() != null){
            loadCodeTable(hbn.left(), str + "0");
        }
        //if right append "1"
        if (hbn.right() != null){
            loadCodeTable(hbn.right(), str + "1");
        }
    }

    /** Code references https://opendsa-server.cs.vt.edu/ODSA/Books/Everything/html/Huffman.html*/
    private HuffTree buildHuffman(MinHeap<HuffTree> mh){
        HuffTree tmp1, tmp2, tmp3 = null;
        while (mh.getSize() > 1){
            tmp1 = mh.removeMin();
            tmp2 = mh.removeMin();
            tmp3 = new HuffTree(tmp1.root(), tmp2.root(), tmp1.weight()+tmp2.weight());
            mh.insert(tmp3);
        }
        return tmp3;
    }



}
