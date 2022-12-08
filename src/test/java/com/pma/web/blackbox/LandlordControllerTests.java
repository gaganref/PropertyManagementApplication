package com.pma.web.blackbox;

import com.github.javafaker.Faker;
import com.pma.web.controller.HouseController;
import com.pma.web.controller.LandlordController;
import com.pma.web.model.Address;
import com.pma.web.model.House;
import com.pma.web.model.Landlord;
import com.pma.web.service.LandlordServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(LandlordController.class)
public class LandlordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LandlordServiceImpl landlordService;

    private static final int FAKE_DATA_QUANTITY = 10;

    // Should always be greater than 0
    // No of times each test should run
    private static final int TEST_REPETITIONS = 10;

    private static final Faker faker = new Faker(Locale.UK);

    static List<Landlord> landlords = new ArrayList<>();
    static List<Address> addresses = new ArrayList<>();
    static List<House> houses = new ArrayList<>();

    private int currentRandIndex = 0;


    @BeforeAll
    public static void populateData(){
        for(int itr=0; itr<FAKE_DATA_QUANTITY; itr++){
            Landlord landlord = new Landlord(
                    itr,
                    faker.name().firstName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().subscriberNumber(10)
            );

            Address address;

            if(itr<FAKE_DATA_QUANTITY-2){
                address = new Address(
                        faker.random().nextInt(1, 1200),
                        faker.number().numberBetween(1, 3000),
                        faker.address().streetName(),
                        getCity(),
                        faker.address().zipCode()
                );
            }
            else{
                address = new Address(
                        faker.number().numberBetween(1, 3000),
                        faker.address().streetName(),
                        getCity(),
                        faker.address().zipCode()
                );
            }

            landlords.add(landlord);
            addresses.add(address);
        }


        for(int itr=0; itr<FAKE_DATA_QUANTITY; itr++){
            Landlord landlord = landlords.get(faker.random().nextInt(0, FAKE_DATA_QUANTITY-1));
            House house = new House(
                    itr,
                    landlord,
                    addresses.get(itr),
                    faker.random().nextInt(1, 10),
                    new BigDecimal(faker.random().nextLong(1000))
            );

            houses.add(house);
        }

        for(int itr=0; itr<FAKE_DATA_QUANTITY; itr++){
            Set<House> houseSet = new HashSet<>();
            houseSet.add(houses.get(itr));
            landlords.get(itr).setHouses(houseSet);
        }
    }

    @BeforeEach
    public void InitEachTests(){
        currentRandIndex = faker.random().nextInt(0, FAKE_DATA_QUANTITY-1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getAllLandlordsTest() throws Exception{
        when(landlordService.getAllLandlords()).thenReturn(landlords);

        this.mockMvc
                .perform(get("/landlord/getall"))
                .andExpect(status().isOk())
                .andExpect(view().name("landlord/allLandlords"))
                .andExpect(model().attributeExists("landlords"))
                .andExpect(model().attribute("landlords", hasSize(FAKE_DATA_QUANTITY)))
                .andExpect(model().attribute("landlords", hasItems(landlords.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getLandlordTest() throws Exception{
        when(landlordService.getLandlord(currentRandIndex)).thenReturn(landlords.get(currentRandIndex));

        this.mockMvc
                .perform(get("/landlord/{landlordId}", currentRandIndex))
                .andExpect(status().isOk())
                .andExpect(view().name("landlord/allLandlords"))
                .andExpect(model().attributeExists("landlords"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showAddLandlordTest() throws Exception{

        this.mockMvc
                .perform(get("/landlord/showAdd"))
                .andExpect(status().isOk())
                .andExpect(view().name("landlord/add"))
                .andExpect(model().attributeExists("landlord"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addLandlordTest() throws Exception{

        Landlord thisLandlord = landlords.get(currentRandIndex);

        this.mockMvc
                .perform(post("/landlord/add")
                        .param("name", thisLandlord.getName())
                        .param("emailId", thisLandlord.getEmailId())
                        .param("phoneNo", thisLandlord.getPhoneNo())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/landlord/getall"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addLandlordErrorTest() throws Exception{

        Landlord thisLandlord = landlords.get(currentRandIndex);

        this.mockMvc
                .perform(post("/landlord/add")
                        .param("name", "")
                        .param("emailId", "no mail")
                        .param("phoneNo", thisLandlord.getPhoneNo())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("landlord/add"))
                .andExpect(model().attributeHasFieldErrors("landlord", "name"))
                .andExpect(model().attributeHasFieldErrors("landlord", "emailId"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showUpdateLandlordTest() throws Exception{

        Landlord thisLandlord = landlords.get(currentRandIndex);
        when(landlordService.getLandlord(thisLandlord.getLandlordId())).thenReturn(thisLandlord);

        this.mockMvc
                .perform(get("/landlord/{landlordid}/edit", thisLandlord.getLandlordId()))
                .andExpect(status().isOk())
                .andExpect(view().name("landlord/update"))
                .andExpect(model().attributeExists("landlord"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void updateLandlordTest() throws Exception{
        Landlord thisLandlord = landlords.get(currentRandIndex);

        this.mockMvc
                .perform(post("/landlord/{landlordid}/update", thisLandlord.getLandlordId())
                        .param("name", thisLandlord.getName())
                        .param("emailId", "newmail@gmail.com")
                        .param("phoneNo", "9865742310")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/landlord/getall"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void updateLandlordErrorTest() throws Exception{
        Landlord thisLandlord = landlords.get(currentRandIndex);

        this.mockMvc
                .perform(post("/landlord/{landlordid}/update", thisLandlord.getLandlordId())
                        .param("name", "")
                        .param("emailId", "newmail@gmail.com")
                        .param("phoneNo", "9865742310")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("landlord/update"))
                .andExpect(model().attributeHasFieldErrors("landlord", "name"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void removeLandlordTest() throws Exception{
        this.mockMvc
                .perform(get("/landlord/{landlordid}/delete", currentRandIndex))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/landlord/getall"));
    }

    public static String getCity(){

        while (true){
            String city = faker.address().city();

            if(city.matches("^(?:[A-Za-z]{2,}(?:(\\.\\s|'s\\s|\\s?-\\s?|\\s)?(?=[A-Za-z]+))){1,2}(?:[A-Za-z]+)?$")){
                return city;
            }
        }
    }
}
