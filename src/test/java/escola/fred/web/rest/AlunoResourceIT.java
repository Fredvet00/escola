package escola.fred.web.rest;

import escola.fred.EscolaApp;
import escola.fred.domain.Aluno;
import escola.fred.repository.AlunoRepository;
import escola.fred.service.AlunoService;
import escola.fred.service.dto.AlunoDTO;
import escola.fred.service.mapper.AlunoMapper;
import escola.fred.service.dto.AlunoCriteria;
import escola.fred.service.AlunoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlunoResource} REST controller.
 */
@SpringBootTest(classes = EscolaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlunoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDADE = 1;
    private static final Integer UPDATED_IDADE = 2;
    private static final Integer SMALLER_IDADE = 1 - 1;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoQueryService alunoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlunoMockMvc;

    private Aluno aluno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .nome(DEFAULT_NOME)
            .idade(DEFAULT_IDADE);
        return aluno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aluno createUpdatedEntity(EntityManager em) {
        Aluno aluno = new Aluno()
            .nome(UPDATED_NOME)
            .idade(UPDATED_IDADE);
        return aluno;
    }

    @BeforeEach
    public void initTest() {
        aluno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAluno() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();
        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isCreated());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate + 1);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAluno.getIdade()).isEqualTo(DEFAULT_IDADE);
    }

    @Test
    @Transactional
    public void createAlunoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alunoRepository.findAll().size();

        // Create the Aluno with an existing ID
        aluno.setId(1L);
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlunoMockMvc.perform(post("/api/alunos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlunos() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)));
    }
    
    @Test
    @Transactional
    public void getAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", aluno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aluno.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.idade").value(DEFAULT_IDADE));
    }


    @Test
    @Transactional
    public void getAlunosByIdFiltering() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        Long id = aluno.getId();

        defaultAlunoShouldBeFound("id.equals=" + id);
        defaultAlunoShouldNotBeFound("id.notEquals=" + id);

        defaultAlunoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlunoShouldNotBeFound("id.greaterThan=" + id);

        defaultAlunoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlunoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlunosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome equals to DEFAULT_NOME
        defaultAlunoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the alunoList where nome equals to UPDATED_NOME
        defaultAlunoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlunosByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome not equals to DEFAULT_NOME
        defaultAlunoShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the alunoList where nome not equals to UPDATED_NOME
        defaultAlunoShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlunosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultAlunoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the alunoList where nome equals to UPDATED_NOME
        defaultAlunoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlunosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome is not null
        defaultAlunoShouldBeFound("nome.specified=true");

        // Get all the alunoList where nome is null
        defaultAlunoShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlunosByNomeContainsSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome contains DEFAULT_NOME
        defaultAlunoShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the alunoList where nome contains UPDATED_NOME
        defaultAlunoShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllAlunosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where nome does not contain DEFAULT_NOME
        defaultAlunoShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the alunoList where nome does not contain UPDATED_NOME
        defaultAlunoShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllAlunosByIdadeIsEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where idade equals to DEFAULT_IDADE
        defaultAlunoShouldBeFound("idade.equals=" + DEFAULT_IDADE);

        // Get all the alunoList where idade equals to UPDATED_IDADE
        defaultAlunoShouldNotBeFound("idade.equals=" + UPDATED_IDADE);
    }

    @Test
    @Transactional
    public void getAllAlunosByIdadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where idade not equals to DEFAULT_IDADE
        defaultAlunoShouldNotBeFound("idade.notEquals=" + DEFAULT_IDADE);

        // Get all the alunoList where idade not equals to UPDATED_IDADE
        defaultAlunoShouldBeFound("idade.notEquals=" + UPDATED_IDADE);
    }

    @Test
    @Transactional
    public void getAllAlunosByIdadeIsInShouldWork() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where idade in DEFAULT_IDADE or UPDATED_IDADE
        defaultAlunoShouldBeFound("idade.in=" + DEFAULT_IDADE + "," + UPDATED_IDADE);

        // Get all the alunoList where idade equals to UPDATED_IDADE
        defaultAlunoShouldNotBeFound("idade.in=" + UPDATED_IDADE);
    }

    @Test
    @Transactional
    public void getAllAlunosByIdadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where idade is not null
        defaultAlunoShouldBeFound("idade.specified=true");

        // Get all the alunoList where idade is null
        defaultAlunoShouldNotBeFound("idade.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlunosByIdadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where idade is greater than or equal to DEFAULT_IDADE
        defaultAlunoShouldBeFound("idade.greaterThanOrEqual=" + DEFAULT_IDADE);

        // Get all the alunoList where idade is greater than or equal to UPDATED_IDADE
        defaultAlunoShouldNotBeFound("idade.greaterThanOrEqual=" + UPDATED_IDADE);
    }

    @Test
    @Transactional
    public void getAllAlunosByIdadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where idade is less than or equal to DEFAULT_IDADE
        defaultAlunoShouldBeFound("idade.lessThanOrEqual=" + DEFAULT_IDADE);

        // Get all the alunoList where idade is less than or equal to SMALLER_IDADE
        defaultAlunoShouldNotBeFound("idade.lessThanOrEqual=" + SMALLER_IDADE);
    }

    @Test
    @Transactional
    public void getAllAlunosByIdadeIsLessThanSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where idade is less than DEFAULT_IDADE
        defaultAlunoShouldNotBeFound("idade.lessThan=" + DEFAULT_IDADE);

        // Get all the alunoList where idade is less than UPDATED_IDADE
        defaultAlunoShouldBeFound("idade.lessThan=" + UPDATED_IDADE);
    }

    @Test
    @Transactional
    public void getAllAlunosByIdadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        // Get all the alunoList where idade is greater than DEFAULT_IDADE
        defaultAlunoShouldNotBeFound("idade.greaterThan=" + DEFAULT_IDADE);

        // Get all the alunoList where idade is greater than SMALLER_IDADE
        defaultAlunoShouldBeFound("idade.greaterThan=" + SMALLER_IDADE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlunoShouldBeFound(String filter) throws Exception {
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aluno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)));

        // Check, that the count call also returns 1
        restAlunoMockMvc.perform(get("/api/alunos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlunoShouldNotBeFound(String filter) throws Exception {
        restAlunoMockMvc.perform(get("/api/alunos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlunoMockMvc.perform(get("/api/alunos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAluno() throws Exception {
        // Get the aluno
        restAlunoMockMvc.perform(get("/api/alunos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Update the aluno
        Aluno updatedAluno = alunoRepository.findById(aluno.getId()).get();
        // Disconnect from session so that the updates on updatedAluno are not directly saved in db
        em.detach(updatedAluno);
        updatedAluno
            .nome(UPDATED_NOME)
            .idade(UPDATED_IDADE);
        AlunoDTO alunoDTO = alunoMapper.toDto(updatedAluno);

        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isOk());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);
        Aluno testAluno = alunoList.get(alunoList.size() - 1);
        assertThat(testAluno.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAluno.getIdade()).isEqualTo(UPDATED_IDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingAluno() throws Exception {
        int databaseSizeBeforeUpdate = alunoRepository.findAll().size();

        // Create the Aluno
        AlunoDTO alunoDTO = alunoMapper.toDto(aluno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlunoMockMvc.perform(put("/api/alunos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alunoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aluno in the database
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAluno() throws Exception {
        // Initialize the database
        alunoRepository.saveAndFlush(aluno);

        int databaseSizeBeforeDelete = alunoRepository.findAll().size();

        // Delete the aluno
        restAlunoMockMvc.perform(delete("/api/alunos/{id}", aluno.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aluno> alunoList = alunoRepository.findAll();
        assertThat(alunoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
