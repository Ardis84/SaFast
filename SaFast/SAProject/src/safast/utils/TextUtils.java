package safast.utils;

public class TextUtils {

	public TextUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String htmlTesto(String testo) {
		String newText ="";
		if(testo!=null && !testo.equals("")){
			String[] lines = testo.split(System.getProperty("line.separator"));
			for (int i = 0; i < lines.length; i++) {
				String ln = lines[i];
				newText += ln+"<br>";
			}
			if(lines.length<2){
				newText="";
				lines = testo.split("\n");
				for (int i = 0; i < lines.length; i++) {
					String ln = lines[i];
					newText += ln+"<br>";
				}
			}
		}
		return newText;		
	}

}
