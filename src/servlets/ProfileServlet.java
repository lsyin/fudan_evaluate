package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  //  final Logger log = LoggerFactory.getLogger(ProfileServlet.class);  
	static Logger log = Logger.getLogger(UploadReceiver.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//get方式是刚开始请求这个页面时就执行get方式
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("userName");
		//连接数据库
		Mongo mg = new Mongo(MongodbConfig.IP, MongodbConfig.PORT);
      	DB mongoDB = mg.getDB(MongodbConfig.DATABASE);
      	mongoDB.authenticate(MongodbConfig.USER, MongodbConfig.PASSWORD.toCharArray());
      	log.info("完善信息时连接数据库成功");
		DBCollection db_users = mongoDB.getCollection("coll_users");
		DBCursor cur = db_users.find(new BasicDBObject("userName", userName));
		
		if(cur.hasNext())
		{   //一大串的set
			DBObject item = cur.next();
			request.setAttribute("realName", item.get("realName").toString());
			request.setAttribute("emailAddress", item.get("emailAddress").toString());
			request.setAttribute("company", item.get("company").toString());
			request.setAttribute("telephone", item.get("telephone").toString());
			//String url = response.encodeRedirectURL(request.getContextPath()+"/profile.jsp");
			String url="profile.jsp";
			RequestDispatcher view = request.getRequestDispatcher(url);
			view.forward(request, response);
		}
		else
		{
			//不应该出现
			log.error("个人信息完善时出错");
			response.sendRedirect("_error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8") ;
		request.setCharacterEncoding("utf-8");		
		
		Mongo mg = new Mongo(MongodbConfig.IP, MongodbConfig.PORT);
      	DB mongoDB = mg.getDB(MongodbConfig.DATABASE);
      	mongoDB.authenticate(MongodbConfig.USER, MongodbConfig.PASSWORD.toCharArray());
    	log.info("连接数据库成功");
		DBCollection db_users = mongoDB.getCollection("coll_users");
		HttpSession session =  request.getSession();
		BasicDBObject updateCondition = new BasicDBObject();		
		updateCondition.put("userName", session.getAttribute("userName"));
		BasicDBObject updateValue = new BasicDBObject();
		updateValue.put("company", request.getParameter("company"));
		updateValue.put("emailAddress", request.getParameter("emailAddress"));
		updateValue.put("realName", request.getParameter("realName"));
		updateValue.put("telephone", request.getParameter("telephone"));
		BasicDBObject updateSetValue = new BasicDBObject("$set", updateValue);
		db_users.update(updateCondition, updateSetValue);
		log.info("个人信息更新完成");
		String url = response.encodeRedirectURL(request.getContextPath()+"/index.jsp");
        log.info("重定向的URL是："+url);
        response.sendRedirect(url);
		//response.sendRedirect("index.jsp");
	}

}
