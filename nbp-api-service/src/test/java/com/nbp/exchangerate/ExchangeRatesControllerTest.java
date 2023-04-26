package com.nbp.exchangerate;

import com.nbp.dto.MaxMinExchangeRateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExchangeRatesController.class)
class ExchangeRatesControllerTest {

    @MockBean
    private ExchangeRatesService nbpExchangeRatesService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAverageExchangeRateByDateWithValidInputs() throws Exception {
        double expectedValue = 4.125;
        when(nbpExchangeRatesService.getAverageExchangeRateByDate(anyString(), anyString())).thenReturn(expectedValue);

        mockMvc.perform(get("/api/exchange-rate/USD/2022-04-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedValue));
    }

    @Test
    void testGetMaxMinAverageExchangeRateWithValidInputs() throws Exception {
        double expectedMinValue = 4.0;
        double expectedMaxValue = 4.5;
        MaxMinExchangeRateDTO expectedValue = new MaxMinExchangeRateDTO(expectedMaxValue, expectedMinValue);
        when(nbpExchangeRatesService.getMaxMinAverageExchangeRate(anyString(), anyString())).thenReturn(expectedValue);

        mockMvc.perform(get("/api/exchange-rate/USD/max-min-average-value?numberOfLastQuotations=5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.minimumExchangeRate").value(expectedMinValue))
                .andExpect(jsonPath("$.maximumExchangeRate").value(expectedMaxValue));
    }

    @Test
    void testGetMajorDifferenceWithValidInputs() throws Exception {
        double expectedValue = 0.1;
        when(nbpExchangeRatesService.getMajorDifference(anyString(), anyString())).thenReturn(expectedValue);

        mockMvc.perform(get("/api/exchange-rate/USD/major-rate-difference?numberOfLastQuotations=5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedValue));
    }
}