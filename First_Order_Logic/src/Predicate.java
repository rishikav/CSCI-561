
public class Predicate {
	String name;
	String[] arguments;
	boolean isNegated;
	public Predicate(String name, String[] arguments) {
		this.name = name;
		this.arguments = arguments;
	}
	
	public static void printPredicates(Predicate p) {
		for (int i = 0; i < p.arguments.length; i++) {
		System.out.println(p.name + p.arguments[i]);
		}
	}
	
	public static int argCount (Predicate p) {
		int count = 0;
		for (int i = 0; i < p.arguments.length; i++) {
			count++;
			}
		return count;
	}
}
