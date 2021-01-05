package escola.fred.service.mapper;


import escola.fred.domain.*;
import escola.fred.service.dto.ProvaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prova} and its DTO {@link ProvaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProvaMapper extends EntityMapper<ProvaDTO, Prova> {



    default Prova fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prova prova = new Prova();
        prova.setId(id);
        return prova;
    }
}
