/**

@author: Nathan Lee
@purpose: Taboola Backend Engineer Intern Take Home Test Question 2 Part 1.


**/

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
		
		public String serialize(Node root){
			
			return "";
		}
		
		public Node deserialize(String str){
			
			
			
			return root;
		}
	}
	
	/**
		preOrderTraversal()
		Performs preorder traversal on the binary tree beginning with given root node.
	**/
	public static void preOrderTraversal(Node root){
		if(root==null){
			return;
		}
		System.out.println(" "+root.num+" "); //visit node
		preOrderTraversal(root.left);
		preOrderTraversal(root.right);
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
		System.out.println("deserialized: "+preOrderTraversal(newRoot));
		
		//TODO
		//tree = implementation.deserialize(implementation.serialize(tree))
		
		
	}
	/**
		main()
		Main Driver. Initializes a new instance of TreeSerializer.
	**/
	public static void main(String[] args){
		
		//TODO Handle incorrect output, with exceptions.
		
		
		TreeSerializer_Q1 tq1 = new TreeSerializer_Q1();
		tq1.launchTreeSerializer();
	}
}

