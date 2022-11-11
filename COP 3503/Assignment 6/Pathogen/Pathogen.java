// Guilherme Camara
// gu618739
// COP 3503
// Fall 2021

import java.io.*;
import java.util.*;

public class Pathogen
{
	// Credits to Dr. Szumlanski for providing the original code.

	// Used to toggle "animated" output on and off (for debugging purposes).
	private static boolean animationEnabled = false;

	// "Animation" frame rate (frames per second).
	private static double frameRate = 4.0;

	// Declare HashSet and StringBuilder used to store paths.
	public static HashSet<String> possiblePaths = new HashSet<>();
	public static StringBuilder sb = new StringBuilder("");

	// Setters. Note that for testing purposes you can call enableAnimation()
	// from your backtracking method's wrapper method (i.e., the first line of
	// your public findPaths() method) if you want to override the fact that the
	// test cases are disabling animation. Just don't forget to remove that
	// method call before submitting!
	public static void enableAnimation() { Pathogen.animationEnabled = true; }
	public static void disableAnimation() { Pathogen.animationEnabled = false; }
	public static void setFrameRate(double fps) { Pathogen.frameRate = fps; }

	// Maze constants.
	private static final char WALL       = '#';
	private static final char PERSON     = '@';
	private static final char EXIT       = 'e';
	private static final char BREADCRUMB = '.';  // visited
	private static final char SPACE      = ' ';  // unvisited
	private static final char CORONA     = '*';  // corona

	// Method to call solveMaze methods and return HashSet with
	// possible paths.
	public static HashSet<String> findPaths(char [][] maze)
	{
		solveMaze(maze);

		return possiblePaths;
	}


	// Takes a 2D char maze and returns true if it can find a path from the
	// starting position to the exit. Assumes the maze is well-formed according
	// to the restrictions above.
	public static boolean solveMaze(char [][] maze)
	{

		int height = maze.length;
		int width = maze[0].length;

		// The visited array keeps track of visited positions. It also keeps
		// track of the exit, since the exit will be overwritten when the '@'
		// symbol covers it up in the maze[][] variable. Each cell contains one
		// of three values:
		//
		//   '.' -- visited
		//   ' ' -- unvisited
		//   'e' -- exit
		char [][] visited = new char[height][width];
		for (int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
					visited[i][j] = SPACE;

		// Find starting position (location of the '@' character).
		int startRow = -1;
		int startCol = -1;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (maze[i][j] == PERSON)
				{
					startRow = i;
					startCol = j;
				}
			}
		}

		// Let's gooooooooo!!
		return solveMaze(maze, visited, startRow, startCol, height, width);
	}

	private static boolean solveMaze(char [][] maze, char [][] visited,
	                                 int currentRow, int currentCol,
	                                 int height, int width)
	{

		// Check if current position is in bounds.
		if (currentRow < 0 || currentCol < 0 ||
				currentRow >= height || currentCol >= width
				|| maze[currentRow][currentCol] == WALL ||
				maze[currentRow][currentCol] == CORONA ||
				visited[currentRow][currentCol] == BREADCRUMB )
					return false;


		// This conditional block prints the maze when a new move is made.
		if (Pathogen.animationEnabled)
		{
			printAndWait(maze, height, width, "Searching...", Pathogen.frameRate);
		}

		// Hoorayyy!
		if (visited[currentRow][currentCol] == 'e')
		{
			if (Pathogen.animationEnabled)
			{
				char [] widgets = {'|', '/', '-', '\\', '|', '/', '-', '\\',
				                   '|', '/', '-', '\\', '|', '/', '-', '\\', '|'};

				for (int i = 0; i < widgets.length; i++)
				{
					maze[currentRow][currentCol] = widgets[i];
					printAndWait(maze, height, width, "Hooray!", 12.0);
				}

				maze[currentRow][currentCol] = PERSON;
				printAndWait(maze, height, width, "Hooray!", Pathogen.frameRate);
			}

			// Add StringBuilder to HashSet.
			possiblePaths.add(sb.toString().trim());
			return true;
		}

		// Moves: left, right, up, down
		int [][] moves = (new int[][] {{0, -1}, {0, 1}, {-1, 0}, {1, 0}});

		for (int i = 0; i < moves.length; i++)
		{
			int newRow = currentRow + moves[i][0];
			int newCol = currentCol + moves[i][1];

			// Strings to be appended to StringBuilder
			String str1 = "l ";
			String str2 = "r ";
			String str3 = "u ";
			String str4 = "d ";

			// Check move is in bounds, not a wall, and not marked as visited.
			if (!isLegalMove(maze, visited, newRow, newCol, height, width))
					continue;


			// Change state. Before moving the person forward in the maze, we
			// need to check whether we're overwriting the exit. If so, save the
			// exit in the visited[][] array so we can actually detect that
			// we've gotten there.


			if (maze[newRow][newCol] == EXIT)
				visited[newRow][newCol] = EXIT;

			maze[currentRow][currentCol] = BREADCRUMB;
			visited[currentRow][currentCol] = BREADCRUMB;
			maze[newRow][newCol] = PERSON;


			// Check which move is incoming from moves[][] and append to
			// StringBuilder.
			if(i == 0)
				sb.append(str1);
			else if(i == 1)
				sb.append(str2);
			else if(i == 2)
				sb.append(str3);
			else if(i == 3)
				sb.append(str4);

			// Recursion step.
			solveMaze(maze, visited, newRow, newCol, height, width);

			// Delete characters from end of StringBuilder.
			sb.setLength(sb.length() - 2);

			// Undo state change. Note that if we return from the previous call,
			// we know visited[newRow][newCol] did not contain the exit, and
			// therefore already contains a breadcrumb, so I haven't updated
			// that here.
			maze[newRow][newCol] = BREADCRUMB;
			maze[currentRow][currentCol] = PERSON;
			visited[currentRow][currentCol] = SPACE;

			// This conditional block prints the maze when a move gets undone
			// (which is effectively another kind of move).
			if (Pathogen.animationEnabled)
			{
				printAndWait(maze, height, width, "Backtracking...", frameRate);
			}
		}

		return false;
	}

	// Returns true if moving to row and col is legal (i.e., we have not visited
	// that position before, and it's not a wall).
	private static boolean isLegalMove(char [][] maze, char [][] visited,
	                                   int row, int col, int height, int width)
	{
		if (row >= height || col >= width || row == -1 || col == -1 ||
				maze[row][col] == WALL ||	maze[row][col] == CORONA ||
				visited[row][col] == BREADCRUMB)
					return false;

		return true;
	}

	// This effectively pauses the program for waitTimeInSeconds seconds.
	private static void wait(double waitTimeInSeconds)
	{
		long startTime = System.nanoTime();
		long endTime = startTime + (long)(waitTimeInSeconds * 1e9);

		while (System.nanoTime() < endTime)
			;
	}

	// Prints maze and waits. frameRate is given in frames per second.
	private static void printAndWait(char [][] maze, int height, int width,
	                                 String header, double frameRate)
	{
		if (header != null && !header.equals(""))
			System.out.println(header);

		if (height < 1 || width < 1)
			return;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				System.out.print(maze[i][j]);
			}

			System.out.println();
		}

		System.out.println();
		wait(1.0 / frameRate);
	}

	// Read maze from file. This function dangerously assumes the input file
	// exists and is well formatted according to the specification above.
	private static char [][] readMaze(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));

		int height = in.nextInt();
		int width = in.nextInt();

		char [][] maze = new char[height][];

		// After reading the integers, there's still a new line character we
		// need to do away with before we can continue.

		in.nextLine();

		for (int i = 0; i < height; i++)
		{
			// Explode out each line from the input file into a char array.
			maze[i] = in.nextLine().toCharArray();
		}

		return maze;
	}

	public static double difficultyRating()
	{
			return 5.0;
	}

	public static double hoursSpent()
	{
			return 30.0;
		}
}
