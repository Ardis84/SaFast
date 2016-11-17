package safast.utils;

import java.io.File;

public class ClassPathManager {

	
	public static String getPath(){
		String path = ClassPathManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return path.substring(1, path.indexOf("SaFast"))+"SaFast";
	}
	
}
