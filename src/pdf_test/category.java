package pdf_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.ResultSetMetaData;
//db추출 알고리즘
public class category {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://best54.cafe24.com/best54?serverTimezone=UTC";
    static final String USERNAME = "best54";
    static final String PASSWORD = "medi1607!";
    
    public  String data[]=new String[13];
    public  String complication[]=new String[15];
    public  String symptom[]=new String[12];
    public ArrayList good_material=new ArrayList()	;
    public ArrayList bad_material=new ArrayList();
    public ArrayList good_nutrient=new ArrayList();
    public ArrayList bad_nutrient=new ArrayList();
    //public  String product[]=new String[30];
    public ArrayList product=new ArrayList();
    public ArrayList raw_material=new ArrayList();
    public ArrayList evertea=new ArrayList();
    public ArrayList evertea_material=new ArrayList();

    //중복체크 함수
    public void evertea_check() { 
		 for (int i = 0; i < evertea.size(); i++) {
            for (int j = 0; j < evertea.size(); j++) {
                if (i == j) {
                } else if (evertea.get(j).equals(evertea.get(i))) {
                	evertea.remove(j);
                }
            }
        }
	 }
    //중복체크 함수
    public void evertea_check2() { 
		 for (int i = 0; i < evertea_material.size(); i++) {
			 try {
            for (int j = 0; j < evertea_material.size(); j++) {
                if (i == j) {
                } 
                else if (evertea_material.get(j).equals(evertea_material.get(i))) {
                	evertea_material.remove(j);
                }
            }
			 }catch(Exception e) {
				 continue;
			 }
			 }
	 }
    //중복체크 함수
	 public void check() { 
		 for (int i = 0; i < product.size(); i++) {
			 try {
             for (int j = 0; j < product.size(); j++) {
                 if (i == j) {
    	            } else if (product.get(j).equals(product.get(i))) {
                	 product.remove(j);
                 }
             }
			 }catch(Exception e) {
				 continue;
			 }
			 }
		 for (int i = 0; i < good_material.size(); i++) {
			 try {
             for (int j = 0; j < good_material.size(); j++) {
                 if (i == j) {
                 } else if (good_material.get(j).equals(good_material.get(i))) {
                	 good_material.remove(j);
                 }
             }
			 }catch(Exception e) {
				 continue;
			 }
         }
		 for (int i = 0; i < bad_material.size(); i++) {
			 try {
             for (int j = 0; j < bad_material.size(); j++) {
                 if (i == j) {
                 } else if (bad_material.get(j).equals(bad_material.get(i))) {
                	 bad_material.remove(j);
                 }
             }
			 }catch(Exception e) {
				 continue;
			 }
         }
		 for (int i = 0; i < good_nutrient.size(); i++) {
			 try {
             for (int j = 0; j < good_nutrient.size(); j++) {
                 if (i == j) {
                 } else if (good_nutrient.get(j).equals(good_nutrient.get(i))) {
                	 good_nutrient.remove(j);
                 }
             }
			 }catch(Exception e) {
				 continue;
			 }
         }
		
		 for (int i = 0; i <bad_nutrient.size(); i++) {
			 try {
             for (int j = 0; j < bad_nutrient.size(); j++) {
                 if (i == j) {
                 } else if (bad_nutrient.get(j).equals(bad_nutrient.get(i))) {
                	 bad_nutrient.remove(j);
                 }
             }
			 }catch(Exception e) {
				 continue;
			 }
         }
	 }
// public void check2() { 
//	  HashSet<String> arr3=new HashSet<String>(raw_material);
//	  raw_material=new ArrayList<String>(arr3);
//	 }
	   //중복체크 함수
	 public void check2() { 
		 for (int i = 0; i < raw_material.size(); i++) {
			 try {
             for (int j = 0; j < raw_material.size(); j++) {
                 if (i == j) {
                 } else if (raw_material.get(j).equals(raw_material.get(i))) {
                	 raw_material.remove(j);
                 }
                
             }
			 }catch(Exception e) {
				 continue;
			 }
         }
	 }
	//영어 검사항목->한글변환 함수
	 public void transform() {
		
		 for (int i = 0; i < data.length; i++) {	
			 try {
             for (int j = 0; j < data.length; j++) {
                 if (i == j) {
                 } else if (data[j].equals(data[i])) {
                	data[j]="";
                 }
             }
			 }catch(Exception e) {
				 continue;
			 }
			 }
			 
		 for(int m=0; m<data.length; m++) {
			 try {
			 if(data[m].equals(null))
			 break;
			 else if(data[m].equals("Body Mass Index"))
				 data[m]="체질량";
			else if(data[m].equals("Triglycerides"))
				data[m]="중성지방";
			 else if(data[m].equals("TC"))
				 data[m]="총 콜레스테롤";
			 else if(data[m].equals("HDL"))
				 data[m]="고밀도 콜레스테롤";
			 else if(data[m].equals("LDL"))
				 data[m]="저밀도 콜레스테롤";
			 else if(data[m].equals("Blood Glucose"))
				 data[m]="혈당";
			 else if(data[m].equals("Blood Pressure"))
				 data[m]="혈압";
			 else if(data[m].equals("Caffeine Metabolism"))
				 data[m]="카페인 대사";
			 else if(data[m].equals("Skin Pigmentation"))
				 data[m]="피부색소침착";
			 else if(data[m].equals("Skin Aging"))
				 data[m]="피부 노화";
			 else if(data[m].equals("Skin Elasticity")) 
				 data[m]="피부 탄력";		 
			 else if(data[m].equals("Male Pattern Baldness"))
				 data[m]="남성형 탈모";
			 else if(data[m].equals("Alopecia Areata"))
				 data[m]="원형 탈모";
			 else if(data[m].equals("Hair Thickness"))
				 data[m]="모발 굵기";
			 else if(data[m].equals("Vitamin C"))
				 data[m]="비타민 C";
			 }catch(Exception e) {
				 continue;
			 }
			// System.out.println(data[m]);
			 }
		 
	 }       

	 //추천/비추천 식품, 영양소, 제품  
    public  void disease() throws Exception {
    
    	String[] high_caffein= {"코블밀크인퍼스트","코블스트레이트","블루스카이","3시의 요정"};
         gui gui=new gui();
         String id=gui.output();
        String sql0,sql1,sql2,sql4,sql5,sql6,sql7,sql8,sql9,sql10,sql11,sql12;
        
          Connection conn = null;
             Statement stmt = null;
             ResultSet rs=null;
             Statement stmt2= null;
             ResultSet rs2=null;
             Statement stmt3= null;
             ResultSet rs3=null;
             Statement stmt4= null;
             ResultSet rs4=null;
             Statement stmt5= null;
             ResultSet rs5=null;
             Statement stmt6= null;
             ResultSet rs6=null;
             Statement stmt7= null;
             ResultSet rs7=null;
             Statement stmt8= null;
             ResultSet rs8=null;
             Statement stmt9= null;
             ResultSet rs9=null;
             Statement stmt10= null;
             ResultSet rs10=null;
             Statement stmt11= null;
             ResultSet rs11=null;
             Statement stmt12= null;
             ResultSet rs12=null;
             try {
                 Class.forName(JDBC_DRIVER);
             conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
             
                 sql0="select * from disease_table where barcode='"+id+"' AND result='높음'";
                 sql12="select * from disease_table where barcode='"+id+"' AND name_disease='Caffeine Metabolism'";
             stmt = conn.createStatement();
             rs = stmt.executeQuery(sql0);
             stmt12 = conn.createStatement();
             rs12 = stmt12.executeQuery(sql12);
             int i=0;
             int j=0;
             int k=0;
             int gm=0;
             int bm=0;
             int gn=0;
             int bn=0;
             int p=0;
             int p1=0;
           
            while(rs.next())
             {
            	 data[i]=rs.getString("name_disease");
            	// System.out.println(data[i]);
            	 
            	 sql1="select * from complication_table where name_disease='"+data[i]+"'";
            	 sql2="select * from complication_table where disease='"+data[i]+"'";
            	 sql4="select * from material_table where name_disease='"+data[i]+"'";
            	 sql5="select * from material_table where name_disease='"+data[i]+"'";
            	 sql6="select * from material_table where name_disease='"+data[i]+"'";
            	 sql7="select * from material_table where name_disease='"+data[i]+"'";
            	 sql8="select * from material_table where name_disease='"+data[i]+"'";
            	 sql9="select * from material_table where name_disease='"+data[i]+"'";
            	 sql10="select * from material_table where name_disease='"+data[i]+"'";
            	
            	
            	 stmt2 = conn.createStatement();
                 rs2 = stmt2.executeQuery(sql1);
                 stmt3 = conn.createStatement();
                 rs3 = stmt3.executeQuery(sql2);
                 stmt4 = conn.createStatement();
                 rs4 = stmt4.executeQuery(sql4);
                 stmt5 = conn.createStatement();
                 rs5 = stmt5.executeQuery(sql5);
                 stmt6 = conn.createStatement();
                 rs6 = stmt6.executeQuery(sql6);
                 stmt7 = conn.createStatement();
                 rs7 = stmt7.executeQuery(sql7);
                 stmt8 = conn.createStatement();
                 rs8 = stmt8.executeQuery(sql8);
                 stmt9 = conn.createStatement();
                 rs9 = stmt9.executeQuery(sql9);
                 stmt10 = conn.createStatement();
                 rs10 = stmt10.executeQuery(sql10);
        
                
                 while(rs2.next()) {
                	  complication[j]=rs2.getString("complication");
                //  System.out.println(complication[j]);
                 j++;
                 }
                 
                 while(rs3.next()) {
                symptom[k]=rs3.getString("symptom");
                   //  System.out.println(symptom[k]);
                    k++;
                    }
                 
                 while(rs4.next()) {
                 	
                	 if(rs4.getString("good_material")==null)
                	 continue;
               
                	 good_material.add(rs4.getString("good_material"));
                	// System.out.println(good_material.get(o).toString());
                	 
                               }
                 
                 while(rs5.next()) {
                  	
                	 if(rs5.getString("bad_material")==null)
                	 continue;
                	 bad_material.add(rs5.getString("bad_material"));
                               }
                 
                   while(rs6.next()) {
                  	
                	 if(rs6.getString("good_nutrient")==null)
                	 continue;
                	 good_nutrient.add(rs6.getString("good_nutrient"));
                               }
                   
                 while(rs7.next()) {
                  	
                	 if(rs7.getString("bad_nutrient")==null)
                	 continue;
                	 bad_nutrient.add(rs7.getString("bad_nutrient"));
                               }
                 
                /*  while(rs5.next()) {
                	 bad_material[bm]=rs5.getString("bad_material");
                   //  System.out.println(bad_material[bm]);
                    bm++;
                    }
                 while(rs6.next()) {
                	 good_nutrient[gn]=rs6.getString("good_nutrient");
                   //  System.out.println(good_nutrient[gn]);
                    gn++;
                    }
                 while(rs7.next()) {
                	bad_nutrient[bn]=rs7.getString("bad_nutrient");
                    // System.out.println(bad_nutrient[bn]);
                    bn++;
                    }*/
                 while(rs8.next()) {
                	
                	 if(rs8.getString("product")==null)
                	 continue;
                     product.add(rs8.getString("product"));
                  
                               }
                 
                 while(rs9.next()) {
                     	 if(rs9.getString("raw_material")==null)
                       	 continue;
                	 raw_material.add(rs9.getString("raw_material"));
                	                                             }
      
                 while(rs10.next()) {
                	 try {
                	//	 evertea.add("라벤더블렌드");
                	//	evertea.add("3시의 요정");
                	//	evertea.add("혼자있는 시간");
                 	 if(rs10.getString("evertea")==null)
                 		continue;
                 
                  	evertea.add(rs10.getString("evertea"));
                  	
                	 }catch(IndexOutOfBoundsException e) {
                    	break; 
                     }
                 }

                 
                 if(rs12.next()) {
          			
            		 if(rs12.getString("result").equals("높음"))
            		 {
            			 for(int i1=0; i1<high_caffein.length;i1++)
            				 evertea.remove(high_caffein[i1]);
            		 }
     
                 }
                 
                 
            
           //      sql11="select * from material_table where evertea='"+evertea.get(i)+"'";
             //    stmt11 = conn.createStatement();
               //  rs11 = stmt11.executeQuery(sql11);
                 try {
                	 for(int b=0;b<evertea.size();b++) {
                		
                		 sql11="select * from material_table where evertea='"+evertea.get(b)+"'";
                         stmt11 = conn.createStatement();
                         rs11 = stmt11.executeQuery(sql11);
                	 
                         if(rs11.next()) {
                     
                 	// if(rs11.getString("evertea_material").equals(null))
                  // 	 continue;
                	 if(rs11.getString("evertea_material")==null)
                       	 continue;
                     	 
                 	evertea_material.add(rs11.getString("evertea_material"));
        
                 }
                 }
                
//                	while(rs9.next()) {
//                  	 if(rs9.getString("raw_material")==null)
//                   	 continue;
//                   	raw_material.add(rs9.getString("raw_material"));
//                	}
                	// product[p]=rs8.getString("product");
                    // System.out.println(product[p]);
                    //p++;
       
            	 i++;        	
                 }catch(Exception e) {
                  	continue; 
                   }
                 }
            if(evertea.size()<4) {
   			 evertea.add("레몬머틀블렌드");
   			 evertea_material.add("레몬머틀 레몬그라스 귤피");
   			 evertea.add("민트블렌드");
   			evertea_material.add("민트허브");
   			 evertea.add("혼자있는 시간");
   			evertea_material.add("다즐링 자스민 건조사과 꽃향");
   		 }  
            if(product.size()<4) {
      			 product.add("휴안");
      			 raw_material.add("연자육 대추 맥문동 진피 감초");
      			 product.add("루이보스");
      			raw_material.add("루이보스 귤피");
      			 product.add("가월");
      			raw_material.add("어성초 연잎 질경이 둥굴레 감초");
      		 }      
            check();
           check2();
         //evertea_check();
         //evertea_check2();
      transform();
           
               // transform();
        
     }catch (SQLException e) {
        System.out.println("SQL Error : " + e.getMessage());
    } catch (ClassNotFoundException e1) {
        System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
    }       
         	if(rs !=null) rs.close();

            if(stmt != null) stmt.close();

            if(conn!= null) conn.close();    
  
    }

   /*public static void main(String[] args)  {
    	try {
			disease();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}*/

    


   
}
