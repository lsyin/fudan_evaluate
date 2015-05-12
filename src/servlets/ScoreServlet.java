package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Servlet implementation class ScoreServlet
 */
@WebServlet("/ScoreServlet")
public class ScoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
   // final Logger log = LoggerFactory.getLogger(ScoreServlet.class); 
	static Logger log = Logger.getLogger(UploadReceiver.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				
			   String type=request.getParameter("type");		
		       //连接数据库		
			   Mongo mg = new Mongo(MongodbConfig.IP, MongodbConfig.PORT);
		       DB mongoDB = mg.getDB(MongodbConfig.DATABASE);
		       mongoDB.authenticate(MongodbConfig.USER, MongodbConfig.PASSWORD.toCharArray());
		       log.info("连接数据库成功");
			   DBCollection db_users = mongoDB.getCollection("coll_score");
			  //查询全部分词的分数，结果按照fb1值降序排序
			   DBCursor cur = db_users.find(new BasicDBObject("type",type)).sort(new BasicDBObject("fb1",-1));			
			   List<DBObject> list=new ArrayList<DBObject>();
				if(cur.hasNext())
				{									
					  while(cur.hasNext())
					   {
						      list.add(cur.next());					      
					   }				  					  					 
					  log.info("mongodb中的分数表结果如下："+list.toString());  
					  //返回结果
					  response.setCharacterEncoding("UTF-8");//解决显示中文乱码问题  
		              response.getWriter().write(list.toString());  					
				}
				else
				{
					 response.getWriter().write("null"); 
					//不应该出现
					log.error(type+"分数表真的为空");
					//response.sendRedirect("_error.jsp");
				}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
