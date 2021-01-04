package escola.fred.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Prova.
 */
@Entity
@Table(name = "prova")
public class Prova implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "numquestoes")
    private Integer numquestoes;

    @Column(name = "enunciado")
    private String enunciado;

    @Column(name = "texto")
    private String texto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Prova nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumquestoes() {
        return numquestoes;
    }

    public Prova numquestoes(Integer numquestoes) {
        this.numquestoes = numquestoes;
        return this;
    }

    public void setNumquestoes(Integer numquestoes) {
        this.numquestoes = numquestoes;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public Prova enunciado(String enunciado) {
        this.enunciado = enunciado;
        return this;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTexto() {
        return texto;
    }

    public Prova texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prova)) {
            return false;
        }
        return id != null && id.equals(((Prova) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prova{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", numquestoes=" + getNumquestoes() +
            ", enunciado='" + getEnunciado() + "'" +
            ", texto='" + getTexto() + "'" +
            "}";
    }
}
