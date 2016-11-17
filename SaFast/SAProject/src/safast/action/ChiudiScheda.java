package safast.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import safast.utils.DbUtils;
import safast.utils.FileUtils;
import safast.utils.QueryUtils;
import safast.utils.TempUtils;

/**
 * Servlet implementation class ChiudiScheda
 */
public class ChiudiScheda extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChiudiScheda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		//PrintWriter out = response.getWriter();
		
		//TempUtils tu = new TempUtils();
		
		//QueryUtils qu = new QueryUtils();
		//String qry = FileUtils.getQuery("elencoSchede.sql", request);
		
		String numsch = request.getParameter("numsch");
		String chiudi = request.getParameter("chiudi");
//		if(chiudi!=null && chiudi.equals("0")){
//			HashMap<String, Object> params = new HashMap<>();
//			params.put("numscheda", numsch);
//			@SuppressWarnings("static-access")
//			String body = tu.getBody("chiudiScheda.htm",params, request);
//			out.println(body);
//		}else 
			String info 	= request.getParameter("info");
			String ore 		= request.getParameter("ore");
			String testo 	= request.getParameter("testo");
			
			String[] ns = numsch.split("/");
			
			try {
				String qry1 = "select IDRICHIESTECLIENTE  as idrichiesta, "
							+ " progressivo , numerorichiesta"
							+ " from schedeanalisi sa, richiesteclienti rc "
							+ " where sa.PROGRESSIVO = rc.IDSCHEDAANALISI"
							+ " and sa.NUMEROSCHEDA = "+ns[0]
							+ " and rc.numerorichiesta = "+ns[1];
				ResultSet res = DbUtils.executeQuery(qry1);
				String idrichiesta		="";
				String idschedeanalisi	="";
				String numerorichiesta 	= "";
				
				while(res.next()){
					idrichiesta 	= res.getString("idrichiesta");
					idschedeanalisi	= res.getString("progressivo");
					numerorichiesta	= res.getString("numerorichiesta");
				}
				//Oggi
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				String oggi = dateFormat.format(date);
				Object minuti = ore!=null && !ore.equals("")?(Integer.valueOf(ore)*60)+"":1;
				if(testo!=null && !testo.equals("")){
					testo	= testo.replaceAll("'", "''");
					testo	= testo.replaceAll("<br>", "\n");
				}
				String updateQry 	= "update richiesteclienti "
									+ " set descrizione='"+testo+"',"
									+ " minuti="+minuti+""
									+ " where IDRICHIESTECLIENTE ="+idrichiesta;
				DbUtils.executeQuery(updateQry);
				if (chiudi!=null && chiudi.equals("1")){
					String updateQry2	= "update ANALISISVOLTE"
										+ " set CONVALIDADATAORA = to_date('"+oggi+"','dd/mm/yyyy')"
										+ " where IDSCHEDAANALISI = "+idschedeanalisi
										+ " and numeroanalisi = "+numerorichiesta;
					
					DbUtils.executeQuery(updateQry2);
					
					if(info!=null && !info.equals("")){
						info	= info.replaceAll("'", "''");
						info	= info.replaceAll("<br>", "\n");
					}
					String updateQry3	= "update RAPPORTIATTIVITA"
							+ " set note = '"+info+"',"
							+ " minuti="+minuti+""
							+ " where idrichieste= "+idrichiesta;
		
					DbUtils.executeQuery(updateQry3);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
