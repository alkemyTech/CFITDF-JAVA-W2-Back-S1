package com.alkemy.wallet.alkywallet.service;
import com.alkemy.wallet.alkywallet.dto.TarjetaDTO;
import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Tarjeta;
import com.alkemy.wallet.alkywallet.repository.ICuentaRepository;
import com.alkemy.wallet.alkywallet.repository.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarjetaServiceImpl implements ITarjetaService{
    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private ICuentaRepository cuentaRepository;

    //Crear tarjeta - CREATE
    @Override
    public TarjetaDTO crearTarjeta(TarjetaDTO dto) {
        Tarjeta tarjeta = new Tarjeta();
        Cuenta cuenta = cuentaRepository.findById(dto.getCuentaDtoId()).
                orElseThrow(() -> {
                    return new RuntimeException("No se encontro la cuenta");
                });
        actualizarCamposDesdeDTO(dto, tarjeta);
        tarjetaRepository.save(tarjeta);
        return new TarjetaDTO(tarjeta);
    }

    // Listar todas las tarjetas con el atributo delete = false - READ
    @Override
    public List<TarjetaDTO> listarTarjetas() {
        List<TarjetaDTO> tarjetas = tarjetaRepository.findByDeleteFalse()
                .stream()
                .map(TarjetaDTO::new)
                .collect(Collectors.toList());
        if (tarjetas.isEmpty()){
            throw new RuntimeException("No se encontraron tarjetas registradas");
        }
        else return tarjetas;
    }

    //Listar tarjeta por id - READ
    @Override
    public TarjetaDTO listarTarjetaPorId(Long id) {
        Tarjeta tarjeta = tarjetaRepository.findByIdAndDeleteFalse(id)
                .orElseThrow(() ->
                new RuntimeException("No se encontro la tarjeta con el id "+id));
        return new TarjetaDTO(tarjeta);
    }

    //Editar tarjeta por id - UPDATE
    @Override
    public TarjetaDTO editarTarjeta(Long id, TarjetaDTO dto) {
        Tarjeta tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la tarjeta con el id "+id));
        actualizarCamposDesdeDTO(dto, tarjeta);
        tarjetaRepository.save(tarjeta);
        return new TarjetaDTO(tarjeta);
    }

    //Eliminar tarjeta por id - DELETE
    @Override
    public void eliminarTarjeta(Long id) {
        Tarjeta tarjeta = tarjetaRepository.findById(id)
            .orElseThrow(() -> {
               return new RuntimeException("No se encontro la tarjeta con el id " +id);
            });
        tarjeta.setDelete(true);
        tarjetaRepository.save(tarjeta);
    }


    private void actualizarCamposDesdeDTO(TarjetaDTO dto, Tarjeta tarjeta) {
        if (dto.getNumero() != null) {
            tarjeta.setNumero(dto.getNumero());
        }
        if (dto.getTipo() != null) {
            tarjeta.setTipo(dto.getTipo());
        }
        if (dto.getFechaExpiracion() != null) {
            tarjeta.setFechaExpiracion(dto.getFechaExpiracion());
        }
        if (dto.getEsVirtual() != null) {
            tarjeta.setEsVirtual(dto.getEsVirtual());
        }
        if (dto.getDelete() != null) {
            tarjeta.setDelete(dto.getDelete());
        }
    }


}
