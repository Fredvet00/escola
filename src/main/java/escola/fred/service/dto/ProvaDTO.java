package escola.fred.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link escola.fred.domain.Prova} entity.
 */
public class ProvaDTO implements Serializable {
    
    private Long id;

    private String nome;

    private Integer numquestoes;

    private String enunciado;

    private String texto;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumquestoes() {
        return numquestoes;
    }

    public void setNumquestoes(Integer numquestoes) {
        this.numquestoes = numquestoes;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProvaDTO)) {
            return false;
        }

        return id != null && id.equals(((ProvaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", numquestoes=" + getNumquestoes() +
            ", enunciado='" + getEnunciado() + "'" +
            ", texto='" + getTexto() + "'" +
            "}";
    }
}
