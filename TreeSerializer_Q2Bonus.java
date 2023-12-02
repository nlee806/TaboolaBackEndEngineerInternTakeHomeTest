/**

@author: Nathan Lee
@purpose: Taboola Backend Engineer Intern Take Home Test Question 2 Part 2 Bonus.


**/

public class TreeSerializer_Q2Bonus{
	
	public class Node{
		Node left;
		Node right;
		int num;
	}
	
	public interface TreeSerializer{
		String serialize(Node root);
		Node deserialize(String str);
	}
		
	public static void main(String[] args){
		
	}
	
}

