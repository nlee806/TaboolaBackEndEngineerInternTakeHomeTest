/**

@author: Nathan Lee
@purpose: Taboola Backend Engineer Intern Take Home Test Question 2 Part 1.


**/

import java.lang.Math;
import java.util.Arrays;

public class TreeSerializer_Q1{
	
	Node root;
	
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
		
		@Override
		public String serialize(Node root){
			String strSerialize = "";
			return levelOrderTraversal(root, strSerialize);
		}
		
		@Override
		public Node deserialize(String str){
			
			
			
			return root;
		}
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
			if(treeArray.length>1 && i>0){
				if(treeArray[i]!=null){
					strSerialize = strSerialize+","+treeArray[i];
				}
				else{
					strSerialize = strSerialize+",";
				}
			}
		}
		System.out.println("strSerialize: "+strSerialize);
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
		
		this.root = a;
		
		BinaryTreeSerializer bts = new BinaryTreeSerializer();
		bts.root = root;
	
		//Serialize
		String str = bts.serialize(root);
		System.out.println("serialized: "+str);
		//Deserialize
		Node newRoot = bts.deserialize(str);
		System.out.println("deserialized: ");
		levelOrderTraversal(newRoot,"");
		
		//TODO
		//tree = implementation.deserialize(implementation.serialize(tree))
		
		
	}
	/**
		main()
		Main Driver. Initializes a new instance of TreeSerializer.
	**/
	public static void main(String[] args){
		
		//TODO Handle incorrect output errors, with exceptions.
		
		
		TreeSerializer_Q1 tq1 = new TreeSerializer_Q1();
		tq1.launchTreeSerializer();
	}
}

