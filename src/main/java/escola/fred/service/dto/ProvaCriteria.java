package escola.fred.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link escola.fred.domain.Prova} entity. This class is used
 * in {@link escola.fred.web.rest.ProvaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prova?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProvaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private IntegerFilter numquestoes;

    private StringFilter enunciado;

    private StringFilter texto;

    public ProvaCriteria() {
    }

    public ProvaCriteria(ProvaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.numquestoes = other.numquestoes == null ? null : other.numquestoes.copy();
        this.enunciado = other.enunciado == null ? null : other.enunciado.copy();
        this.texto = other.texto == null ? null : other.texto.copy();
    }

    @Override
    public ProvaCriteria copy() {
        return new ProvaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public IntegerFilter getNumquestoes() {
        return numquestoes;
    }

    public void setNumquestoes(IntegerFilter numquestoes) {
        this.numquestoes = numquestoes;
    }

    public StringFilter getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(StringFilter enunciado) {
        this.enunciado = enunciado;
    }

    public StringFilter getTexto() {
        return texto;
    }

    public void setTexto(StringFilter texto) {
        this.texto = texto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProvaCriteria that = (ProvaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(numquestoes, that.numquestoes) &&
            Objects.equals(enunciado, that.enunciado) &&
            Objects.equals(texto, that.texto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nome,
        numquestoes,
        enunciado,
        texto
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (numquestoes != null ? "numquestoes=" + numquestoes + ", " : "") +
                (enunciado != null ? "enunciado=" + enunciado + ", " : "") +
                (texto != null ? "texto=" + texto + ", " : "") +
            "}";
    }

}
