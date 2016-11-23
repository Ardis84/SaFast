package safast.action;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import safast.utils.ClassPathManager;
import safast.utils.DbUtils;
import safast.utils.FileUtils;
import safast.utils.QueryUtils;
import safast.utils.SchedeUtils;
import safast.utils.TempUtils;
import safast.utils.TextUtils;

/**
 * Servlet implementation class ElencoSchedePersonale
 */
public class ElencoSchedeTutte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static String redstar ="<img src='css/images/star_red.png' alt='Priorit�' >";
	private static String yellowstar ="<img src='css/images/star_yellow.png' alt='Priorit�' >";
	private static String greenstar ="<img src='css/images/star_green.png' alt='Priorit�' >";
	private static String purplestar ="<img src='css/images/star_purple.png' alt='Priorit�' >";
	private static String bluestar ="<img src='css/images/star_blue.png' alt='Priorit�' >";
	private static String blackstar ="<img src='css/images/star_black.png' alt='Priorit�' >";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElencoSchedeTutte() {
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
		String body="";
		
		QueryUtils qu = new QueryUtils();
		String qry = FileUtils.getQuery("elencoSchede_tutte.sql", request);
		
		String schede="";
		String schedeImportanti="";
		ResultSet res = DbUtils.executeQuery(qry);
			try {			
				int c=0;
				while(res.next()){
					String idschedeanalisi 	= res.getString("idschedeanalisi");
					String scheda 			= res.getString("scheda");
					Date datarichiesta 		= res.getDate("datarichiesta");
					SimpleDateFormat sdf 	= new SimpleDateFormat("dd/MM/yyyy");	
					String testo 			= res.getString("testo");
					String procedura 		= res.getString("procedura");
					String cliente 			= res.getString("cliente");
					String identifier 		= res.getString("identifier");
					String classes="";
					
					HashMap<String, Object> params = new HashMap<>();
					params.put("idschedeanalisi", idschedeanalisi);
					params.put("scheda", scheda);
					params.put("datarichiesta", sdf.format(datarichiesta));
					if(testo!=null && !testo.equals(""))
						testo = TextUtils.htmlTesto(testo);
					params.put("testo", testo);
					params.put("procedura", procedura);
					params.put("cliente", cliente);
					params.put("id", identifier);
					
					String path ="";
					path = request.getContextPath();
					params.put("path", path);
					/* Controllo se la scheda � segnata come importante */
					boolean isImportant = SchedeUtils.checkSchedaImportante(scheda);
					//redstar = redstar.replaceAll("/", "\\\\");
										
					//Controllo se ci sono allegati
					int numAllegati = res.getInt("numallegati");
					String allegati ="";
					if(numAllegati>0){
						 	
							allegati = "<img class='imgAllegati' onclick='viewAllegati(\""+identifier+"\",\""+path+"\")' src='css/images/attach.png'/>";
						
					}
					
					params.put("allegati", allegati);
					
					if(isImportant){
						params.put("important", redstar);
						classes = " schedaImportant ";
						params.put("classes", classes);
						schedeImportanti += tu.getBody("scheda.htm",params, request);
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
		body = tu.setBookmark(body, "container", schedeImportanti+" "+ schede);
		//body = tu.setContent(body);
		body = tu.setTitle(body,"Dettaglio Scheda");
		
		
		out.write(body);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}