package escola.fred.service.jasperReport;

import escola.fred.domain.Prova;
import escola.fred.repository.ProvaRepository;
import escola.fred.service.dto.ProvaDTO;
import io.jsonwebtoken.io.IOException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;


@Service
public class JasperProvaService {

    /*Função busca o template referente ao dto*/
    public ByteArrayResource simpleReport(ProvaDTO report) throws JRException, IOException, FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:prova.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Collections.singletonList(report));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, source);
        return new ByteArrayResource(JasperExportManager.exportReportToPdf(jasperPrint));

    }

}
