// Guilherme Camara
// gu618739
// COP 3503
// Fall 2021

import java.io.*;
import java.util.*;

public class SneakyQueens
{
	public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		int numQueens = coordinateStrings.size();
		int columnPosition = 0, rowPosition = 0;
		int [] checkRow = new int [boardSize + 1];
		int [] checkColumn = new int [boardSize + 1];
		int [] diagonalFromRight = new int [(boardSize * 2) + 2];
			// +2 accounts for last index
			int [] diagonalFromLeft = new int [(boardSize * 2) + 2];
			// +2 accounts for last index

			for(int i = 0; i < numQueens; i++)
			{

				if(numQueens < 2)
					return true;

					int strLen = coordinateStrings.get(i).length();

					String coordinateString = coordinateStrings.get(i);
					// Sorry for the lack of creativity

					for(int j = 0; j < strLen; j++)
					{
	                if(Character.isAlphabetic(coordinateString.charAt(j))) // check if it's letter
	                {
	                  columnPosition = columnPosition * 26 + (coordinateString.charAt(j) - 'a' + 1);
	                }
	                else if(Character.isAlphabetic(coordinateString.charAt(j)) == false) // if not, #
	                {
	                  rowPosition = rowPosition * 10 + (coordinateString.charAt(j) - '0');
	                }
					}

					int leftDiagonal = (rowPosition + 1) + (columnPosition + 1);
					// left diagonal goes 1 up and 1 right from bottom left corner
					int rightDiagonal = (rowPosition + 1) + ((boardSize - 1) - (columnPosition - 1));
					// right diagonal goes 1 up and 1 left from bottom right corner

					if(diagonalFromLeft[leftDiagonal] == 0)
						diagonalFromLeft[leftDiagonal]++;
					else
						return false;

					if(diagonalFromRight[rightDiagonal] == 0)
						diagonalFromRight[rightDiagonal]++;
					else
						return false;

					if(checkRow[rowPosition] == 0)
						checkRow[rowPosition]++;
					else
						return false;

					if(checkColumn[columnPosition] == 0)
						checkColumn[columnPosition]++;
					else
						return false;

						// remember to reset positions in order to receive next value
						rowPosition = 0;
						columnPosition = 0;

					}

					return true;

		}

		public static double difficultyRating()
		{
			return 3.0; // spent quite some time to figure out diagonals
		}

		public static double hoursSpent()
		{
			return 12.0;
		}
}
