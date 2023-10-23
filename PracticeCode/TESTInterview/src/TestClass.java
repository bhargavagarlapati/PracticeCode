
public class TestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String result = "Search Results (20) records";

		//System.out.println(result.replaceAll("[^0-9]", ""));
		
        System.out.println(result.replaceAll("([^0-9]*", ""));
		
	}
}
