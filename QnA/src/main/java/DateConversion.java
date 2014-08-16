import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;

public class DateConversion {

	static WordprocessingMLPackage wordMLPackage = null;
	static String inputfilepath;
	static boolean save;

	public static void main(String[] args) throws Exception {
		String inputfilepath;
		String str = "<w:p xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" ><w:r><w:rPr><w:b /></w:rPr><w:t>Example 4</w:t></w:r></w:p>";
		Properties properties=new Properties();
		properties.load(new FileInputStream("events.properties"));
		
		System.out.println("---properties---"+properties.getProperty("projectName"));
		
		LinkedList<LinkedList<String>> rowDataLinkedList = readFormData.getAllData(
				"CN_DIGITAL", "01-DEC-2012", "31-DEC-2012", "Bug Fixing", "8");

	 System.out.println("rowDataLinkedList.size()---"+rowDataLinkedList.size());
		try {
			// getInputFilePath(args);
			inputfilepath = System.getProperty("user.dir")
					+ "/DATE_CONVERSION.docx";
		} catch (IllegalArgumentException e) {

			inputfilepath = System.getProperty("user.dir")
					+ "/CreateWordprocessingMLDocument_out.docx";
		}

		save = (inputfilepath == null ? false : true);

		System.out.println("Creating package..");
		wordMLPackage = WordprocessingMLPackage.createPackage();

		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title",
				"Time Sheet");
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubTitle", "");
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"TableGrid", " Company Name-L&T Infotech ");

		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubTitle", "");
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"CommentSubject", "Address:- ");
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubtleEmphasis", "NO.25-31,EPIP PHASE-II Industrial Area,");
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubtleEmphasis", "WHITEFIELD,BANGALORE-560066 INDIA");

		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubTitle", "");

		Tbl tblHeader = HeaderCreateTable.createTable();
		wordMLPackage.getMainDocumentPart().addObject(tblHeader);
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubTitle", "");
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubTitle", "");
		org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();

		// Let's add a table
		int writableWidthTwips = wordMLPackage.getDocumentModel().getSections()
				.get(0).getPageDimensions().getWritableWidthTwips();
		int cols = 5;
		int cellWidthTwips = new Double(Math.floor((writableWidthTwips / cols)))
				.intValue();

		Tbl tbl = Context.getWmlObjectFactory().createTbl();

		// ///////////////////
		String strTblPr = "<w:tblPr " + Namespaces.W_NAMESPACE_DECLARATION
				+ ">" + "<w:tblStyle w:val=\"TableGrid\"/>"
				+ "<w:tblW w:w=\"0\" w:type=\"auto\"/>"
				+ "<w:tblLook w:val=\"04A0\"/>" +
				// "<w:tblBorders> <w:insideH w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"white\" w:themeColor=\"white\" /><w:insideV w:val=\"single\" w:sz=\"4\" w:space=\"0\" w:color=\"white\" w:themeColor=\"white\" /></w:tblBorders>"+
				"</w:tblPr>";
		TblPr tblPr = null;

		try {
			System.out.println("strTblPr---" + strTblPr);
			tblPr = (TblPr) XmlUtils.unmarshalString(strTblPr);

		}

		catch (JAXBException e) {
			// Shouldn't happen
			e.printStackTrace();
		}
		tbl.setTblPr(tblPr);

		TblGrid tblGrid = Context.getWmlObjectFactory().createTblGrid();
		tbl.setTblGrid(tblGrid);

		int writableWidthTwips1 = wordMLPackage.getDocumentModel()
				.getSections().get(0).getPageDimensions()
				.getWritableWidthTwips();
		int cellWidthTwips1 = new Double(
				Math.floor((writableWidthTwips / cols))).intValue();

		for (int i = 0; i < cols; i++) {
			TblGridCol gridCol = Context.getWmlObjectFactory()
					.createTblGridCol();
			gridCol.setW(BigInteger.valueOf(cellWidthTwips));

			tblGrid.getGridCol().add(gridCol);

		}

		Tc tc = null;
		Tr tr = null;
		
		tbl.getEGContentRowContent().add(readFormData.getFirstRow());

		for (Iterator iterator = rowDataLinkedList.iterator(); iterator.hasNext();) {
			LinkedList<String> columnLinkedList = (LinkedList<String>) iterator
					.next();
			tr = Context.getWmlObjectFactory().createTr();
			for (Iterator iterator2 = columnLinkedList.iterator(); iterator2
					.hasNext();) {
				tc = Context.getWmlObjectFactory().createTc();

				TcPr tcPrData = Context.getWmlObjectFactory().createTcPr();
				tc.setTcPr(tcPrData);
				TblWidth cellWidth = Context.getWmlObjectFactory()
						.createTblWidth();
				tcPrData.setTcW(cellWidth);
				cellWidth.setType("dxa");
				cellWidth.setW(BigInteger.valueOf(cellWidthTwips));

				org.docx4j.wml.P p1 = factory.createP();
				org.docx4j.wml.Text t1 = factory.createText();
				// ls.add("val :" + i);
				t1.setValue((String) iterator2.next());
				org.docx4j.wml.R run1 = factory.createR();
				run1.getRunContent().add(t1);

				p1.getParagraphContent().add(run1);
				tc.getEGBlockLevelElts().add(p1);
				tr.getEGContentCellContent().add(tc);

			}

			tbl.getEGContentRowContent().add(tr);
		}

		tbl.getEGContentRowContent().add(readFormData.getLastRow());
		System.out.println("-@@@@@@@@@@@@@@@-" + tbl);

		wordMLPackage.getMainDocumentPart().addObject(tbl);
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubTitle", "");
		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubTitle", "");
		Tbl tblFooter = FooterCreateTable.createTable();
		wordMLPackage.getMainDocumentPart().addObject(tblFooter);

		wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
				"SubTitle", "");
		if (save) {
			wordMLPackage.save(new java.io.File(inputfilepath));
			System.out.println("Saved " + inputfilepath);
		}

		System.out.println("Done.");

	}

}