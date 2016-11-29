package safast.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import safast.utils.DbUtils;
import safast.utils.FileUtils;
import safast.utils.QueryUtils;
import safast.utils.TempUtils;

/**
 * Servlet implementation class ElencoPersonale
 */
public class ElencoPersonale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElencoPersonale() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String qry = FileUtils.getQuery("elencoPersonale.sql", request);

		String select="<select id='selPersonale' name='selPersonale'>\n";
		ResultSet res = DbUtils.executeQuery(qry);
			try {			
				int c=0;
				while(res.next()){
					String idpersonale 	= res.getString("idpersonale");
					String personale 	= res.getString("personale");
					select += "<option value='"+idpersonale+"'>"+personale+"</option>\n";
				}
			}catch (Exception e) {
				System.out.println(e);
			}
		select += "</select>";
		String label = "<label>Seleziona l'utente</label>\n";
		out.write(label+select);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
