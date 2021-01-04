package escola.fred.web.rest;

import escola.fred.service.EscolaService;
import escola.fred.web.rest.errors.BadRequestAlertException;
import escola.fred.service.dto.EscolaDTO;
import escola.fred.service.dto.EscolaCriteria;
import escola.fred.service.EscolaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link escola.fred.domain.Escola}.
 */
@RestController
@RequestMapping("/api")
public class EscolaResource {

    private final Logger log = LoggerFactory.getLogger(EscolaResource.class);

    private static final String ENTITY_NAME = "escola";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EscolaService escolaService;

    private final EscolaQueryService escolaQueryService;

    public EscolaResource(EscolaService escolaService, EscolaQueryService escolaQueryService) {
        this.escolaService = escolaService;
        this.escolaQueryService = escolaQueryService;
    }

    /**
     * {@code POST  /escolas} : Create a new escola.
     *
     * @param escolaDTO the escolaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new escolaDTO, or with status {@code 400 (Bad Request)} if the escola has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/escolas")
    public ResponseEntity<EscolaDTO> createEscola(@RequestBody EscolaDTO escolaDTO) throws URISyntaxException {
        log.debug("REST request to save Escola : {}", escolaDTO);
        if (escolaDTO.getId() != null) {
            throw new BadRequestAlertException("A new escola cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EscolaDTO result = escolaService.save(escolaDTO);
        return ResponseEntity.created(new URI("/api/escolas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /escolas} : Updates an existing escola.
     *
     * @param escolaDTO the escolaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escolaDTO,
     * or with status {@code 400 (Bad Request)} if the escolaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the escolaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/escolas")
    public ResponseEntity<EscolaDTO> updateEscola(@RequestBody EscolaDTO escolaDTO) throws URISyntaxException {
        log.debug("REST request to update Escola : {}", escolaDTO);
        if (escolaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EscolaDTO result = escolaService.save(escolaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escolaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /escolas} : get all the escolas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of escolas in body.
     */
    @GetMapping("/escolas")
    public ResponseEntity<List<EscolaDTO>> getAllEscolas(EscolaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Escolas by criteria: {}", criteria);
        Page<EscolaDTO> page = escolaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /escolas/count} : count all the escolas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/escolas/count")
    public ResponseEntity<Long> countEscolas(EscolaCriteria criteria) {
        log.debug("REST request to count Escolas by criteria: {}", criteria);
        return ResponseEntity.ok().body(escolaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /escolas/:id} : get the "id" escola.
     *
     * @param id the id of the escolaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the escolaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/escolas/{id}")
    public ResponseEntity<EscolaDTO> getEscola(@PathVariable Long id) {
        log.debug("REST request to get Escola : {}", id);
        Optional<EscolaDTO> escolaDTO = escolaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(escolaDTO);
    }

    /**
     * {@code DELETE  /escolas/:id} : delete the "id" escola.
     *
     * @param id the id of the escolaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/escolas/{id}")
    public ResponseEntity<Void> deleteEscola(@PathVariable Long id) {
        log.debug("REST request to delete Escola : {}", id);
        escolaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
