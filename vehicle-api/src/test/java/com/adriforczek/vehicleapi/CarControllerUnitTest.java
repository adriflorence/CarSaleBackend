package com.adriforczek.vehicleapi;

import com.adriforczek.vehicles.api.CarController;
import com.adriforczek.vehicles.api.CarResourceAssembler;
import com.adriforczek.vehicles.client.maps.MapsClient;
import com.adriforczek.vehicles.client.prices.PriceClient;
import com.adriforczek.vehicles.domain.Condition;
import com.adriforczek.vehicles.domain.Location;
import com.adriforczek.vehicles.domain.car.Car;
import com.adriforczek.vehicles.domain.car.Details;
import com.adriforczek.vehicles.domain.manufacturer.Manufacturer;
import com.adriforczek.vehicles.service.CarService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.URI;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {CarController.class, CarResourceAssembler.class})
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@EnableWebMvc
public class CarControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    CarService carService;

    @MockBean
    private PriceClient priceClient;

    @MockBean
    private MapsClient mapsClient;

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @Before
    public void setup() {
        Car car = createCar();
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.listCars()).willReturn(Collections.singletonList(car));
    }

    @Test
    public void saveCar() throws Exception {
        Car car = createCar();
        mockMvc.perform(post(new URI("/cars/"))
                .content(json.write(car).getJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAllCars() throws Exception {
        mockMvc.perform(get("/cars/"))
                .andExpect(status().isOk());

        verify(carService, times(1)).listCars();
    }

    @Test
    public void getCar() throws Exception {
        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk());

        verify(carService, times(1)).findById(1L);
    }

    @Test
    public void updateCar() throws Exception {
        Car car = createCar();
        mockMvc.perform(put(new URI("/cars/1"))
                .content(json.write(car).getJson())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCar() throws Exception {
        mockMvc.perform(delete(new URI("/cars/1"))).andExpect(status().is2xxSuccessful());
    }


    private Car createCar() {
        Car car = new Car();
        car.setId(1L);
        car.setLocation(new Location(55.8642, 4.2518));
        car.setDetails(createDetails());
        car.setCondition(Condition.USED);

        return car;
    }

    private Details createDetails() {
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(1, "Volkswagen");

        details.setManufacturer(manufacturer);
        details.setEngine("I4 8V");
        details.setExternalColor("Black");
        details.setFuelType("Petrol");
        details.setMileage(15300);
        details.setModel("1.9 TDI");
        details.setModelYear(2003);
        details.setNumberOfDoors(7);
        details.setProductionYear(2004);
        details.setBody("Sharan");

        return details;
    }

}
