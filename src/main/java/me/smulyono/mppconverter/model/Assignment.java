package me.smulyono.mppconverter.model;

import net.sf.mpxj.ResourceAssignment;

public class Assignment implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7663607216648143181L;
	private Integer taskUniqueId;
	private Integer taskId;
	
	private Integer resourceUniqueId;
	private Integer resourceId;
	
	public Assignment(){}
	public Assignment(ResourceAssignment assignment){
		// get the unique ID
		this.setTaskUniqueid(assignment.getTaskUniqueID());
		this.setResourceUniqueid(assignment.getResourceUniqueID());
		// get the ID
		this.setTaskId(assignment.getTask().getID());
		if (assignment.getResource() == null){
			this.setResourceId(null);
		} else
		this.setResourceId(assignment.getResource().getID());
	}

	/**
	 * @return the taskid
	 */
	public Integer getTaskUniqueid() {
		return taskUniqueId;
	}

	/**
	 * @param taskid the taskid to set
	 */
	public void setTaskUniqueid(Integer taskid) {
		this.taskUniqueId = taskid;
	}

	/**
	 * @return the resourceid
	 */
	public Integer getResourceUniqueid() {
		return resourceUniqueId;
	}

	/**
	 * @param resourceid the resourceid to set
	 */
	public void setResourceUniqueid(Integer resourceid) {
		this.resourceUniqueId = resourceid;
	}

	/**
	 * @return the taskId
	 */
	public Integer getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
}
