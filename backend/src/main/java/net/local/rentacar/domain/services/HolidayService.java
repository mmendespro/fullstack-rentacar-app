package net.local.rentacar.domain.services;

import java.time.LocalDate;

public interface HolidayService {
    boolean isHoliday(LocalDate date);
}
