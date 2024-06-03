package javaprograms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Programs {

	static int total = 0;

	public static void main(String[] args) {
		
       String str = "Test!ing@123";
       String newstring = str.replaceAll("[^0-9a-zA-Z]","");
       
       System.out.println(newstring);

		Programs.hackerrank("He is a very very good boy, isn't he?");
		System.out.println(Programs.palindrome("MADAM") ? "It is a palindrome" : "It is not a palindrome");
		System.out.println(Programs.Anagrams("SOLENT", "LISTEN") ? "Anagrams" : "Not Anagrams");
		System.out.println(Programs.Anagrams2("SILENT", "LISTEN") ? "Anagrams" : "Not Anagrams");
		
		Programs.duplicatechar("MISSISIPPI");
		Programs.duplicateword("Hi How are Hi are");
		Programs.charrepeat("MISSISIPPI");
		Programs.wordrepeat("Hi How are Hi are");
		Programs.sum(1, 10);
		Double[] d = { 1.5, 2.6, 2.3, 5.6, 2.0, 1.5, 2.6 };
		Programs.streams(d);
		Programs.reversestring("Hello");
		Programs.swap(2, 3);
		Programs.duplicatewordregex("Hello Hello@!@ Hello123 Hi# Hi1242&*(");
		System.out.println("How are you");
		System.out.println(Programs.facr(5));
		System.out.println(Programs.fac(5));

	}

	// Palindrome:

	public static boolean palindrome(String a) {
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != a.charAt(a.length() - i - 1)) {
				return false;
			}
		}
		return true;
	}

	// Anagrams approach 1:
	public static boolean Anagrams(String a, String b) {
		a = a.toLowerCase();
		b = b.toLowerCase();
		if (a.length() == b.length()) {
			int ch[] = new int[26];
			for (int i = 0; i < a.length(); i++) {
				ch[a.charAt(i) - 'a']++;
				ch[b.charAt(i) - 'a']--;
			}
			for (int c : ch) {
				if (c != 0)
					return false;
			}
			return true;
		} else
			return false;
	}

	// Anagrams approach 2:
	public static boolean Anagrams2(String a, String b) {
		char[] ch1 = a.toCharArray();
		char[] ch2 = b.toCharArray();

		Arrays.sort(ch1);
		Arrays.sort(ch2);

		if (new String(ch1).equals(new String(ch2))) {
			return true;
		} else
			return false;
	}

	// duplicate char in a string:
	public static void duplicatechar(String a) {
		List<Character> l = new ArrayList<Character>();

		for (int i = 0; i < a.length(); i++) {
			l.add(a.charAt(i));
		}
		System.out.println(
				l.stream().filter(e -> Collections.frequency(l, e) > 1).distinct().collect(Collectors.toList()));
	}

	// duplicate words in a string:
	public static void duplicateword(String a) {

		String[] s = a.split(" ");
		List<String> l = Arrays.asList(s);

		System.out.println(
				l.stream().filter(e -> Collections.frequency(l, e) > 1).distinct().collect(Collectors.toList()));
	}

	// duplicate words in a string with regex:

	public static void duplicatewordregex(String a) {

		String[] s = a.split(" ");

		List<String> l = new ArrayList<String>();

		for (String n : s) {
			l.add(n.replaceAll("[^a-zA-Z]", ""));
		}

		System.out.println(
				l.stream().filter(e -> Collections.frequency(l, e) > 1).distinct().collect(Collectors.toList()));
	}

	// count of char repetitions in a string
	public static void charrepeat(String s) {
		HashMap<Character, Integer> h = new HashMap<Character, Integer>();
		for (int i = 0; i < s.length(); i++) {
			h.compute(s.charAt(i), (key, value) -> value == null ? 1 : value + 1);
		}
		System.out.println(h);
	}

	// count of word repetitions in a string
	public static void wordrepeat(String s) {
		String[] a = s.split(" ");
		HashMap<String, Integer> h = new HashMap<String, Integer>();
		for (int i = 0; i < a.length; i++) {
			h.compute(a[i], (key, value) -> value == null ? 1 : value + 1);
		}
		System.out.println(h);
	}

	// sum of integers without using a loop
	public static void sum(int a, int b) {
		if (a <= b) {
			total = total + a;
			a++;
			sum(a, b);
		} else
			System.out.println(total);
	}

	// reversing a string
	public static void reversestring(String a) {
		String reverse = "";
		StringBuilder reversestring = new StringBuilder(a);
		for (int i = 0; i < a.length(); i++) {
			reverse = reverse + a.charAt(a.length() - i - 1);
		}
		/* approach 1 */ System.out.println("Reversed String: " + reverse);
		/* approach 2 */ System.out.println("Reversed String: " + reversestring.reverse());
	}

	// swapping two number without third variable
	public static void swap(int a, int b) {
		a = a + b;
		b = a - b;
		a = a - b;

		System.out.println("Swapped Integers are: " + a + "," + b);
	}

	// Streams questions
	public static void streams(Double[] a) {
		List<Double> l = Arrays.asList(a);
		System.out.println("Sum of Integers is: " + l.stream().mapToDouble(e -> e).sum());
		System.out.println("Average of Integers is: " + l.stream().mapToDouble(e -> e).average().getAsDouble());
		System.out.println("Duplicate numbers are "
				+ l.stream().filter(e -> Collections.frequency(l, e) > 1).distinct().collect(Collectors.toList()));
		System.out.println("Maximum of numbers are " + l.stream().mapToDouble(e -> e).max().getAsDouble());
		System.out.println("Minimum of numbers are " + l.stream().mapToDouble(e -> e).min().getAsDouble());
		System.out.println("Descending order of numbers is "
				+ l.stream().sorted((val1, val2) -> -Double.valueOf(val1).compareTo(Double.valueOf(val2)))
						.collect(Collectors.toList()));
		System.out.println("Second highest value "
				+ l.stream().sorted(Comparator.reverseOrder()).limit(2).sorted().findFirst().get());
	}

	public static void hackerrank(String input) {
		String[] a = new String(input).split("[^a-zA-Z]");

		System.out.println(Arrays.asList(a).stream().filter(e -> !e.equals("")).count());

		Arrays.asList(a).stream().filter(e -> !e.equals("")).forEach(e -> System.out.println(e));
	}

	//with recursion
	public static int facr(int i)
	{
		if (i==0)
		{
			return 1;
		}
		return i*facr(i-1);
	}
	//without recursion
	public static int fac(int i)
	{
		int fact = 1;
		if(i==0)
		{
			return 1;
		}
		else
		{
		for(int n =1;n<=i;n++)
		{
			fact = fact*n;
		}
		return fact;
		}
	}
}
