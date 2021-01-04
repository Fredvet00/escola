package escola.fred.service;

import escola.fred.service.dto.EscolaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link escola.fred.domain.Escola}.
 */
public interface EscolaService {

    /**
     * Save a escola.
     *
     * @param escolaDTO the entity to save.
     * @return the persisted entity.
     */
    EscolaDTO save(EscolaDTO escolaDTO);

    /**
     * Get all the escolas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EscolaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" escola.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EscolaDTO> findOne(Long id);

    /**
     * Delete the "id" escola.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
