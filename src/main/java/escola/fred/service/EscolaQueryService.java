package escola.fred.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import escola.fred.domain.Escola;
import escola.fred.domain.*; // for static metamodels
import escola.fred.repository.EscolaRepository;
import escola.fred.service.dto.EscolaCriteria;
import escola.fred.service.dto.EscolaDTO;
import escola.fred.service.mapper.EscolaMapper;

/**
 * Service for executing complex queries for {@link Escola} entities in the database.
 * The main input is a {@link EscolaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EscolaDTO} or a {@link Page} of {@link EscolaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EscolaQueryService extends QueryService<Escola> {

    private final Logger log = LoggerFactory.getLogger(EscolaQueryService.class);

    private final EscolaRepository escolaRepository;

    private final EscolaMapper escolaMapper;

    public EscolaQueryService(EscolaRepository escolaRepository, EscolaMapper escolaMapper) {
        this.escolaRepository = escolaRepository;
        this.escolaMapper = escolaMapper;
    }

    /**
     * Return a {@link List} of {@link EscolaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EscolaDTO> findByCriteria(EscolaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Escola> specification = createSpecification(criteria);
        return escolaMapper.toDto(escolaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EscolaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EscolaDTO> findByCriteria(EscolaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Escola> specification = createSpecification(criteria);
        return escolaRepository.findAll(specification, page)
            .map(escolaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EscolaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Escola> specification = createSpecification(criteria);
        return escolaRepository.count(specification);
    }

    /**
     * Function to convert {@link EscolaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Escola> createSpecification(EscolaCriteria criteria) {
        Specification<Escola> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Escola_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Escola_.nome));
            }
        }
        return specification;
    }
}
