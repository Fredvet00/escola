package escola.fred.service.mapper;


import escola.fred.domain.*;
import escola.fred.service.dto.CursoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curso} and its DTO {@link CursoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CursoMapper extends EntityMapper<CursoDTO, Curso> {



    default Curso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Curso curso = new Curso();
        curso.setId(id);
        return curso;
    }
}
