import java.util.*;

public class Cipher {
	
	private char[] freq = {'E', 'T', 'A', 'O', 'I', 'N', 'S', 'R', 'H', 'D', 'L', 'U', 
            'C', 'M', 'F', 'Y', 'W', 'G', 'P', 'B', 'V', 'K', 'X', 'Q', 'J', 'Z'};
	
	public Cipher() {
		//this.freq = freq;
	}
	
	/*
	 * Encode with the Affine Cipher. We're only dealing with capital plaintexts here. Including stuff for lowercase takes more effort.
	 */
	public String encode(String plaintext, int a, int b) {
		String cipher = "";
		for (int i = 0; i < plaintext.length(); i++) {
			if(plaintext.charAt(i) != ' ') {
				cipher += (char) ((((a * (plaintext.charAt(i)-'A') ) + b) % 26) + 'A');
			}
			
			else {
				cipher += plaintext.charAt(i);
			}
			
		}
		return cipher;
	}
	
	/*
	 * Decode with letter frequency analysis. Seems needlessly complicated with Java. Also, only capitals. Don't want to bother with lowercase.
	 */
	public String decode(String message) {
		Map<Character, Integer> dictionary = new HashMap<Character, Integer>();
		Map<Character, Character> codeBook = new HashMap<Character, Character>();
		List<Character> arrangedKeys = new ArrayList<Character>();
		Set<Integer> sortedValues = new TreeSet<Integer>(Collections.reverseOrder());
		StringBuilder decrypted = new StringBuilder();
		char ch = 'A';
		for (int i = 0; i < 26; i++) {
			dictionary.put(ch, 0);
			ch += 1;
		}
		
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) != ' ') {
				dictionary.put(message.charAt(i), dictionary.get(message.charAt(i)) + 1);
			}
		}
		
		for (Integer i : dictionary.values()) {
			sortedValues.add(i);
		}
		
		for (Integer i : sortedValues) {
			for (Character c : dictionary.keySet()) {
				if (dictionary.get(c) == i) {
					arrangedKeys.add(c);
				}
			}
		}
		
		int index = 0;
		for (Character c : arrangedKeys) {
			codeBook.put(c, freq[index]);
			index++;
		}
		
		for (int i = 0; i < message.length(); i++) {
			char ci = message.charAt(i);
			if (ci != ' ') {
				decrypted.append(codeBook.get(ci));
			}
			else {
				decrypted.append(' ');
			}
		}
		
		return decrypted.toString();
	}
	
	public static void main(String[] args) {
		Cipher thing = new Cipher();
		String plaintext1 = "PERHAPS I CAN BE OF ASSISTANCE";
		String encrypt1 = thing.encode(plaintext1, 15, 21);
		String decrypt1 = thing.decode(encrypt1);
		System.out.println("Plaintext:  " + plaintext1);
		System.out.println("Encryption: " + encrypt1);
		System.out.println("Decryption: " + decrypt1);
	}
}
