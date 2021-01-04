package escola.fred.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProvaMapperTest {

    private ProvaMapper provaMapper;

    @BeforeEach
    public void setUp() {
        provaMapper = new ProvaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(provaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(provaMapper.fromId(null)).isNull();
    }
}
