package escola.fred.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import escola.fred.web.rest.TestUtil;

public class EscolaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscolaDTO.class);
        EscolaDTO escolaDTO1 = new EscolaDTO();
        escolaDTO1.setId(1L);
        EscolaDTO escolaDTO2 = new EscolaDTO();
        assertThat(escolaDTO1).isNotEqualTo(escolaDTO2);
        escolaDTO2.setId(escolaDTO1.getId());
        assertThat(escolaDTO1).isEqualTo(escolaDTO2);
        escolaDTO2.setId(2L);
        assertThat(escolaDTO1).isNotEqualTo(escolaDTO2);
        escolaDTO1.setId(null);
        assertThat(escolaDTO1).isNotEqualTo(escolaDTO2);
    }
}
