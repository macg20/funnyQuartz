package pl.funnyqrz.services.reports;

import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;


public interface PDFReportRenderer {

    File renderReport() throws FileNotFoundException, DocumentException;
}
