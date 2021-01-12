package com.udacity.pricing;

import com.udacity.pricing.domain.Currency;
import com.udacity.pricing.domain.Price;
import com.udacity.pricing.repository.PriceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;



@RunWith(SpringRunner.class)
@DataJpaTest
public class PriceRepositoryTests {

    protected Price price;
    protected BigDecimal expectedValue = new BigDecimal(10.50);

    @Autowired
    PriceRepository priceRepository;

    @Before
    public void setup() {
        price = new Price(Currency.GBP, expectedValue, 1L);
        priceRepository.save(price);
    }

    @Test
    public void testFindById() {
        Price retrievedPrice = priceRepository.findById(1L).get();
        assertThat(retrievedPrice.getPrice()).isEqualTo(expectedValue);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDelete(){
        Long newId = 2L;
        priceRepository.save(new Price(Currency.GBP, new BigDecimal(50), newId));
        Price retrievedPrice = priceRepository.findById(newId).get();
        assertThat(retrievedPrice.getVehicleId()).isEqualTo(newId);
        priceRepository.delete(retrievedPrice);
        priceRepository.findById(newId).get();
    }


}