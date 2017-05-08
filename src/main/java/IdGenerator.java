
public class IdGenerator {
	private static int lastId = 0;
	public static int getId(){
		return ++lastId;
	}
}
