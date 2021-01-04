package escola.fred.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EscolaMapperTest {

    private EscolaMapper escolaMapper;

    @BeforeEach
    public void setUp() {
        escolaMapper = new EscolaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(escolaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(escolaMapper.fromId(null)).isNull();
    }
}
