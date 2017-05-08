import java.util.ArrayList;

public class Process {
	String name;
	String state = "pasiruoses";
	int priority = 50;
	
	int id;
	int parentId;
	
	ArrayList<Integer> childrenIds = new ArrayList<Integer>();
	ArrayList<Integer> resourceIds = new ArrayList<Integer>();
	
	Process(int parentId, String name){
		this.parentId = parentId;
		this.name = name;
	}
	
	void run(){
		
	}
	
	void increasePriority(){
		priority += 2;
		if (priority > 100)
			priority = 100;
	}
	void decreasePriority(){
		priority -= 2;
		if (priority < 100)
			priority = 0;
	}
}
