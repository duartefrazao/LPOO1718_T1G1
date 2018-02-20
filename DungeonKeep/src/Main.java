
public class Main {
	static char level[][] = 
		{
		{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
        {'X', 'H', ' ', ' ', 'I', ' ', 'X', ' ', 'G', 'X'},
        {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
        {'X', ' ', 'I', ' ', 'I', ' ', 'X', ' ', ' ', 'X'},
        {'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', ' ', 'X'},
        {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
        {'I', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
        {'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
        {'X', ' ', 'I', ' ', 'I', ' ', 'X', 'k', ' ', 'X'},
        {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
        };

	public static void main(String args[]) {
		System.out.println("Hello");
		for (char[] row : level) {
			for (char i : row)
				System.out.print(i + " ");
			System.out.println();
		}

	}
}
