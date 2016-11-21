package safast.utils;

import java.io.File;
import java.io.IOException;

public class SchedeUtils {

	public SchedeUtils() {
		// TODO Auto-generated constructor stub
	}

	public static boolean checkSchedaImportante(String numscheda) throws IOException {
		boolean isImportant=false;
		String path = ClassPathManager.getPath();
		File f = new File(path+"/WebContent/info/schede_importanti.txt");
		if(!f.exists())
			f.createNewFile();
		String schede = FileUtils.getStrFile(path+"/WebContent/info/schede_importanti.txt");
		if(schede.contains(numscheda))
			isImportant = true;		
		return isImportant;		
	}
	
	public static void  insertSchedaImportante(String numsch) throws IOException{
		String path = ClassPathManager.getPath();
		String percorso = path+"/WebContent/info/schede_importanti.txt";
		FileUtils.scriviFile(percorso, "--"+numsch, false);

	}

	public static  void deleteSchedaImportante(String numsch) throws IOException {
		String path = ClassPathManager.getPath();
		String percorso = path+"/WebContent/info/schede_importanti.txt";
		FileUtils.scriviFile(percorso, "--"+numsch, true);	
	}

}
