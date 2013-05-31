package me.smulyono.mppconverter.model;


public class Task implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 610580980722549049L;
	private String name;
	// ID respective to the other task (order in the project)
	private Integer id;
	// unique ID 
	private Integer uniqueId;
	private boolean milestone;
	private boolean active;
	private Number percentageComplete;
	
	public Task(net.sf.mpxj.Task task){
		this.setName(task.getName());
		this.setId(task.getID());
		this.setUniqueId(task.getUniqueID());
		this.setMilestone(task.getMilestone());
		this.setActive(task.getActive());
		this.setPercentageComplete(task.getPercentageComplete());
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
	 * @return the milestone
	 */
	public boolean isMilestone() {
		return milestone;
	}

	/**
	 * @param milestone the milestone to set
	 */
	public void setMilestone(boolean milestone) {
		this.milestone = milestone;
	}

	/**
	 * @return the percentageComplete
	 */
	public Number getPercentageComplete() {
		return percentageComplete;
	}

	/**
	 * @param percentageComplete the percentageComplete to set
	 */
	public void setPercentageComplete(Number percentageComplete) {
		this.percentageComplete = percentageComplete;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
