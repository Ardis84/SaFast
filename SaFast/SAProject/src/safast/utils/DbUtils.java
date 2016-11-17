package safast.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import sun.text.resources.cldr.FormatData;

public class DbUtils {

	public static Connection getConn(){
		Connection conn = null;
		try {
			Class.forName ("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@srvdipora11.dedalus.lan:1521:deddip11", "schedeanalisi", "sa2009sa");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;	
	}
	
	public static ResultSet executeQuery(String qry){
		Connection conn = getConn();
		Statement st;
		ResultSet rs=null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static Object getRsElement(String type, ResultSet rs, int i){
		Object itm = "";
		
		try {
			if(rs.getString(i)!=null){
				if(type.equals("DATE")){
					Date d = rs.getDate(i);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					itm = sdf.format(d);			
				}else if(type.equals("NUMBER")){
						itm = rs.getInt(i);
				}else{
						itm = rs.getString(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return itm;
	}
	
	public static Object getRsElement(ResultSet rs, int i){
		Object itm = "";
		
		String type="";
		try {
			type = rs.getMetaData().getColumnTypeName(i);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			if(rs.getString(i)!=null){
				if(type.equals("DATE")){
					Date d = rs.getDate(i);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					itm = sdf.format(d);			
				}else if(type.equals("NUMBER")){
						itm = rs.getInt(i);
				}else{
						itm = rs.getString(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return itm;
	}
	
	public static Object getRsElement(ResultSet rs, String name){
		Object itm = "";
		int i =0;
		String type="";
		try {
			ResultSetMetaData md = rs.getMetaData();
			for (int j = 0; j < md.getColumnCount(); j++) {
				if(md.getColumnName(j).equals(name)){
					type = md.getColumnTypeName(j);
					i=j;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			if(rs.getString(i)!=null){
				if(type.equals("DATE")){
					Date d = rs.getDate(i);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					itm = sdf.format(d);			
				}else if(type.equals("NUMBER")){
						itm = rs.getInt(i);
				}else{
						itm = rs.getString(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return itm;
	}

	private static boolean checkNull(Object itm) {
		boolean ret=false;
		if(itm == null)
			ret = true;
		return ret;
	}
}
