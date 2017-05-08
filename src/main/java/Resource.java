
public class Resource {
	int id;
	Process parent;
	int parentId;
	String name;
	
	Integer adresatoId;
	
	String data;
	
	public Resource(Process parent, String name, Integer adresatoId, String data){
		this.parent = parent;
		this.parentId = parent.id;
		this.name = name;
		this.adresatoId = adresatoId;
		this.data = data;
	}
}
