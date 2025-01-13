package net.local.rentacar.domain.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public interface HolidayServiceTest<T extends HolidayService> {

    T createHolidayService();

    @Test
    default void testIsHoliday_ReturnsTrueForHoliday() {
        T service = createHolidayService();
        LocalDate holiday = LocalDate.of(2025, 1, 1);
        assertTrue(service.isHoliday(holiday));
    }

    @Test
    default void testIsHoliday_ReturnsFalseForNonHoliday() {
        T service = createHolidayService();
        LocalDate nonHoliday = LocalDate.of(2025, 1, 2);
        assertFalse(service.isHoliday(nonHoliday));
    }
}