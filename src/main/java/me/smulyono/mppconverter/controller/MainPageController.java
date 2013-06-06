package me.smulyono.mppconverter.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import me.smulyono.mppconverter.model.FileUploadForm;
import me.smulyono.mppconverter.model.Project;
import me.smulyono.mppconverter.service.ConverterService;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainPageController {
	static Logger logger = LoggerFactory.getLogger(MainPageController.class);
	// Directory where the new file will be living / created
	static final String TEMP_PATH = "/tmp/";
	
	@Autowired
	ConverterService mppconverter;
	
    /*
     * To allow CORS 
     */
    private void CorsActivation(HttpServletResponse resp){
		resp.addHeader("Access-Control-Allow-Origin", "*");
    }
	
	
	@RequestMapping(value="/")
	public String IndexPage(Model model) {
		fillDefault(model);
		return "index";
	}
	
	/*
	 * Receive the form to create JSON representation of Project File
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public Project saveFile(
				@ModelAttribute("uploadForm") FileUploadForm uploadform,
				Model model){
		fillDefault(model);
		model.addAttribute("uploaded_file", uploadform.getFile().getOriginalFilename());
		Project result = new Project();
		try {
			ProjectFile project = mppconverter.ConvertFile(uploadform.getFile().getInputStream(), ConverterService.MPP_TYPE);
			model.addAttribute("project_info", project.getProjectHeader().getProjectTitle());
			result = new Project(project);
		} catch (MPXJException ex){
			logger.error("MPXJ Error :: " + ex.getMessage());
		} catch (IOException ex){
			logger.error("IOException Error :: " + ex.getMessage());
		}
		
		return result;
	}
	
	/*
	 * Convert File from MPP Project File
	 */
	@RequestMapping(value="/convertmpp", method=RequestMethod.POST)
	@ResponseBody
	public Project convertmpp(@RequestParam("file") MultipartFile mpfile, HttpServletResponse resp){
		CorsActivation(resp);
		Project result = new Project();
		try {
			ProjectFile project = mppconverter.ConvertFile(mpfile.getInputStream(), ConverterService.MPP_TYPE);
			result = new Project(project);
		} catch (MPXJException ex){
			ex.printStackTrace();
			logger.error("MPXJ Error :: " + ex.getMessage());
		} catch (IOException ex){
			logger.error("IOException Error :: " + ex.getMessage());
		}
		
		return result;
	}
	
	/*
	 * Create new MPX File from JSON Parameter in Body
	 * will create new file with the format
	 *	result-{timestamp}.mpx
	 *  result-{projectid}.mpx
	 */
	@RequestMapping(value="/creatempx", method=RequestMethod.POST)
	@ResponseBody
	public File creatempx(@RequestBody Project result, Model model, HttpServletResponse resp){
		return creatempx_with_params(result, null, model, resp);
	}
	
	@RequestMapping(value="/creatempx/{projectid}", method=RequestMethod.POST)
	@ResponseBody
	public File creatempx_with_params(@RequestBody Project result, 
						  @PathVariable("projectid") String projectid, 
						  Model model, HttpServletResponse resp){
		CorsActivation(resp);
		String fileDestination = "/tmp/result.mpx";
		if (projectid != null){
			fileDestination = "/tmp/proejct-" + projectid + ".mpx";
		} else {
			fileDestination = "/tmp/project-" + new Date().getTime() + ".mpx";
		}
		File newfile = new File(fileDestination);
		
		try {
			newfile = mppconverter.CreateFile(result, fileDestination);
		} catch (IOException ex){
			ex.printStackTrace();
			logger.error("IOException Error :: " + ex.getMessage());
		}
		logger.info(">>> MPX FILE GENERATED!");
		return newfile;
	}
	
	private void fillDefault(Model model){
		model.addAttribute("title", "MPP/X Converter in Heroku");
		model.addAttribute("subtitle", "MPP/X Converter to JSON");
	}
}
