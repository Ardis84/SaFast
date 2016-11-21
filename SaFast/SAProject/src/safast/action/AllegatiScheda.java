package safast.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Blob;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import safast.utils.ClassPathManager;
import safast.utils.DbUtils;

/**
 * Servlet implementation class AllegatiScheda
 */
public class AllegatiScheda extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllegatiScheda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String scarica = request.getParameter("scarica");
		
		if(scarica!=null && scarica.equals("0")){
			String dialog="<ul>";
			String progressivo = request.getParameter("progressivo");
			String qry = "select * from allegati where IDSCHEDAANALISI ="+progressivo;
			DbUtils du = new DbUtils();
			try{
				ResultSet res = du.executeQuery(qry);
				while(res.next()){
					String nomefile = res.getString("nomefile");
					String idallegati = res.getString("idallegati");
					
					String path ="";
					path = request.getContextPath();
					
					//Che estensione ha il file
					String[] filePart = nomefile.split("\\.");
					String ext = filePart[filePart.length-1];
					String eccezzioni = "sql,htm,html,php,asp,txt,jpg,jpeg,gif,png,bmp,pdf";
					String target="";
					if(eccezzioni.contains(ext)){
						target = "target='_blank'";
					}else{
						target="";
					}

					dialog += "<li class='allegatiUl' onclick='downloadAllegati(\""+idallegati+"\",\""+path+"\")'>"
							+nomefile
							+ "</li>"
							+ "<a href='allegati/"+nomefile+"' id='"+nomefile.trim()+"' "+target+">"
							+"</a>";
				
					
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			out.write(dialog+"</ul>");
		}else{
			String idallegati = request.getParameter("idallegati");
			String qry = "select * from allegati where idallegati ="+idallegati;
			DbUtils du = new DbUtils();
			try{
				ResultSet res = du.executeQuery(qry);
				while(res.next()){
					Blob filescan 	= res.getBlob("filescan");
					String nomefile = res.getString("nomefile");
					
					String path = ClassPathManager.getPath();
					
					InputStream in = filescan.getBinaryStream();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
//					File f = new File(path+"WebContent/allegati/"+nomefile);
//					f.createNewFile();
					String url = path+"/WebContent/allegati/"+nomefile;
					FileOutputStream outputStream = new FileOutputStream(url);
					
					int bufferSize = 1024;
			        int length = (int) filescan.length();
			 
			        byte[] buffer = new byte[bufferSize];
			 
			        while((length = in.read(buffer)) != -1)
			        {
			        	bos.write(buffer,0,length);
			        }
			        bos.writeTo(outputStream);
			        in.close();
			        
			        outputStream.close();
			        
			        out.write(nomefile.trim());
			        
					/*
					InputStream inputStream = filescan.getBinaryStream();
	                int fileLength = inputStream.available();
	                 
	                System.out.println("fileLength = " + fileLength);
	 
	                ServletContext context = getServletContext();
	 
	                // sets MIME type for the file download
	                String mimeType = context.getMimeType(nomefile);
	                if (mimeType == null) {        
	                    mimeType = "application/octet-stream";
	                }              
	                 
	                // set content properties and header attributes for the response
	                response.setContentType(mimeType);
	                response.setContentLength(fileLength);
	                String headerKey = "Content-Disposition";
	                String headerValue = String.format("attachment; filename=\"%s\"", nomefile);
	                response.setHeader(headerKey, headerValue);
	 
	                // writes the file to the client
	                OutputStream outStream = response.getOutputStream();
	                 
	                byte[] buffer = new byte[BUFFER_SIZE];
	                int bytesRead = -1;
	                 
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    outStream.write(buffer, 0, bytesRead);
	                }
	                 
	                inputStream.close();
	                outStream.close();*/
			        
				}
			}catch (Exception e) {
				System.out.println(e);
			}
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
