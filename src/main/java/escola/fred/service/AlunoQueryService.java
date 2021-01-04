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

import escola.fred.domain.Aluno;
import escola.fred.domain.*; // for static metamodels
import escola.fred.repository.AlunoRepository;
import escola.fred.service.dto.AlunoCriteria;
import escola.fred.service.dto.AlunoDTO;
import escola.fred.service.mapper.AlunoMapper;

/**
 * Service for executing complex queries for {@link Aluno} entities in the database.
 * The main input is a {@link AlunoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlunoDTO} or a {@link Page} of {@link AlunoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlunoQueryService extends QueryService<Aluno> {

    private final Logger log = LoggerFactory.getLogger(AlunoQueryService.class);

    private final AlunoRepository alunoRepository;

    private final AlunoMapper alunoMapper;

    public AlunoQueryService(AlunoRepository alunoRepository, AlunoMapper alunoMapper) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
    }

    /**
     * Return a {@link List} of {@link AlunoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlunoDTO> findByCriteria(AlunoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Aluno> specification = createSpecification(criteria);
        return alunoMapper.toDto(alunoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlunoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlunoDTO> findByCriteria(AlunoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Aluno> specification = createSpecification(criteria);
        return alunoRepository.findAll(specification, page)
            .map(alunoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlunoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Aluno> specification = createSpecification(criteria);
        return alunoRepository.count(specification);
    }

    /**
     * Function to convert {@link AlunoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Aluno> createSpecification(AlunoCriteria criteria) {
        Specification<Aluno> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Aluno_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Aluno_.nome));
            }
            if (criteria.getIdade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdade(), Aluno_.idade));
            }
        }
        return specification;
    }
}
