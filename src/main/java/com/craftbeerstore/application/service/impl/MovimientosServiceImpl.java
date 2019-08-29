package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Receta;
import com.craftbeerstore.application.domain.enumeration.TipoMovimiento;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.repository.ProductoRepository;
import com.craftbeerstore.application.repository.RecetaRepository;
import com.craftbeerstore.application.service.MovimientosService;
import com.craftbeerstore.application.domain.Movimientos;
import com.craftbeerstore.application.repository.MovimientosRepository;
import com.craftbeerstore.application.service.dto.MovimientosDTO;
import com.craftbeerstore.application.service.dto.MovimientosProductoSemanaDTO;
import com.craftbeerstore.application.service.dto.MovimientosSemanaDTO;
import com.craftbeerstore.application.service.dto.MovimientosVentasDTO;
import com.craftbeerstore.application.service.mapper.MovimientosMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Movimientos.
 */
@Service
@Transactional
public class MovimientosServiceImpl implements MovimientosService {

    private final Logger log = LoggerFactory.getLogger(MovimientosServiceImpl.class);

  private static final DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final MovimientosRepository movimientosRepository;

    private final MovimientosMapper movimientosMapper;

    private final RecetaRepository recetaRepository;

    private final ProductoRepository productoRepository;

    private final EmpresaRepository empresaRepository;

    public MovimientosServiceImpl(MovimientosRepository movimientosRepository,
                                  MovimientosMapper movimientosMapper,
                                  RecetaRepository recetaRepository, ProductoRepository productoRepository, EmpresaRepository empresaRepository) {
        this.movimientosRepository = movimientosRepository;
        this.movimientosMapper = movimientosMapper;
      this.recetaRepository = recetaRepository;
      this.productoRepository = productoRepository;
      this.empresaRepository = empresaRepository;
    }

    /**
     * Save a movimientos.
     *
     * @param movimientosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MovimientosDTO save(MovimientosDTO movimientosDTO) {
        log.debug("Request to save Movimientos : {}", movimientosDTO);
        Movimientos movimientos = movimientosMapper.toEntity(movimientosDTO);
        movimientos = movimientosRepository.save(movimientos);
        MovimientosDTO result = movimientosMapper.toDto(movimientos);
        return result;
    }

    /**
     * Get all the movimientos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovimientosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Movimientos");
        return movimientosRepository.findAll(pageable)
            .map(movimientosMapper::toDto);
    }

    @Override
    public Page<MovimientosDTO> findAll(Pageable pageable, Long empresaId) {
        Empresa empresa = this.empresaRepository.getOne(empresaId);
        return movimientosRepository.findAllByEmpresa(pageable, empresa)
            .map(movimientosMapper::toDto);
    }


    /**
     * Get one movimientos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MovimientosDTO> findOne(Long id) {
        log.debug("Request to get Movimientos : {}", id);
        return movimientosRepository.findById(id)
            .map(movimientosMapper::toDto);
    }

    /**
     * Delete the movimientos by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movimientos : {}", id);
        movimientosRepository.deleteById(id);
    }

    @Override
    public List<MovimientosSemanaDTO> findMovimientosSemana(Long empresaId, String dias) {
        List<MovimientosSemanaDTO> list = new ArrayList<>();
        List<Object[]> movimientos = this.movimientosRepository.queryMovimientoSemana(empresaId, LocalDate.now().minusDays(Long.valueOf(dias)), LocalDate.now());
        movimientos.forEach(mov -> {
            list.add(new MovimientosSemanaDTO(Long.valueOf(mov[0].toString()), TipoMovimiento.valueOf(mov[1].toString()), LocalDate.parse(mov[2].toString()),
                BigDecimal.valueOf(Double.valueOf(mov[3].toString()))));
        });
        return list;
    }

    @Override
    public List<MovimientosProductoSemanaDTO> findMovimientoProductoSemana(Long empresaId,
        String dias) {
        List<MovimientosProductoSemanaDTO> list = new ArrayList<>();
        List<Object[]> movimientos = this.movimientosRepository.queryVentaProductoSemana(empresaId, LocalDate.now().minusDays(Long.valueOf(dias)), LocalDate.now());
        movimientos.forEach(mov -> {
          list.add(new MovimientosProductoSemanaDTO(Long.valueOf(mov[0].toString()), TipoMovimiento.valueOf(mov[1].toString()), LocalDate.parse(mov[2].toString()),
                BigDecimal.valueOf(Double.valueOf(mov[3].toString())), Long.valueOf(mov[4].toString()), mov[5].toString(), mov[6].toString()));
        });
        return list;
    }

  @Override
  public MovimientosProductoSemanaDTO findLitrosSemana(Long empresaId, String dias) {
    MovimientosProductoSemanaDTO list = new MovimientosProductoSemanaDTO();
    BigDecimal movimientos = this.movimientosRepository.queryLitrosSemana(empresaId, LocalDate.now().minusDays(Long.valueOf(dias)), LocalDate.now());
    list.setLitros(movimientos.toString());
    return list;
  }

  @Override
  public List<MovimientosVentasDTO> findPeriodoLitrosSemana(Long empresaId){
    List<MovimientosVentasDTO> list = new ArrayList<>();
    List<Object[]> movimientos = this.movimientosRepository.queryLitrosSemana(empresaId);
    movimientos.forEach(mov -> {
      MovimientosVentasDTO movimientosVentasDTO = new MovimientosVentasDTO(LocalDate.parse(mov[0].toString(), DATEFORMATTER), BigDecimal.valueOf(Double.valueOf(mov[1].toString())));
      list.add(movimientosVentasDTO);
    });

    return list;
  }

  @Override
  public List<MovimientosVentasDTO> findPeriodoLitrosMes(Long empresaId) {
    List<MovimientosVentasDTO> list = new ArrayList<>();
    List<Object[]> movimientos = this.movimientosRepository.queryLitrosMes(empresaId);
    movimientos.forEach(mov -> {
      MovimientosVentasDTO movimientosVentasDTO = new MovimientosVentasDTO(LocalDate.parse(mov[0].toString(), DATEFORMATTER), BigDecimal.valueOf(Double.valueOf(mov[1].toString())));
      list.add(movimientosVentasDTO);
    });

    return list;
  }
}
