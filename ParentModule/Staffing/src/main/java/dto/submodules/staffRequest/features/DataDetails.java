package dto.submodules.staffRequest.features;

public class DataDetails {
	
	private int id;
	private String name;
	private int creator_id;
	private String sekey;
	
	private boolean uncleanable;
	
	public String getSekey() {
		return sekey;
	}
	public void setSekey(String sekey) {
		this.sekey = sekey;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}
	public boolean getUncleanable() {
		return uncleanable;
	}
	public void setUncleanable(boolean uncleanable) {
		this.uncleanable = uncleanable;
	}
	@Override
	public String toString() {
		return "DataDetails [id=" + id + ", name=" + name + ", creator_id=" + creator_id + ", sekey=" + sekey
				+ ", uncleanable=" + uncleanable + "]";
	}
}
