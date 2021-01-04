package escola.fred.repository;

import escola.fred.domain.Escola;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Escola entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long>, JpaSpecificationExecutor<Escola> {
}
