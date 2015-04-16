/**
 * Author: Conner Reilly
 * Goal: calculate the "alphabetical rank" of an input word
 * Notes: 1) see method getAlphRank() for primary algorithm used to solve this problem
 * 			2) used Eclipse to develop this code
 */
import java.util.*;
import java.lang.management.*;

public class RankByAlphabetical {

	HashMap <Character, Integer> letters = new HashMap <Character, Integer>();	// contains the letters of the input word along with corresponding count of each letter
	char [] wordLetters;
	
	/**
	 * MAIN will:
	 * 		1) ensure that an input is given to the command line (and assume the given input is a valid one)
	 * 		2) create an instance of the RankByAlphabetical class
	 * 		3) initialize the wordLetters char array by calling initLetters(String word)
	 * 		4) print the output in the following format : "word = word.rank"
	 * 		5) tell us how much time it took for the algo to run
	 * 		6) tell us how much memory was used by the Java Virtual Machine
	 * @param args
	 */
	public static void main (String [] args) {
		if(args.length < 1){
			System.err.println("ERROR: Please enter a String as a command line argument");
		}

		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		
		{
		    long t = bean.getCurrentThreadUserTime();   // NOTE  (t is a *long*)
			RankByAlphabetical a = new RankByAlphabetical();
			a.initLetters(args[0]);
			System.out.println(args[0] + " = " + a.getAlphRank());
		    System.out.println("This algo took: " + (bean.getCurrentThreadUserTime()-t) / 1e6 + " milliseconds");
			System.out.println("Total memory use: " + Runtime.getRuntime().totalMemory()/1000000 + " MB");
		}
		
	}
	
	/**
	 * this method will fill out our HashMap with the needed key : value pairs
	 * it will also initialize our wordLetters char array, which we will use in this method and also to calculate the rank of our word
	 * @param word
	 */
	public void initLetters (String word) {
		wordLetters = word.toCharArray();
		for(int i = 0; i < wordLetters.length; i++) {
			if(letters.containsKey(wordLetters[i])) {
				letters.put(wordLetters[i], letters.get(wordLetters[i])+1);
			} else {
				letters.put(wordLetters[i], 1);
			}
		}
	}

	/**
	 * here is a summary of how this algo will work:
	 * 
	 * I found a formula on the internet that will basically calculate the total number of anagrams for a given word: http://en.wikipedia.org/wiki/Permutation#Permutations_of_multisets
	 * 
     * if you don't want to go to the wiki page, the formula is: n!/(m1!m2!m3!...mn!), where (in this specific application) n is the number
     * of letters in the word, m1 is the number of times letter 1 appears in the word, m2 is the number of times letter 2 appears in the word, etc.
     * 
	 * HOWEVER, this formula is calculating the TOTAL number of anagrams, which is not what we want
	 * 		what we want is the alphabetical rank of the given word AMONG AN ALPHABETICALLY ORDERED LIST OF ALL ANAGRAMS of the word 
	 * 
	 * Luckily, we should also be able to use this formula to find the alphabetical rank of a word
	 * 
	 * THIS IS HOW WE USE THE FORMULA TO DO WHAT WE WANT:
	 * 		
	 * 		- First, we receive an input word (e.g. "QUESTION")
	 * 		- Allow "current letter" to just be any letter in the input word (e.g. we could choose
	 * 			current letter to be "E" in the input word "QUESTION")
	 * 		- Allow "letter X" to be a letter that is a) to the right of the current letter in the word and that b) comes before the current letter
	 * 			in the alphabet (so, if the input word is "QUESTION", and the current letter is "E", then "T" could be
	 * 			"letter X")
	 * 		
	 * 		So, what we will want to do is:
	 * 			1) take letter X AND REMOVE IT FROM THE HASHSET (this might be a good time to notice a couple things:
	 * 				a) In the numerator() method we start sum at -1. This is because we are removing letter X from
	 * 					the hashset (so by starting sum at -1 we are in effect calculating (n-1)! in the numerator()
	 * 					method.
	 * 				b) In the denominator() method we have the line "letters.put(rep, letters.get(rep)-1);" - again,
	 * 					we are subtracting 1 because we want to run our calculation pretending letter X is not in the hashset
	 * 			)
	 * 			To summarize the first step, we are calculating the number of anagrams of the input word without letter X
	 * 			2) once we have done the calculation described in step 1, we add the result to the rank
	 * 			3) if the current letter appears 1 or fewer times in the word, remove it from the hashmap
	 * 				otherwise, decrement the number of times the current letter appears by 1
	 * 
	 * 			If we do the above three steps, iterating over all possible current letters and letter Xs,
	 * 			then we can calculate the rank of the given input word
	 * 
	 * 		The code in getAlphRank() implements the algo described above
	 * 
	 * @param wordLetters
	 * @return rank of the word
	 */
	public long getAlphRank() {
		long rank = 1;
		if( letters.isEmpty()) {
			return 0;
		}
		
		for(int i = 0; i < wordLetters.length; i++) {
			for(char b : letters.keySet()) {
				if( b < wordLetters[i] ) {
					rank += numerator()/denominator(b);
				}
			}
			// if there are no remaining instances of the current letter in the HashMap, then remove the letter altogether
			if(letters.get(wordLetters[i]) < 2) {
				letters.remove(wordLetters[i]);
			// otherwise, decrease the letter count
			} else {
				letters.put(wordLetters[i], letters.get(wordLetters[i])-1);
			}
		}
	
		return rank;
	}

/**
 * method to calculate n!
 * @param n
 * @return n!
 */
	public long factorial(int n) {
		long ret = 1;
		for (int i = 1; i <= n; i++) ret *= i;
		return ret;
	}

/**
 * calculates sum!, where sum is the number of letters remaining in the HashMap
 * @return sum! (i.e. number of diff permutations of remaining letters)
 */
	public long numerator() {
		int sum = -1;
		for(Integer a : letters.values()) {
			sum += a;
		}
	
		return factorial(sum);
	}

/**
 * calculate the denominator for the combinatoric formula I'm using (i.e. m1!m2!...mn!),
 * ignoring letter X (see description above getAlphRank for the definition of letter X)
 * @param rep (i.e. letter X)
 * @return m1!m2!...mn!
 */
	public long denominator(Character rep) {
		long product = 1;
		letters.put(rep, letters.get(rep)-1);
	
		for(Integer x : letters.values()) {
			product *= factorial(x);
		}
		letters.put(rep, letters.get(rep)+1);
		return product;
	}
		
		
}
