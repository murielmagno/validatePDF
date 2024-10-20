package murielmagno.validatePDF.service;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

@Service
public class PDFSignatureVerifier {

    public static void verifySignatures(InputStream pdfInputStream) throws IOException, GeneralSecurityException {
        PdfReader reader = new PdfReader(pdfInputStream);
        AcroFields fields = reader.getAcroFields();

        List<String> signatureNames = fields.getSignatureNames();
        if (signatureNames.isEmpty()) {
            System.out.println("Nenhuma assinatura digital encontrada no PDF.");
            return;
        }
        for (String signature : signatureNames) {
            PdfPKCS7 pkcs7 = fields.verifySignature(signature);
            Certificate[] certs = pkcs7.getSignCertificateChain();
            X509Certificate cert = (X509Certificate) certs[0];

            boolean isValid = pkcs7.verify();
            System.out.println("Signatário: " + cert.getSubjectDN());
            System.out.println("Assinatura válida? " + isValid);
        }
    }
}
