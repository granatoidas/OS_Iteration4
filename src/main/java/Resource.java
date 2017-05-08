
public class Resource {
	int id;
	int parentId;
	String name;
	
	Integer adresatoId;
	
	public Resource(int parentId, String name){
		this(parentId, name, (Integer) null);
	}
	
	public Resource(int parentId, String name, int adresatoId){
		this.parentId = parentId;
		this.name = name;
		this.adresatoId = adresatoId;
	}
}
