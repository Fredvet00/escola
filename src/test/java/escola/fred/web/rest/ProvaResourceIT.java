package escola.fred.web.rest;

import escola.fred.EscolaApp;
import escola.fred.domain.Prova;
import escola.fred.repository.ProvaRepository;
import escola.fred.service.ProvaService;
import escola.fred.service.dto.ProvaDTO;
import escola.fred.service.mapper.ProvaMapper;
import escola.fred.service.dto.ProvaCriteria;
import escola.fred.service.ProvaQueryService;

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
 * Integration tests for the {@link ProvaResource} REST controller.
 */
@SpringBootTest(classes = EscolaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProvaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMQUESTOES = 1;
    private static final Integer UPDATED_NUMQUESTOES = 2;
    private static final Integer SMALLER_NUMQUESTOES = 1 - 1;

    private static final String DEFAULT_ENUNCIADO = "AAAAAAAAAA";
    private static final String UPDATED_ENUNCIADO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private ProvaMapper provaMapper;

    @Autowired
    private ProvaService provaService;

    @Autowired
    private ProvaQueryService provaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProvaMockMvc;

    private Prova prova;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prova createEntity(EntityManager em) {
        Prova prova = new Prova()
            .nome(DEFAULT_NOME)
            .numquestoes(DEFAULT_NUMQUESTOES)
            .enunciado(DEFAULT_ENUNCIADO)
            .texto(DEFAULT_TEXTO);
        return prova;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prova createUpdatedEntity(EntityManager em) {
        Prova prova = new Prova()
            .nome(UPDATED_NOME)
            .numquestoes(UPDATED_NUMQUESTOES)
            .enunciado(UPDATED_ENUNCIADO)
            .texto(UPDATED_TEXTO);
        return prova;
    }

    @BeforeEach
    public void initTest() {
        prova = createEntity(em);
    }

    @Test
    @Transactional
    public void createProva() throws Exception {
        int databaseSizeBeforeCreate = provaRepository.findAll().size();
        // Create the Prova
        ProvaDTO provaDTO = provaMapper.toDto(prova);
        restProvaMockMvc.perform(post("/api/prova")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(provaDTO)))
            .andExpect(status().isCreated());

        // Validate the Prova in the database
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeCreate + 1);
        Prova testProva = provaList.get(provaList.size() - 1);
        assertThat(testProva.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProva.getNumquestoes()).isEqualTo(DEFAULT_NUMQUESTOES);
        assertThat(testProva.getEnunciado()).isEqualTo(DEFAULT_ENUNCIADO);
        assertThat(testProva.getTexto()).isEqualTo(DEFAULT_TEXTO);
    }

    @Test
    @Transactional
    public void createProvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = provaRepository.findAll().size();

        // Create the Prova with an existing ID
        prova.setId(1L);
        ProvaDTO provaDTO = provaMapper.toDto(prova);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvaMockMvc.perform(post("/api/prova")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(provaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prova in the database
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProva() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList
        restProvaMockMvc.perform(get("/api/prova?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prova.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].numquestoes").value(hasItem(DEFAULT_NUMQUESTOES)))
            .andExpect(jsonPath("$.[*].enunciado").value(hasItem(DEFAULT_ENUNCIADO)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)));
    }
    
    @Test
    @Transactional
    public void getProva() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get the prova
        restProvaMockMvc.perform(get("/api/prova/{id}", prova.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prova.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.numquestoes").value(DEFAULT_NUMQUESTOES))
            .andExpect(jsonPath("$.enunciado").value(DEFAULT_ENUNCIADO))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO));
    }


    @Test
    @Transactional
    public void getProvaByIdFiltering() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        Long id = prova.getId();

        defaultProvaShouldBeFound("id.equals=" + id);
        defaultProvaShouldNotBeFound("id.notEquals=" + id);

        defaultProvaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProvaShouldNotBeFound("id.greaterThan=" + id);

        defaultProvaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProvaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProvaByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where nome equals to DEFAULT_NOME
        defaultProvaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the provaList where nome equals to UPDATED_NOME
        defaultProvaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProvaByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where nome not equals to DEFAULT_NOME
        defaultProvaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the provaList where nome not equals to UPDATED_NOME
        defaultProvaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProvaByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultProvaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the provaList where nome equals to UPDATED_NOME
        defaultProvaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProvaByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where nome is not null
        defaultProvaShouldBeFound("nome.specified=true");

        // Get all the provaList where nome is null
        defaultProvaShouldNotBeFound("nome.specified=false");
    }
                @Test
    @Transactional
    public void getAllProvaByNomeContainsSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where nome contains DEFAULT_NOME
        defaultProvaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the provaList where nome contains UPDATED_NOME
        defaultProvaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProvaByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where nome does not contain DEFAULT_NOME
        defaultProvaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the provaList where nome does not contain UPDATED_NOME
        defaultProvaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }


    @Test
    @Transactional
    public void getAllProvaByNumquestoesIsEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where numquestoes equals to DEFAULT_NUMQUESTOES
        defaultProvaShouldBeFound("numquestoes.equals=" + DEFAULT_NUMQUESTOES);

        // Get all the provaList where numquestoes equals to UPDATED_NUMQUESTOES
        defaultProvaShouldNotBeFound("numquestoes.equals=" + UPDATED_NUMQUESTOES);
    }

    @Test
    @Transactional
    public void getAllProvaByNumquestoesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where numquestoes not equals to DEFAULT_NUMQUESTOES
        defaultProvaShouldNotBeFound("numquestoes.notEquals=" + DEFAULT_NUMQUESTOES);

        // Get all the provaList where numquestoes not equals to UPDATED_NUMQUESTOES
        defaultProvaShouldBeFound("numquestoes.notEquals=" + UPDATED_NUMQUESTOES);
    }

    @Test
    @Transactional
    public void getAllProvaByNumquestoesIsInShouldWork() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where numquestoes in DEFAULT_NUMQUESTOES or UPDATED_NUMQUESTOES
        defaultProvaShouldBeFound("numquestoes.in=" + DEFAULT_NUMQUESTOES + "," + UPDATED_NUMQUESTOES);

        // Get all the provaList where numquestoes equals to UPDATED_NUMQUESTOES
        defaultProvaShouldNotBeFound("numquestoes.in=" + UPDATED_NUMQUESTOES);
    }

    @Test
    @Transactional
    public void getAllProvaByNumquestoesIsNullOrNotNull() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where numquestoes is not null
        defaultProvaShouldBeFound("numquestoes.specified=true");

        // Get all the provaList where numquestoes is null
        defaultProvaShouldNotBeFound("numquestoes.specified=false");
    }

    @Test
    @Transactional
    public void getAllProvaByNumquestoesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where numquestoes is greater than or equal to DEFAULT_NUMQUESTOES
        defaultProvaShouldBeFound("numquestoes.greaterThanOrEqual=" + DEFAULT_NUMQUESTOES);

        // Get all the provaList where numquestoes is greater than or equal to UPDATED_NUMQUESTOES
        defaultProvaShouldNotBeFound("numquestoes.greaterThanOrEqual=" + UPDATED_NUMQUESTOES);
    }

    @Test
    @Transactional
    public void getAllProvaByNumquestoesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where numquestoes is less than or equal to DEFAULT_NUMQUESTOES
        defaultProvaShouldBeFound("numquestoes.lessThanOrEqual=" + DEFAULT_NUMQUESTOES);

        // Get all the provaList where numquestoes is less than or equal to SMALLER_NUMQUESTOES
        defaultProvaShouldNotBeFound("numquestoes.lessThanOrEqual=" + SMALLER_NUMQUESTOES);
    }

    @Test
    @Transactional
    public void getAllProvaByNumquestoesIsLessThanSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where numquestoes is less than DEFAULT_NUMQUESTOES
        defaultProvaShouldNotBeFound("numquestoes.lessThan=" + DEFAULT_NUMQUESTOES);

        // Get all the provaList where numquestoes is less than UPDATED_NUMQUESTOES
        defaultProvaShouldBeFound("numquestoes.lessThan=" + UPDATED_NUMQUESTOES);
    }

    @Test
    @Transactional
    public void getAllProvaByNumquestoesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where numquestoes is greater than DEFAULT_NUMQUESTOES
        defaultProvaShouldNotBeFound("numquestoes.greaterThan=" + DEFAULT_NUMQUESTOES);

        // Get all the provaList where numquestoes is greater than SMALLER_NUMQUESTOES
        defaultProvaShouldBeFound("numquestoes.greaterThan=" + SMALLER_NUMQUESTOES);
    }


    @Test
    @Transactional
    public void getAllProvaByEnunciadoIsEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where enunciado equals to DEFAULT_ENUNCIADO
        defaultProvaShouldBeFound("enunciado.equals=" + DEFAULT_ENUNCIADO);

        // Get all the provaList where enunciado equals to UPDATED_ENUNCIADO
        defaultProvaShouldNotBeFound("enunciado.equals=" + UPDATED_ENUNCIADO);
    }

    @Test
    @Transactional
    public void getAllProvaByEnunciadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where enunciado not equals to DEFAULT_ENUNCIADO
        defaultProvaShouldNotBeFound("enunciado.notEquals=" + DEFAULT_ENUNCIADO);

        // Get all the provaList where enunciado not equals to UPDATED_ENUNCIADO
        defaultProvaShouldBeFound("enunciado.notEquals=" + UPDATED_ENUNCIADO);
    }

    @Test
    @Transactional
    public void getAllProvaByEnunciadoIsInShouldWork() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where enunciado in DEFAULT_ENUNCIADO or UPDATED_ENUNCIADO
        defaultProvaShouldBeFound("enunciado.in=" + DEFAULT_ENUNCIADO + "," + UPDATED_ENUNCIADO);

        // Get all the provaList where enunciado equals to UPDATED_ENUNCIADO
        defaultProvaShouldNotBeFound("enunciado.in=" + UPDATED_ENUNCIADO);
    }

    @Test
    @Transactional
    public void getAllProvaByEnunciadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where enunciado is not null
        defaultProvaShouldBeFound("enunciado.specified=true");

        // Get all the provaList where enunciado is null
        defaultProvaShouldNotBeFound("enunciado.specified=false");
    }
                @Test
    @Transactional
    public void getAllProvaByEnunciadoContainsSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where enunciado contains DEFAULT_ENUNCIADO
        defaultProvaShouldBeFound("enunciado.contains=" + DEFAULT_ENUNCIADO);

        // Get all the provaList where enunciado contains UPDATED_ENUNCIADO
        defaultProvaShouldNotBeFound("enunciado.contains=" + UPDATED_ENUNCIADO);
    }

    @Test
    @Transactional
    public void getAllProvaByEnunciadoNotContainsSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where enunciado does not contain DEFAULT_ENUNCIADO
        defaultProvaShouldNotBeFound("enunciado.doesNotContain=" + DEFAULT_ENUNCIADO);

        // Get all the provaList where enunciado does not contain UPDATED_ENUNCIADO
        defaultProvaShouldBeFound("enunciado.doesNotContain=" + UPDATED_ENUNCIADO);
    }


    @Test
    @Transactional
    public void getAllProvaByTextoIsEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where texto equals to DEFAULT_TEXTO
        defaultProvaShouldBeFound("texto.equals=" + DEFAULT_TEXTO);

        // Get all the provaList where texto equals to UPDATED_TEXTO
        defaultProvaShouldNotBeFound("texto.equals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllProvaByTextoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where texto not equals to DEFAULT_TEXTO
        defaultProvaShouldNotBeFound("texto.notEquals=" + DEFAULT_TEXTO);

        // Get all the provaList where texto not equals to UPDATED_TEXTO
        defaultProvaShouldBeFound("texto.notEquals=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllProvaByTextoIsInShouldWork() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where texto in DEFAULT_TEXTO or UPDATED_TEXTO
        defaultProvaShouldBeFound("texto.in=" + DEFAULT_TEXTO + "," + UPDATED_TEXTO);

        // Get all the provaList where texto equals to UPDATED_TEXTO
        defaultProvaShouldNotBeFound("texto.in=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllProvaByTextoIsNullOrNotNull() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where texto is not null
        defaultProvaShouldBeFound("texto.specified=true");

        // Get all the provaList where texto is null
        defaultProvaShouldNotBeFound("texto.specified=false");
    }
                @Test
    @Transactional
    public void getAllProvaByTextoContainsSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where texto contains DEFAULT_TEXTO
        defaultProvaShouldBeFound("texto.contains=" + DEFAULT_TEXTO);

        // Get all the provaList where texto contains UPDATED_TEXTO
        defaultProvaShouldNotBeFound("texto.contains=" + UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void getAllProvaByTextoNotContainsSomething() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        // Get all the provaList where texto does not contain DEFAULT_TEXTO
        defaultProvaShouldNotBeFound("texto.doesNotContain=" + DEFAULT_TEXTO);

        // Get all the provaList where texto does not contain UPDATED_TEXTO
        defaultProvaShouldBeFound("texto.doesNotContain=" + UPDATED_TEXTO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProvaShouldBeFound(String filter) throws Exception {
        restProvaMockMvc.perform(get("/api/prova?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prova.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].numquestoes").value(hasItem(DEFAULT_NUMQUESTOES)))
            .andExpect(jsonPath("$.[*].enunciado").value(hasItem(DEFAULT_ENUNCIADO)))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO)));

        // Check, that the count call also returns 1
        restProvaMockMvc.perform(get("/api/prova/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProvaShouldNotBeFound(String filter) throws Exception {
        restProvaMockMvc.perform(get("/api/prova?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProvaMockMvc.perform(get("/api/prova/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProva() throws Exception {
        // Get the prova
        restProvaMockMvc.perform(get("/api/prova/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProva() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        int databaseSizeBeforeUpdate = provaRepository.findAll().size();

        // Update the prova
        Prova updatedProva = provaRepository.findById(prova.getId()).get();
        // Disconnect from session so that the updates on updatedProva are not directly saved in db
        em.detach(updatedProva);
        updatedProva
            .nome(UPDATED_NOME)
            .numquestoes(UPDATED_NUMQUESTOES)
            .enunciado(UPDATED_ENUNCIADO)
            .texto(UPDATED_TEXTO);
        ProvaDTO provaDTO = provaMapper.toDto(updatedProva);

        restProvaMockMvc.perform(put("/api/prova")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(provaDTO)))
            .andExpect(status().isOk());

        // Validate the Prova in the database
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeUpdate);
        Prova testProva = provaList.get(provaList.size() - 1);
        assertThat(testProva.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProva.getNumquestoes()).isEqualTo(UPDATED_NUMQUESTOES);
        assertThat(testProva.getEnunciado()).isEqualTo(UPDATED_ENUNCIADO);
        assertThat(testProva.getTexto()).isEqualTo(UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void updateNonExistingProva() throws Exception {
        int databaseSizeBeforeUpdate = provaRepository.findAll().size();

        // Create the Prova
        ProvaDTO provaDTO = provaMapper.toDto(prova);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvaMockMvc.perform(put("/api/prova")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(provaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prova in the database
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProva() throws Exception {
        // Initialize the database
        provaRepository.saveAndFlush(prova);

        int databaseSizeBeforeDelete = provaRepository.findAll().size();

        // Delete the prova
        restProvaMockMvc.perform(delete("/api/prova/{id}", prova.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prova> provaList = provaRepository.findAll();
        assertThat(provaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
