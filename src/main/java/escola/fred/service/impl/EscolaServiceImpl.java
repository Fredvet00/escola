package escola.fred.service.impl;

import escola.fred.service.EscolaService;
import escola.fred.domain.Escola;
import escola.fred.repository.EscolaRepository;
import escola.fred.service.dto.EscolaDTO;
import escola.fred.service.mapper.EscolaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Escola}.
 */
@Service
@Transactional
public class EscolaServiceImpl implements EscolaService {

    private final Logger log = LoggerFactory.getLogger(EscolaServiceImpl.class);

    private final EscolaRepository escolaRepository;

    private final EscolaMapper escolaMapper;

    public EscolaServiceImpl(EscolaRepository escolaRepository, EscolaMapper escolaMapper) {
        this.escolaRepository = escolaRepository;
        this.escolaMapper = escolaMapper;
    }

    @Override
    public EscolaDTO save(EscolaDTO escolaDTO) {
        log.debug("Request to save Escola : {}", escolaDTO);
        Escola escola = escolaMapper.toEntity(escolaDTO);
        escola = escolaRepository.save(escola);
        return escolaMapper.toDto(escola);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EscolaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Escolas");
        return escolaRepository.findAll(pageable)
            .map(escolaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EscolaDTO> findOne(Long id) {
        log.debug("Request to get Escola : {}", id);
        return escolaRepository.findById(id)
            .map(escolaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Escola : {}", id);
        escolaRepository.deleteById(id);
    }
}
