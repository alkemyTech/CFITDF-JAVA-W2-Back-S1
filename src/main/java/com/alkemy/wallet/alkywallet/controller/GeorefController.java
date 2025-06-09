package com.alkemy.wallet.alkywallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alkemy.wallet.alkywallet.service.GeorefService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class GeorefController {

    @Autowired
    private GeorefService georefService;

    @GetMapping("/provincias")
    public List<Map<String, String>> obtenerProvincias() {
        return georefService.getProvincias();
    }

    @GetMapping("/departamentos")
    public List<Map<String, String>> obtenerDepartamentosPorProvincia(
            @RequestParam String provincia) {

        return georefService.getDepartamentosPorProvincia(provincia);
    }

    @GetMapping("/municipios")
    public List<Map<String, String>> obtenerMunicipiosPorProvincia(
            @RequestParam String provincia) {

        return georefService.getMunicipiosPorProvincia(provincia);
    }
}

