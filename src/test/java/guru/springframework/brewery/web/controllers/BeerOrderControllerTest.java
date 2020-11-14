package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.repositories.CustomerRepository;
import guru.springframework.brewery.services.BeerOrderService;
import guru.springframework.brewery.web.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerOrderController.class)
class BeerOrderControllerTest {

    @MockBean
    BeerOrderService beerOrderService;

    @Autowired
    MockMvc mockMvc;

    BeerDto testBeer;
    BeerOrderDto beerOrder;
    BeerOrderPagedList beerOrderPagedList;

    String customerId = "8230ccf4-41b5-4f96-85ff-2e2a400d3951";

    @BeforeEach
    void setUp() {
        System.out.println("BeerOrderControllerTest");

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

        beerOrder = BeerOrderDto.builder()
                .id(UUID.randomUUID())
                .customerRef("1234")
                .beerOrderLines(
                        Arrays.asList(BeerOrderLineDto.builder()
                                .beerId(testBeer.getId())
                                .build())
                )
                .build();

        beerOrderPagedList = new BeerOrderPagedList(
                Arrays.asList(beerOrder),
                PageRequest.of(1, 1),
                1L

        );
    }

    @AfterEach
    void tearDown() {
        reset(beerOrderService);
    }

    @Test
    void listOrders() throws Exception {
        given(beerOrderService.listOrders(
                any(UUID.class), any(PageRequest.class)
        )).willReturn(beerOrderPagedList);

        mockMvc.perform(get("/app/v1/customers/"+customerId+"/orders"))
                //.andExpect(status().isOk())
                ;
        System.out.println("wip");
    }

    @Test
    void getOrder() {
        System.out.println("wip");
    }
}