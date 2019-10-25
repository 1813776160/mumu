package http2;

import java.io.PrintWriter;

public class RedirectServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 
		 */
		
		
		response.sendRedirect("/moshou.html");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}

}
