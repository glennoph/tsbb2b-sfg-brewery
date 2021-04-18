package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.services.BeerService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerPagedList;
import guru.springframework.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BeerControllerTest {

    @Mock
    BeerService beerService;

    @InjectMocks
    BeerController beerController;

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

        mockMvc = MockMvcBuilders.standaloneSetup(beerController).build();
    }

    @Test
    void testGetBeerById() throws Exception {
        given(beerService.findBeerById(any())).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId()))
                .andDo(MockMvcResultHandlers.print()) // print the json result string
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer1")));
    }

    /***/
    //@Nested - java: annotation type not applicable to this kind of declaration WHY?
    @DisplayName("list ops -")
    public class TestListOperations {

        @Captor
        ArgumentCaptor<String> beerNameCaptor;
        @Captor
        ArgumentCaptor<BeerStyleEnum> styleCaptor;
        @Captor
        ArgumentCaptor<PageRequest> pageReqCaptor;

        BeerPagedList beerPagedList;

        @BeforeEach
        void setup() {
            List<BeerDto> beers = new ArrayList<>();
            beers.add(testBeer);
            beers.add(BeerDto.builder()
                    .id(UUID.randomUUID())
                    .version(1)
                    .upc(1122334455L)
                    .beerStyle(BeerStyleEnum.LAGER)
                    .price(new BigDecimal("22.22"))
                    .quantityOnHand(77)
                    .createdDate(OffsetDateTime.now())
                    .lastModifiedDate(OffsetDateTime.now())
                    .build());

            beerPagedList = new BeerPagedList(beers,
                    PageRequest.of(1,1), 2);

            given(beerService.listBeers(
                    beerNameCaptor.capture(),
                    styleCaptor.capture(),
                    pageReqCaptor.capture()
            )).willReturn(beerPagedList);

        }


        @DisplayName("test list beer - no parms")
        @Test
        void testListBeers() throws Exception {
            mockMvc.perform(get("/api/v1/beer")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        }


    }
/***/

}