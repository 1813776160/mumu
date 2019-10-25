package http2;

import java.io.PrintWriter;

public class HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response){
	
		
		
		//response.sendRedirect("index.html");
		
	}
	
	public void doPut(HttpServletRequest request,HttpServletResponse response){
		
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response){
		
	}
	
	public void service(HttpServletRequest request,HttpServletResponse response){
		switch(request.getMethod()){
		case "GET": doGet(request,response);break;
		case "POST": doPost(request,response);break;
		case "PUT": doPut(request,response);break;
		
		}
	}
}
