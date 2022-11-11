// Guilherme Camara
// gu618739
// COP 3503
// Fall 2021

import java.io.*;
import java.util.*;

// I went ahead and removed my recursive and memoization solutions
// before I submitted the assignment.
public class RunLikeHell
{
	public static int maxGain (int [] blocks)
	{
		// Base cases that do not affect attached test cases, but may
		// affect new test cases given.

		if(blocks.length == 0)
			return 0;

		if(blocks.length == 1)
			return blocks[0];

		if(blocks.length == 2)
			return Math.max(blocks[0], blocks[1]);

		// Remember that "int" arrays are initialized to 0 in Java
		// by default, so first two values of new array will be
		// discarded when for loop below iterates.
		int [] maximumKnowledge = new int [blocks.length + 2];

		for(int i = 2; i <= blocks.length + 1; i++)
		{
				// Deciding if we will skip more than one block or not.
				maximumKnowledge[i] = Math.max(blocks[i-2] + maximumKnowledge[i-2],
																				maximumKnowledge[i-1]);
		}

		// System.out.println(Arrays.toString(maximumKnowledge));

		return maximumKnowledge[maximumKnowledge.length - 1];
	}


	public static double difficultyRating()
	{
		return 3.0;
	}

	public static double hoursSpent()
	{
		return 12.0;
	}
}
