package net.local.rentacar.domain.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class BrazilianHolidayService implements HolidayService {
    
    private final Set<LocalDate> holidays;

    public BrazilianHolidayService() {
        holidays = new HashSet<>();
        int year = LocalDate.now().getYear();
        
        // Feriados fixos
        holidays.add(LocalDate.of(year, 1, 1));   // Ano Novo
        holidays.add(LocalDate.of(year, 4, 21));  // Tiradentes
        holidays.add(LocalDate.of(year, 5, 1));   // Dia do Trabalho
        holidays.add(LocalDate.of(year, 9, 7));   // Independência
        holidays.add(LocalDate.of(year, 10, 12)); // Nossa Senhora Aparecida
        holidays.add(LocalDate.of(year, 11, 2));  // Finados
        holidays.add(LocalDate.of(year, 11, 15)); // Proclamação da República
        holidays.add(LocalDate.of(year, 12, 25)); // Natal
        
        // Pode-se adicionar lógica para feriados móveis (Carnaval, Páscoa, etc.)
        // ou integrar com uma API externa de feriados
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }
}
