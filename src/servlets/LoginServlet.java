package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.apache.log4j.Logger;

import util.MongodbConfig;
import actions.UploadReceiver;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   // final Logger log = LoggerFactory.getLogger(LoginServlet.class);
	static Logger log = Logger.getLogger(UploadReceiver.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		request.setCharacterEncoding("utf-8") ;
		//获取用户名
		String userName = request.getParameter("userName") ;
		String passWord = request.getParameter("passWord") ;
		log.info("用户"+userName+"登录");
		Mongo mg = new Mongo(MongodbConfig.IP, MongodbConfig.PORT);
      	DB mongoDB = mg.getDB(MongodbConfig.DATABASE);
      	mongoDB.authenticate(MongodbConfig.USER, MongodbConfig.PASSWORD.toCharArray());
      	log.info("数据库连接成功");
		DBCollection persons = mongoDB.getCollection("coll_users");
		BasicDBObject query = new BasicDBObject();
		query.put("userName", userName);
		query.put("passWord", passWord);
		DBCursor cur = persons.find(query);
		
		HttpSession session =  request.getSession();
		log.info("session的值是："+session.getId());
		
		if(cur.hasNext())
		{
			log.info("验证通过");		
			session.setAttribute("userName", userName);			
		//	response.encodeRedirectURL("index.jsp");
		//	response.sendRedirect("index.jsp");
			String url = response.encodeRedirectURL(request.getContextPath()+"/index.jsp");
	        log.info("重定向的URL是："+url);
	        response.sendRedirect(url);
			
		}
		else
		{
			log.info("验证失败,用户名与密码不匹配");
			session.setAttribute("message", "Sorry!The user name  doesn't match the password");
			response.sendRedirect("login.jsp");
		}
		
	}

}
