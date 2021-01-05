package escola.fred.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlunoMapperTest {

    private AlunoMapper alunoMapper;

    @BeforeEach
    public void setUp() {
        alunoMapper = new AlunoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alunoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alunoMapper.fromId(null)).isNull();
    }
}
