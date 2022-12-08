package com.pma.web.blackbox;

import com.github.javafaker.Faker;
import com.pma.web.controller.TenancyInfoController;
import com.pma.web.model.*;
import com.pma.web.service.HouseServiceImpl;
import com.pma.web.service.LandlordServiceImpl;
import com.pma.web.service.TenancyInfoServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(TenancyInfoController.class)
public class TenancyInfoControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TenancyInfoServiceImpl tenancyInfoService;

    @MockBean
    private HouseServiceImpl houseService;

    private static final int FAKE_DATA_QUANTITY = 10;

    // Should always be greater than 0
    // No of times each test should run
    private static final int TEST_REPETITIONS = 10;

    private static final Faker faker = new Faker(Locale.UK);

    static List<Landlord> landlords = new ArrayList<>();
    static List<Address> addresses = new ArrayList<>();

    static List<Address> previousAddresses = new ArrayList<>();
    static List<House> houses = new ArrayList<>();
    static List<Tenant> tenants = new ArrayList<>();
    static List<TenancyInfo> tenancies = new ArrayList<>();

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

            Address previousAddress;

            if(itr<FAKE_DATA_QUANTITY-2){
                previousAddress = new Address(
                        faker.random().nextInt(1, 1200),
                        faker.number().numberBetween(1, 3000),
                        faker.address().streetName(),
                        getCity(),
                        faker.address().zipCode()
                );
            }
            else{
                previousAddress = new Address(
                        faker.number().numberBetween(1, 3000),
                        faker.address().streetName(),
                        getCity(),
                        faker.address().zipCode()
                );
            }

            landlords.add(landlord);
            addresses.add(address);
            previousAddresses.add(previousAddress);
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

            TenancyInfo tenancyInfo = new TenancyInfo(
                    itr,
                    house,
                    calcDate(true),
                    calcDate(false)
            );

            tenancies.add(tenancyInfo);

            Tenant tenant = new Tenant(
                    itr,
                    faker.name().firstName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().subscriberNumber(10),
                    previousAddresses.get(itr)
            );

            tenants.add(tenant);
        }

        for(int itr=0; itr<FAKE_DATA_QUANTITY; itr++){
            Set<House> houseSet = new HashSet<>();
            houseSet.add(houses.get(itr));
            landlords.get(itr).setHouses(houseSet);

            Set<Tenant> tenantSet = new HashSet<>();
            tenantSet.add(tenants.get(itr));
            tenancies.get(itr).setTenant(tenantSet);
        }
    }

    @BeforeEach
    public void InitEachTests(){
        currentRandIndex = faker.random().nextInt(0, FAKE_DATA_QUANTITY-1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getAllTenanciesTest() throws Exception{

        when(tenancyInfoService.getAllTenancyInfos()).thenReturn(tenancies);

        this.mockMvc
                .perform(get("/tenancies/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("tenancies/allTenancies"))
                .andExpect(model().attributeExists("tenancies"))
                .andExpect(model().attribute("tenancies", hasSize(FAKE_DATA_QUANTITY)))
                .andExpect(model().attribute("tenancies", hasItems(tenancies.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getTenancyTest() throws Exception{
        when(tenancyInfoService.getTenancyInfo(currentRandIndex)).thenReturn(tenancies.get(currentRandIndex));

        this.mockMvc
                .perform(get("/tenancies/{tenancyInfoID}", currentRandIndex))
                .andExpect(status().isOk())
                .andExpect(view().name("tenancies/allTenancies"))
                .andExpect(model().attributeExists("tenancies"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showAddTenancyTest() throws Exception{

        when(houseService.getAllHouses()).thenReturn(houses);

        this.mockMvc
                .perform(get("/tenancies/showAdd"))
                .andExpect(status().isOk())
                .andExpect(view().name("tenancies/add"))
                .andExpect(model().attributeExists("tenancy"))
                .andExpect(model().attribute("houses", hasSize(houses.size())))
                .andExpect(model().attribute("houses", hasItems(houses.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addTenancyTest() throws Exception{

        when(houseService.getAllHouses()).thenReturn(houses);

        TenancyInfo thisTenancy = tenancies.get(currentRandIndex);

        House thisHouse = thisTenancy.getHouse();
        String thisHouseFlatNo = "";
        if(thisHouse.getAddress().getFlatNo() != null){
            thisHouseFlatNo = thisHouse.getAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/tenancies/add")
                        .param("startDate", thisTenancy.getStartDateString())
                        .param("endDate", thisTenancy.getEndDateString())
                        .param("house.landlord.name", thisHouse.getLandlord().getName())
                        .param("house.landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("house.landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("house.noOfRooms", thisHouse.getNoOfRooms().toString())
                        .param("house.cost", thisHouse.getCost().toString())
                        .param("house.address.flatNo", thisHouseFlatNo)
                        .param("house.address.houseNo", thisHouse.getAddress().getHouseNo().toString())
                        .param("house.address.street", thisHouse.getAddress().getStreet())
                        .param("house.address.city", thisHouse.getAddress().getCity())
                        .param("house.address.postcode", thisHouse.getAddress().getPostcode())

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/tenancies/all"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addTenancyErrorTest() throws Exception{

        when(houseService.getAllHouses()).thenReturn(houses);

        TenancyInfo thisTenancy = tenancies.get(currentRandIndex);

        House thisHouse = thisTenancy.getHouse();
        String thisHouseFlatNo = "";
        if(thisHouse.getAddress().getFlatNo() != null){
            thisHouseFlatNo = thisHouse.getAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/tenancies/add")
                        .param("startDate", "")
                        .param("endDate", thisTenancy.getEndDateString())
                        .param("house.landlord.name", thisHouse.getLandlord().getName())
                        .param("house.landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("house.landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("house.noOfRooms", thisHouse.getNoOfRooms().toString())
                        .param("house.cost", thisHouse.getCost().toString())
                        .param("house.address.flatNo", thisHouseFlatNo)
                        .param("house.address.houseNo", thisHouse.getAddress().getHouseNo().toString())
                        .param("house.address.street", thisHouse.getAddress().getStreet())
                        .param("house.address.city", thisHouse.getAddress().getCity())
                        .param("house.address.postcode", "")
                )
                .andExpect(status().is4xxClientError());
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showUpdateTenancyTest() throws Exception{

        TenancyInfo thisTenancy = tenancies.get(currentRandIndex);

        when(tenancyInfoService.getTenancyInfo(thisTenancy.getTenancyInfoID())).thenReturn(thisTenancy);
        when(houseService.getAllHouses()).thenReturn(houses);

        this.mockMvc
                .perform(get("/tenancies/{tenancyInfoID}/edit", thisTenancy.getTenancyInfoID()))
                .andExpect(status().isOk())
                .andExpect(view().name("tenancies/update"))
                .andExpect(model().attributeExists("tenancy"))
                .andExpect(model().attribute("houses", hasSize(houses.size())))
                .andExpect(model().attribute("houses", hasItems(houses.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void updateTenancyTest() throws Exception{
        TenancyInfo thisTenancy = tenancies.get(currentRandIndex);

        when(tenancyInfoService.getTenancyInfo(thisTenancy.getTenancyInfoID())).thenReturn(thisTenancy);
        when(houseService.getAllHouses()).thenReturn(houses);

        House thisHouse = thisTenancy.getHouse();
        String thisHouseFlatNo = "";
        if(thisHouse.getAddress().getFlatNo() != null){
            thisHouseFlatNo = thisHouse.getAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/tenancies/{tenancyInfoID}/update", thisTenancy.getTenancyInfoID())
                        .param("startDate", thisTenancy.getStartDateString())
                        .param("endDate", "2022-02-11")
                        .param("house.landlord.name", thisHouse.getLandlord().getName())
                        .param("house.landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("house.landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("house.noOfRooms", thisHouse.getNoOfRooms().toString())
                        .param("house.cost", "340.00")
                        .param("house.address.flatNo", thisHouseFlatNo)
                        .param("house.address.houseNo", "5")
                        .param("house.address.street", "St Steven Road")
                        .param("house.address.city", "London")
                        .param("house.address.postcode", thisHouse.getAddress().getPostcode())

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/tenancies/all"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void removeTenancyTest() throws Exception{
        this.mockMvc
                .perform(get("/tenancies/{tenancyInfoID}/delete", currentRandIndex))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/tenancies/all"));
    }

    public static Date calcDate(boolean isStartDate){

        int year;
        if(isStartDate){
            year = faker.random().nextInt(2019, 2021);
        }
        else{
            year = faker.random().nextInt(2022, 2025);
        }

        LocalDate localDate = LocalDate.of(
                year ,
                faker.random().nextInt(1, 12) ,
                faker.random().nextInt(1, 28) );

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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
