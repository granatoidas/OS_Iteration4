import java.util.Scanner;

public class Utils {
	private static int lastId = 0;
	public static int getId(){
		return ++lastId;
	}
	static Scanner sc = new Scanner(System.in);
	public static void LOG(String s){
		System.out.println(s);
		sc.nextLine();
	}
}
