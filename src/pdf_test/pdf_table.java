package pdf_test;


import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import pdf_test.gui;

import org.w3c.dom.views.DocumentView;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

//pdf 생성 클래스
public  class pdf_table  {
	
	//DB연동관련 변수
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://best54.cafe24.com/best54?serverTimezone=UTC";
    static final String USERNAME = "best54";
    static final String PASSWORD = "medi1607!";

    //PDF 설정 함수(css, 폰트, 사이즈 등)
	
    public static void total_pdf() throws Exception{
    	//외부 클래스 및 함수 선언

    	gui gui=new gui();  //gui 클래스 선언
          String id=gui.output(); //고객번호
          String name=gui.output2(); //고객이름
                
        Document document = new Document(PageSize.A4, 10, 10, 10, 10); // 용지 및 여백 설정
        
        mixpage mix=new mixpage(); //edgc pdf에 합치는 클래스
        
        // PdfWriter 생성
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(mix.addfolder()+"\\(medi)"+id+".pdf")); //메디프레소 버전 pdf 파일 이름
        // PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\jooho\\pdf생성 실행파일 관련\\(메디프레소).pdf"));
 
        // 파일 다운로드 설정
        String fileName = URLEncoder.encode("한글파일명", "UTF-8"); // 파일명이 한글일 땐 인코딩 필요


        // Document 오픈
        document.open();
        XMLWorkerHelper helper = XMLWorkerHelper.getInstance();

        // CSS
       /* ClassLoader classLoader = pdf_table.class.getClassLoader();
        File file = new File(classLoader.getResource("table2.css").getFile());
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssFile = helper.getCSS(new FileInputStream(file));
        cssResolver.addCss(cssFile);*/
        
       CSSResolver cssResolver = new StyleAttrCSSResolver();
    	CssFile cssFile = helper.getCSS(new FileInputStream("D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\table2.css"));//css 파일 위치 및 파일
    	cssResolver.addCss(cssFile);
        
       /* CSSResolver cssResolver = new StyleAttrCSSResolver();
    	CssFile cssFile = helper.getCSS(new FileInputStream("pdf_test/Resource/table2.css"));
    	cssResolver.addCss(cssFile);*/

        
       // ClassLoader classLoader2 = pdf_table.class.getClassLoader();
       // File file2 = new File(classLoader2.getResource("NanumSquareB.ttf").getFile());
       // String aa=file2.toString();
    	
    	// HTML, 폰트 설정
    	XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
    	fontProvider.register("D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\NanumSquareB.ttf", "NanumGothicBold"); // MalgunGothic은 alias,  //폰트 위치및 굵기설정
    	//  fontProvider.register(aa, "NanumGothicBold");
    	CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
      
        // 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document,writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        XMLWorker worker = new XMLWorker(css, true);
        XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));
        
        //db관련 sql변수 
        String sql0,sql1,sql2,sql3,sql4,sql5,sql6,sql7,sql8,sql9,sql10,sql11,sql12,sql13,sql14;   
      // 유전자 수치값 변수
        String sql_HDL=null,sql_LDL=null,sql_TC=null,sql_body=null,sql_trig=null,sql_glucose=null,sql_pressure=null,sql_skin=null,sql_areata=null,sql_male=null;
        // 유전자 검사 결과 변수
        String sql_HDL_result=null,sql_LDL_result=null,sql_TC_result=null,sql_body_result=null,sql_trig_result=null,sql_glucose_result=null,sql_pressure_result=null,sql_skin_result=null,sql_areata_result=null,sql_male_result=null;    
        String sql_CaffeineMetabolism_result=null,sql_SkinAging_result=null,sql_SkinElasticity_result=null, sql_HairThickness_result=null, sql_VitaminC_result=null;
        //db연동 변수
        Connection conn = null;
            Statement stmt = null;
            ResultSet rs=null;
            //수치 모형 변환
            String TC=null,HDL=null,LDL=null,body=null, trig=null, glucose=null, pressure=null,skin=null, areata=null,male=null,VitaminC=null,CaffeineMetabolism=null,SkinAging=null;
           //결과값에 따라 색 변환 변수
            String color_TC="black",color_HDL="black",color_LDL="black",color_body="black",color_trig="black",color_glucose="black",color_pressure="black",color_skin="black",color_areata="black",color_male="black",color_CaffeineMetabolism="black",color_SkinAging="black",color_VitaminC="black";
           
            
            //db추출 부분
            try {
            	//해당 클래스 존재 여부 확인용
                Class.forName(JDBC_DRIVER);
                //해당 드라이버의 클래스를 이용하여 db접속
                conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
               //쿼리문장
                sql0="select * from disease_table where barcode="+id +" AND name_disease='TC'";
                sql1="select * from disease_table where barcode="+id +" AND name_disease='Body Mass Index'";
                sql2="select * from disease_table where barcode="+id +" AND name_disease='Triglycerides'";
                sql3="select * from disease_table where barcode="+id +" AND name_disease='Blood Glucose'";
                sql4="select * from disease_table where barcode="+id +" AND name_disease='Blood Pressure'";
                sql5="select * from disease_table where barcode="+id +" AND name_disease='Skin Pigmentation'";
                sql6="select * from disease_table where barcode="+id +" AND name_disease='Alopecia Areata'";
                sql7="select * from disease_table where barcode="+id +" AND name_disease='Male Pattern Baldness'";
                sql8="select * from disease_table where barcode="+id +" AND name_disease='HDL'";
                sql9="select * from disease_table where barcode="+id +" AND name_disease='LDL'";
                sql10="select * from disease_table where barcode="+id +" AND name_disease='Caffeine Metabolism'";
                sql11="select * from disease_table where barcode="+id +" AND name_disease='Skin Aging'";
                sql12="select * from disease_table where barcode="+id +" AND name_disease='Skin Elasticity'";
                sql13="select * from disease_table where barcode="+id +" AND name_disease='Hair Thickness'";
                sql14="select * from disease_table where barcode="+id +" AND name_disease='Vitamin C'";

          
                	
            //db에 명령어를 전달할 수 있는 객체 생성
            stmt = conn.createStatement();
            //실행 결과 rs변수에 담는다.
            rs = stmt.executeQuery(sql0);
            
            //질병의 높고 낮음을 통한 글씨 색 및 아이콘 변경, 수치값 및 결과값 변수에 입력
            if(rs.next())
            {
                sql_TC = rs.getString("multiple_score");
                sql_TC_result = rs.getString("result");
                switch(sql_TC_result) {
                    case "높음":
                        TC=" ▲"; color_TC="red"; break;
                    case "낮음":
                        TC=" ▼"; color_TC="green"; break;
                    default:
                        TC="&nbsp;-";
                }
            }
          
            rs = stmt.executeQuery(sql1);
            if(rs.next())
            {
                sql_body = rs.getString("multiple_score");
                sql_body_result = rs.getString("result");
                switch(sql_body_result) {
                    case "높음":
                        body=" ▲"; color_body="red"; break;
                    case "낮음":
                        body=" ▼"; color_body="green"; break;
                    default:
                        body="&nbsp;-";
                }
            }
            rs = stmt.executeQuery(sql2);
            if(rs.next())
            {
                sql_trig = rs.getString("multiple_score");
                sql_trig_result = rs.getString("result");
                switch(sql_trig_result) {
                    case "높음":
                        trig=" ▲"; color_trig="red"; break;
                    case "낮음":
                        trig=" ▼"; color_trig="green"; break;
                    default:
                        trig="&nbsp;-";
                }
            }
            rs = stmt.executeQuery(sql3);
            if(rs.next())
            {
                sql_glucose = rs.getString("multiple_score");
                sql_glucose_result = rs.getString("result");
                switch(sql_glucose_result) {
                    case "높음":
                        glucose=" ▲"; color_glucose="red"; break;
                    case "낮음":
                        glucose=" ▼"; color_glucose="green"; break;
                    default:
                        glucose="&nbsp;-";
                }
            }
            rs = stmt.executeQuery(sql4);
            if(rs.next())
            {
                sql_pressure = rs.getString("multiple_score");
                sql_pressure_result = rs.getString("result");
                switch(sql_pressure_result) {
                    case "높음":
                        pressure=" ▲"; color_pressure="red"; break;
                    case "낮음":
                        pressure=" ▼"; color_pressure="green"; break;
                    default:
                        pressure="&nbsp;-";
                }
            }
            rs = stmt.executeQuery(sql5);
            if(rs.next())
            {
                sql_skin = rs.getString("multiple_score");
                sql_skin_result = rs.getString("result");
                switch(sql_skin_result) {
                    case "높음":
                        skin=" ▲"; color_skin="red"; break;
                    case "낮음":
                        skin=" ▼"; color_skin="green"; break;
                    default:
                        skin="&nbsp;-";
                }
            }
            rs = stmt.executeQuery(sql6);
            if(rs.next())
            {
                sql_areata = rs.getString("multiple_score");
                sql_areata_result = rs.getString("result");
                switch(sql_areata_result) {
                    case "높음":
                        areata=" ▲"; color_areata="red"; break;
                    case "낮음":
                        areata=" ▼"; color_areata="green"; break;
                    default:
                        areata="&nbsp;-";
                }
            }

            rs = stmt.executeQuery(sql7);
            if(rs.next())
            {
                sql_male = rs.getString("multiple_score");
                sql_male_result = rs.getString("result");
                switch(sql_male_result) {
                    case "높음":
                        male=" ▲"; color_male="red"; break;
                    case "낮음":
                        male=" ▼"; color_male="green"; break;
                    default:
                        male="&nbsp;-";
                }
            }
            rs = stmt.executeQuery(sql8);
            if(rs.next())
            {
                sql_HDL = rs.getString("multiple_score");
                sql_HDL_result = rs.getString("result");
                switch(sql_HDL_result) {
                    case "높음":
                        HDL=" ▲"; color_HDL="red"; break;
                    case "낮음":
                        HDL=" ▼"; color_HDL="green"; break;
                    default:
                        HDL="&nbsp;-";
                }
            }
            rs = stmt.executeQuery(sql9);
            if(rs.next())
            {
                sql_LDL = rs.getString("multiple_score");
                sql_LDL_result = rs.getString("result");
                switch(sql_LDL_result) {
                    case "높음":
                        LDL=" ▲"; color_LDL="red"; break;
                    case "낮음":
                        LDL=" ▼"; color_LDL="green"; break;
                    default:
                        LDL="&nbsp;-";
                }
            }

            rs = stmt.executeQuery(sql10);
            if(rs.next())
            {
                sql_CaffeineMetabolism_result = rs.getString("result");
                switch(sql_CaffeineMetabolism_result) {
                    case "높음":
                        CaffeineMetabolism=" ▲"; color_CaffeineMetabolism="red"; break;
                    case "낮음":
                        CaffeineMetabolism=" ▼"; color_CaffeineMetabolism="green"; break;
                    default:
                        CaffeineMetabolism="&nbsp;-";
                }
            }
            rs = stmt.executeQuery(sql11);
            if(rs.next())
            {
                sql_SkinAging_result = rs.getString("result");
                switch(sql_SkinAging_result) {
                    case "높음":
                        SkinAging=" ▲"; color_SkinAging="red"; break;
                    case "낮음":
                        SkinAging=" ▼"; color_SkinAging="green"; break;
                    default:
                        SkinAging="&nbsp;-";
                }
            }
            
            
            rs = stmt.executeQuery(sql12);
            if(rs.next())
            {
                sql_SkinElasticity_result = rs.getString("result");
            }
            rs = stmt.executeQuery(sql13);
            if(rs.next())
            {
                sql_HairThickness_result = rs.getString("result");
            }

            rs = stmt.executeQuery(sql14);
            if(rs.next())
            {
                sql_VitaminC_result = rs.getString("result");
                switch(sql_VitaminC_result) {
                    case "높음":
                        VitaminC=" ▲"; color_VitaminC="red"; break;
                    case "낮음":
                        VitaminC=" ▼"; color_VitaminC="green"; break;
                    default:
                        VitaminC="&nbsp;-";
                }
            }
        }catch (SQLException e) {
            System.out.println("SQL Error : " + e.getMessage());
        } catch (ClassNotFoundException e1) {
            System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");
        }
            	
            
      //category 클래스에서 뽑아낸 db값 담을 변수
      category category=new category();
      category.disease();
      category_gene category_gene=new category_gene();
      category_gene.gene();
      category_gene.explan();
    //  String data[]=new String[5];	//질병
      ArrayList data=new ArrayList();
      String complication[]= new String[15];  //합병증
      String symptom[]=new String[5];	//증상
      String good_material[]=new String[15];
      String bad_material[]=new String[11];
      String good_nutrient[]=new String[15];
      String bad_nutrient[]=new String[11];
      String product[]=new String[10];
      String raw_material[]=new String[10];
      String evertea[]=new String[5];
      
      //String evertea_material[]=new String[5];
      ArrayList evertea_material=new ArrayList();
      ArrayList name_gene=new ArrayList();
      ArrayList marker_result=new ArrayList();
      ArrayList genotype=new ArrayList();
      ArrayList risk=new ArrayList();
      ArrayList bmi=new ArrayList();
      ArrayList explanation=new ArrayList();
      String arrow[]=new String[21];
      int left[]=new int[12];


      for(int i=0; i<5; i++) {
    		 try {	
    		if(category.data[i].equals(null)){
    			data.add("");
    			continue;
    		}
    			 data.add(category.data[i]);
    		}catch(Exception e) {
    			data.add("");
    		}
			/*
			 if(category.data[i]==null) { data[i] =""; } else {
			 
			  data[i]=category.data[i]; }
			 */
      }
      for(int i=0; i<15; i++) {
      	  if(category.complication[i]==null) {
      		complication[i]="";


    		  	  }
      	  else{
      		 complication[i]=category.complication[i];
      		 
      		 switch(complication[i]) {
      		 case "심장질환":
      		 case "심근경색증":
      			 left[0]=500;
      			 break;
      		case"뇌출혈":
      		 case"뇌졸중":
      		 case"원형탈모":
      		 case"남성형탈모":
      			left[1]=500;
     			 break;
      		case"신장질환":
      			left[2]=500;
    			 break;
      		 case"관상동맥":
      		 case"심혈관질환":
      		 case"동맥경화증":
      		case"동맥경화":
      			left[3]=500;
     			 break;
      		case"말초신경증":
      			left[4]=500;
    			 break;
      		case"지방간":
      		case"췌장암":
      			left[5]=500;
    			 break;
      		case"대장암":
      		case"과민성 대장 질환":
      			left[6]=500;
    			 break;
      		case"유방암":
      			left[7]=500;
    			 break;
      		case"요실금":
      		case"요로감염":
      			left[8]=500;
    			 break;
      		case"통풍":
      			left[9]=500;
    			 break;
      		case"위궤양":
      			left[10]=500;
    			 break;
      		case"눈 질환(황색판종)":
    		case"치아변색":
      		case"치주질환":
      			left[11]=500;
    			 break;
    			 default:
        			 break;
      		 }
      	  }
      }

      if(complication[0].equals(""))
    	  complication[0]="유전적 요인으로 인한 합병증 발병 확률이 낮습니다.";
      for(int i=0; i<5; i++) {
      	  if(category.symptom[i]==null) {
      		symptom[i]="";
    		  	  }
      	  else {
      		  symptom[i]=category.symptom[i];
      	  }
      	  }
      if(symptom[0].equals(""))
    	  symptom[0]="유전적 요인으로 인한 이상증상 발생 확률이 낮습니다.";
    
      for(int i=0; i<category.good_material.size(); i++) {
    	  try {
    	  good_material[i]=(String) category.good_material.get(i);
    	  }

  		catch(IndexOutOfBoundsException e) {

  		}
}
      for(int i=0; i<category.bad_material.size(); i++) {
    	  try {
    	  bad_material[i]=(String) category.bad_material.get(i);
    	  }

    		catch(IndexOutOfBoundsException e) {

        		}
}
      for(int i=0; i<category.good_nutrient.size(); i++) {
    	  try {
    	  good_nutrient[i]=(String) category.good_nutrient.get(i);
      }

		catch(IndexOutOfBoundsException e) {
		}
}
      for(int i=0; i<category.bad_nutrient.size(); i++) {
    	  try {
    	  bad_nutrient[i]=(String) category.bad_nutrient.get(i);
      }

		catch(IndexOutOfBoundsException e) {
		}
}
      for(int i=0; i<bad_nutrient.length; i++) {
    	  
    	  if(bad_nutrient[i]==null ) {
    		  bad_nutrient[i]="";
    		  }
    		  if(bad_material[i]==null ) {
    			  bad_material[i]="";
      		  	  }
}
     for(int i=0; i<good_material.length; i++) {
    	  
    	  if(good_material[i]==null ) {
    		  good_material[i]="";
    		  }
    		  if(good_nutrient[i]==null ) {
    			  good_nutrient[i]="";
      		  	  }
}
      for(int i=0; i<3; i++) {
      
            		  product[i]=(String) category.product.get(i);
      	  
      	  }
      for(int i=0; i<3; i++) {
          
		  raw_material[i]=(String) category.raw_material.get(i);

}

      for(int i=0; i<5; i++) {
    	  try {
    	  evertea[i]= category.evertea.get(i).toString();
    	  }catch(Exception e) {
    			continue;
    		}
}
for(int i=0; i<5; i++) {
try {
	evertea_material.add(category.evertea_material.get(i).toString());
}catch(Exception e) {
	continue;
}
}






  for(int i=0; i<21; i++) { 
try {
	 name_gene.add(category_gene.name_gene.get(i).toString());
	 arrow[i]="▲";
}catch(IndexOutOfBoundsException e) {
	name_gene.add("");
	arrow[i]="";
}catch(Exception e) {
	name_gene.add("");
	arrow[i]="";
}
}
 for(int i=0; i<21; i++) { 
	 try {
		 marker_result.add(category_gene.marker_result.get(i).toString());
	}catch(IndexOutOfBoundsException e) {
		marker_result.add("");
	}catch(Exception e) {
		marker_result.add("");
	}
	 
}
 for(int i=0; i<21; i++) { 
	 try {
		 risk.add(category_gene.risk.get(i).toString());
	}catch(IndexOutOfBoundsException e) {
		risk.add("");
	}catch(Exception e) {
		risk.add("");
	}
}
 for(int i=0; i<21; i++) { 

	 try {
		 genotype.add(category_gene.genotype.get(i).toString());
	}catch(IndexOutOfBoundsException e) {
		genotype.add("");
	}catch(Exception e) {
		genotype.add("");
	}
}
 for(int i=0; i<21; i++) {
	 try {
		 bmi.add(category_gene.bmi.get(i));
	}catch(IndexOutOfBoundsException e) {
		bmi.add("");
	}catch(Exception e) {
		bmi.add("");
	}
}
 for(int i=0; i<21; i++) { 

	 try {
		 explanation.add(category_gene.explanation.get(i).toString());
	}catch(IndexOutOfBoundsException e) {
		explanation.add("");
	}catch(Exception e) {
		explanation.add("");
	}
}
ArrayList score=new ArrayList();

/*double tc_arr=1.3335;
//score.add(Math.round(100-(100*(Float.parseFloat(sql_TC)-1.070))/0.7378));
double tc=Float.parseFloat(sql_TC);
for(;;) {
if(tc<1.3335) {
	tc_arr=tc_arr-0.01;
	if(tc<tc_arr)
		defult=defult+1;
	else
		break;
}
else if(tc>1.3335){
	tc_arr=tc_arr+0.01;
	if(tc>tc_arr)
		defult=defult-1;
	else
		break;
}
else
	break;
}*/
double arr[]= {1.3335,1.1945,1.2985,1.1605,3.308,1.5915,1.800,0.9605,1.3205,0.8805};
//score.add(Math.round(100-(100*(Float.parseFloat(sql_TC)-1.070))/0.7378));
double gene[]= {Float.parseFloat(sql_TC),Float.parseFloat(sql_HDL),Float.parseFloat(sql_LDL),Float.parseFloat(sql_body),Float.parseFloat(sql_trig),
		Float.parseFloat(sql_glucose),Float.parseFloat(sql_pressure),Float.parseFloat(sql_skin),Float.parseFloat(sql_male),Float.parseFloat(sql_areata)};
for(int i=0;i<arr.length;i++) {
	int defult=55;
	while(true) {
	if(gene[i]<arr[i]) {
	defult=defult+1;
	arr[i]=arr[i]-0.04;
	if(gene[i]<arr[i])
		defult=defult+1;
	else
		break;
}
else if(gene[i]>arr[i]){
	defult=defult-1;
	arr[i]=arr[i]+0.04;
	if(gene[i]>arr[i])
		defult=defult-1;
	else
		break;
}
else
	break;
}
	if(defult>100) 
		defult=100;
	else if(defult<0)
		defult=0;
	score.add(defult);
}
int defult2=0;
defult2=100-Integer.parseInt(score.get(1).toString());
/*score.add(Math.round(100-(100*((Float.parseFloat(sql_TC)-1.070)/0.7378))));
score.add(Math.round(100-(100*(Float.parseFloat(sql_HDL)-0.937))/0.721));
score.add(Math.round(100-(100*(Float.parseFloat(sql_LDL)-1.145))/0.4298));
score.add(Math.round(100-(100*(Float.parseFloat(sql_body)-0.969))/0.5362));
score.add(Math.round(100-(100*((Float.parseFloat(sql_trig)-2.406)/2.5256))));
score.add(Math.round(100-(100*(Float.parseFloat(sql_glucose)-1.165))/1.1942));
score.add(Math.round(100-(100*(Float.parseFloat(sql_pressure)-1.398))/1.1256));
score.add(Math.round(100-(100*(Float.parseFloat(sql_skin)-0.801))/0.4466));
score.add(Math.round(100-(100*(Float.parseFloat(sql_male)-1.081))/0.6706));
score.add(Math.round(100-(100*(Float.parseFloat(sql_areata)-0.661))/0.6146));*/

 
 //pdf 생성을  위한 html작성 
// 1 페이지

		String htmlStr ="<html>"
                +"<head>"
                +"</head>"
              //글씨체
                + "<body style='font-family: NanumGothicBold;'class='down'>"
               //상단 타이틀
                +"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\DNA\\title0.jpg'/>"
       
                +"<div class='toptitle'>"
                //대제목 구분선
                +"<div class='line'>"
                +"</div>"
                
                +"<div class='line2'>"
                +"<p class='text3'>&nbsp;&nbsp;유전자 검사 결과 그래프</p>"
                +"</div>"
                
                +"</div>"
                //그래프이미지
                
                +"<img class='chart' src='"+mix.addfolder()+"\\(img)"+id+".jpeg'></img>"
              
                
+"<div class='toptitle2'>"
/* +"<div class='line'>"
	+"</div>"
				
				  +"<div class='line2'>"
				  +"<p class='text3'><span style='font-size:20px'>&nbsp;&nbsp;당신의 유전자 <span style='font-weight:bold;'>레벨은?</span>&nbsp;&nbsp;<span style='font-size:25px;color:#fdbf6a;font-weight:bold;'>Levle 2 </span></span></p>"
				  +"</div>"
				 */
			+"</div>"
			//구분 중간라인
		+"<div class='line3' ></div>"

+"<div class='toptitle3'>"

+"<div class='line'>"
+"</div>"

+"<div class='line2'>"
+"<p class='text3'>&nbsp;&nbsp;&nbsp;유전자 검사 결과표</p>"
+"</div>"

+"</div>"
//결과표 
+"<table class='table1'> "
+ "<tbody class='simple_table'>"
+  "<tr class='tr1'> "+

"	<th class='th1' colspan='2' width:'50%'> "+
"	검사 항목"+
"	</th>"+
"	<th class='th1' width='80px'>"+
"	나의 유전자 수치"+
"	</th>"+
"	<th class='th1' width='80px'>"+
"	검사 결과"+
"	</th>"+
"	<th class='th1' width='80px'>"+
"	나의 점수"+
"	</th>"+

"	</tr>"+

"     <tr class='tr1'>"+
"	        <td class='td1' width= '100px' rowspan='3'><font size='3'>콜레스테롤</font></td>"+
"           <td class='td1'> <font size='3'>총콜레스테롤(Total Cholesterol)</font></td>"+
"           <td class='td1-1'  text-align='center'>"+sql_TC+"</td>"+
"            <td class='td1-1' >"+sql_TC_result+" &nbsp;&nbsp; <span style='color:"+color_TC+";'>"+TC+"</span></td>"+
"            <td class='td1-1' >"+score.get(0).toString()+"점</td>"+
"	      </tr>"+

"       <tr  class='tr1'>"+
"	         <td class='td1'><font size='3'>고밀도 콜레스테롤(HDL Cholesterol)</font></td>"+
"            <td class='td1-1' text-align='center'> "+sql_HDL+"</td>"+
"            <td class='td1-1' >"+sql_HDL_result+" &nbsp;&nbsp; <span style='color:"+color_HDL+";'>"+HDL+"</span></td>"+
"            <td class='td1-1' > "+defult2+"점</td>"+
"	        </tr>"+

"	        <tr class='tr1'>"+
"	           <td class='td1'><font size='3'>저밀도 콜레스테롤(LDL Cholesterol)</font></td>"+
"           <td class='td1-1' text-align='center'> "+sql_LDL+"</td>"+
"            <td class='td1-1' >"+sql_LDL_result+"&nbsp;&nbsp; <span style='color:"+color_LDL+";'>"+LDL+"</span></td>"+
"            <td class='td1-1' > "+score.get(2).toString()+"점</td>"+
"    </tr>"+

" <tr class='tr1'>"+
"  <td class='td1' colspan='2'><font size='3' >체질량 지수 (Body Mass Index, BMI)</font></td>"+
"           <td class='td1-1' text-align='center'> "+sql_body+"</td>"+
"           <td class='td1-1' >"+sql_body_result+"&nbsp;&nbsp; <span style='color:"+color_body+";'>"+body+"</span></td>"+
"   <td class='td1-1' > "+score.get(3).toString()+"점</td>"+
"  </tr>"+

"   <tr class='tr1'>"+
"  <td class='td1' colspan='2'><font size='3'>중성지방 (Triglycerides)</font></td>"+
"           <td class='td1-1' text-align='center'> "+sql_trig+"</td>"+
"          <td class='td1-1' >"+sql_trig_result+"&nbsp;&nbsp; <span style='color:"+color_trig+";'>"+trig+"</span></td>"+
"    <td class='td1-1' > "+score.get(4).toString()+"점</td>"+
"   </tr>"+

"   <tr class='tr1'>"+
"  <td class='td1' colspan='2'><font size='3' >혈당(Blood Glucose)</font></td>"+
"           <td class='td1-1' text-align='center'> "+sql_glucose +"</td>"+
"           <td class='td1-1' >"+sql_glucose_result+"&nbsp;&nbsp; <span style='color:"+color_glucose+";'>"+glucose+"</span></td>"+
"   <td class='td1-1' > "+score.get(5).toString()+"점</td>"+
"  </tr>"+
" <tr class='tr1'>"+
"  <td class='td1' colspan='2'><font size='3' >혈압(Blood Pressure)</font></td>"+
"          <td class='td1-1' text-align='center'> "+sql_pressure+"</td>"+
"         <td class='td1-1' >"+sql_pressure_result+" &nbsp;&nbsp; <span style='color:"+color_pressure+";'>"+pressure+"</span></td>"+
"   <td class='td1-1' > "+score.get(6).toString()+"점</td>"+
"  </tr>"+
"  <tr class='tr1'>"+
"  <td class='td1' colspan='2'><font size='3' >피부색소침착 (Skin Pigmentation)</font></td>"+
"         <td class='td1-1' text-align='center'> "+sql_skin+"</td>"+
"         <td class='td1-1' >"+sql_skin_result+"&nbsp;&nbsp;<span style='color:"+color_skin+";'>"+skin+"</span></td>"+
"  <td class='td1-1' > "+score.get(7).toString()+"점</td>"+
"  </tr>"+

"  <tr class='tr1'>"+
"    <td class='td1' colspan='2'><font size='3' >남성형 탈모 (Male Pattern Baldness)</font></td>"+
"           <td class='td1-1' text-align='center'> "+sql_male+"</td>"+
"           <td class='td1-1' >"+sql_male_result+"&nbsp;&nbsp; <span style='color:"+color_male+";'>"+male+"</span></td>"+
"     <td class='td1-1' > "+score.get(8).toString()+"점</td>"+
"   </tr>"+

"  <tr class='tr1'>"+
"  <td class='td1' colspan='2'><font size='3' >원형 탈모 (Alopecia Areata)</font></td>"+
"           <td class='td1-1' text-align='center'> "+sql_areata+"</td>"+
"           <td class='td1-1' >"+sql_areata_result+"&nbsp;&nbsp;<span style='color:"+color_areata+";'>"+areata+"</span></td>"+
"    <td class='td1-1' > "+score.get(9).toString()+"점</td>"+
" </tr>"+

"  <tr class='tr1'>"+
"<td class='td1' colspan='2'><font size='3'>카페인 대사(Caffeine Metabolism)</font></td>"+
"           <td class='td1-1' colspan='2' > "+sql_CaffeineMetabolism_result+"&nbsp;&nbsp; <span style='color:"+color_CaffeineMetabolism+";'>"+CaffeineMetabolism+"</span></td>"+
"              <td class='td1-1' > -</td>"+
"  </tr>"+

"    <tr class='tr1'>"+
"   <td class='td1' colspan='2'><font size='3'>피부 노화 (Skin Aging)</font></td>"+
"           <td class='td1-1' colspan='2' > "+sql_SkinAging_result+"&nbsp;&nbsp; <span style='color:"+color_SkinAging+";'>"+SkinAging+"</span></td>"+
"               <td class='td1-1' > -</td>"+
"  </tr>"+

"   <tr class='tr1'>"+
"  <td class='td1' colspan='2'><font size='3'>피부 탄력 (Skin Elasticity)</font></td>"+
"           <td class='td1-1' colspan='2' > "+sql_SkinElasticity_result+"&nbsp;&nbsp;</td>"+
"               <td class='td1-1'> -</td>"+
"  </tr>"+

"   <tr class='tr1'>"+
" <td class='td1' colspan='2'><font size='3'>모발 굵기 (Hair Thickness)</font></td>"+
"           <td class='td1-1' colspan='2' > "+sql_HairThickness_result+"&nbsp;&nbsp;</td>"+
"               <td class='td1-1'> - </td>"+
" </tr>"+

"  <tr class='tr1'>"+
"  <td class='td1' colspan='2'><font size='3'>비타민 C (Vitamin C)</font></td>"+
"            <td class='td1-1' colspan='2' > "+sql_VitaminC_result+"&nbsp;&nbsp;<span style='color:"+color_VitaminC+";'>"+VitaminC+"</span></td>"+
"               <td class='td1-1'  > - </td>"+
"</tr>"+

"	    </tbody>"+
"</table>"

//2페이지
//상단 타이틀
+"<div>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\DNA PDF관련\\DNA키트리뉴얼/1_상단.jpg' style='padding-bottom:10px;' >"
+"</img>"
+"</div>"
+"<div style='margin:20px;height:100%;position:relative;'>"
//사람이미지

+"<div class='human'>"
 +"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_사람2.jpg' style='hegiht:1200px;width:400px;position:fixed;' >"+"</img>" 
 +"<div class='red_point'style='padding-left:"+(180-500+left[0])+"px;padding-bottom:"+760+";'>"
 +"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(190-500+left[1])+"px;padding-bottom:"+970+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(230-500+left[2])+"px;padding-bottom:"+785+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(285-500+left[3])+"px;padding-bottom:"+860+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_혈관.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(92-500+left[4])+"px;padding-bottom:"+1000+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(170-500+left[5])+"px;padding-bottom:"+1650+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(190-500+left[6])+"px;padding-bottom:"+2600+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(227-500+left[7])+"px;padding-bottom:"+4750+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(190-500+left[8])+"px;padding-bottom:"+8600+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(165-500+left[9])+"px;padding-bottom:"+16550+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(190-500+left[10])+"px;padding-bottom:"+33050+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
+"<div class='red_point'style='padding-left:"+(190-500+left[11])+"px;padding-bottom:"+65570+";'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;hegiht:30px;'>"+"</img>" 
+"</div>"
 +"</div>"


				/*
				  +"<div class='red_point'style='padding-top:0px;padding-right:0px;padding-left:"
				  +Integer.parseInt(left.get(3).toString())+"px;padding-bottom:"+Integer.
				  parseInt(bottom.get(3).toString())+"px;'>"
				  +"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\"+img.get(3).toString()+"
				  .png' style='width:35px;hegiht:35px;'>"+"</img>" +"</div>"
				 */


//이미지 우측 글씨부분 전체
 +"<div style='float:left;height:100%;position:absolute;'>"
 
//우측 검사결과 한 부분
 +"<div class='block'>"
 //검사결과+돋보기
+"<div class='sub' style='padding-top:60px;'>"
 
 +"<div style='float:left;' >"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\돋보기아이콘.png' style='width:30px;height:30px;'>" + "</img>"
+"</div>"

 +"<div class='result_title'>" 
 +"<p>&nbsp;&nbsp;검사 결과</p>"  
 + "</div>"
 
 +"</div>"
 //검사결과 텍스트
 +"<div class='sub2'>"
 +"<p style='font-size:12px;font-weight:lighter;line-height:200%;'><span style='font-weight:bold;font-size:20px;'>"+name+" </span>님은</p>"
+"</div>"
 
+"<div class='sub2'>"
+"<p style='font-size:12px;font-weight:lighter;line-height:200%;'>"+data.get(0)+"&nbsp;"+data.get(1)+"&nbsp;"+data.get(2)+"&nbsp;"+data.get(3)+"&nbsp;"+data.get(4)+"에 대해 "
		+ "주의가 필요하며, 검사 결과를 통해 발생 가능한 합병증과 몸이 느낄 수 있는 증상을 나타냅니다.</p>"
+"</div>"
		
+"</div>"

//합병증 부분
+"<div class='block2'>"
//합병증 소제목
+"<div class='sub'>"

 +"<div style='float:left;' >"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\돋보기아이콘.png' style='width:30px;height:30px;'>" + "</img>"
+"</div>"
 
 +"<div class='result_title'>"
 +"<p>&nbsp;&nbsp;검사 항목에 따른 합병증</p>"  
 + "</div>"
 
 +"</div>"
 //합병증 텍스트
 +"<div class='sub2'>"
 +"<p style='line-height:200%;'><span style='font-weight:bold;font-size:20px;'>합병증 </span></p>"
+"<p style='font-size:12px;font-weight:lighter;line-height:200%;'>"
 +complication[0]+"&nbsp;"+complication[1]+"&nbsp;"+complication[2]+"&nbsp;"+complication[3]+"&nbsp;"+complication[4]+"&nbsp;"+
complication[5]+"&nbsp;"+complication[6]+"&nbsp;"+complication[7]+"&nbsp;"+complication[8]+"&nbsp;"+complication[9]
		+"&nbsp;"+complication[10]+"&nbsp;"+complication[11]+"&nbsp;"+complication[12]
+"</p>"
+"</div>"
//이상증상 텍스트
+"<div class='sub2'>"
+"<p style='line-height:200%;'><span style='font-weight:bold;font-size:20px;'>이상증상 </span></p>"
+"<p style='font-size:12px;font-weight:lighter;line-height:200%;'>"
+symptom[0]+"&nbsp;"+symptom[1]+"&nbsp;"+symptom[2]+"&nbsp;"+symptom[3]+"&nbsp;"+symptom[4]+"</p>"
+"</div>"

+"</div>"
//위치안내 한부분
 +"<div class='block2' style='height:250px;'>"
 
+"<div class='sub'>"
 +"<div style='float:left;' >"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\1_빨간점.png' style='width:30px;height:30px;'>" + "</img>"
+"</div>"

 +"<div class='result_title'>" 
 +"<p>&nbsp;&nbsp;검사 결과를 통해 발생 가능한 합&nbsp;&nbsp;병증과 이상증상의 위치를 나타&nbsp;&nbsp;냅니다.</p>"  
 + "</div>"
 
 +"</div>"
 //안내 텍스트
+"<div class='sub2'>"
+"<p style='font-size:12px;font-weight:lighter;line-height:200%;'>***<br />본 검사는 질병의 유전적 위험도를 예측하는 검사입니다. 실제 위험도는 생활습관 및 환경적 요인 등에 의해 달라질 수 있으며, 정확한 진단을 위해서는 전문의의 상담이 필요합니다.</p>"
+"</div>"

+"</div>"

+"</div>"
+"</div>"
	

//3페이지
/*
 //상단 타이틀
+"<img src='http://best54.cafe24.com/medi/downloadFile/title0.jpg'>"
+"</img>"
//상단 설명 네모칸
+"<div class='level'>"
+"<div class='level_text'><p>네가지 레벨이란?</p></div>"
+"<div class='level_text2'><p>항목별 유전자에 점수를 매겨 각각 뷰티, 헬스, 모발쪽 점수를 나타내 수검자의 관리 중요성과 솔루션 방향을 나타냅니다." 
+"	각 레벨별, 항목별, 점수별 식단추천, 제품추천, 관리항목이 달라지니 평소 집중적인 관리에 도움이 되고자 합니다.</p></div>"
+"</div>"
//수평선
+"<hr class='hr1'></hr>"
//레벨1
+" <table class='hidden'>" 
+"<tr>"
//소제목부분
+"<td class='td3'>"
+"<img src='http://best54.cafe24.com/medi/downloadFile/Level1.png' class='level1'>"
+"</img>"
+"</td>"
+"<td class='yang_td'>"
+"<div>"
+"<div class='level1_title'>"
+"<p>Level  1</p>"
+"</div>"

+"<div style='float:left;'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\DNA\\Level1네모.png' style='width:100px;height:20px;'></img>"
+"</div>"

+"</div>"
//본문 텍스트
+"<p class='consti_p'>"
+"level 1는 dna검사 결과 health, hair, skin 분야 모두 양호하며, 각각의 유전자를 분석했을때 위험인자 유전자형이 많지 않습니다. \r\n" + 
"유전자 상으로는 질병이나 피부, 모발 전반적으로 이상증상이 생길 가능성이 낮다고 판단됩니다. 12가지 검사분야에 유전자 결과가 대부분 평균치를\r\n" + 
"가지고 있습니다. 하지만 질병이나 외형은 유전적 요인과 생활방식, 환경적 요인의 복합적인 상호작용에 의해 최종적으로 결정되니 생활방식과\r\n" + 
"환경적 요인도 중요합니다." 
+"</p>"
//구분선
+"<hr width='100%'></hr>"
+"</td>"
+"</tr>"
+"</table>"

//레벨2
+" <table class='hidden'>" 
+"<tr><td class='td3'>"
+"<img src='http://best54.cafe24.com/medi/downloadFile/Level2.png' class='level1'>"
+"</img>"
+"</td>"
+"<td class='yang_td'>"
+"<div>"
+"<div class='level1_title'>"
+"<p style='color:#fdbf6a;'>Level  2</p></div>"
+"<div style='float:left;'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\DNA\\Level2네모.png'style='width:100px;height:20px;'></img></div>"
+"</div>"
+"<p class='consti_p'>"
+"level 2는 dna검사 결과  health, hair, skin 분야 에서 관심이 있으며, 각각의 유전자를 분석했을때 위험인자 유전자형이 섞여있습니다.\r\n" + 
"유전자 상으로는 질병이나 피부, 모발 부분에서 이상증상이 생길 가능성이 있다고 판단됩니다. 12가지 검사분야중 관심분야에 유전자 결과가 평균이나 높음을 가지고 있습니다.\r\n" + 
"질병이나 외형은 유전적 요인과 생활방식, 환경적 요인의 복합적인 상호작용에 의해 최종적으로 결정되니 관심분야에 대해서 관리가 필요합니다. \r\n" + 
"생활방식, 식단, 운동 등을 통해서 미리 예방하는 것을 추천합니다." 
+"</p>"
+"<hr width='100%'></hr>"
+"</td>"
+"</tr>"
+"</table>"	

//레벨3
+" <table class='hidden'>" 
+"<tr><td class='td3'>"
+"<img src='http://best54.cafe24.com/medi/downloadFile/Level3.png' class='level1'>"
+"</img>"
+"</td>"
+"<td class='yang_td'>"
+"<div>"
+"<div class='level1_title'>"
+"<p style='color:#f3743e;'>Level  3</p></div>"
+"<div style='float:left;'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\DNA\\Level3네모.png'style='width:100px;height:20px;'></img></div>"
+"</div>"
+"<p class='consti_p'>"
+"level 3는 dna검사 결과  health, haitr, skin 분야 에서 주의가 있으며, 각각의 유전자를 분석했을때 위험인자 유전자형이 섞여있습니다.\r\n" + 
"유전자 상으로는 질병이나 피부, 모발 부분에서 이상증상이 생길 가능성이 비교적 있다고 판단됩니다. 12가지 검사분야중 관심, 주의분야에 유전자 결과가 평균이나 높음을 가지고 있습니다.\r\n" + 
"질병이나 외형은 유전적 요인과 생활방식, 환경적 요인의 복합적인 상호작용에 의해 최종적으로 결정되니 관심, 주의분야에 대해서 철저한 관리가 필요합니다. \r\n" + 
"생활방식, 식단, 운동, 제품 등을 통해서 미리 예방하는 것을 추천합니다." 
+"</p>"
+"<hr width='100%'></hr>"
+"</td>"
+"</tr>"
+"</table>"

//레벨4
+" <table class='hidden'>" 
+"<tr><td class='td3'>"
+"<img src='http://best54.cafe24.com/medi/downloadFile/Level4.png' class='level1'>"
+"</img>"
+"</td>"
+"<td class='yang_td'>"
+"<div>"
+"<div class='level1_title'>"
+"<p style='color:#f15b4e;'>Level  4</p></div>"
+"<div style='float:left;'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\DNA\\Level4네모.png' style='width:100px;height:20px;'></img></div>"
+"</div>"
+"<p class='consti_p'>"
+"level 4는 dna검사 결과  health, hair, skin 분야 에서 주의가 있으며, 각각의 유전자를 분석했을때 위험인자 유전자형이 상당수 섞여있습니다.\r\n" + 
"유전자 상으로는 질병이나 피부, 모발 부분에서 이상증상이 생길 가능성이 비교적 높다고 판단됩니다. 12가지 검사분야중 관심, 주의분야에 유전자 결과가 평균이나 높음을 가지고 있습니다.\r\n" + 
"질병이나 외형은 유전적 요인과 생활방식, 환경적 요인의 복합적인 상호작용에 의해 최종적으로 결정되니 관심, 주의분야에 대해서 철저한 관리가 필요합니다. \r\n" + 
"생활방식, 식단, 운동, 제품, 상담 등을 통해서 미리 예방하는 것을 추천합니다." 
+"</p>"
//+"<hr width='100%'></hr>"
+"</td>"
+"</tr>"
+"</table>"

*/

//4페이지
//상단타이틀
//+"<img style='padding-top:15px;' src='http://best54.cafe24.com/medi/downloadFile/title0.jpg'>"
+"<img style='padding-top:15px;' src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\DNA\\title0.jpg'>"
+"</img>"

+"<div style='padding-top:20px;' class='toptitle4'>"
//소제목 라인선
+"<div class='line'>"
+"</div>"

+"<div class='line2'>"
+"<p class='text3'>&nbsp;&nbsp;위험 유전자 결과표</p>"
+"</div>"

+"</div>"

//본문 테이블
+"<div style='padding-bottom:80px;'>"
+" <table class='table2'>" + 
"    <tr><th>검사 항목</th><th>유전자</th><th>위험인자</th><th>수검자 유전자</th><th>결과</th></tr>" + 

"    <tr><td>"+bmi.get(0).toString()+"</td><td><font size='4'>"+name_gene.get(0).toString()+"</font> "+explanation.get(0).toString()+"</td><td>"+risk.get(0).toString()+"</td><td>"+genotype.get(0).toString()+"</td><td >"+marker_result.get(0).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[0]+"</span></td></tr>" + 
"   <tr><td>"+bmi.get(1).toString()+"</td><td><font size='4'>"+name_gene.get(1).toString()+"</font> "+explanation.get(1).toString()+"</td><td>"+risk.get(1).toString()+"</td><td>"+genotype.get(1).toString()+"</td><td class='danger'>"+marker_result.get(1).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[1]+"</span></td></tr>" + 
"<tr><td>"+bmi.get(2).toString()+"</td><td><font size='4'>"+name_gene.get(2).toString()+"</font> "+explanation.get(2).toString()+"</td><td>"+risk.get(2).toString()+"</td><td>"+genotype.get(2).toString()+"</td><td class='danger'>"+marker_result.get(2).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[2]+"</span></td></tr>" +
"<tr><td>"+bmi.get(3).toString()+"</td><td><font size='4'>"+name_gene.get(3).toString()+"</font> "+explanation.get(3).toString()+" </td><td>"+risk.get(3).toString()+"</td><td>"+genotype.get(3).toString()+"</td><td class='danger'>"+marker_result.get(3).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[3]+"</span></td></tr>" +
"    <tr><td>"+bmi.get(4).toString()+"</td><td><font size='4'>"+name_gene.get(4).toString()+"</font> "+explanation.get(4).toString()+"</td><td>"+risk.get(4).toString()+"</td><td>"+genotype.get(4).toString()+"</td><td class='danger'>"+marker_result.get(4).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[4]+"</span></td></tr>" + 
"   <tr><td>"+bmi.get(5).toString()+"</td><td><font size='4'>"+name_gene.get(5).toString()+"</font> "+explanation.get(5).toString()+"</td><td>"+risk.get(5).toString()+"</td><td>"+genotype.get(5).toString()+"</td><td class='danger'>"+marker_result.get(5).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[5]+"</span></td></tr>" + 
"<tr><td>"+bmi.get(6).toString()+"</td><td><font size='4'>"+name_gene.get(6).toString()+"</font> "+explanation.get(6).toString()+"</td><td>"+risk.get(6).toString()+"</td><td>"+genotype.get(6).toString()+"</td><td class='danger'>"+marker_result.get(6).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[6]+"</span></td></tr>" +
"<tr><td>"+bmi.get(7).toString()+"</td><td><font size='4'>"+name_gene.get(7).toString()+"</font> "+explanation.get(7).toString()+"</td><td>"+risk.get(7).toString()+"</td><td>"+genotype.get(7).toString()+"</td><td class='danger'>"+marker_result.get(7).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[7]+"</span></td></tr>" +
"   <tr><td>"+bmi.get(8).toString()+"</td><td><font size='4'>"+name_gene.get(8).toString()+"</font> "+explanation.get(8).toString()+"</td><td>"+risk.get(8).toString()+"</td><td>"+genotype.get(8).toString()+"</td><td class='danger'>"+marker_result.get(8).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[8]+"</span></td></tr>" + 
"<tr><td>"+bmi.get(9).toString()+"</td><td><font size='4'>"+name_gene.get(9).toString()+"</font> "+explanation.get(9).toString()+"</td><td>"+risk.get(9).toString()+"</td><td>"+genotype.get(9).toString()+"</td><td class='danger'>"+marker_result.get(9).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[9]+"</span></td></tr>" +
"<tr><td>"+bmi.get(10).toString()+"</td><td><font size='4'>"+name_gene.get(10).toString()+" </font> "+explanation.get(10).toString()+"</td><td>"+risk.get(10).toString()+"</td><td>"+genotype.get(10).toString()+"</td><td class='danger'>"+marker_result.get(10).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[10]+"</span></td></tr>" +
"    <tr><td>"+bmi.get(11).toString()+"</td><td><font size='4'>"+name_gene.get(11).toString()+"</font> "+explanation.get(11).toString()+"</td><td>"+risk.get(11).toString()+"</td><td>"+genotype.get(11).toString()+"</td><td class='danger'>"+marker_result.get(11).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[11]+"</span></td></tr>" + 
"    <tr><td>"+bmi.get(12).toString()+"</td><td><font size='4'>"+name_gene.get(12).toString()+"</font> "+explanation.get(12).toString()+"</td><td>"+risk.get(12).toString()+"</td><td>"+genotype.get(12).toString()+"</td><td class='danger'>"+marker_result.get(12).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[12]+"</span></td></tr>" + 
"    <tr><td>"+bmi.get(13).toString()+"</td><td><font size='4'>"+name_gene.get(13).toString()+"</font> "+explanation.get(13).toString()+"</td><td>"+risk.get(13).toString()+"</td><td>"+genotype.get(13).toString()+"</td><td class='danger'>"+marker_result.get(13).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[13]+"</span></td></tr>" +
"    <tr><td>"+bmi.get(14).toString()+"</td><td><font size='4'>"+name_gene.get(14).toString()+"</font> "+explanation.get(14).toString()+"</td><td>"+risk.get(14).toString()+"</td><td>"+genotype.get(14).toString()+"</td><td class='danger'>"+marker_result.get(14).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[14]+"</span></td></tr>" + 
"    <tr><td>"+bmi.get(15).toString()+"</td><td><font size='4'>"+name_gene.get(15).toString()+"</font> "+explanation.get(15).toString()+"</td><td>"+risk.get(15).toString()+"</td><td>"+genotype.get(15).toString()+"</td><td class='danger'>"+marker_result.get(15).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[15]+"</span></td></tr>" + 
"    <tr><td>"+bmi.get(16).toString()+"</td><td><font size='4'>"+name_gene.get(16).toString()+"</font> "+explanation.get(16).toString()+"</td><td>"+risk.get(16).toString()+"</td><td>"+genotype.get(16).toString()+"</td><td class='danger'>"+marker_result.get(16).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[16]+"</span></td></tr>" + 
"    <tr><td>"+bmi.get(17).toString()+"</td><td><font size='4'>"+name_gene.get(17).toString()+"</font> "+explanation.get(17).toString()+"</td><td>"+risk.get(17).toString()+"</td><td>"+genotype.get(17).toString()+"</td><td class='danger'>"+marker_result.get(17).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[17]+"</span></td></tr>" + 
"    <tr><td>"+bmi.get(18).toString()+"</td><td><font size='4'>"+name_gene.get(18).toString()+"</font> "+explanation.get(18).toString()+"</td><td>"+risk.get(18).toString()+"</td><td>"+genotype.get(18).toString()+"</td><td class='danger'>"+marker_result.get(18).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[18]+"</span></td></tr>" + 
"    <tr><td>"+bmi.get(19).toString()+"</td><td><font size='4'>"+name_gene.get(19).toString()+"</font> "+explanation.get(19).toString()+"</td><td>"+risk.get(19).toString()+"</td><td>"+genotype.get(19).toString()+"</td><td class='danger'>"+marker_result.get(19).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[19]+"</span></td></tr>" + 
"    <tr><td>"+bmi.get(20).toString()+"</td><td><font size='4'>"+name_gene.get(20).toString()+"</font> "+explanation.get(20).toString()+"</td><td>"+risk.get(20).toString()+"</td><td>"+genotype.get(20).toString()+"</td><td class='danger'>"+marker_result.get(20).toString()+"<span style='color:#e55346;'>&nbsp;&nbsp;&nbsp;"+arrow[20]+"</span></td></tr>" + 

"  </table>"

+"</div>"

				/*
				 * +"<div class='toptitle3'>" +"<div class='line'>" +"</div>"
				 * +"<div class='line2'>" +"<p class='text3'>&nbsp;&nbsp;수검자 총평</p>" +"</div>"
				 * +"</div>" +" <table class='table3'>" +"<tr><td class='td3'>"
				 * +"<img src='http://best54.cafe24.com/medi/downloadFile/level2.png' class='consti'>"
				 * +"</img>" +"</td>" +"<td class='td3-1'>" +"<p class='consti_p'>"
				 * +"홍길동 님은 폭식이나 과식을 할 것으로 예상되며 식사 전 충분한 수분 섭취와 규칙적인 식사가 중요합니다. 혈장 내의 중성지방 수치는 정상입니다. 하지만 지질대사에 관여하는 유전자들이 높음 수준을 나타내므로 "
				 * +
				 * "중성지방이 쉽게 쌓일 가능성이 있습니다. 따라서 규칙적인 운동을 하시는 걸 추천합니다. 콜레스테롤은 비교적 평균 수준을 보여 관리 수준을 추천합니다. 혈액 내 포도당이 평균보다 많이 분비됩니다. 때문에 인슐린과 같은 혈당 조절 호르몬이 중요합니다. 하지만 홍길동 님은 인슐린 생산 관여 유전자와 지방 대사 유전자, 혈당 조절 유전자가 높음 수준입니다. "
				 * +
				 * "평소 식단 관리를 철저히 하여야 하며 규칙적인 식사와 꾸준한 운동을 추천합니다. 혈압은 평균을 보입니다. 하지만 고혈압을 일으킬 수 있는 유전자가 높음 수준에 있습니다. 저혈압보다는 고혈압을 주의하며 평소 미지근한 물을 자주 마시는 것을 추천합니다."
				 * +
				 * "피부의 재생성, 노화, 수분에 관여하는 콜라겐을 분해하는 유전자가 높음 수준에 있습니다. 이는 콜라겐이 빠르게 분해되어 노화 촉진, 재생성 감소, 탄력감소 등 피부 전반적으로 안 좋은 영향을 미칠 수 있습니다. 따라서 평소 물을 자주 마시고 햇빛 노출을 삼가며 꾸준한 관리를 요구합니다."
				 * +
				 * " 색소침착과 관련된 멜라닌 생성 유전자가 높음 수준에 있습니다. 이는 기미,주근 깨, 피부암 등을 발생시킬 수 있습니다. 평소 자외선 노출을 자제하고 선크림, 팩으로 관리를 추천합니다. 남성형 탈모 발생 가능성은 평균입니다. 하지만 높음 유전자가 있으니 관심을 추천합니다. 혈장 내 비타민c 농도가 낮습니다. 비타민c를 체내로 전달하는 수송체 유전자가 높음 수준입니다. "
				 * + "많은 양의 비타민보다는 매일 꾸준한 비타민 섭취를 추천합니다. " +"</p>" +"</td>" +"</tr>" +"</table>"
				 */

//4페이지
//상단 타이틀

//+"<img src='http://best54.cafe24.com/medi/downloadFile/title1.jpg'></img>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\DNA\\title1.jpg'></img>"
+"<div style='height:100%;'>"
//맞춤검사결과 부분
 +"<div class='4page_block'>"
 //소제목 부분
+"<div class='sub1_1' style='padding-top:10px;'>"
 
 +"<div style='float:left;' >"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\돋보기아이콘.png' style='width:30px;height:30px;'>" + "</img>"
 +"</div>"

 +"<div class='result_title'>"
 +"<p>&nbsp;&nbsp;맞춤 분석 검사 결과</p>"  
 + "</div>"
 
 +"</div>"
 //본문 텍스트
 +"<div class='sub2_2'>"
 +"<p style='font-size:15px;font-weight:lighter;line-height:200%;'><span style='font-weight:bold;font-size:20px;'>"+name+" </span>님</p>"
+"</div>"
+"<div class='sub2_2'>"
+"<p style='font-size:15px;font-weight:lighter;line-height:200%;'>유전자 분석 결과를 토대로 "+name+"님에게 좋은 식품, 영양소와 나쁜 식품, 영양소를 추천해드립니다.</p>"
+"<p style='font-size:15px;font-weight:lighter;line-height:200%;'>제품추천은 분석 결과 섭취하면 좋을 제품으로 구성되었으며 꾸준히 드시는것을 권유해 드립니다.</p>"
+"</div>"

+"</div>"

//추천식품
+"<div class='4page_block'>"

+"<div class='sub3_3'>"
//소제목
+"<div>"

 +"<div style='float:left;' >"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\돋보기아이콘.png' style='width:30px;height:30px;'>" + "</img>"
+"</div>"

 +"<div class='result_title' >"
 +"<p>&nbsp;&nbsp;추천 식품 <span style='font-weight:bold;font-size:20px;color:green;'>GOOD</span></p>" 
 + "</div>"
 
 +"</div>"
 //본문
 +"<div style='padding_top:10px;padding-left:40px;padding-right:40px'>"
 +"<p style='font-size:15px;font-weight:lighter;line-height:200%;'>"+good_material[0]+"&nbsp;"+good_material[1]+"&nbsp;"
 +good_material[2]+"&nbsp;"+good_material[3]+"&nbsp;"+good_material[6]+"&nbsp;"+good_material[8]+"&nbsp;"+good_material[10]+"&nbsp;"+good_material[12]+"&nbsp;"+good_material[13]
 +"&nbsp;"+good_material[14]+"&nbsp;"+good_material[4]
+"</p>"
 +"</div>"

 +"</div>"
 
 //비추천 식품
 +"<div style='float:left;'>"
 
 +"<div style='padding-top:30px;'>"
 
  +"<div style='float:left;' >"
 +"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\돋보기아이콘.png' style='width:30px;height:30px;'>" + "</img>"
  +"</div>"
 
  +"<div class='result_title'>"
  +"<p>&nbsp;&nbsp; 비추천 식품 <span style='font-weight:bold;font-size:20px;color:red;'>BED</span></p>"
  +"</div>"
  
 + "<div style='padding_top:10px;padding-left:40px;padding-right:40px'>"
  +"<p style='font-size:15px;font-weight:lighter;line-height:200%;'>"+bad_material[0]+"&nbsp;"+bad_material[1]+"&nbsp;"
  +bad_material[2]+"&nbsp;"+bad_material[3]+"&nbsp;"+bad_material[4]+"&nbsp;"+bad_material[5]+"&nbsp;"+bad_material[6]+"&nbsp;"+bad_material[7]+"&nbsp;"+bad_material[8]
 +"&nbsp;"+bad_material[9]+"&nbsp;"+bad_material[10]
 +"</p>"
 + "</div>"
 
  +"</div>"
  
  +"</div>"
+"</div>"//전

//추천 영양소
+"<div class='4page_block'>"//전
//소제목
+"<div class='sub3_3'>"

+"<div>"
 +"<div style='float:left;'>"
+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\돋보기아이콘.png' style='width:30px;height:30px;'>" + "</img>"
 +"</div>"

 +"<div class='result_title'>" 
 +"<p>&nbsp;&nbsp;추천 영양소 <span style='font-weight:bold;font-size:20px;color:green;'>GOOD</span></p>"  
 + "</div>"
 
 +"</div>"
 //본문
 +"<div style='padding-left:40px;padding-right:40px''>"
 +"<p style='font-size:15px;font-weight:lighter;line-height:200%;'>"+good_nutrient[0]+"&nbsp;"+good_nutrient[1]+"&nbsp;"
 +good_nutrient[2]+"&nbsp;"+good_nutrient[3]+"&nbsp;"+good_nutrient[4]+"&nbsp;"+good_nutrient[5]+"&nbsp;"+good_nutrient[6]+"&nbsp;"+good_nutrient[7]+"&nbsp;"+good_nutrient[8]
 +"&nbsp;"+good_nutrient[9]+"&nbsp;"+good_nutrient[10]+"</p>"
 +"</div>"
 
 +"</div>"
 //비추천 영양소
 +"<div style='float:left;'>"
 //소제목
 +"<div style='padding-top:30px;'>"
 
  +"<div style='float:left;' >"
  +"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\돋보기아이콘.png' style='width:30px;height:30px;'>" + "</img>"
  +"</div>"
  
  +"<div class='result_title'>" 
  +"<p>&nbsp;&nbsp;비추천 영양소 <span style='font-weight:bold;font-size:20px;color:red;'>BED</span></p>" 
  + "</div>"
  
  +"</div>"
  //본문
  +"<div style='padding-left:40px;padding-right:30px''>"
  +"<p style='font-size:15px;font-weight:lighter;line-height:200%;'>"+bad_nutrient[0]+"&nbsp;"+bad_nutrient[1]+"&nbsp;"
  +bad_nutrient[2]+"&nbsp;"+bad_nutrient[3]+"&nbsp;"+bad_nutrient[4]+"&nbsp;"+bad_nutrient[5]+"&nbsp;"+bad_nutrient[6]+"&nbsp;"+bad_nutrient[7]+"&nbsp;"+bad_nutrient[8]
		  +"&nbsp;"+bad_nutrient[9]+"&nbsp;"+bad_nutrient[10]+"</p>"
  +"</div>"
		  
  +"</div>"
+"</div>"//전
  
  //맞춤 제품추천
+"<div class='4page_block'>"//전
//소제목
	+"<div class='sub1_1' style='padding-top:30px;'>"
	
	+"<div style='float:left;' >"
	+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\별아이콘.png' style='width:30px;height:30px;'>" + "</img>"
	+"</div>"
	
	+"<div class='result_title'>"
	+"<p>&nbsp;&nbsp;맞춤형 제품추천</p>" 
	+ "</div>"
	
	+"</div>"
	//본문
	+"<div class='sub4'>"
	
	+"<div style='float:left;'>"
	//height 300 티캡슐 내용추가 img 위치 변경, padding-top삭제 
	//이미지1
	+"<div style='width:130px;height:250px;padding-top:20px;'>"
	+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\evertea\\"+evertea[0]+".jpg' style='width:130px;height:220px;'>" 
	+ "</img>"
	+"</div>"
	//이미지1 소제목
	+"<div style='width:130px;padding-bottom:7px;'>"
	+"<p style='text-align:center;font-size:16px;font-weight:bold;line-height:200%;'>"+evertea[0]+"</p>"
	+"</div>"
	//구분선
	+"<div style='padding-left:10px;'>"
	+"<div class='line4_1'>"
	+"</div>"
	+"</div>"
	//본문 내용
	+"<div style='width:130px;'>"
	+"<p style='text-align:center;font-size:12px;font-weight:lighter;line-height:200%;'>"+evertea_material.get(0).toString()+"</p>"
	+"</div>"
	
	+"</div>"//그림 글씨
	//이미지2
	+"<div style='float:left;padding-left:10px;'>"
	
	+"<div style='width:130px;height:250px;padding-top:20px;'>"
	+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\evertea\\"+evertea[1]+".jpg' style='width:130px;height:220px;'>" 
	+ "</img>"
	+"</div>"
	
	+"<div style='width:130px;padding-bottom:7px;'>"
	+"<p style='text-align:center;font-size:16px;font-weight:bold;line-height:200%;'>"+evertea[1]+"</p>"
	+"</div>"
	
	+"<div style='padding-left:10px;'>"
	+"<div class='line4_1'>"
	+"</div>"
	+"</div>"//선
	
	+"<div style='width:130px;'>"
	+"<p style='text-align:center;font-size:12px;font-weight:lighter;line-height:200%;'>"+evertea_material.get(1).toString()+"</p>"
	+"</div>"
	
	+"</div>"//그림 글씨
	
	+"<div style='float:left;padding-left:10px;'>"
	
	+"<div style='width:130px;height:250px;padding-top:20px;'>"
	+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\evertea\\"+evertea[2]+".jpg' style='width:130px;height:220px;'>" 
	+ "</img>"
	+"</div>"
	
	+"<div style='width:130px;padding-bottom:7px;'>"
	+"<p style='text-align:center;font-size:16px;font-weight:bold;line-height:200%;'>"+evertea[2]+"</p>"
	+"</div>"
	
	+"<div style='padding-left:10px;'>"
	+"<div class='line4_1'>"
	+"</div>"
	+"</div>"//선
	
	+"<div style='width:130px;'>"
	+"<p style='text-align:center;font-size:12px;font-weight:lighter;line-height:200%;'>"+evertea_material.get(2).toString()+"</p>"
	+"</div>"
	
	+"</div>"//그림 글씨
	
	//이미지3
	+"<div style='float:left;padding-left:10px;'>"
	+"<div style='width:130px;height:250px;padding-top:20px;'>"
	+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\"+product[0]+".jpg' style='width:130px;height:220px;'>" 
	+ "</img>"
	+"</div>"
	
	+"<div style='width:130px;padding-bottom:7px;'>"+"<p style='text-align:center;font-size:16px;font-weight:bold;line-height:200%;'>"+product[0]+"</p>"+"</div>"
	+"<div style='padding-left:10px;'>"+"<div class='line4_1'>"+"</div>"+"</div>"//선
	+"<div style='width:130px;'>"+"<p style='text-align:center;font-size:12px;font-weight:lighter;line-height:200%;'>"+raw_material[0]+"</p>"+"</div>"
	+"</div>"//그림 글씨
	
	+"<div style='float:left;padding-left:10px;'>"
	
	+"<div style='width:130px;height:250px;padding-top:20px;'>"
	+"<img src='D:\\jooho\\pdf생성 실행파일 관련\\html_image\\pdf_use\\html_image\\"+product[1]+".jpg' style='width:130px;height:220px;'>" 
	+ "</img>"
	+"</div>"
	
	+"<div style='width:130px;padding-bottom:7px;'>"
	+"<p style='text-align:center;font-size:16px;font-weight:bold;line-height:200%;'>"+product[1]+"</p>"
	+"</div>"
	
	+"<div style='padding-left:10px;'>"
	+"<div class='line4_1'>"
	+"</div>"
	+"</div>"//선
	
	+"<div style='width:130px;'>"
	+"<p style='text-align:center;font-size:12px;font-weight:lighter;line-height:200%;'>"+raw_material[1]+"</p>"
	+"</div>"
	
	+"</div>"//그림 글씨
	
	+"</div>"

	+"</div>"//전
	
+"</div>"//전체

+"</body>"+
"</html>";

//html 생성 및 닫기
        StringReader strReader = new StringReader(htmlStr);
        xmlParser.parse(strReader);

        document.close();
        writer.close();

    }
}

