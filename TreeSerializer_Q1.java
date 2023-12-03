/**

@author: Nathan Lee
@purpose: Taboola Backend Engineer Intern Take Home Test Question 2 Part 1.


**/

import java.lang.Math;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

public class TreeSerializer_Q1{
	
	Node root;
	ArrayList<Node> nodesList;
	
	public TreeSerializer_Q1(){
		nodesList = new ArrayList<Node>();
	}
	
	/**
		Node()
		Node Objects.
	**/
	public class Node{
		Node left;
		Node right;
		int num;
	}
	/**
		TreeSerializer()
		Outline for serialize(), deserialize().
	**/
	public interface TreeSerializer{
		String serialize(Node root);
		Node deserialize(String str);
	}
	
	/**
		BinaryTreeSerializer()
		Implements serialize(), deserialize().
	**/
	public static class BinaryTreeSerializer implements TreeSerializer{
		Node root;
		ArrayList<Node> nodesList;
		
		@Override
		public String serialize(Node root){
			String strSerialize = "";
			return levelOrderTraversal(root, strSerialize);
		}
		
		@Override
		public Node deserialize(String str){
			
			nodesList=new ArrayList<Node>();
			
			//Count Number of Nodes to make. Valid and Invalid Nodes.
			int numValues = 1;
			boolean isQuote = false;
			for(int e=0;e<str.length();e++){
				if(str.charAt(e)=='"'){
					isQuote = !isQuote;
				}
				if(str.charAt(e)==',' && isQuote == false){
					numValues++;
				}
			}
			System.out.println("numValues "+numValues);
			
			//Convert Strings or Nulls or empty "" into nodes Object Array.
			Object[] nodes = new Object[numValues];
			String stringWrite = "";
			int count = 0;
			for(int f=0;f<str.length();f++){
				if(str.charAt(f)==','){
					System.out.println("stringWrite "+stringWrite);
					nodes[count] = stringWrite;
					stringWrite = "";
					count++;
				}
				else{
					stringWrite = stringWrite+str.charAt(f);
				}
			}
			System.out.println("stringWrite "+stringWrite);
			count++;
			nodes[numValues-1] = stringWrite; //insert Last Value after comma.
			System.out.println("XXX- Check last value is in:"+nodes[numValues-1]+" count: "+count);
			
			List<Node> nodesList = new ArrayList<Node>();
			//nodesList 
			root = deSerializeToNodes(nodes, root);
			
			//this.nodesList = nodesList;
			
			//root = nodesList.get(0);
			
			return root;
		}
	}
	
	
	/**
		setChildren()
		set all nodes of the children of root.
	**/
	public Node setChildren(Node root, int level, int rootIndex, Object[] nodes){

		Node nl = new Node();
		Node nr = new Node();
		if(root==null){
			return null;
		}
		if(level==1){
			root.num = (Integer) nodes[rootIndex]; 
		}
		else if(level>1){
			setChildren(nl, level+1, 2*rootIndex+1, nodes);
			root.left = nl;
			setChildren(nr, level+1, 2*rootIndex+2, nodes);
			root.right = nr;
		}
		return root;
	}
	
	/*public static void getCurrentLevel(Node root, int level, int rootIndex, Object[] treeArray){
		if(root==null){
			return;
		}
		if(level==1){
			treeArray[rootIndex]=root.num;
		}
		else if(level>1){
			getCurrentLevel(root.left, level-1,2*rootIndex+1, treeArray);
			getCurrentLevel(root.right, level-1,2*rootIndex+2, treeArray);
		}
	}*/
	
	
	
	/**
		deSerializeToNodes()
		Transfers Objects to Integers, Assigns Node Values. Returns Root Node.
	**/
	public static Node deSerializeToNodes(Object[] nodes, Node root){
		//Node root;
		List<Node> nodesList = new ArrayList<Node>();
		
		int numValidValues = 0;
		List<Integer> positionRecorder = new ArrayList<Integer>();
		List<Object> valueRecorder = new ArrayList<Object>();

		//g=position of node, value is int of node.
		for(int g=0;g<nodes.length;g++){ //17
			//If there is a value.
			if(nodes[g]!=null && nodes[g]!=""){
				numValidValues++; //number of nodes to make.
				System.out.println("position: "+g);
				positionRecorder.add(g);
				System.out.println("value: "+((nodes[g]))); //TODO Change in case of Object, TreeSerializer_Q3.
				valueRecorder.add((nodes[g]));//TODO Change in case of Object, TreeSerializer_Q3.
				
			}
			//Else ignore, is a null or "".
		}
		
		
		//If node is root, index = 0.
		//If left child of a parent, childIndex = 2*parentIndex+1
		//If right child of a parent, childIndex = 2*parentIndex+2
		for(int h=0;h<numValidValues;h++){ //TODO Can just use array length instead of numValidValues
			int parentPosition = positionRecorder.get(h);
			Object value = valueRecorder.get(h);
			
			/*Node newNode = new Node(); //TODO
			newNode.num = (Integer)value;
				
			if(g==0){
				root = newNode;
			}
			nodesList.add(newNode);*/
		//}
		
		//Set children.
		//for(int i=0;i<numValidValues;i++){
			
			int leftChildLocation = 2*parentPosition+1;
			int rightChildLocation = 2*parentPosition+2;
			if(positionRecorder.contains(leftChildLocation)){
				
			}
			//newNode.left = nodes[2*g+1]; //TODO
			//newNode.right = nodes[2*g+2]; //TODO
		}
		
		
		
		
		//Array Size = 2^treeHeight + 1 .
		//treeHeight = log2(arraysize-1)
		//If node is root, index = 0.
		//If left child of a parent, childIndex = 2*parentIndex+1
		//If right child of a parent, childIndex = 2*parentIndex+2
		double heightRoot = (Math.log(nodes.length-1)/Math.log(2));
		
		Node myroot;
		//setChildren sc = new setChildren();
		for(int l=0;l<=heightRoot;l++){
			//root = setChildren(root,l,0,nodes); //TODO
		}
		
		
		//setChildren sc = new setChildren();
		//Node root = setChildren(root, 0,nodes);
		
		System.out.println("returning--");
		
		return root;
	}
	/**
		levelOrderTraversal()
		Performs levelorder traversal on the binary tree beginning with given root node.
		Modified to write to array implementation of binary tree.
	**/
	public static String levelOrderTraversal(Node root, String strSerialize){
		//Array Size = 2^treeHeight + 1 .
		int heightRoot = height(root);
		Object[] treeArray = new Object[((int)Math.pow(2,heightRoot)+1)];
		Arrays.fill(treeArray, null);
		
		//If node is root, index = 0.
		//If left child of a parent, childIndex = 2*parentIndex+1
		//If right child of a parent, childIndex = 2*parentIndex+2
		for(int l=0;l<=heightRoot;l++){
			getCurrentLevel(root,l,0,treeArray);
		}
		
		for(int i=0;i<treeArray.length;i++){
			if(treeArray.length>1 && i==0){
				if(treeArray[i]!=null){
					strSerialize = strSerialize+treeArray[i];
				}
				else{
					strSerialize = strSerialize;
				}
			}
			else if(treeArray.length>1 && i>0){
				if(treeArray[i]!=null){
					strSerialize = strSerialize+","+treeArray[i];
				}
				else{
					strSerialize = strSerialize+",";
				}
			}
		}
		return strSerialize;
	}
	/**
		getCurrentLevel()
		get all nodes of the current level of the tree.
		Modified to add to treeArray based on parentIndex.
	**/
	public static void getCurrentLevel(Node root, int level, int rootIndex, Object[] treeArray){
		if(root==null){
			return;
		}
		if(level==1){
			treeArray[rootIndex]=root.num;
		}
		else if(level>1){
			getCurrentLevel(root.left, level-1,2*rootIndex+1, treeArray);
			getCurrentLevel(root.right, level-1,2*rootIndex+2, treeArray);
		}
	}
	/**
		height()
		Return the height of the tree.
	**/
	public static int height(Node root){
		if(root==null){
			return 0;
		}
		else{
			int leftHeight = height(root.left);
			int rightHeight = height(root.right);
			if(leftHeight>rightHeight){
				return leftHeight+1;
			}
			else{
				return rightHeight+1;
			}
		}
	}
	/**
		launchTreeSerializer()
		Initializes a new binary tree of integers, calls serialize(), then deserialize().
	**/
	public void launchTreeSerializer(){
		if(true){//TODO delete this.
		//try{
			Node a = new Node();
			Node b = new Node();
			Node c = new Node();
			Node d = new Node();
			Node e = new Node();
			Node g = new Node();
			Node h = new Node();
			a.left = b;
			a.right = c;
			a.num = 1;
			b.left = d;
			b.right = e;
			b.num = 2;
			//c.left = null;
			c.right = g;
			c.num = 1;
			d.left = h;
			//d.right = null;
			d.num = 7;
			//e.left = null;
			//e.right = null;
			e.num = 5;
			//g.left = null;
			//g.right = null;
			g.num = 28;
			//h.left = null;
			//h.right = null;
			h.num = 4;
			
			//this. keyword is unnecessary, but to emphasize is of TreeSerializer_Q1's List:
			this.nodesList.add(a);
			this.nodesList.add(b);
			this.nodesList.add(c);
			this.nodesList.add(d);
			this.nodesList.add(e);
			this.nodesList.add(g);
			this.nodesList.add(h);
			
			this.root = this.nodesList.get(0);
			
			BinaryTreeSerializer bts = new BinaryTreeSerializer();
			bts.root = this.root;
	
			//Serialize
			String str = bts.serialize(root);
			System.out.println("serialized: "+str);
			//Deserialize
			Node newRoot = bts.deserialize(str);
			this.nodesList.clear();
			this.nodesList = bts.nodesList;
			System.out.println("deserialized: ");
			levelOrderTraversal(newRoot,"");
		
			//TODO
			//tree = implementation.deserialize(implementation.serialize(tree))
		/*}
		catch(Exception ex){
			System.out.println("Exception has occurred. Please use valid int inputs, no nulls, no empty strings \"\".");
		}*/
		}//TODO delete this.
	}
	/**
		main()
		Main Driver. Initializes a new instance of TreeSerializer.
	**/
	public static void main(String[] args){	
		TreeSerializer_Q1 tq1 = new TreeSerializer_Q1();
		tq1.launchTreeSerializer();
	}
}

