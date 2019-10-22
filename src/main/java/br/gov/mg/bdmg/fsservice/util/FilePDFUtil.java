package br.gov.mg.bdmg.fsservice.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.PdfContentByte;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import br.gov.mg.bdmg.fsservice.exception.FileUtilException;



public class FilePDFUtil {

	private FilePDFUtil() {
		super();
	}

	public static void unionPDFs(String saveTo, List<String> files) throws FileUtilException {
		try {
			FileOutputStream fos = new FileOutputStream(saveTo);
			PdfCopyFields copy = new PdfCopyFields(fos);
			for (String file : files) {
				PdfReader reader = new PdfReader(file);
				copy.addDocument(reader);
				reader.close();
			}
			copy.close();
		} catch (FileNotFoundException e) {
			throw new FileUtilException(e);
		} catch (DocumentException e) {
			throw new FileUtilException(e);
		} catch (IOException e) {
			throw new FileUtilException(e);
		}
	}

	public static void insertWaterMark(InputStream is, OutputStream outputStream, String texto) throws DocumentException, IOException {
		Document document = new Document();
		try {
			List<PdfReader> readers = new ArrayList<PdfReader>();

			PdfReader pdfReader = new PdfReader(is);
			readers.add(pdfReader);

			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();

			com.lowagie.text.pdf.PdfContentByte cb = writer.getDirectContent();

			PdfImportedPage page;

			int pageOfCurrentReaderPDF = 0;

			while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
				document.newPage();
				pageOfCurrentReaderPDF++;

				page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
				cb.addTemplate(page, 0, 0);

				com.lowagie.text.pdf.PdfGState gs1 = new com.lowagie.text.pdf.PdfGState();

				gs1.setFillOpacity(0.5f);

				cb.saveState();
				cb.beginText();

				cb.setGState(gs1);

				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);

				cb.setRGBColorFill(105, 105, 105);
				cb.setFontAndSize(bf, 78);
				cb.showTextAligned(PdfContentByte.ALIGN_CENTER, texto, 310, 400, 55);

				cb.endText();
				cb.restoreState();
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		} finally {
			if (document.isOpen()) {
				document.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

}
