package escola.fred.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import escola.fred.web.rest.TestUtil;

public class ProvaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prova.class);
        Prova prova1 = new Prova();
        prova1.setId(1L);
        Prova prova2 = new Prova();
        prova2.setId(prova1.getId());
        assertThat(prova1).isEqualTo(prova2);
        prova2.setId(2L);
        assertThat(prova1).isNotEqualTo(prova2);
        prova1.setId(null);
        assertThat(prova1).isNotEqualTo(prova2);
    }
}
