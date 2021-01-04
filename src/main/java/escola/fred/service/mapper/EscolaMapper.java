package escola.fred.service.mapper;


import escola.fred.domain.*;
import escola.fred.service.dto.EscolaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Escola} and its DTO {@link EscolaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EscolaMapper extends EntityMapper<EscolaDTO, Escola> {



    default Escola fromId(Long id) {
        if (id == null) {
            return null;
        }
        Escola escola = new Escola();
        escola.setId(id);
        return escola;
    }
}
