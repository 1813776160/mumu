package http;

import java.io.IOException;
import java.io.OutputStream;

public class HttpServletResponse{
	private static WebXmlParser webXmlparse=new WebXmlParser("web.xml");
	private HttpServletRequest request;
	private OutputStream out;
	
	public HttpServletResponse(HttpServletRequest request,OutputStream out){
		
		super();
		this.request=request;
		this.out=out;
		
	}
		
		
		
	public void commit() throws IOException{
		
		String suffix =request.getRequestURL().substring(
							request.getRequestURL().lastIndexOf(".")+1
						);
		//从web.xml 文件中解析出contentType 
		String contentType=webXmlparse.getContentType(suffix);
		
		String resp="HTTP/1.1 200 OK\r\n";
		resp +="Content-Type:"+contentType+"\r\n";
		resp +="\r\n";
		out.write(resp.getBytes());
		
		String rootPath="E:/apache-tomcat-8.0.51/webapps";
		String filePath =request.getRequestURL();
		
		
		
		
	}
	
	
	
}
