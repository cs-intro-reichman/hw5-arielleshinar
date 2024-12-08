/*
 * RUNI version of the Scrabble game.
 */

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

public class Scrabble {

	// Note 1: "Class variables", like the five class-level variables declared below,
	// are global variables that can be accessed by any function in the class. It is
	// customary to name class variables using capital letters and underline characters.
	// Note 2: If a variable is declared "final", it is treated as a constant value
	// which is initialized once and cannot be changed later.

	// Dictionary file for this Scrabble game
	static final String WORDS_FILE = "dictionary.txt";

	// The "Scrabble value" of each letter in the English alphabet.
	// 'a' is worth 1 point, 'b' is worth 3 points, ..., z is worth 10 points.
	static final int[] SCRABBLE_LETTER_VALUES = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3,
												  1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };

	// Number of random letters dealt at each round of this Scrabble game
	static int HAND_SIZE = 10;

	// Maximum number of possible words in this Scrabble game
	static int MAX_NUMBER_OF_WORDS = 100000;

    // The dictionary array (will contain the words from the dictionary file)
	static String[] DICTIONARY = new String[MAX_NUMBER_OF_WORDS];

	// Actual number of words in the dictionary (set by the init function, below)
	static int NUM_OF_WORDS;

	// Populates the DICTIONARY array with the lowercase version of all the words read
	// from the WORDS_FILE, and sets NUM_OF_WORDS to the number of words read from the file.
	public static void init() {
		// Declares the variable in to refer to an object of type In, and initializes it to represent
		// the stream of characters coming from the given file. Used for reading words from the file.  
		In in = new In(WORDS_FILE);
        System.out.println("Loading word list from file...");
        NUM_OF_WORDS = 0;
		while (!in.isEmpty()) {
			// Reads the next "token" from the file. A token is defined as a string of 
			// non-whitespace characters. Whitespace is either space characters, or  
			// end-of-line characters.
			DICTIONARY[NUM_OF_WORDS++] = in.readString().toLowerCase();
		}
        System.out.println(NUM_OF_WORDS + " words loaded.");
	}


	// Checks if the given word is in the dictionary.
	public static boolean isWordInDictionary(String word) {
		
		for (int i = 0; i < NUM_OF_WORDS; i++){
			if(DICTIONARY[i] != null && DICTIONARY[i].equals(word)){
				return true;
			}
		}
		return false;
		
	}
	
	// Returns the Scrabble score of the given word.
	// If the length of the word equals the length of the hand, adds 50 points to the score.
	// If the word includes the sequence "runi", adds 1000 points to the game.
	public static int wordScore(String word) {
		String runi = "runi";
		int score = 0;

	
	
		// Add scrabble letter values for each character in the word
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			if (letter >= 'a' && letter <= 'z'){
				int index = letter - 'a'; 
				score += SCRABBLE_LETTER_VALUES[index];
			}
		}
			score *= word.length();
	
	
		// Add bonus for "runi"
		if (MyString.subsetOf(runi, word) == true) {
			score += 1000;
		}

		// Add bonus for word length == 10
		if (word.length() == 10){
			score += 50;
		}
	
		return score;
	}

	// Creates a random hand of length (HAND_SIZE - 2) and then inserts
	// into it, at random indexes, the letters 'a' and 'e'
	// (these two vowels make it easier for the user to construct words)
	public static String createHand() {
	

		//calls the random string of letters function i created.
		// Generate random letters
			String rand = MyString.randomStringOfLetters(HAND_SIZE - 2); 
			// Insert 'a'
			rand = MyString.insertRandomly('a', rand);  
			// Insert 'e'
			rand = MyString.insertRandomly('e', rand); 
			return rand;
		
	}

	public static String remove(String hand, String word) {
		for (int i = 0; i < word.length(); i++) {
			int index = hand.indexOf(word.charAt(i));
			if (index != -1) {
				// Remove the character at the found index
				hand = hand.substring(0, index) + hand.substring(index + 1);
			} else {
				// If we can't find the letter, return the original hand
				return hand;
			}
		}
		return hand;
	}
	
    // Runs a single hand in a Scrabble game. Each time the user enters a valid word:
    // 1. The letters in the word are removed from the hand, which becomes smaller.
    // 2. The user gets the Scrabble points of the entered word.
    // 3. The user is prompted to enter another word, or '.' to end the hand. 
	/*
	public static void playHand(String hand) {
		int n = hand.length();
		int score = 0;
		String dot = ".";
		// Declares the variable in to refer to an object of type In, and initializes it to represent
		// the stream of characters coming from the keyboard. Used for reading the user's inputs.   
		In in = new In();
		while (hand.length() > 0) {
			System.out.println("Current Hand: " + MyString.spacedString(hand));
			System.out.println("Enter a word, or '.' to finish playing this hand:");
			// Reads the next "token" from the keyboard. A token is defined as a string of 
			// non-whitespace characters. Whitespace is either space characters, or  
			// end-of-line characters.
			String input = in.readString();

			if (input.equals(dot)) {
				System.out.println("End of hand. Total score: " + score + " points");
				break;  // End the hand
			} else if (!MyString.subsetOf(input, hand)) {
				System.out.println("Invalid word. Try again.");
			} else if (!isWordInDictionary(input)) {
				System.out.println("No such word in the dictionary. Try again.");
			} else {
				// Valid word, process the score and remove letters from hand
				score += wordScore(input);
				hand = remove(hand, input);
				System.out.println(input + " earned " + wordScore(input) + " points. Score: " + score + " points");
			}
		}
		if (hand.length() == 0) {
	        System.out.println("Ran out of letters. Total score: " + score + " points");
		}
	}
	 */
	public static void playHand(String hand) {
		int score = 0;
		String dot = ".";
		In in = new In();
	
		// Flag to check if at least one word is played
		boolean validWordPlayed = false;
	
		while (hand.length() > 0) {
			// Only print the current hand once at the start of the loop
			System.out.println("Current Hand: " + MyString.spacedString(hand));
			System.out.println("Enter a word, or '.' to finish playing this hand:");
	
			String input = in.readString().trim();
	
			// If the user inputs '.', end the hand
			if (input.equals(dot)) {
				System.out.println("End of hand. Total score: " + score + " points");
				break;  // End the hand
			}
	
			// Check if the word can be made from the available letters in the hand
			if (!MyString.subsetOf(input, hand)) {
				// If it's not possible to make the word from the hand, skip it silently
				continue;
			}
	
			// Check if the word is in the dictionary
			if (!isWordInDictionary(input)) {
				// If it's not a valid word in the dictionary, skip it silently
				continue;
			}
	
			// Word is valid, calculate its score
			int wordScore = wordScore(input);
			score += wordScore;
			hand = remove(hand, input);  // Remove the used letters from the hand
	
			// Output the word and score in the required format
			System.out.println(input + " earned " + wordScore + " points. Score: " + score + " points");
	
			// Mark that a valid word was played
			validWordPlayed = true;
		}
	
		// Final check if we exhausted the letters in the hand
		if (validWordPlayed && hand.length() == 0) {
			System.out.println("Ran out of letters. Total score: " + score + " points");
		}
	}
	

	// Plays a Scrabble game. Prompts the user to enter 'n' for playing a new hand, or 'e'
	// to end the game. If the user enters any other input, writes an error message.
	public static void playGame() {
		// Initializes the dictionary
    	init();
		// The variable in is set to represent the stream of characters 
		// coming from the keyboard. Used for getting the user's inputs.  
		In in = new In();

		while(true) {
			System.out.println("Enter n to deal a new hand, or e to end the game:");
			// Gets the user's input, which is all the characters entered by 
			// the user until the user enter the ENTER character.
			String input = in.readString();
	
			//// checks if its n or e
			if (input.length() != 1) {
				System.out.println("Not a valid answer, try again.");
				continue;  // Continue the loop to prompt again
			}
			if (input.charAt(0) != 'n' && input.charAt(0) != 'e') {
				System.out.println("Not a valid answer, try again.");
				continue; // Continue the loop
			}
			// Exit the loop and end the game
			if (input.charAt(0) == 'e') {
				break;  
			}
	
			playHand(createHand());
		}
			
		}

	public static void main(String[] args) {
		//System.out.println("'' -> " + Scrabble.isWordInDictionary("") + " (expected: false)");
		//System.out.println("'CAT' -> " + Scrabble.isWordInDictionary("CAT") + " (expected: false)");
		//System.out.println("'xyz123' -> " + Scrabble.isWordInDictionary("xyz123") + " (expected: false)");
		//System.out.println("'qwxz' -> " + Scrabble.isWordInDictionary("qwxz") + " (expected: false)");

		
		//// Uncomment the test you want to run
		testBuildingTheDictionary();  
		testScrabbleScore();    
		testCreateHands();  
		testPlayHands();
		playGame();

		
	}

	
	public static void testBuildingTheDictionary() {
		init();
		// Prints a few words
		for (int i = 0; i < 5; i++) {
			System.out.println(DICTIONARY[i]);
		}
		System.out.println(isWordInDictionary("mango"));
	}
	
	public static void testScrabbleScore() {
		System.out.println(wordScore("bee"));	
		System.out.println(wordScore("babe"));
		System.out.println(wordScore("friendship"));
		System.out.println(wordScore("running"));
	}
	
	public static void testCreateHands() {
		System.out.println(createHand());
		System.out.println(createHand());
		System.out.println(createHand());
	}
	public static void testPlayHands() {
		init();
		playHand("ocostrza");
		playHand("arbffip");
		playHand("aretiin");
	}


}
