package escola.fred.service;

import escola.fred.service.dto.ProvaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link escola.fred.domain.Prova}.
 */
public interface ProvaService {

    /**
     * Save a prova.
     *
     * @param provaDTO the entity to save.
     * @return the persisted entity.
     */
    ProvaDTO save(ProvaDTO provaDTO);

    /**
     * Get all the prova.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProvaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" prova.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProvaDTO> findOne(Long id);

    /**
     * Delete the "id" prova.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
