package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.enumeration.TipoMovimientoCaja;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.CajaService;
import com.craftbeerstore.application.domain.Caja;
import com.craftbeerstore.application.repository.CajaRepository;
import com.craftbeerstore.application.service.dto.CajaChartDTO;
import com.craftbeerstore.application.service.dto.CajaDTO;
import com.craftbeerstore.application.service.mapper.CajaMapper;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Caja.
 */
@Service
@Transactional
public class CajaServiceImpl implements CajaService {

    private final Logger log = LoggerFactory.getLogger(CajaServiceImpl.class);

    private static final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final CajaRepository cajaRepository;

    private final CajaMapper cajaMapper;

    private final EmpresaRepository empresaRepository;

    public CajaServiceImpl(CajaRepository cajaRepository, CajaMapper cajaMapper,
        EmpresaRepository empresaRepository) {
        this.cajaRepository = cajaRepository;
        this.cajaMapper = cajaMapper;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Save a caja.
     *
     * @param cajaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CajaDTO save(CajaDTO cajaDTO) {
        log.debug("Request to save Caja : {}", cajaDTO);
        Caja caja = cajaMapper.toEntity(cajaDTO);
        caja = cajaRepository.save(caja);
        CajaDTO result = cajaMapper.toDto(caja);
        return result;
    }

    /**
     * Get all the cajas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CajaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cajas");
        return cajaRepository.findAll(pageable)
            .map(cajaMapper::toDto);
    }

    @Override
    public Page<CajaDTO> findAll(Pageable pageable, Long empresaId) {
        Empresa empresa = this.empresaRepository.getOne(empresaId);
        return cajaRepository.findAllByEmpresa(pageable, empresa)
            .map(cajaMapper::toDto);
    }


    /**
     * Get one caja by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CajaDTO> findOne(Long id) {
        log.debug("Request to get Caja : {}", id);
        return cajaRepository.findById(id)
            .map(cajaMapper::toDto);
    }

    /**
     * Delete the caja by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caja : {}", id);
        cajaRepository.deleteById(id);
    }

    @Override
    public Optional<CajaChartDTO> searchIngresoEgreso(Long empresaId) {
        Empresa empresa = this.empresaRepository.getOne(empresaId);
        BigDecimal ingreso = this.cajaRepository
            .sumIngreso(empresa, TipoMovimientoCaja.INGRESO);
        BigDecimal egreso = this.cajaRepository.sumIngreso(empresa, TipoMovimientoCaja.EGRESO);

        return Optional.of(new CajaChartDTO(ingreso, egreso));
    }

    @Override
    public List<CajaDTO> getIngresoWeek(Long empresaId) {
        Empresa empresa = this.empresaRepository.getOne(empresaId);
        List<Object[]> list = this.cajaRepository.getSemanaIngresos(empresa.getId());
        List<CajaDTO> semanaList = new ArrayList<>();
        list.forEach(semana ->
            semanaList.add(new CajaDTO(BigDecimal.valueOf(Double.valueOf(semana[1].toString())), LocalDate.parse(semana[0].toString(), DATEFORMATTER)))
        );
        return semanaList;
    }

  @Override
  public List<CajaDTO> getIngresoMonth(Long empresaId) {
    Empresa empresa = this.empresaRepository.getOne(empresaId);
    List<Object[]> list = this.cajaRepository.getMesIngresos(empresa.getId());
    List<CajaDTO> mesList = new ArrayList<>();
    list.forEach(semana ->
      mesList.add(new CajaDTO(BigDecimal.valueOf(Double.valueOf(semana[1].toString())), LocalDate.parse(semana[0].toString(), DATEFORMATTER)))
    );
    return mesList;
  }
}
