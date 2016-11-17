package safast.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
public class ModificaScheda extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaScheda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		TempUtils tu = new TempUtils();
		
		QueryUtils qu = new QueryUtils();
		String qry = FileUtils.getQuery("elencoSchede.sql", request);
		
		String numsch = request.getParameter("numsch");
		HashMap<String, Object> params = new HashMap<>();
		params.put("numscheda", numsch);
		
		String[] ns = numsch.split("/");
		
		try {
			String qry1 = "select descrizione from schedeanalisi sa, richiesteclienti rc "
						+ " where sa.PROGRESSIVO = rc.IDSCHEDAANALISI"
						+ " and sa.NUMEROSCHEDA = "+ns[0]
						+ " and rc.numerorichiesta = "+ns[1];
			ResultSet res = DbUtils.executeQuery(qry1);
			String testoScheda="";
			
			while(res.next()){
				testoScheda = res.getString("descrizione");
			}
			
			params.put("testoScheda", testoScheda);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		String body = tu.getBody("modificaScheda.htm",params, request);
		out.println(body);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
