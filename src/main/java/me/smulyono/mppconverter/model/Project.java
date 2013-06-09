package me.smulyono.mppconverter.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpxj.ProjectFile;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Project implements java.io.Serializable{
	static Logger logger = LoggerFactory.getLogger(Project.class); 
	/**
	 * 
	 */
	private static final long serialVersionUID = -4397853852371867561L;
	private String projectTitle;
	private String publisher;
	private List<Resource> resources;
	private List<Task> tasks;
	private List<Assignment> assignments;
	
	/*
	 * No-parameter constructor
	 */
	public Project(){}
	
	/*
	 * @param ProjectFile, project information to parse
	 */
	public Project(ProjectFile project){
		// during construction, we will parse all needed information
		// fill in all project information
		fillProjectInfo(project);
		
		// Retrieve Resources
		resources = new ArrayList<Resource>();
		for (net.sf.mpxj.Resource resource : project.getAllResources()){
			Resource newresource= new Resource(resource);
			resources.add(newresource);
		}
		
		// Retrieve Task
		tasks = new ArrayList<Task>();
		for (net.sf.mpxj.Task task : project.getAllTasks()){
			Task newtask = new Task(task);
			tasks.add(newtask);
		}
		
		// Retrieve assignment
		assignments = new ArrayList<Assignment>();
		for (net.sf.mpxj.ResourceAssignment assgn : project.getAllResourceAssignments()){
			Assignment newassignment= new Assignment(assgn);
			assignments.add(newassignment);
		}
		
	}

	/**
	 * Parse all project header information
	 * @param project
	 */
	private void fillProjectInfo(ProjectFile project){
		this.setProjectTitle(project.getProjectHeader().getProjectTitle());
		this.setPublisher(project.getProjectHeader().getAuthor());
	}
	
	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the resources
	 */
	public List<Resource> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	/**
	 * @return the tasks
	 */
	public List<Task> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the assignments
	 */
	public List<Assignment> getAssignments() {
		return assignments;
	}

	/**
	 * @param assignments the assignments to set
	 */
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

}
