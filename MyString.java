/**
 * A library of string functions.
 */
public class MyString {
    public static void main(String args[]) {
        String hello = "hello";
        System.out.println(countChar(hello, 'h'));
        System.out.println(countChar(hello, 'l'));
        System.out.println(countChar(hello, 'z'));
        System.out.println(spacedString(hello));
        System.out.println("\nTesting subsetOf:");
        System.out.println("sap in space -> " + MyString.subsetOf("sap", "space") + " (expected: true)");
        System.out.println("spa in space -> " + MyString.subsetOf("spa", "space") + " (expected: true)");
        System.out.println("pass in space -> " + MyString.subsetOf("pass", "space") + " (expected: false)");
        System.out.println("c in space -> " + MyString.subsetOf("c", "space") + " (expected: true)");
        System.out.println("empty string in anything -> " + MyString.subsetOf("", "anything") + " (expected: true)");
        System.out.println("\nTesting spacedString:");
        System.out.println("silent -> \"" + MyString.spacedString("silent") + "\" (expected: s i l e n t)");
        System.out.println("a -> \"" + MyString.spacedString("a") + "\" (expected: a)");
        System.out.println("empty string -> \"" + MyString.spacedString("") + "\" (expected: )");
        System.out.println("hi -> \"" + MyString.spacedString("hi") + "\" (expected: h i)");
        System.out.println("\nTesting randomStringOfLetters:");
        System.out.println("length 5 -> " + MyString.randomStringOfLetters(5));
        System.out.println("length 10 -> " + MyString.randomStringOfLetters(10));
        System.out.println("length 0 -> \"" + MyString.randomStringOfLetters(0) + "\"");
        System.out.println("\nTesting remove:");
        System.out.println("committee - meet -> " + MyString.remove("committee", "meet") + " (expected: comit)");
        System.out.println("abc - abc -> " + MyString.remove("abc", "abc") + " (expected: )");
        System.out.println("abc - b -> " + MyString.remove("abc", "b") + " (expected: ac)");
        System.out.println("hello - empty string -> " + MyString.remove("hello", "") + " (expected: hello)");
       
    }

    /**
     * Returns the number of times the given character appears in the given string.
     * Example: countChar("Center",'e') returns 2 and countChar("Center",'c') returns 0. 
     * 
     * @param str - a string
     * @param c - a character
     * @return the number of times c appears in str
     */
    public static int countChar(String str, char ch) {
     
        int count = 0;
        //create a for loop that goes over the string and each time it finds the char adds 1 to count
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == ch){
                count++ ;
            }
        }
        return count;
    }

    /** Returns true if str1 is a subset string str2, false otherwise
     *  Examples:
     *  subsetOf("sap","space") returns true
     *  subsetOf("spa","space") returns true
     *  subsetOf("pass","space") returns false
     *  subsetOf("c","space") returns true
     *
     * @param str1 - a string
     * @param str2 - a string
     * @return true is str1 is a subset of str2, false otherwise
     */
    public static boolean subsetOf(String str1, String str2) {
         
         //makes sure str1 is <= str2
         if (str1.length() > str2.length()){
            return false;
         }
         //create a for loop that checks no letter appears in str1 more than in str2
         for (int i = 0; i < str1.length(); i++){
            if (countChar(str1, str1.charAt(i)) > countChar(str2, str2.charAt(i))){
                return false;
            }
         }
         
        return true;
    }

    /** Returns a string which is the same as the given string, with a space
     * character inserted after each character in the given string, except
     * for the last character. 
     * Example: spacedString("silent") returns "s i l e n t"
     * 
     * @param str - a string
     * @return a string consisting of the characters of str, separated by spaces.
     */
    public static String spacedString(String str) {
        //// Replace the following statement with your code
        String spaced = "";
        //empty string returns a space
        if (str == ""){
            return " ";
        }

        for (int i = 0; i < str.length(); i++){
            if ((i == str.length() - 1)){
                spaced += str.charAt(i);
                break;
            }
            spaced += (str.charAt(i)) + " ";
        }
        
        return spaced;
    }
    
    /**
     * Returns a string of n lowercase letters, selected randomly from 
     * the English alphabet 'a', 'b', 'c', ..., 'z'. Note that the same
     * letter can be selected more than once.
     * 
     * Example: randomStringOfLetters(3) can return "zoo"
     * 
     * @param n - the number of letter to select
     * @return a randomly generated string, consisting of 'n' lowercase letters
     */
    public static String randomStringOfLetters(int n) {
        //// use math random times 26 to generate a letter in the alphabet
        String abc = "abcdefghijklmnopqrstuvwxyz";
        String rand = "";

        //loop the happens n times and checks which letter the random number between 0-25 
        //represents and add it to the string
        for (int i = 0; i < n; i++){
            rand += abc.charAt((int) (Math.random() * 26));
        }

        return rand;
    }

    /**
     * Returns a string consisting of the string str1, minus all the characters in the
     * string str2. Assumes (without checking) that str2 is a subset of str1.
     * Example: remove("meet","committee") returns "comit" 
     * 
     * @param str1 - a string
     * @param str2 - a string
     * @return a string consisting of str1 minus all the characters of str2
     */
    public static String remove(String str1, String str2) {
       // create a string to store the result string
        String result = "";

        // go over over each character in str1
        for (int i = 0; i < str1.length(); i++) {
            
            // If the character is found in str2, remove it by using substring
            int indexInStr2 = str2.indexOf(str1.charAt(i));
            
            if (indexInStr2 != -1) {
                // If the character is found in str2, remove it from str2 and continue with the next character
                str2 = str2.substring(0, indexInStr2) + str2.substring(indexInStr2 + 1);
            } else {
                // If the character is not found in str2, keep it in the result string
                result+= str1.charAt(i);
            }
        }
    
        return result;  
    }

    /**
     * Returns a string consisting of the given string, with the given 
     * character inserted randomly somewhere in the string.
     * For example, insertRandomly("s","cat") can return "scat", or "csat", or "cast", or "cats".  
     * @param ch - a character
     * @param str - a string
     * @return a string consisting of str with ch inserted somewhere
     * You can use any possible String function, including subString, which can be quite useful in the
    implementation of the insertRandomly function.
     */
    public static String insertRandomly(char ch, String str) {
         // Generate a random index between 0 and str.length()
         int randomIndex = (int) (Math.random() * (str.length() + 1));
         // Insert the character at the random index
         String result = str.substring(0, randomIndex) + ch + str.substring(randomIndex);
         return result;
     }    
     
}
