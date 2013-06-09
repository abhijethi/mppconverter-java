package me.smulyono.mppconverter.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import me.smulyono.mppconverter.model.Project;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.force.api.ApiSession;
import com.force.api.ForceApi;
import com.force.sdk.oauth.context.ForceSecurityContextHolder;
import com.force.sdk.oauth.context.SecurityContext;

/**
 * 
 * Class will be creating Rest Authenticated request will be utilizing ForceAPI
 * for any simple rest request (which supported) will utilized the session
 * header to create manual REST request 
 * 
 * @author Sanny M - http://smulyono.github.io/
 * 
 */
@Service
public class SforceRestService {
	static Logger logger = LoggerFactory.getLogger(SforceRestService.class);

	final String SF_VERSION = "v27.0";
	final String SF_ENDPOINT_URL = "/services/data/" + SF_VERSION;

	private ApiSession session;

	public SforceRestService() {}
	
	/** 
	 * @param type
	 * @return REST URL Endpoint for Standard Salesforce Object
	 */
	private String getSFObjectUrl(String type) {
		return session.getApiEndpoint() + SF_ENDPOINT_URL + "/sobjects/" + type;
	}
	
	/** 
	 * @param type
	 * @return REST URL Endpoint for Standard Salesforce Apex REST Class
	 */
	private String getSFApexURL(String endpoint){
		return session.getApiEndpoint() + "/services/apexrest/" + endpoint;
	}
	
	/** 
	 * @param type
	 * @return URL Endpoint for Standard Salesforce Object Page
	 */
	public String createObjectURL(String objectId){
		getForceApi();
		String retval = objectId;
		if (objectId != null){
			return session.getApiEndpoint() + "/" + objectId;
		} 
		return retval;
	}
	
	public String createPageURL(String apexpage){
		return createPageURL(apexpage, null);
	}
	
	/**
	 * 
	 * @param apexpage
	 * @param objectid
	 * @return URL for Apex Page
	 */
	public String createPageURL(String apexpage, String objectid){
		getForceApi();
		String returnURL = session.getApiEndpoint() + "/apex/" + apexpage;
		if (objectid != null){
			returnURL +=  "?id=" + objectid;
		}
		return returnURL;
	}
	
	/**
	 * Initiate a REST call to Apex REST Webservice, to retrieve the project
	 *  information in JSON format
	 *  
	 * @param projectId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public Project findProjectInfo(String projectId) throws HttpException, IOException{
		getForceApi();
		
		HttpClient httpclient = new HttpClient();
		// Prepare the GET Request with the headers
		GetMethod getMethod = new GetMethod(getSFApexURL("exportproject"));
		// utilize the access token which already obtained
		getMethod.setRequestHeader("Authorization", "OAuth " + session.getAccessToken());
		getMethod.addRequestHeader("X-PrettyPrint", "1");
		
		// Prepare the parameters
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("projectid", projectId);
		getMethod.setQueryString(params);
		
		int statuscode = httpclient.executeMethod(getMethod);
		if (statuscode == HttpStatus.SC_OK){
			// convert result to JSON, utilize Jackson for this
			ObjectMapper mapper = new ObjectMapper();
			if (getMethod.getResponseBodyAsStream() != null){
				InputStream rawJson = getMethod.getResponseBodyAsStream();
				Project result = mapper.readValue(rawJson, Project.class);
				return result;
			} else {
				logger.error(">>> no result <<< ");
			}
		}
		// if comes here then something is wrong so will return null
		return null;
	}
	
	/**
	 * Initiate REST API Call to send Binary file into Project Record
	 * 
	 * @param filename
	 * @param newfile
	 * @param parentId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String saveAttachments(String filename, File newfile, String parentId)
			throws HttpException, IOException, JSONException {
		getForceApi();
		
		// generated Attachment ID
		String generatedID = null;
		
		HttpClient httpclient = new HttpClient();

		// Read the file
		byte[] data = IOUtils.toByteArray(new FileInputStream(newfile)); 
		// Prepare the parameter with file information
		JSONObject content = new JSONObject(); 
		content.put("Name", filename); 
		content.put("Description", "Generated from mppconverter"); 
		content.put("Body", new String(Base64.encodeBase64(data))); 
		content.put("ParentId", parentId);

		final PostMethod postMethod = new PostMethod(
				getSFObjectUrl("Attachment"));
		try {
			// send the call with JSON parameter
			postMethod.setRequestEntity(new StringRequestEntity(content.toString(), "application/json", null));
			// fill in the oauth header
			postMethod.setRequestHeader("Authorization",
					"OAuth " + session.getAccessToken());
			postMethod.addRequestHeader("X-PrettyPrint", "1");
			httpclient.getParams().setSoTimeout(60000);
			int returncode = httpclient.executeMethod(postMethod);
			
			if (returncode == HttpStatus.SC_CREATED){
				// retrieve the json response
				JSONObject response = new JSONObject(new JSONTokener(
						new InputStreamReader(postMethod.getResponseBodyAsStream())));
				// make sure that success response is received
				if (response.getBoolean("success")) {
					// based on the documentation, it will return ID of the attachment
					generatedID = response.getString("id"); 
				}			
			}
		} finally {
			postMethod.releaseConnection();
		}
		return createObjectURL(generatedID);
	}

	public ForceApi getForceApi() {
		SecurityContext sc = ForceSecurityContextHolder.get();

		session = new ApiSession();
		session.setAccessToken(sc.getSessionId());
		session.setApiEndpoint(sc.getEndPointHost());

		return new ForceApi(session);
	}

}
