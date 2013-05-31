package me.smulyono.mppconverter.model;


public class Resource implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5128066412328710548L;
	private String name;
	private String type;
	private Double stdRate;
	// ID in the project which also shows the order of the resource
	private Integer id;
	// unique ID
	private Integer uniqueId;
	
	public Resource(){}
	
	public Resource(net.sf.mpxj.Resource resource){
		this.setId(resource.getID());
		if (resource.getName() == null){
			this.setName("Unassigned Resource");
		} else {
			this.setName(resource.getName());
		}
		this.setUniqueId(resource.getUniqueID());
		this.setType(resource.getType().name());
		this.setStdRate(resource.getStandardRate().getAmount());
	}
	
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
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the uniqueId
	 */
	public Integer getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(Integer uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the stdRate
	 */
	public Double getStdRate() {
		return stdRate;
	}

	/**
	 * @param stdRate the stdRate to set
	 */
	public void setStdRate(Double stdRate) {
		this.stdRate = stdRate;
	}
}
