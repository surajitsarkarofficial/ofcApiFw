package dto.submodules.globers;

import java.util.List;

public class GloberStatusObjects {
	private String id;
	private String name;
	private List<String> subFilterList;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param name
	 */
	public GloberStatusObjects(String name) {
		super();
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GloberStatusObjects other = (GloberStatusObjects) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/**
	 * @param id
	 * @param name
	 */
	public GloberStatusObjects(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the subFilterList
	 */
	public List<String> getSubFilterList() {
		return subFilterList;
	}
	/**
	 * @param subFilterList the subFilterList to set
	 */
	public void setSubFilterList(List<String> subFilterList) {
		this.subFilterList = subFilterList;
	}
	/**
	 * @param id
	 * @param name
	 * @param subFilterList
	 */
	public GloberStatusObjects(String id, String name, List<String> subFilterList) {
		super();
		this.id = id;
		this.name = name;
		this.subFilterList = subFilterList;
	}
	/**
	 * 
	 */
	public GloberStatusObjects() {
		super();
	}
}
