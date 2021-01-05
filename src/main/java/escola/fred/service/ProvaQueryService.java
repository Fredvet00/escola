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

import escola.fred.domain.Prova;
import escola.fred.domain.*; // for static metamodels
import escola.fred.repository.ProvaRepository;
import escola.fred.service.dto.ProvaCriteria;
import escola.fred.service.dto.ProvaDTO;
import escola.fred.service.mapper.ProvaMapper;

/**
 * Service for executing complex queries for {@link Prova} entities in the database.
 * The main input is a {@link ProvaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProvaDTO} or a {@link Page} of {@link ProvaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProvaQueryService extends QueryService<Prova> {

    private final Logger log = LoggerFactory.getLogger(ProvaQueryService.class);

    private final ProvaRepository provaRepository;

    private final ProvaMapper provaMapper;

    public ProvaQueryService(ProvaRepository provaRepository, ProvaMapper provaMapper) {
        this.provaRepository = provaRepository;
        this.provaMapper = provaMapper;
    }

    /**
     * Return a {@link List} of {@link ProvaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProvaDTO> findByCriteria(ProvaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Prova> specification = createSpecification(criteria);
        return provaMapper.toDto(provaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProvaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProvaDTO> findByCriteria(ProvaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Prova> specification = createSpecification(criteria);
        return provaRepository.findAll(specification, page)
            .map(provaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProvaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Prova> specification = createSpecification(criteria);
        return provaRepository.count(specification);
    }

    /**
     * Function to convert {@link ProvaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Prova> createSpecification(ProvaCriteria criteria) {
        Specification<Prova> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Prova_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Prova_.nome));
            }
            if (criteria.getNumquestoes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumquestoes(), Prova_.numquestoes));
            }
            if (criteria.getEnunciado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnunciado(), Prova_.enunciado));
            }
            if (criteria.getTexto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTexto(), Prova_.texto));
            }
        }
        return specification;
    }
}
