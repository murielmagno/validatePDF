package murielmagno.validatePDF.controller;

import murielmagno.validatePDF.service.PDFSignatureVerifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/pdf")
public class PDFSignatureController {

    private final PDFSignatureVerifier pdfSignatureVerifier;
    public PDFSignatureController(PDFSignatureVerifier pdfSignatureVerifier) {
        this.pdfSignatureVerifier = pdfSignatureVerifier;
    }

    @PostMapping("/verify")
    public String verifyPdfSignature(@RequestParam("file") MultipartFile file) {
        try {
            pdfSignatureVerifier.verifySignatures(file.getInputStream());
            return "Assinaturas verificadas com sucesso!";
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return "Falha ao verificar assinaturas: " + e.getMessage();
        }
    }
}
