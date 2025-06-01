package com.alkemy.wallet.alkywallet.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeorefService {

    private final RestTemplate restTemplate;

    public GeorefService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String BASE_URL = "https://apis.datos.gob.ar/georef/api";

    public List<Map<String, String>> getProvincias() {
        // Construir la URL con el parámetro 'campos' para obtener solo id y nombre
        String url = BASE_URL + "/provincias?campos=id,nombre";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        // Extraer solo las provincias
        List<Map<String, String>> provincias = (List<Map<String, String>>) response.getBody().get("provincias");

        // Retornar solo id y nombre
        return provincias.stream()
                .map(provincia -> Map.of("id", provincia.get("id"), "nombre", provincia.get("nombre")))
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> getDepartamentosPorProvincia(String provincia) {
        // Construir la URL base
        String url = BASE_URL + "/departamentos?provincia=" + provincia;

        // Realizar la solicitud GET
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        // Extraer solo los departamentos
        List<Map<String, String>> departamentos = (List<Map<String, String>>) response.getBody().get("departamentos");

        // Retornar solo id y nombre
        return departamentos.stream()
                .map(departamento -> Map.of("id", departamento.get("id"), "nombre", departamento.get("nombre")))
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> getMunicipiosPorProvincia(String provincia) {
        // Construir la URL base
        String url = BASE_URL + "/municipios?provincia=" + provincia;

        // Realizar la solicitud GET
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        // Extraer solo los municipios
        List<Map<String, String>> municipios = (List<Map<String, String>>) response.getBody().get("municipios");

        // Retornar solo id y nombre
        return municipios.stream()
                .map(municipio -> Map.of("id", municipio.get("id"), "nombre", municipio.get("nombre")))
                .collect(Collectors.toList());
    }
}

