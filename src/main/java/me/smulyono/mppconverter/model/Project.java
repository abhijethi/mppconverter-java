package me.smulyono.mppconverter.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpxj.ProjectFile;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Project implements java.io.Serializable{
	static Logger logger = LoggerFactory.getLogger(Project.class); 
	/**
	 * 
	 */
	private static final long serialVersionUID = -4397853852371867561L;
	private String projectTitle;
	private String publisher;
	public String StartDate;
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
		this.setStartDate(project.getStartDate());
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
	
	public String getStartDate(){
		return StartDate;
	}
	public void setStartDate(Date stdDate){
		if(StartDate!=null){
			this.StartDate = convertStringToDate(stdDate);
		}
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
	
	public String convertStringToDate(Date indate)
	{
	   String dateString = null;
	   SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
	   /*you can also use DateFormat reference instead of SimpleDateFormat 
	    * like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	    */
	   try{
		dateString = sdfr.format( indate );
	   }catch (Exception ex ){
		System.out.println(ex);
	   }
	   return dateString;
	}

}
