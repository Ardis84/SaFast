package safast.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import safast.utils.ClassPathManager;
import safast.utils.DbUtils;
import safast.utils.FileUtils;
import safast.utils.GridUtils;
import safast.utils.QueryUtils;
import safast.utils.SchedeUtils;
import safast.utils.TempUtils;
import safast.utils.TextUtils;

/**
 * Servlet implementation class ViewScheda
 */
public class ViewScheda extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static String redstar ="<img src='css/images/star_red.png' alt='Priorità' >";
	private static String yellowstar ="<img src='css/images/star_yellow.png' alt='Priorità' >";
	private static String greenstar ="<img src='css/images/star_green.png' alt='Priorità' >";
	private static String purplestar ="<img src='css/images/star_purple.png' alt='Priorità' >";
	private static String bluestar ="<img src='css/images/star_blue.png' alt='Priorità' >";
	private static String blackstar ="<img src='css/images/star_black.png' alt='Priorità' >";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewScheda() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		TempUtils tu = new TempUtils();
		String body="";
		
		String numeroscheda = (String)request.getParameter("numeroscheda");
		//String numerorichiesta = request.getParameter("numerorichiesta");
		
		QueryUtils qu = new QueryUtils();
		String qry = FileUtils.getQuery("cercaScheda.sql", request);
		qry = qu.setParam(qry,numeroscheda);
		//qry = qu.setParam(qry,numerorichiesta);
		
		String schedeImportanti="";
		String schede="";
		ResultSet res = DbUtils.executeQuery(qry);
			try {			
				int c=0;
				while(res.next()){
					String scheda = res.getString("scheda");
					Date datarichiesta = res.getDate("datarichiesta");
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
					String testo = res.getString("testo");
					String procedura = res.getString("procedura");
					String cliente = res.getString("cliente");
					String identifier = res.getString("identifier");
					String classes="";
					if(c%2==0){
						classes = " pari ";
					}else
						classes = " dispari ";
					c++;
					HashMap<String, Object> params = new HashMap<>();
					params.put("scheda", scheda);
					params.put("datarichiesta", sdf.format(datarichiesta));
					testo = TextUtils.htmlTesto(testo);
					params.put("testo", testo);
					params.put("procedura", procedura);
					params.put("id", identifier);
					params.put("cliente", cliente);				
					params.put("classes", classes);
					
					String path ="";
					path = request.getContextPath();
					params.put("path", path);
					
					//Controllo se ci sono allegati
					int numAllegati = res.getInt("numallegati");
					String allegati ="";
					if(numAllegati>0){
						 	
							allegati = "<img class='imgAllegati' onclick='viewAllegati(\""+identifier+"\",\""+path+"\")' src='css/images/attach.png'/>";
						
					}
					
					params.put("allegati", allegati);
					
					/* Controllo se la scheda è segnata come importante */
					boolean isImportant = SchedeUtils.checkSchedaImportante(scheda);
					//redstar = redstar.replaceAll("/", "\\\\");
//					if(isImportant)
//						params.put("important", redstar);
//					else
//						params.put("important", "");
//					
//					schede += tu.getBody("scheda.htm",params, request);
					if(isImportant){
						params.put("important", redstar);
						classes = " schedaImportant ";
						params.put("classes", classes);
						schede += tu.getBody("scheda.htm",params, request);
					}else{
						params.put("important", "");
						if(c%2==0){
							classes = " pari ";
						}else
							classes = " dispari ";
						c++;
						params.put("classes", classes);
						schede += tu.getBody("scheda.htm",params, request);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		body = tu.getContent();
		body = tu.setBookmark(body, "container", schede);
		//body = tu.setContent(body);
		body = tu.setTitle(body,"Dettaglio Scheda");
		
		out.write(body);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

}
