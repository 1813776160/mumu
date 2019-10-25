package http2;

import java.io.PrintWriter;

public class GetCookieServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 
		 */
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWrite();
		pw.print("<h1>测试cookie 值</h1>");
		
		Cookie[] cookies =request.getCookies();
		if(cookies!=null){
			for(Cookie c:cookies){
				pw.print(c.getName()+"="+c.getValue()+"<br>");
			}
		}
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}

	
}
