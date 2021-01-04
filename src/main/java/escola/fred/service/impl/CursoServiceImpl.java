package escola.fred.service.impl;

import escola.fred.service.CursoService;
import escola.fred.domain.Curso;
import escola.fred.repository.CursoRepository;
import escola.fred.service.dto.CursoDTO;
import escola.fred.service.mapper.CursoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Curso}.
 */
@Service
@Transactional
public class CursoServiceImpl implements CursoService {

    private final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);

    private final CursoRepository cursoRepository;

    private final CursoMapper cursoMapper;

    public CursoServiceImpl(CursoRepository cursoRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
    }

    @Override
    public CursoDTO save(CursoDTO cursoDTO) {
        log.debug("Request to save Curso : {}", cursoDTO);
        Curso curso = cursoMapper.toEntity(cursoDTO);
        curso = cursoRepository.save(curso);
        return cursoMapper.toDto(curso);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CursoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cursos");
        return cursoRepository.findAll(pageable)
            .map(cursoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CursoDTO> findOne(Long id) {
        log.debug("Request to get Curso : {}", id);
        return cursoRepository.findById(id)
            .map(cursoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Curso : {}", id);
        cursoRepository.deleteById(id);
    }
}
