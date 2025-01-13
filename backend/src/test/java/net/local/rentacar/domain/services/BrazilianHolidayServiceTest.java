package net.local.rentacar.domain.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BrazilianHolidayServiceTest implements HolidayServiceTest<BrazilianHolidayService> {

    private BrazilianHolidayService holidayService;

    @Override
    public BrazilianHolidayService createHolidayService() {
        return new BrazilianHolidayService();
    }

    @BeforeEach
    void setUp() {
        holidayService = createHolidayService();
    }

    @Test
    void testIsHoliday_NewYearsDay() {
        LocalDate newYearsDay = LocalDate.of(2025, 1, 1);
        assertTrue(holidayService.isHoliday(newYearsDay));
    }

    @Test
    void testIsHoliday_Tiradentes() {
        LocalDate tiradentes = LocalDate.of(2025, 4, 21);
        assertTrue(holidayService.isHoliday(tiradentes));
    }

    @Test
    void testIsHoliday_LaborDay() {
        LocalDate laborDay = LocalDate.of(2025, 5, 1);
        assertTrue(holidayService.isHoliday(laborDay));
    }

    @Test
    void testIsHoliday_IndependenceDay() {
        LocalDate independenceDay = LocalDate.of(2025, 9, 7);
        assertTrue(holidayService.isHoliday(independenceDay));
    }

    @Test
    void testIsHoliday_OurLadyOfAparecida() {
        LocalDate ourLadyOfAparecida = LocalDate.of(2025, 10, 12);
        assertTrue(holidayService.isHoliday(ourLadyOfAparecida));
    }

    @Test
    void testIsHoliday_AllSoulsDay() {
        LocalDate allSoulsDay = LocalDate.of(2025, 11, 2);
        assertTrue(holidayService.isHoliday(allSoulsDay));
    }

    @Test
    void testIsHoliday_RepublicProclamationDay() {
        LocalDate republicProclamationDay = LocalDate.of(2025, 11, 15);
        assertTrue(holidayService.isHoliday(republicProclamationDay));
    }

    @Test
    void testIsHoliday_Christmas() {
        LocalDate christmas = LocalDate.of(2025, 12, 25);
        assertTrue(holidayService.isHoliday(christmas));
    }

    @Test
    void testIsHoliday_RegularDay() {
        LocalDate regularDay = LocalDate.of(2025, 1, 2);
        assertFalse(holidayService.isHoliday(regularDay));
    }
}