package escola.fred.service.jasperReport;

import escola.fred.domain.Prova;
import escola.fred.repository.ProvaRepository;
import escola.fred.service.dto.ProvaDTO;
import io.jsonwebtoken.io.IOException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperProvaService {
    @Autowired
    private ProvaRepository repository;



    public ResponseEntity<String> exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path= "C:\\Users\\fredy\\OneDrive\\Documentos\\GitHub\\escola\\src\\main\\resources\\Report";
        List<Prova> provas = repository.findAll();
        //carregar arquivo
        File file= ResourceUtils.getFile("classpath:prova.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(provas);
        Map<String, Object> parameters=new HashMap<>();
        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        if(reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint,path+"\\provas.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint,path+"\\provas.pdf");
        }
    return ResponseEntity.ok("OK");
    }

    public ByteArrayResource simpleReport(ProvaDTO report) throws JRException, IOException, FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:prova.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singletonList(report));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, source);
        return new ByteArrayResource(JasperExportManager.exportReportToPdf(jasperPrint));

    }

}
