// Guilherme Camara
// gu618739
// COP 3503
// Fall 2021

import java.io.*;
import java.util.*;
import java.awt.Point;

public class SneakyKnights
{
	// method to check if any knights in the board can attack each other and returns true if not so.
	public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		int numKnights = coordinateStrings.size();
		int columnPosition = 0, rowPosition = 0;
		HashSet<Point> knightPoint = new HashSet<>();
		// knightPoint stores and retrieves knights coordinates in O(1), satisfying program complexity.

		for(int i = 0; i < numKnights; i++)
		{
			// If there is a single knight, it is safe.
			if(numKnights < 2)
				return true;

			String coordinateString = coordinateStrings.get(i);

			// For loop below converts alphabetic characters into numeric and checks decimal point
			// of row to form coordinates (reused from SneakyQueens).
			for(int j = 0; j < coordinateString.length(); j++)
			{
					if(Character.isAlphabetic(coordinateString.charAt(j)))
					{
						columnPosition = columnPosition * 26 + (coordinateString.charAt(j) - 'a' + 1);
					}
					else if(Character.isAlphabetic(coordinateString.charAt(j)) == false)
					{
						rowPosition = rowPosition * 10 + (coordinateString.charAt(j) - '0');
					}
			}

			Point currentPoint = new Point(columnPosition, rowPosition);

			knightPoint.add(currentPoint);

			// After we add the current knight to the set/board, we check each one of its 8 possible
			// attack positions. If any of the positions already contains a knight, it means that
			// not all knights are safe, thus returning false.
			if(checkDimensions(columnPosition - 2, rowPosition - 1, boardSize) == true)
			{
					Point leftDown = new Point(columnPosition - 2, rowPosition - 1);

					if(knightPoint.contains(leftDown))
						return false;
			}

			if(checkDimensions(columnPosition - 2, rowPosition + 1, boardSize) == true)
			{
					Point leftUp = new Point(columnPosition - 2, rowPosition + 1);

					if(knightPoint.contains(leftUp))
						return false;
			}

			if(checkDimensions(columnPosition - 1, rowPosition - 2, boardSize) == true)
			{
					Point downLeft = new Point(columnPosition - 1, rowPosition - 2);

					if(knightPoint.contains(downLeft))
						return false;
			}

			if(checkDimensions(columnPosition - 1, rowPosition + 2, boardSize) == true)
			{
					Point upLeft = new Point(columnPosition - 1, rowPosition + 2);

					if(knightPoint.contains(upLeft))
						return false;
			}

			if(checkDimensions(columnPosition + 1, rowPosition - 2, boardSize) == true)
			{
					Point downRight = new Point(columnPosition + 1, rowPosition - 2);

					if(knightPoint.contains(downRight))
						return false;
			}

			if(checkDimensions(columnPosition + 1, rowPosition + 2, boardSize) == true)
			{
					Point upRight = new Point(columnPosition + 1, rowPosition + 2);

					if(knightPoint.contains(upRight))
						return false;
			}

			if(checkDimensions(columnPosition + 2, rowPosition + 1, boardSize) == true)
			{
					Point rightUp = new Point(columnPosition + 2, rowPosition + 1);

					if(knightPoint.contains(rightUp))
						return false;
			}

			if(checkDimensions(columnPosition + 2, rowPosition - 1, boardSize) == true)
			{
					Point rightDown = new Point(columnPosition + 2, rowPosition - 1);

					if(knightPoint.contains(rightDown))
						return false;
			}

			// Reset positions in order to receive next value.
			rowPosition = 0;
			columnPosition = 0;

		}
			// If none of the false statements are triggered, all knights are indeed safe.
			return true;
	}

	// Helper function to check if any of the attack positions of the current knight is out
	// of bounds. If so, we will simply move on to check the next attack position.
	public static boolean checkDimensions(int columnPosition, int rowPosition, int boardSize)
	{
		if((columnPosition > 0 && rowPosition > 0) && (columnPosition <= boardSize && rowPosition <= boardSize));
			return true;
	}


	public static double difficultyRating()
	{
		return 2.8;
	}

	public static double hoursSpent()
	{
		return 8.0;
	}
}
