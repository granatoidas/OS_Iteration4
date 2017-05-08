import java.util.ArrayList;

public class Process {
	static final String PASIRUOSES = "pasiruoses";
	static final String BLOKUOTAS = "blokuotas";
	static final String SUSTABDYTAS = "sustabdytas";
	static final String AKTYVUS = "aktyvus";
	
	String name;
	String state = PASIRUOSES;
	int priority = 50;
	
	int segmentToRunFrom = 0;
	
	int id;
	int parentId;
	
	ArrayList<Process> children = new ArrayList<Process>();
	ArrayList<Resource> generatedResources = new ArrayList<Resource>();
	
	ArrayList<Resource> resourcesInPossesion = new ArrayList<Resource>();
	
	Process(int id, int parentId, String name){
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}
	
	void run(){
		
	}
	
	void increasePriority(){
		priority += 1;
		if (priority > 100)
			priority = 100;
	}
	void decreasePriority(){
		priority -= 1;
		if (priority < 100)
			priority = 0;
	}
}
