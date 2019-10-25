package http2;

public class ForwardServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd =request.getReqeustDispatcher("/moshou.html");
		rd.forward(request,response);
	} 

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request,response);
	}
	
	
	
}
