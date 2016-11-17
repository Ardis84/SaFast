package safast.utils;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;


public class TempUtils {

	public static String html;
	public static String bodyPath;
	
	
	public static void setBookmark(String bkm, String replace){
		if(replace.contains("\\"))
			replace = replace.replaceAll("\\\\", "/");
		html = html.replaceAll("\\{v:"+bkm+"\\}", replace);
	}
	
	public static String setBookmark(String text, String bkm, String replace){
		if(replace.contains("\\"))
			replace = replace.replaceAll("\\\\", "/");
		if(text.contains("{v:"+bkm+"}"))
			text = text.replaceAll("\\{v:"+bkm+"\\}", replace);
		return text;
	}
	
	public static void setContent(String replace){
		setBookmark("container", replace);
	}
	
	
	public static String  setContent(String text, String replace){
		return setBookmark(text, "container", replace);
	}
	
	public static void setContent(String filename, HttpServletRequest request){		
		String body = FileUtils.getStrFile(bodyPath+filename, request);
		setContent(body);
	}
	
	public static void setTitle(String replace){
		setBookmark("title", replace);
	}
	
	public static String setTitle(String text,String replace){
		return setBookmark(text,"title", replace);
	}
	
	public static String getContent(){
		html = FileUtils.getStrFile(ClassPathManager.getPath()+"/WebContent/pages/content.htm");
		setBookmark("path", ClassPathManager.getPath()+"WebContent\\");
		return html;
	}
	
	public static String getBody(String filename, HashMap<String, Object> params, HttpServletRequest request){	
		bodyPath = ClassPathManager.getPath()+"/WebContent/pages/";
//		URL[] path = ((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs();
//		path.toString();
		String body = FileUtils.getStrFile(bodyPath+filename);
		Object[] keys = params.keySet().toArray();
		for(int i=0; i<params.size();i++){
			String key = (String) keys[i];
			Object value = params.get(key);
			if(value==null)
				value = "";
			body = setBookmark(body, key, value.toString());
		}
		return body;
	}
}
