package com.Send;
import com.client.Restclient;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class transervlet
 */
@WebServlet("/transervlet")
public class transervlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transervlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String addr = request.getParameter("address");
		String msg = request.getParameter("msg");
		Restclient client = new Restclient();
		if (client.ValidateEmailAddress(addr)) 
		{
			if(client.SendEmail(addr, msg)) 
			{
				//response.sendRedirect(request.getContextPath()+"/res.jsp");
				request.setAttribute("message", "发送成功<br>");
				request.getRequestDispatcher("/default.jsp").forward(request, response);
			}
			else 
			{
				request.setAttribute("message", "发送失败<br>");
				request.getRequestDispatcher("/default.jsp").forward(request, response);
			}
		}
		else
		{
			request.setAttribute("message", "地址格式错误<br>");
			request.getRequestDispatcher("/default.jsp").forward(request, response);
		}
		//doGet(request, response);
	}

}
