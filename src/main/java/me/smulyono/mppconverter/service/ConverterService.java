package me.smulyono.mppconverter.service;

import java.io.IOException;
import java.io.InputStream;

import me.smulyono.mppconverter.model.Project;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Resource;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mpx.MPXWriter;
import net.sf.mpxj.reader.ProjectReader;
import net.sf.mpxj.writer.ProjectWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {
	static Logger logger = LoggerFactory.getLogger(ConverterService.class); 
	
	public static final String MPP_TYPE = "mpp";
	public static final String MPX_TYPE = "mpx";
	
	public ProjectFile ConvertFile (InputStream istream, String filetype) throws MPXJException, IOException{
		ProjectReader reader = null;
		if (filetype.toLowerCase().equalsIgnoreCase(ConverterService.MPP_TYPE)){
			reader = new MPPReader();
		}
		if (filetype.toLowerCase().equalsIgnoreCase(ConverterService.MPX_TYPE)){
			reader = new MPXReader();
		}
		if (reader == null){
			return null;
		}
		/* retrieve the correct projectfile */
		ProjectFile project = reader.read(istream);
		
		return project;
	}
	
	public void CreateFile(String jsonRaw, String filedestination) throws IOException{
		// Parse JSON
		JsonFactory jFactory = new JsonFactory();
		JsonParser jparser = jFactory.createJsonParser(jsonRaw);
		
		ObjectMapper mapper = new ObjectMapper();
		Project result = mapper.readValue(jparser, Project.class);
		
		ProjectFile project = new ProjectFile();

		// Resource 
		for (me.smulyono.mppconverter.model.Resource res : result.getResources()){
			Resource newresource = project.addResource();
			newresource.setName(res.getName());
		}
		
		// Task
		for (me.smulyono.mppconverter.model.Task tsk : result.getTasks()){
			Task newtask= project.addTask();
			newtask.setName(tsk.getName());
			newtask.setActive(tsk.isActive());
			newtask.setMilestone(tsk.isMilestone());
		}
		
		
		ProjectWriter writer = new MPXWriter();
		writer.write(project, filedestination);
	}
}
