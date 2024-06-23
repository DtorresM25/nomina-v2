package com.konecta.nomina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class NominaApplication {

    private static final Logger log = LoggerFactory.getLogger(NominaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NominaApplication.class, args);
    }

    @GetMapping("/fechaPagoNomina")
    public String proximaFechaPago(@RequestParam String fecha) {
        try {
            LocalDate fechaPago = LocalDate.parse(fecha);
            LocalDate proximaFechaPago = calcularProximaFechaPago(fechaPago);
            return proximaFechaPago.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Ocurrio un error al calcular la fecha de pago de nomina: " + e.getMessage();
        }

    }

    private LocalDate calcularProximaFechaPago(LocalDate fechaPago) {
        int diaPago = fechaPago.getDayOfMonth();
        int mes = fechaPago.getMonthValue();
        int anio = fechaPago.getYear();

        LocalDate proximaFechaPago;

        if (diaPago <= 15) {
            proximaFechaPago = LocalDate.of(anio, mes, 15);
        } else {
            proximaFechaPago = LocalDate.of(anio, mes, 30);
        }

        while (!esDiaHabil(proximaFechaPago)) {
            proximaFechaPago = proximaFechaPago.minusDays(1);
        }

        return proximaFechaPago;
    }

    private boolean esDiaHabil(LocalDate fecha) {
        return !esFestivo(fecha) && !esFinDeSemana(fecha);
    }

    private boolean esFinDeSemana(LocalDate fecha) {
        return fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean esFestivo(LocalDate fecha) {
        List<LocalDate> festivosColombia = getFestivosColombia();
        return festivosColombia.contains(fecha);
    }

    private List<LocalDate> getFestivosColombia() {
        // Lista de fechas festivas en colombia 2024
        List<LocalDate> festivosColombia = new ArrayList<>();
        festivosColombia.add(LocalDate.of(2024, 1, 1));
        festivosColombia.add(LocalDate.of(2024, 1, 8));
        festivosColombia.add(LocalDate.of(2024, 3, 25));
        festivosColombia.add(LocalDate.of(2024, 3, 28));
        festivosColombia.add(LocalDate.of(2024, 3, 29));
        festivosColombia.add(LocalDate.of(2024, 5, 1));
        festivosColombia.add(LocalDate.of(2024, 5, 13));
        festivosColombia.add(LocalDate.of(2024, 6, 3));
        festivosColombia.add(LocalDate.of(2024, 6, 10));
        festivosColombia.add(LocalDate.of(2024, 7, 1));
        festivosColombia.add(LocalDate.of(2024, 7, 20));
        festivosColombia.add(LocalDate.of(2024, 8, 7));
        festivosColombia.add(LocalDate.of(2024, 8, 19));
        festivosColombia.add(LocalDate.of(2024, 10, 14));
        festivosColombia.add(LocalDate.of(2024, 11, 4));
        festivosColombia.add(LocalDate.of(2024, 11, 11));
        festivosColombia.add(LocalDate.of(2024, 12, 8));
        festivosColombia.add(LocalDate.of(2024, 12, 25));

        return festivosColombia;
    }
}
