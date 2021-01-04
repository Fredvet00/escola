package escola.fred.web.rest;

import escola.fred.service.AlunoService;
import escola.fred.web.rest.errors.BadRequestAlertException;
import escola.fred.service.dto.AlunoDTO;
import escola.fred.service.dto.AlunoCriteria;
import escola.fred.service.AlunoQueryService;

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
 * REST controller for managing {@link escola.fred.domain.Aluno}.
 */
@RestController
@RequestMapping("/api")
public class AlunoResource {

    private final Logger log = LoggerFactory.getLogger(AlunoResource.class);

    private static final String ENTITY_NAME = "aluno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlunoService alunoService;

    private final AlunoQueryService alunoQueryService;

    public AlunoResource(AlunoService alunoService, AlunoQueryService alunoQueryService) {
        this.alunoService = alunoService;
        this.alunoQueryService = alunoQueryService;
    }

    /**
     * {@code POST  /alunos} : Create a new aluno.
     *
     * @param alunoDTO the alunoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alunoDTO, or with status {@code 400 (Bad Request)} if the aluno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alunos")
    public ResponseEntity<AlunoDTO> createAluno(@RequestBody AlunoDTO alunoDTO) throws URISyntaxException {
        log.debug("REST request to save Aluno : {}", alunoDTO);
        if (alunoDTO.getId() != null) {
            throw new BadRequestAlertException("A new aluno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlunoDTO result = alunoService.save(alunoDTO);
        return ResponseEntity.created(new URI("/api/alunos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alunos} : Updates an existing aluno.
     *
     * @param alunoDTO the alunoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alunoDTO,
     * or with status {@code 400 (Bad Request)} if the alunoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alunoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alunos")
    public ResponseEntity<AlunoDTO> updateAluno(@RequestBody AlunoDTO alunoDTO) throws URISyntaxException {
        log.debug("REST request to update Aluno : {}", alunoDTO);
        if (alunoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlunoDTO result = alunoService.save(alunoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alunoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alunos} : get all the alunos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alunos in body.
     */
    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoDTO>> getAllAlunos(AlunoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Alunos by criteria: {}", criteria);
        Page<AlunoDTO> page = alunoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alunos/count} : count all the alunos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alunos/count")
    public ResponseEntity<Long> countAlunos(AlunoCriteria criteria) {
        log.debug("REST request to count Alunos by criteria: {}", criteria);
        return ResponseEntity.ok().body(alunoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alunos/:id} : get the "id" aluno.
     *
     * @param id the id of the alunoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alunoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alunos/{id}")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable Long id) {
        log.debug("REST request to get Aluno : {}", id);
        Optional<AlunoDTO> alunoDTO = alunoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alunoDTO);
    }

    /**
     * {@code DELETE  /alunos/:id} : delete the "id" aluno.
     *
     * @param id the id of the alunoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        log.debug("REST request to delete Aluno : {}", id);
        alunoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
