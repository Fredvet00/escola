package escola.fred.service.mapper;


import escola.fred.domain.*;
import escola.fred.service.dto.AlunoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aluno} and its DTO {@link AlunoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlunoMapper extends EntityMapper<AlunoDTO, Aluno> {



    default Aluno fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aluno aluno = new Aluno();
        aluno.setId(id);
        return aluno;
    }
}
