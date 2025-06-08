package com.alkemy.wallet.alkywallet.service;

import com.alkemy.wallet.alkywallet.dto.ConversionMonedaRequestDTO;
import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.ResultadoConversionDTO;

public interface IConversionService {
    ResultadoConversionDTO convertirMoneda(ConversionMonedaRequestDTO dto);
}
