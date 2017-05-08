
public class Resource {
	int id;
	Process parent;
	int parentId;
	String name;
	
	Integer adresatoId;
	
	public Resource(Process parent, String name, Integer adresatoId){
		this.parent = parent;
		this.parentId = parent.id;
		this.name = name;
		this.adresatoId = adresatoId;
	}
}
