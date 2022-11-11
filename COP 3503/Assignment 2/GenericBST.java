// Sean Szumlanski, Guilherme Camara
// gu618739
// COP 3503, Fall 2021

// ====================
// GenericBST: BST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provided for you to modify as part of
// Programming Assignment #2.


import java.io.*;
import java.util.*;

// I will be using AnyType to make the class generic.
// Below we have the class which contains the left and right child of the
// current node, as well as its data.
class Node <AnyType extends Comparable<AnyType>>
{
	AnyType data;
	Node<AnyType> left, right;

	Node(AnyType data)
	{
		this.data = data;
	}
}

public class GenericBST <AnyType extends Comparable<AnyType>>
{
	private Node<AnyType> root;

	public void insert(AnyType data)
	{
		root = insert(root, data);
	}

	// Recursive method to insert data in the BST
	private Node<AnyType> insert(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return new Node<>(data);
		}

		// I will be using the compareTo method given that we are allowed to store
		// classes that implement Comparable in the BST.

		// If data is smaller than root, it will become the left child.
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}

		// Otherwise, it will become the right child.
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}

		return root;
	}

	public void delete(AnyType data)
	{
		root = delete(root, data);
	}

	// Recursive method to delete existing data from the BST.
	private Node<AnyType> delete(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return null;
		}

		// Once again, I will be using the compareTo method to recur down the tree.
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}
		else
		{
			// If node has no children, return null.
			if (root.left == null && root.right == null)
			{
				return null;
			}

			// If node has one child, return the non-null subtree.
			else if (root.left == null)
			{
				return root.right;
			}
			else if (root.right == null)
			{
				return root.left;
			}

			// If node has two children, return max value of left subtree.
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private AnyType findMax(Node<AnyType> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}

	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}

	// Recursive method to check if BST contains dead end
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		// If root is null or recursion moves after leaf node, there is not
		// a dead end.
		if (root == null)
		{
			return false;
		}

		// Traverse left and right subtrees.
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}

		// If base case is not triggered, the BST has a dead end.
		else
		{
			return true;
		}
	}

	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	// Inorder traversal of BST = left-> root -> right
	private void inorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// Preorder traversal of BST = root-> left-> right
	private void preorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// Postorder traversal of BST = left-> right-> root
	private void postorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static double difficultyRating()
	{
		return 2.0;
	}

	public static double hoursSpent()
	{
		return 3.0;
	}
}
