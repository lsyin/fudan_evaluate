package actions;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import servlets.LoginServlet;
import util.Evaluate;
import util.MongodbConfig;
import util.Score;
import util.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UploadReceiver extends HttpServlet
{
	
	private static final File UPLOAD_DIR = new File("test/uploads");
    private static File TEMP_DIR = new File("test/uploadsTemp");

    private static String CONTENT_LENGTH = "Content-Length";
    private static int SUCCESS_RESPONSE_CODE = 200;
    
    private String filePath=null; //保存待评测文件路径
    private String savePath=null; //保存待评测文件路径
    private String dictPath=null;  //词典文件路径
    private String basisPath=null;
  
    private Map<String, String> params=new HashMap<String, String>();
   // final Logger log = LoggerFactory.getLogger(UploadReceiver.class); 
	static Logger log = Logger.getLogger(UploadReceiver.class);

    @Override
    public void init() throws ServletException
    {
        UPLOAD_DIR.mkdirs();
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String uuid = req.getPathInfo().replaceAll("/", "");

        handleDeleteFileRequest(uuid, resp);
    }

    private void handleDeleteFileRequest(String uuid, HttpServletResponse resp) throws IOException
    {
        FileUtils.deleteDirectory(new File(UPLOAD_DIR, uuid));

        if (new File(UPLOAD_DIR, uuid).exists())
        {
            log.warn("couldn't find or delete " + uuid);
        }
        else
        {
            log.info("deleted " + uuid);
        }

        resp.setStatus(SUCCESS_RESPONSE_CODE);
//        resp.addHeader("Access-Control-Allow-Origin", "*");
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(SUCCESS_RESPONSE_CODE);
        resp.addHeader("Access-Control-Allow-Origin", "http://192.168.130.118:8080");
//        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "POST, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "x-requested-with, cache-control, content-type");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        RequestParser requestParser = null;
        dictPath=getServletContext().getRealPath("/")+"/source/all.dict";
        basisPath=getServletContext().getRealPath("/");
        log.info("dictPath的路径是"+dictPath);
        boolean isIframe = req.getHeader("X-Requested-With") == null || !req.getHeader("X-Requested-With").equals("XMLHttpRequest");

        try
        {
//            resp.setContentType(isIframe ? "text/html" : "text/plain");
            resp.setContentType("text/plain");
            resp.setStatus(SUCCESS_RESPONSE_CODE);

//            resp.addHeader("Access-Control-Allow-Origin", "http://192.168.130.118:8080");
//            resp.addHeader("Access-Control-Allow-Credentials", "true");
//            resp.addHeader("Access-Control-Allow-Origin", "*");

            if (ServletFileUpload.isMultipartContent(req))
            {
                MultipartUploadParser multipartUploadParser = new MultipartUploadParser(req, TEMP_DIR, getServletContext());
                requestParser = RequestParser.getInstance(req, multipartUploadParser);
                writeFileForMultipartRequest(requestParser);
                
            //获取用户传过来的参数    
                params=requestParser.getCustomParams();
                
                if(filePath==null)
                {
                	log.error("上传文件为空");                	
                }
                else
                {
                	//进行评分操作，将得分存入数据库
                	Score score;
                	boolean rightfile=doEvaluate(basisPath);//进行评价
                	if(rightfile) 
                	{	
                		score = getNewScore();
                		log.info(params.get("userName")+"连接数据库");
                		Mongo mg = new Mongo(MongodbConfig.IP, MongodbConfig.PORT);
                		DB mongoDB = mg.getDB(MongodbConfig.DATABASE);
                		mongoDB.authenticate(MongodbConfig.USER, MongodbConfig.PASSWORD.toCharArray());
                		DBCollection db_score = mongoDB.getCollection("coll_score");			
                		db_score.insert(score);
                		writeResponse(resp.getWriter(), requestParser.generateError() ? "Generated error" : null, isIframe, false, requestParser);
                	}
                	else
                	{
                		log.info("errpr code:110");
                		 String errorRs= "The file you uploaded doesn't match the basis file , please check the file you upload and retry";
                		 writeResponse(resp.getWriter(),errorRs, isIframe, false, requestParser);
                		
                	}
                }
                
              
                                
            }
            else
            {
                requestParser = RequestParser.getInstance(req, null);

                //handle POST delete file request
                if (requestParser.getMethod() != null
                        && requestParser.getMethod().equalsIgnoreCase("DELETE"))
                {
                    String uuid = requestParser.getUuid();
                    handleDeleteFileRequest(uuid, resp);
                }
                else
                {
                    writeFileForNonMultipartRequest(req, requestParser);
                    writeResponse(resp.getWriter(), requestParser.generateError() ? "Generated error" : null, isIframe, false, requestParser);
                }
            }
        } catch (Exception e)
        {
            log.error("Problem handling upload request", e);
            if (e instanceof MergePartsException)
            {
                writeResponse(resp.getWriter(), e.getMessage(), isIframe, true, requestParser);
            }
            else
            {
                writeResponse(resp.getWriter(), e.getMessage(), isIframe, false, requestParser);
            }
        }
    }

    private void writeFileForNonMultipartRequest(HttpServletRequest req, RequestParser requestParser) throws Exception
    {
        File dir = new File(UPLOAD_DIR, requestParser.getUuid());
        dir.mkdirs();

        String contentLengthHeader = req.getHeader(CONTENT_LENGTH);
        long expectedFileSize = Long.parseLong(contentLengthHeader);

        if (requestParser.getPartIndex() >= 0)
        {
            writeFile(req.getInputStream(), new File(dir, requestParser.getUuid() + "_" + String.format("%05d", requestParser.getPartIndex())), null);

            if (requestParser.getTotalParts()-1 == requestParser.getPartIndex())
            {
                File[] parts = getPartitionFiles(dir, requestParser.getUuid());
                File outputFile = new File(dir, requestParser.getFilename());
                for (File part : parts)
                {
                    mergeFiles(outputFile, part);
                }

                assertCombinedFileIsVaid(requestParser.getTotalFileSize(), outputFile, requestParser.getUuid());
                deletePartitionFiles(dir, requestParser.getUuid());
            }
        }
        else
        {
            writeFile(req.getInputStream(), new File(dir, requestParser.getFilename()), expectedFileSize);
        }
    }


    private void writeFileForMultipartRequest(RequestParser requestParser) throws Exception
    {
        File dir = new File(UPLOAD_DIR,requestParser.getCustomParams().get("userName")+"\\"+requestParser.getCustomParams().get("optionsRadios")+"\\"+ requestParser.getUuid());//新建用户id目录，再在用户id目录下创建文件夹
    	//File dir = new File(UPLOAD_DIR,requestParser.getUuid());
    	dir.mkdirs();
    	//获得当前文件保存路径
    	log.info(dir.toString()+"\\"+requestParser.getFilename());
    	//保存文件上传路径
    	filePath=dir.toString()+"\\"+requestParser.getFilename();
    	//欲保存的文件路径,在tomcat中其实是放在tomcat的bin文件夹下的
    	savePath=dir.toString();
    	
        if (requestParser.getPartIndex() >= 0)
        {
            writeFile(requestParser.getUploadItem().getInputStream(), new File(dir, requestParser.getUuid() + "_" + String.format("%05d", requestParser.getPartIndex())), null);

            if (requestParser.getTotalParts()-1 == requestParser.getPartIndex())
            {
                File[] parts = getPartitionFiles(dir, requestParser.getUuid());
                File outputFile = new File(dir, requestParser.getOriginalFilename());
                for (File part : parts)
                {
                    mergeFiles(outputFile, part);
                }

                assertCombinedFileIsVaid(requestParser.getTotalFileSize(), outputFile, requestParser.getUuid());
                deletePartitionFiles(dir, requestParser.getUuid());
            }
        }
        else
        {
            writeFile(requestParser.getUploadItem().getInputStream(), new File(dir, requestParser.getFilename()), null);
        }
    }

    private void assertCombinedFileIsVaid(long totalFileSize, File outputFile, String uuid) throws MergePartsException
    {
        if (totalFileSize != outputFile.length())
        {
            deletePartitionFiles(UPLOAD_DIR, uuid);
            outputFile.delete();
            throw new MergePartsException("Incorrect combined file size!");
        }

    }


    private static class PartitionFilesFilter implements FilenameFilter
    {
        private String filename;
        PartitionFilesFilter(String filename)
        {
            this.filename = filename;
        }

        @Override
        public boolean accept(File file, String s)
        {
            return s.matches(Pattern.quote(filename) + "_\\d+");
        }
    }

    private static File[] getPartitionFiles(File directory, String filename)
    {
        File[] files = directory.listFiles(new PartitionFilesFilter(filename));
        Arrays.sort(files);
        return files;
    }

    private static void deletePartitionFiles(File directory, String filename)
    {
        File[] partFiles = getPartitionFiles(directory, filename);
        for (File partFile : partFiles)
        {
            partFile.delete();
        }
    }

    private File mergeFiles(File outputFile, File partFile) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(outputFile, true);

        try
        {
            FileInputStream fis = new FileInputStream(partFile);

            try
            {
                IOUtils.copy(fis, fos);
            }
            finally
            {
                IOUtils.closeQuietly(fis);
            }
        }
        finally
        {
            IOUtils.closeQuietly(fos);
        }

        return outputFile;
    }

    private File writeFile(InputStream in, File out, Long expectedFileSize) throws IOException
    {
        FileOutputStream fos = null;

        try
        {
            fos = new FileOutputStream(out);

            IOUtils.copy(in, fos);

            if (expectedFileSize != null)
            {
                Long bytesWrittenToDisk = out.length();
                if (!expectedFileSize.equals(bytesWrittenToDisk))
                {
                  //  log.warn("Expected file {} to be {} bytes; file on disk is {} bytes", new Object[] { out.getAbsolutePath(), expectedFileSize, 1 });
                    out.delete();
                    throw new IOException(String.format("Unexpected file size mismatch. Actual bytes %s. Expected bytes %s.", bytesWrittenToDisk, expectedFileSize));
                }
            }

            return out;
        }
        catch (Exception e)
        {
            throw new IOException(e);
        }
        finally
        {
            IOUtils.closeQuietly(fos);
        }
    }

    private void writeResponse(PrintWriter writer, String failureReason, boolean isIframe, boolean restartChunking, RequestParser requestParser)
    {
        if (failureReason == null)
        {
//            if (isIframe)
//            {
//                writer.print("{\"success\": true, \"uuid\": \"" + requestParser.getUuid() + "\"}<script src=\"http://192.168.130.118:8080/client/js/iframe.xss.response.js\"></script>");
//            }
//            else
//            {
                writer.print("{\"success\": true}");
//            }
        }
        else
        {
            if (restartChunking)
            {
                writer.print("{\"error\": \"" + failureReason + "\", \"reset\": true}");
            }
            else
            {
//                if (isIframe)
//                {
//                    writer.print("{\"error\": \"" + failureReason + "\", \"uuid\": \"" + requestParser.getUuid() + "\"}<script src=\"http://192.168.130.118:8080/client/js/iframe.xss.response.js\"></script>");
//                }
//                else
//                {

                    writer.print("{\"error\": \"" + failureReason + "\"}");
//                }
            }
        }
    }

    private class MergePartsException extends Exception
    {
        MergePartsException(String message)
        {
            super(message);
        }
    }
    
   private Score getNewScore()
   {  
	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	   String submitTime=df.format(new Date());	   
	   Score score =new Score();
	   score.setUserName(params.get("userName"));	
	   score.setTask(params.get("type"));
	   score.setTrack(params.get("optionsRadios"));
	   score.setPrecision(params.get("precision"));
	   score.setRecall(params.get("recall"));
	   score.setFb1(params.get("fb1"));
	   score.setOov(params.get("oov"));
	   score.setDescription(params.get("description"));
	   score.setSubmitTime(submitTime);
	   
	   score.put("userName", params.get("userName"));
	   score.put("type", params.get("type"));
	   score.put("optionsRadios", params.get("optionsRadios"));
	   score.put("precision", params.get("precision"));
	   score.put("recall", params.get("recall"));
	   score.put("fb1", params.get("fb1"));
	   score.put("oov", params.get("oov"));
	   score.put("description", params.get("description"));
	   score.put("submitTime", submitTime);
	   return score;
   }
   
   
  //获取评价指标 ,保存在params中
   private boolean doEvaluate(String basisPath) throws IOException
   {
	 
	   double[] result;
	   Evaluate eval=new Evaluate();
	   if(params.get("type").equals("seg"))
		   basisPath=basisPath+"/source/testSeg.txt";
	   else
		   basisPath=basisPath+"/source/testPosE.txt";
	   log.info("标准文件的路径是"+basisPath);
	   result=eval.getScores(filePath,basisPath,savePath,dictPath);
	   if(result==null)
	   {
		   log.info("上传文件出现错误,与模板文件不匹配");
		   return false;
	   }
	   params.put("precision", String.valueOf(result[0]));
	   params.put("recall", String.valueOf(result[1]));
	   params.put("fb1", String.valueOf(result[2]));
	   params.put("oov", String.valueOf(result[3]));
	   log.info("评价的结果为：precision："+String.valueOf(result[0])+" recall:"+String.valueOf(result[1])+" fb1:"+String.valueOf(result[2])+" oov:"+String.valueOf(result[3]));
	   return true;
   }

}
