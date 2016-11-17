package safast.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

public class GridUtils {

	public static String createGrid(String filename, HttpServletRequest request){
		String grid="";
		String qry = FileUtils.getQuery(filename, request);
		ResultSet rs = DbUtils.executeQuery(qry);
		try {
			ResultSetMetaData md = rs.getMetaData();
			String t1="<table class='saGrid'><tr>";
			for(int i = 1;i<=md.getColumnCount();i++){
				String nameField = md.getColumnName(i);
				t1 += "<th>"+nameField+"</th>";
			}
			t1 += "</tr>";
			
			String t2 = "";
			while(rs.next()) {
				t2 += "<tr>";
				for(int i = 1;i<=md.getColumnCount();i++){
					Object itm = DbUtils.getRsElement(md.getColumnTypeName(i), rs, i);
					t2 += "<td>"+itm+"</td>";
				}
				t2 += "</tr>";
			}
			t2 += "</table>";
			
			grid += t1+t2;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return grid;
	}
	
}
