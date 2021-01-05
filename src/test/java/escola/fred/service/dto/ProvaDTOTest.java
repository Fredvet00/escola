package escola.fred.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import escola.fred.web.rest.TestUtil;

public class ProvaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProvaDTO.class);
        ProvaDTO provaDTO1 = new ProvaDTO();
        provaDTO1.setId(1L);
        ProvaDTO provaDTO2 = new ProvaDTO();
        assertThat(provaDTO1).isNotEqualTo(provaDTO2);
        provaDTO2.setId(provaDTO1.getId());
        assertThat(provaDTO1).isEqualTo(provaDTO2);
        provaDTO2.setId(2L);
        assertThat(provaDTO1).isNotEqualTo(provaDTO2);
        provaDTO1.setId(null);
        assertThat(provaDTO1).isNotEqualTo(provaDTO2);
    }
}
