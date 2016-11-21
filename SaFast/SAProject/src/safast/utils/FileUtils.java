package safast.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class FileUtils {

	public static String getQuery(String name, HttpServletRequest request){
		BufferedReader br;
		String doc="";
		String line;
		try {
			br = new BufferedReader(new FileReader(request.getRealPath("")+"/sql/"+name));
			while ((line = br.readLine()) != null) {
				doc += line+"\n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public static String getStrFile(String percorso, HttpServletRequest request){
		BufferedReader br;
		String doc="";
		String line;
		try {
			br = new BufferedReader(new FileReader(request.getRealPath("")+percorso));
			while ((line = br.readLine()) != null) {
				doc += line+"\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public static String getStrFile(String percorso){
		BufferedReader br;
		String doc="";
		String line;
		try {
			br = new BufferedReader(new FileReader(percorso));
			while ((line = br.readLine()) != null) {
				doc += line+"\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public static void scriviFile(String percorso, String item, boolean isItemToDelete) throws IOException{
		String contenuto="";
		if(!isItemToDelete){
			File f = new File(percorso);
			if(!f.exists())
				f.createNewFile();
			else
				contenuto = getStrFile(percorso);
			if(!contenuto.contains(item)){
				FileOutputStream fos = new FileOutputStream(f);
				contenuto += item;
				fos.write(contenuto.getBytes());
				fos.close();
			}
		
		}else if(isItemToDelete){
			BufferedReader br;
			String doc="";
			String line;
			try {
				br = new BufferedReader(new FileReader(percorso));
				while ((line = br.readLine()) != null) {
					if(!line.contains(item))
						doc += line+"\n";
				}
				File f = new File(percorso);
				FileOutputStream fos = new FileOutputStream(f);
				contenuto += item;
				fos.write(doc.getBytes());
				fos.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}
