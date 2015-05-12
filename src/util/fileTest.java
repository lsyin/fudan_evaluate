package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class fileTest {
	 public static void main(String[] args) {
         try {              
         int k=1;
        	 // 从文件中读取文件内容
         FileReader reader = new FileReader("e://test.txt");
         FileReader reader1 = new FileReader("e://test.txt");
         BufferedReader br = new BufferedReader(reader);
         BufferedReader br1 = new BufferedReader(reader1);
               
         String str = null;
         String str1 = null;
         //暂存读出的内容
         StringBuffer sb= new StringBuffer("");
        
         while((str = br.readLine()) != null&&(str1 = br1.readLine()) != null) {
        	 
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
	 k=9;
  }
        	      System.out.println(temp.length);
        	      if(!temp[0].equals(temp1[0]))
        	       {
        		           k=-1;  //设置匹配标记，如果词不匹配，则说明上传文件错误
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
         FileWriter writer = new FileWriter("e://test4.txt");
         BufferedWriter bw = new BufferedWriter(writer);
         bw.write(sb.toString());
         bw.close();
         writer.close();
         
         System.out.println(sb.toString());
         System.out.println(k);
   }
   catch(FileNotFoundException e) {
               e.printStackTrace();
         }
         catch(IOException e) {
               e.printStackTrace();
         }
   }


}
