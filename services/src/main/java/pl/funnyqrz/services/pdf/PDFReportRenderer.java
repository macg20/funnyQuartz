package pl.funnyqrz.services.pdf;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;


public interface PDFReportRenderer {

    File renderReport() throws FileNotFoundException, DocumentException;
}
