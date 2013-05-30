package me.smulyono.mppconverter.service;

import java.io.IOException;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.reader.ProjectReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConverterService {
	static Logger logger = LoggerFactory.getLogger(ConverterService.class); 
	
	public static final String MPP_TYPE = "mpp";
	public static final String MPX_TYPE = "mpx";
	
	public ProjectFile ConvertFile (MultipartFile file, String filetype) throws MPXJException, IOException{
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
		ProjectFile project = reader.read(file.getInputStream());
		
		return project;
	}
}
