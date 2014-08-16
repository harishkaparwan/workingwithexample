import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.TcPrInner.GridSpan;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;


public class HeaderCreateTable {
	static Tbl createTable(){
		WordprocessingMLPackage wordMLPackage = null;

		 org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
		 	 try {
				wordMLPackage = WordprocessingMLPackage.createPackage();
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		// Let's add a table
		int writableWidthTwips = wordMLPackage.getDocumentModel().getSections()
		.get(0).getPageDimensions().getWritableWidthTwips();
		int cols = 1;
		int cellWidthTwips = new Double(Math.floor((writableWidthTwips / cols)))
		.intValue();
		 Tbl tbl = Context.getWmlObjectFactory().createTbl();
	/////////////////////
	String strTblPr = "<w:tblPr " + Namespaces.W_NAMESPACE_DECLARATION
	+ ">" + "<w:tblStyle w:val=\"TableGrid\"/>"
	+ "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
	+ "<w:tblLook w:val=\"04A0\"/>" +
	//"<w:tblBorders> <w:insideH w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"white\" w:themeColor=\"white\" /><w:insideV w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"white\" w:themeColor=\"white\" /></w:tblBorders>"+
	"</w:tblPr>";
	TblPr tblPr = null;

	try {
		System.out.println("strTblPr---"+strTblPr);
	tblPr = (TblPr) XmlUtils.unmarshalString(strTblPr);

	}

	catch (JAXBException e) {
	//Shouldn't happen
	e.printStackTrace();
	}
	tbl.setTblPr(tblPr);

	TblGrid tblGrid = Context.getWmlObjectFactory().createTblGrid();
	tbl.setTblGrid(tblGrid);
				TblGridCol gridCol = Context.getWmlObjectFactory()
						.createTblGridCol();
				gridCol.setW(BigInteger.valueOf(cellWidthTwips));
				tblGrid.getGridCol().add(gridCol);

			Tr tr = null;
			Tc tc1 =null;
			Tc tc2 =null;
			int rows = 3;
			for (int j = 0; j < rows; j++) {
				tr = Context.getWmlObjectFactory().createTr();
				// tbl.getEGContentRowContent().add(tr);
				//for (int i = 1; i <= cols; i++) {
				//	System.out.println("rows+cols--"+j+i);
					 tc1 = Context.getWmlObjectFactory().createTc();
					 tc2 = Context.getWmlObjectFactory().createTc();
				
					TcPr tcPr = Context.getWmlObjectFactory().createTcPr();
					  TrPr pr3 = new TrPr();
	                
					  tr.setTrPr(pr3);
	               
	               if(j==0){
	                tc1.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Client Name: Condenast "));
	                tc2.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Week of : 01-Jan-2013"));
	               }else if(j==2){
	            	   tc1.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Department : IT"));
		                tc2.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Supervisor: Harish"));
	               }
	               else if(j==1){
	   				 GridSpan gridSpan = new GridSpan();
	                  gridSpan.setVal(new BigInteger("2"));
	                  tcPr.setGridSpan(gridSpan);
	            	   tc1.getEGBlockLevelElts().add( wordMLPackage.getMainDocumentPart().createParagraphOfText("Client Address:1166 Avenue of the Americas, NEW YORK ,NY 10036"));

	               }
	                tr.getEGContentCellContent().add(tc1);
					tr.getEGContentCellContent().add(tc2);
					tbl.getEGContentRowContent().add(tr);
				}
				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$ one rows finished$$$$$$===="+j);
				
			//}
			System.out.println("-@@@@@@@@@@@@@@@-" + tbl);
			wordMLPackage.getMainDocumentPart().addObject(tbl);
			return tbl;
	 }
}
