package com.craftbeerstore.application.service.impl;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.repository.EmpresaRepository;
import com.craftbeerstore.application.service.ClienteService;
import com.craftbeerstore.application.domain.Cliente;
import com.craftbeerstore.application.repository.ClienteRepository;
import com.craftbeerstore.application.service.dto.ClienteDTO;
import com.craftbeerstore.application.service.mapper.ClienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    private final ClienteMapper clienteMapper;

    private final EmpresaRepository empresaRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper, EmpresaRepository empresaRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.empresaRepository = empresaRepository;
    }

    /**
     * Save a cliente.
     *
     * @param clienteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClienteDTO save(ClienteDTO clienteDTO) {
        log.debug("Request to save Cliente : {}", clienteDTO);
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toDto(cliente);
    }

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll(pageable)
            .map(clienteMapper::toDto);
    }

  @Override
  public Page<ClienteDTO> findAll(Pageable pageable, Long empresaId) {
    Empresa empresa = this.empresaRepository.getOne(empresaId);
    return clienteRepository.findAllByEmpresa(pageable, empresa).map(clienteMapper::toDto);
  }


    /**
     * Get one cliente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id)
            .map(clienteMapper::toDto);
    }

    /**
     * Delete the cliente by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
    }
}
