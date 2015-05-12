package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.http.HttpSession;




import org.apache.log4j.Logger;

import util.User;
import util.MongodbConfig;
import actions.UploadReceiver;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  // final Logger log = LoggerFactory.getLogger(RegisterServlet.class);    
	static Logger log = Logger.getLogger(UploadReceiver.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=utf-8") ;
		request.setCharacterEncoding("utf-8");
		User user;
		try 
		{
			log.info("用户注册");
			user = getRegisterUser(request);			
			Mongo mg = new Mongo(MongodbConfig.IP, MongodbConfig.PORT);
	      	DB mongoDB = mg.getDB(MongodbConfig.DATABASE);
	      	mongoDB.authenticate(MongodbConfig.USER, MongodbConfig.PASSWORD.toCharArray());
	      	log.info("数据库连接成功");
			DBCollection db_users = mongoDB.getCollection("coll_users");			
			DBCursor cur = db_users.find(new BasicDBObject("userName", user.get("userName")));			
			HttpSession session =  request.getSession();
			if(cur.hasNext())
			{
				log.warn("用户名已存在");
				session.setAttribute("message", "Sorry,the username already exist");
				String url = response.encodeRedirectURL(request.getContextPath()+"/register.jsp");
		        log.info("重定向的URL是："+url);
		        response.sendRedirect(url);
			//	response.sendRedirect("register.jsp");
			}
			else
			{//可以进行注册
				db_users.insert(user);
				log.info("注册成功");
				session.setAttribute("message", "sign up sucess!");
				//各种获得session，后期补上//
				String url = response.encodeRedirectURL(request.getContextPath()+"/login.jsp");
		        log.info("重定向的URL是："+url);
		        response.sendRedirect(url);
				
				//response.sendRedirect("login.jsp");
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}				
	}

	private User getRegisterUser(HttpServletRequest request) throws Exception{
		User user = new User();
		user.setUserName(request.getParameter("userName"));
		user.setPassWord(request.getParameter("passWord"));
		user.setCompany(request.getParameter("company"));
		user.setEmailAddress(request.getParameter("emailAddress"));
		user.setRealName("");
		user.setTelephone("");
		user.setLevel(0);
	
		//其实类好像还是有点用的，只是作用不详。。。
		user.put("userName", request.getParameter("userName"));
		user.put("passWord", request.getParameter("passWord"));
		user.put("company", request.getParameter("company"));
		user.put("emailAddress", request.getParameter("emailAddress"));
		user.put("realName", "");
		user.put("telephone", "");
		user.put("level", 0);		
		return user;
	}
}
