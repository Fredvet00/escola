package escola.fred.web.rest;

import escola.fred.service.ProvaService;
import escola.fred.service.jasperReport.JasperProvaService;
import escola.fred.web.rest.errors.BadRequestAlertException;
import escola.fred.service.dto.ProvaDTO;
import escola.fred.service.dto.ProvaCriteria;
import escola.fred.service.ProvaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.jsonwebtoken.io.IOException;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link escola.fred.domain.Prova}.
 */
@RestController
@RequestMapping("/api")
public class ProvaResource {

    private final Logger log = LoggerFactory.getLogger(ProvaResource.class);

    private static final String ENTITY_NAME = "prova";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProvaService provaService;

    private final ProvaQueryService provaQueryService;

    @Autowired
    private JasperProvaService jasperProvaService;

    public ProvaResource(ProvaService provaService, ProvaQueryService provaQueryService) {
        this.provaService = provaService;
        this.provaQueryService = provaQueryService;
    }

    /**
     * {@code POST  /prova} : Create a new prova.
     *
     * @param provaDTO the provaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new provaDTO, or with status {@code 400 (Bad Request)} if the prova has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prova")
    public ResponseEntity<ProvaDTO> createProva(@RequestBody ProvaDTO provaDTO) throws URISyntaxException {
        log.debug("REST request to save Prova : {}", provaDTO);
        if (provaDTO.getId() != null) {
            throw new BadRequestAlertException("A new prova cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProvaDTO result = provaService.save(provaDTO);
        return ResponseEntity.created(new URI("/api/prova/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prova} : Updates an existing prova.
     *
     * @param provaDTO the provaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated provaDTO,
     * or with status {@code 400 (Bad Request)} if the provaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the provaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prova")
    public ResponseEntity<ProvaDTO> updateProva(@RequestBody ProvaDTO provaDTO) throws URISyntaxException {
        log.debug("REST request to update Prova : {}", provaDTO);
        if (provaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProvaDTO result = provaService.save(provaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, provaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prova} : get all the prova.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prova in body.
     */
    @GetMapping("/prova")
    public ResponseEntity<List<ProvaDTO>> getAllProva(ProvaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Prova by criteria: {}", criteria);
        Page<ProvaDTO> page = provaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prova/count} : count all the prova.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/prova/count")
    public ResponseEntity<Long> countProva(ProvaCriteria criteria) {
        log.debug("REST request to count Prova by criteria: {}", criteria);
        return ResponseEntity.ok().body(provaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prova/:id} : get the "id" prova.
     *
     * @param id the id of the provaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the provaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prova/{id}")
    public ResponseEntity<ProvaDTO> getProva(@PathVariable Long id) {
        log.debug("REST request to get Prova : {}", id);
        Optional<ProvaDTO> provaDTO = provaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provaDTO);
    }

    @PostMapping("/prova/download")
    public ResponseEntity<ByteArrayResource> getProvaDownload(@Valid @RequestBody ProvaDTO provaDTO) throws FileNotFoundException, JRException {

        try {  log.debug("Payload for generating simple PDF report: {}", provaDTO);
        ByteArrayResource byteArrayResource = jasperProvaService.simpleReport(provaDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename= prova.pdf");
        headers.setContentLength(byteArrayResource.contentLength());
        log.debug("REST request to POST prova/download:     return prova:"+provaDTO.getNome());

        return new ResponseEntity<>(byteArrayResource, headers, HttpStatus.OK);
        }
        catch (IOException e) {
            log.error(e.getMessage());
            System.out.println("nao funciona");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * {@code DELETE  /prova/:id} : delete the "id" prova.
     *
     * @param id the id of the provaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prova/{id}")
    public ResponseEntity<Void> deleteProva(@PathVariable Long id) {
        log.debug("REST request to delete Prova : {}", id);
        provaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
