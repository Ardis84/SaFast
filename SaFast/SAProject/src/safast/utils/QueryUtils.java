package safast.utils;

public class QueryUtils {
	
	public static String query;
	
	public static void setQuery(String qry){
		query = qry;
	}
	
	public static void setParam(String param){
		if(param ==null  || param.equals("") )
			param = "null";
		query = query.replace("?", param);
	}
	
	public static String setParam(String qry, String param){
		if(param ==null  || param.equals("") )
			param = "null";
		qry = qry.replace("?", param);
		return qry;
	}
	
	public static String getFinalSql(){
		return query;
	}

}
