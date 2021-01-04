package escola.fred.service.impl;

import escola.fred.service.ProvaService;
import escola.fred.domain.Prova;
import escola.fred.repository.ProvaRepository;
import escola.fred.service.dto.ProvaDTO;
import escola.fred.service.mapper.ProvaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Prova}.
 */
@Service
@Transactional
public class ProvaServiceImpl implements ProvaService {

    private final Logger log = LoggerFactory.getLogger(ProvaServiceImpl.class);

    private final ProvaRepository provaRepository;

    private final ProvaMapper provaMapper;

    public ProvaServiceImpl(ProvaRepository provaRepository, ProvaMapper provaMapper) {
        this.provaRepository = provaRepository;
        this.provaMapper = provaMapper;
    }

    @Override
    public ProvaDTO save(ProvaDTO provaDTO) {
        log.debug("Request to save Prova : {}", provaDTO);
        Prova prova = provaMapper.toEntity(provaDTO);
        prova = provaRepository.save(prova);
        return provaMapper.toDto(prova);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProvaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prova");
        return provaRepository.findAll(pageable)
            .map(provaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProvaDTO> findOne(Long id) {
        log.debug("Request to get Prova : {}", id);
        return provaRepository.findById(id)
            .map(provaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prova : {}", id);
        provaRepository.deleteById(id);
    }
}
