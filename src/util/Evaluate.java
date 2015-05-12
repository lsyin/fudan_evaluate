package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.fnlp.ml.eval.SeqEval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import servlets.ProfileServlet;

public class Evaluate {
	
	final Logger log = LoggerFactory.getLogger(Evaluate.class);  
	int k=1;	//设置出错标记
	public static void main(String[] args) throws IOException{
	
		String dictpath="e://all.dict";
		String filePath="e://test3.txt";
		
		SeqEval ne1;
		ne1 = new SeqEval();
		ne1.readOOV(dictpath);
		ne1.read(filePath);
		double[] res = ne1.calcPRF();
		System.out.print(res[0] +" " + res[1]+" " +res[2]+" "+res[3]);
	
	}
	
	
	public double[] getScores(String filePath,String basisPath,String savePath,String dictPath) throws IOException
	{
		//获取合并后的结果文件
		String resultPath=mergeFile(filePath,basisPath,savePath);	
		if(k==-1) 
		{
			//说明出错
			return null;
			
		}
			
		log.info("词典文件的路径是："+dictPath);		
		SeqEval ne1;
		ne1 = new SeqEval();
		ne1.readOOV(dictPath);
		ne1.read(resultPath);
		double[] res = ne1.calcPRF();
		log.info("evaluate result:"+res[0] +" " + res[1]+" " +res[2]+" "+res[3]);		
		return res;
	}
	
	
	
	public String mergeFile(String filePath,String basisPath,String savePath)
	{
		 try {              
	         // 从文件中读取文件内容		
	         FileReader reader = new FileReader(filePath);
	         FileReader reader1 = new FileReader(basisPath);
	         BufferedReader br = new BufferedReader(reader);
	         BufferedReader br1 = new BufferedReader(reader1);
	               
	         String str = null;
	         String str1 = null;
	         //暂存读出的内容
	         StringBuffer sb= new StringBuffer("");
	        
	         while((str = br.readLine()) != null&&(str1 = br1.readLine()) != null)
	         {
	        	 log.info("开始进行两个文件的合并");
	        	 if(str.equals("")&&str1.equals(""))
	        	 {
	        		  String usr=""+'\n';
	           	      sb.append(usr);	                
	        	 }
	        	 else
	        	 {	 
	        	      String[] temp=str.split("\t");     	   
	        	      String[] temp1=str1.split("\t");
	        	      if(temp.length!=temp1.length)
	        	      {
	        	    	  log.info("文件中的分隔符为空格，不是\\t");
	        	    	   temp=str.split(" ");     	   
	        	      }
	        	    		  
	        	      if(!temp[0].equals(temp1[0]))
	        	       {
	        		         k=-1;  //设置匹配标记，如果词不匹配，则说明上传文件错误	        		         
	        		         log.info("上传文件出现错误，文件与标准文件不匹配");
	        		         break;
	        	       }
	        	       String usr=str+"\t"+temp1[1]+'\n';
	        	       sb.append(usr);  
	                   usr="";
	             }
	        	
	         }
	        
	         br.close();
	         reader.close();
	         br1.close();
	         reader1.close();   
	        //将从两个文件读入的拼接内容写入到新的文件中 
	         FileWriter writer = new FileWriter(savePath+"\\"+"result.txt");
	         BufferedWriter bw = new BufferedWriter(writer);
	         bw.write(sb.toString());
	         bw.close();
	         writer.close();
	        
	             
	        
	   }
	   catch(FileNotFoundException e) {
	               e.printStackTrace();
	         }
	         catch(IOException e) {
	               e.printStackTrace();
	         }
		 
		 //返回保存文件的路径
		 savePath=savePath+"\\"+"result.txt";
		 log.info("生成的文件保存在:"+savePath);
		     return savePath;				   		 
	}
	
	
	
	

}
