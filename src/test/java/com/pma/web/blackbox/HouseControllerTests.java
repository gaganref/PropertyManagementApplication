package com.pma.web.blackbox;

import com.github.javafaker.Faker;
import com.pma.web.controller.HouseController;
import com.pma.web.model.Address;
import com.pma.web.model.House;
import com.pma.web.model.Landlord;
import com.pma.web.service.HouseServiceImpl;
import com.pma.web.service.LandlordServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasValue;


@WebMvcTest(HouseController.class)
public class HouseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseServiceImpl houseService;

    @MockBean
    private LandlordServiceImpl landlordService;

    private static final int FAKE_DATA_QUANTITY = 10;

    // Should always be greater than 0
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
    }

    @BeforeEach
    public void InitEachTests(){
        currentRandIndex = faker.random().nextInt(0, FAKE_DATA_QUANTITY-1);
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getAllHousesTest() throws Exception{
        when(houseService.getAllHouses()).thenReturn(houses);

        this.mockMvc
                .perform(get("/house/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("houses/allHouses"))
                .andExpect(model().attributeExists("houses"))
                .andExpect(model().attribute("houses", hasSize(FAKE_DATA_QUANTITY)))
                .andExpect(model().attribute("houses", hasItems(houses.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getHouseTest() throws Exception{
        when(houseService.getHouse(currentRandIndex)).thenReturn(houses.get(currentRandIndex));

        this.mockMvc
                .perform(get("/house/{houseID}", currentRandIndex))
                .andExpect(status().isOk())
                .andExpect(view().name("houses/allHouses"))
                .andExpect(model().attributeExists("houses"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getHouseLandlordTest() throws Exception{
        List<House> houseList = houses.subList(1, 4);

        when(landlordService.getLandlord(currentRandIndex)).thenReturn(landlords.get(currentRandIndex));
        when(houseService.getHouseByLandlord(any(Landlord.class))).thenReturn(houseList);

        this.mockMvc
                .perform(get("/house/byLandlord/{landlordID}", currentRandIndex))
                .andExpect(status().isOk())
                .andExpect(view().name("houses/allHouses"))
                .andExpect(model().attributeExists("houses"))
                .andExpect(model().attribute("houses", hasSize(3)))
                .andExpect(model().attribute("houses", hasItems(houseList.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getHouseByCostTest() throws Exception{
        List<House> houseList = houses.subList(0,2);

        when(houseService.getHouseByCost(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(houseList);

        this.mockMvc
                .perform(get("/house/byCost/")
                        .param("min", "300.00")
                        .param("max", "600.00"))
                .andExpect(status().isOk())
                .andExpect(view().name("houses/allHouses"))
                .andExpect(model().attributeExists("houses"))
                .andExpect(model().attribute("houses", hasSize(2)))
                .andExpect(model().attribute("houses", hasItems(houseList.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getHouseByRoomsTest() throws Exception{
        List<House> houseList = houses.subList(3, 4);

        when(houseService.getHouseByRooms(anyInt(), anyInt())).thenReturn(houseList);

        this.mockMvc
                .perform(get("/house/byRooms/")
                        .param("min", "1")
                        .param("max", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("houses/allHouses"))
                .andExpect(model().attributeExists("houses"))
                .andExpect(model().attribute("houses", hasSize(1)))
                .andExpect(model().attribute("houses", hasItems(houseList.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showAddHouseTest() throws Exception{
        when(landlordService.getAllLandlords()).thenReturn(landlords);

        this.mockMvc
                .perform(get("/house/showAdd"))
                .andExpect(status().isOk())
                .andExpect(view().name("houses/add"))
                .andExpect(model().attributeExists("house"))
                .andExpect(model().attribute("landlords", hasSize(landlords.size())))
                .andExpect(model().attribute("landlords", hasItems(landlords.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addHouseTest() throws Exception{

        House thisHouse = houses.get(currentRandIndex);
        String thisHouseFlatNo = "";
        if(thisHouse.getAddress().getFlatNo() != null){
            thisHouseFlatNo = thisHouse.getAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/house/add")
                        .param("landlord.name", thisHouse.getLandlord().getName())
                        .param("landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("noOfRooms", thisHouse.getNoOfRooms().toString())
                        .param("cost", thisHouse.getCost().toString())
                        .param("address.flatNo", thisHouseFlatNo)
                        .param("address.houseNo", thisHouse.getAddress().getHouseNo().toString())
                        .param("address.street", thisHouse.getAddress().getStreet())
                        .param("address.city", thisHouse.getAddress().getCity())
                        .param("address.postcode", thisHouse.getAddress().getPostcode())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/house/all"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addHouseErrorTest() throws Exception{
        when(landlordService.getAllLandlords()).thenReturn(landlords);

        House thisHouse = houses.get(currentRandIndex);
        String thisHouseFlatNo = "";
        if(thisHouse.getAddress().getFlatNo() != null){
            thisHouseFlatNo = thisHouse.getAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/house/add")
                        .param("landlord.name", thisHouse.getLandlord().getName())
                        .param("landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("noOfRooms", thisHouse.getNoOfRooms().toString())
                        .param("cost", "error field")
                        .param("address.flatNo", thisHouseFlatNo)
                        .param("address.houseNo", thisHouse.getAddress().getHouseNo().toString())
                        .param("address.street", thisHouse.getAddress().getStreet())
                        .param("address.city", thisHouse.getAddress().getCity())
                        .param("address.postcode", "error field wrong code")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("houses/add"))
                .andExpect(model().attributeHasFieldErrors("house", "cost"))
                .andExpect(model().attributeHasFieldErrors("house", "address.postcode"))
                .andExpect(model().attribute("landlords", hasSize(landlords.size())))
                .andExpect(model().attribute("landlords", hasItems(landlords.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showAddHouseLandlordTest() throws Exception{

        when(landlordService.getLandlord(currentRandIndex)).thenReturn(landlords.get(currentRandIndex));

        this.mockMvc
                .perform(get("/house/showAdd/landlord/{landlordID}", landlords.get(currentRandIndex).getLandlordId()))
                .andExpect(status().isOk())
                .andExpect(view().name("houses/addHouseWithLandlord"))
                .andExpect(model().attributeExists("house"))
                .andExpect(model().attributeExists("inLandlord"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addHouseLandlordTest() throws Exception{
        when(landlordService.getLandlord(currentRandIndex)).thenReturn(landlords.get(currentRandIndex));

        House thisHouse = houses.get(currentRandIndex);
        String thisHouseFlatNo = "";
        if(thisHouse.getAddress().getFlatNo() != null){
            thisHouseFlatNo = thisHouse.getAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/house/add/landlord/{landlordID}", landlords.get(currentRandIndex).getLandlordId())
                        .param("landlord.name", thisHouse.getLandlord().getName())
                        .param("landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("noOfRooms", thisHouse.getNoOfRooms().toString())
                        .param("cost", thisHouse.getCost().toString())
                        .param("address.flatNo", thisHouseFlatNo)
                        .param("address.houseNo", thisHouse.getAddress().getHouseNo().toString())
                        .param("address.street", thisHouse.getAddress().getStreet())
                        .param("address.city", thisHouse.getAddress().getCity())
                        .param("address.postcode", thisHouse.getAddress().getPostcode())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/landlord/" + landlords.get(currentRandIndex).getLandlordId()));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addHouseLandlordErrorTest() throws Exception{
        when(landlordService.getLandlord(currentRandIndex)).thenReturn(landlords.get(currentRandIndex));

        House thisHouse = houses.get(currentRandIndex);
        String thisHouseFlatNo = "";
        if(thisHouse.getAddress().getFlatNo() != null){
            thisHouseFlatNo = thisHouse.getAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/house/add/landlord/{landlordID}", landlords.get(currentRandIndex).getLandlordId())
                        .param("landlord.name", thisHouse.getLandlord().getName())
                        .param("landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("noOfRooms", "8999")
                        .param("cost", thisHouse.getCost().toString())
                        .param("address.flatNo", thisHouseFlatNo)
                        .param("address.houseNo", thisHouse.getAddress().getHouseNo().toString())
                        .param("address.street", "")
                        .param("address.city", thisHouse.getAddress().getCity())
                        .param("address.postcode", thisHouse.getAddress().getPostcode())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("houses/addHouseWithLandlord"))
                .andExpect(model().attributeHasFieldErrors("house", "noOfRooms"))
                .andExpect(model().attributeHasFieldErrors("house", "address.street"))
                .andExpect(model().attributeExists("inLandlord"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showUpdateHouseTest() throws Exception{
        when(houseService.getHouse(currentRandIndex)).thenReturn(houses.get(currentRandIndex));
        when(landlordService.getAllLandlords()).thenReturn(landlords);

        this.mockMvc
                .perform(get("/house/{houseID}/edit", houses.get(currentRandIndex).getHouseID()))
                .andExpect(status().isOk())
                .andExpect(view().name("houses/update"))
                .andExpect(model().attributeExists("house"))
                .andExpect(model().attributeExists("landlords"))
                .andExpect(model().attribute("landlords", hasSize(landlords.size())))
                .andExpect(model().attribute("landlords", hasItems(landlords.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void updateHouseTest() throws Exception{

        House thisHouse = houses.get(currentRandIndex);
        String thisHouseFlatNo = "";
        if(thisHouse.getAddress().getFlatNo() != null){
            thisHouseFlatNo = thisHouse.getAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/house/{houseID}/update", thisHouse.getHouseID())
                        .param("landlord.name", thisHouse.getLandlord().getName())
                        .param("landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("noOfRooms", thisHouse.getNoOfRooms().toString())
                        .param("cost", thisHouse.getCost().toString())
                        .param("address.flatNo", thisHouseFlatNo)
                        .param("address.houseNo", thisHouse.getAddress().getHouseNo().toString())
                        .param("address.street",thisHouse.getAddress().getStreet())
                        .param("address.city", thisHouse.getAddress().getCity())
                        .param("address.postcode", thisHouse.getAddress().getPostcode())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/house/all"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void updateHouseErrorTest() throws Exception{

        when(landlordService.getAllLandlords()).thenReturn(landlords);

        House thisHouse = houses.get(currentRandIndex);
        this.mockMvc
                .perform(post("/house/{houseID}/update", thisHouse.getHouseID())
                        .param("landlord.name", thisHouse.getLandlord().getName())
                        .param("landlord.emailId", thisHouse.getLandlord().getEmailId())
                        .param("landlord.phoneNo", thisHouse.getLandlord().getPhoneNo())
                        .param("noOfRooms", thisHouse.getNoOfRooms().toString())
                        .param("cost", "")
                        .param("address.flatNo", "")
                        .param("address.houseNo", "")
                        .param("address.street", "")
                        .param("address.city", "")
                        .param("address.postcode", "")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("houses/update"))
                .andExpect(model().attributeHasFieldErrors("house", "cost"))
                .andExpect(model().attributeHasFieldErrors("house", "address.houseNo"))
                .andExpect(model().attributeHasFieldErrors("house", "address.street"))
                .andExpect(model().attributeHasFieldErrors("house", "address.city"))
                .andExpect(model().attributeHasFieldErrors("house", "address.postcode"))
                .andExpect(model().attribute("landlords", hasSize(landlords.size())))
                .andExpect(model().attribute("landlords", hasItems(landlords.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void removeHouseTest() throws Exception{
        this.mockMvc
                .perform(get("/house/{houseID}/delete", currentRandIndex))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/house/all"));
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
