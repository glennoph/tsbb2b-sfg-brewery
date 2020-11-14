package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.services.BeerService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerWebMvcTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    BeerDto testBeer;

    @BeforeEach
    void setUp() {
        testBeer = BeerDto.builder().id(UUID.randomUUID())
                .version(1)
                .beerName("Beer1")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .price(new BigDecimal("12.99"))
                .quantityOnHand(4)
                .upc(123456789012L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

    }

    @AfterEach
    void tearDown() {
        reset(beerService); // reset the beer service mock
    }

    @Test
    void testGetBeerById() throws Exception {
        given(beerService.findBeerById(any())).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer1")));
    }

}