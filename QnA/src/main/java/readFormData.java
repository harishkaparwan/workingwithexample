import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;
import org.docx4j.wml.TcPrInner.GridSpan;


public class readFormData {

	
	public static String FINAL_SAT="SAT";
	public static String FINAL_SUN="sun";
	public static LinkedList getAllData(String ProjectName,String startDate, String endDate, String dailyActivity,String hours)
	{
		LinkedList<LinkedList<String>> linkedList= new LinkedList <LinkedList<String>>();
		
		Arrays arrays;
		
		LinkedList hitList = null;
		try {
			hitList = searchBetweenDates(
			        new SimpleDateFormat("dd-MMM-yyyy").parse(startDate),
			        new SimpleDateFormat("dd-MMM-yyyy").parse(endDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        String[] comboDatesLength = new String[hitList.size()];
	        for(int i=0; i<hitList.size(); i++)
	        	comboDatesLength[i] = new java.text.SimpleDateFormat("E :: dd-MMM-yyyy").format(((java.util.Date)hitList.get(i)));
	            
	        for(int i=0; i<comboDatesLength.length; i++){
		            System.out.println("---comboDatesLength[i]:::::"+comboDatesLength[i]);
		            LinkedList rowList=new LinkedList();
		         StringTokenizer stringTokenizer= new StringTokenizer(comboDatesLength[i],"::");
		         String dateArray[]=comboDatesLength[i].split("::");
		         String day=dateArray[0].toUpperCase().toString();
		         String date=dateArray[1].toString();
		         if(day.contains(new StringBuffer("SAT")) || day.contains(new StringBuffer("SUN"))) {
			            System.out.println("--############SAT#############:::");
 
		         }else if(day.contains(new StringBuffer("FRI")) ) {
		        	 rowList.add(ProjectName);
			         rowList.add(day);
			         rowList.add(date);
			         rowList.add(hours);
			         rowList.add(dailyActivity);
		         linkedList.add(rowList);
			         break;
		         } else{
		         rowList.add(ProjectName);
		         rowList.add(day);
		         rowList.add(date);
		         rowList.add(hours);
		         rowList.add(dailyActivity);
		         linkedList.add(rowList);
		         }
		         }
	        
		return linkedList;
		
		
	}
	
	 public static java.util.LinkedList searchBetweenDates(Date startDate, Date endDate) {
	        
	       Date begin = new Date(startDate.getTime());
	        LinkedList list = new LinkedList();
	        list.add(new Date(begin.getTime()));
	        
	        while(begin.compareTo(endDate)<0){
	            
	            begin = new Date(begin.getTime() + 86400000);
	            list.add(new Date(begin.getTime()));
	        }
	        
	        return list;
	    }
	 
	 public static Tr getLastRow(){
		 WordprocessingMLPackage wordMLPackage = null;

		 org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
		 	 try {
				wordMLPackage = WordprocessingMLPackage.createPackage();
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 Tr trFinalRows = Context.getWmlObjectFactory().createTr();
			Tc tc01 = Context.getWmlObjectFactory().createTc();
			Tc tc11 = Context.getWmlObjectFactory().createTc();
			Tc tc21 = Context.getWmlObjectFactory().createTc();
			Tc tc31 = Context.getWmlObjectFactory().createTc();
			
			TcPr tcPrfinalData = Context.getWmlObjectFactory().createTcPr();
			  TrPr pr31= new TrPr();
	          
			  trFinalRows.setTrPr(pr31);
	         
	          //Tc tc31 = factory.createTc();
	          TblWidth finalwidth31 = new TblWidth();
	          finalwidth31.setType("dxa");
	          finalwidth31.setW(new BigInteger("0"));
	          tcPrfinalData.setTcW(finalwidth31);
	         
	          // for setting colspan of 3
	          GridSpan gridSpan1 = new GridSpan();
	          gridSpan1.setVal(new BigInteger("2"));
	         
	          tcPrfinalData.setGridSpan(gridSpan1);
	         
	          //tc.setTcPr(tcPr3);
	          tc01.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText(""));
	          tc11.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Weekly Total Hours"));
	          tc21.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("16"));
	          tc31.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText(" "));

	          //tr.getEGContentCellContent().add( tc );
			  tc11.setTcPr(tcPrfinalData);
			//  tc2.setTcPr(tcPr);
			
			  trFinalRows.getEGContentCellContent().add(tc01);
			  trFinalRows.getEGContentCellContent().add(tc11);
			  trFinalRows.getEGContentCellContent().add(tc21);
			  trFinalRows.getEGContentCellContent().add(tc31);
			  return trFinalRows;
	 }
	 
	 public static Tr getFirstRow(){
		 WordprocessingMLPackage wordMLPackage = null;
		 Tr firstTableRow = Context.getWmlObjectFactory().createTr();;
		 org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
		 	 try {
				wordMLPackage = WordprocessingMLPackage.createPackage();
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 Tc tc = Context.getWmlObjectFactory().createTc();
			Tc tc1 = Context.getWmlObjectFactory().createTc();
			Tc tc2 = Context.getWmlObjectFactory().createTc();
			Tc tc3 = Context.getWmlObjectFactory().createTc();
			Tc tc4 = Context.getWmlObjectFactory().createTc();
			org.docx4j.wml.ObjectFactory factory1 = Context
					.getWmlObjectFactory();
			TcPr tcPr = Context.getWmlObjectFactory().createTcPr();
			  TrPr pr3 = new TrPr();
           
			  firstTableRow.setTrPr(pr3);
          
           //Tc tc31 = factory.createTc();
           TblWidth width31 = new TblWidth();
           width31.setType("dxa");
           width31.setW(new BigInteger("0"));
           tcPr.setTcW(width31);
          
           // for setting colspan of 3
           GridSpan gridSpan = new GridSpan();
           gridSpan.setVal(new BigInteger("2"));
          
           tcPr.setGridSpan(gridSpan);
          
           //tc.setTcPr(tcPr3);
           tc.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Project"));
           tc1.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Day"));
           tc2.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Date"));
          tc3.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Hours "));
          tc4.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Activity List "));

           //tr.getEGContentCellContent().add( tc );
			 // tc1.setTcPr(tcPr);
			//  tc2.setTcPr(tcPr);
			
          firstTableRow.getEGContentCellContent().add(tc);
          firstTableRow.getEGContentCellContent().add(tc1);
          firstTableRow.getEGContentCellContent().add(tc2);
          firstTableRow.getEGContentCellContent().add(tc3);
          firstTableRow.getEGContentCellContent().add(tc4);

          return firstTableRow;
	 }
}
