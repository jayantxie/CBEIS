package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.rmi.runtime.Log;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet(
		urlPatterns = { "/FirstServlet" }, 
		initParams = { 
				@WebInitParam(name = "reaper", value = "abc", description = "用户名")
		})
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FirstServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        String[] titles= new String[30];//标题数组
        String[] newsdates= new String[30];//日期数组
    	int i = 0;
    	
        response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式  
        PrintWriter pw = response.getWriter(); // 获取 response 的输出流 
        
        
        /* 这里我们做一个最简单的注册逻辑，当然，你的实际业务可以相当复杂 */ 
		
        try {  
            Connection connect = DBUtil.getConnect();  
            Statement statement = (Statement) connect.createStatement(); // Statement可以理解为数据库操作实例，对数据库的所有操作都通过它来实现 
            ResultSet result;  
            
            String sqlQuery = "select * from crawler_data order by newsdate desc";
        	result = statement.executeQuery(sqlQuery);
        	
            while(result.next()){
                titles[i] = result.getString("title"); //查询title值
                newsdates[i] = result.getString("newsdate");//查询newsdate值
                i++;
            }
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
          
         
        for(int j=0;j<i;j++){
        	pw.append(titles[j]+"\n");//一行一行相加输出，Android端分离
        	pw.append(newsdates[j]+"\n");
        }
        pw.flush();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
