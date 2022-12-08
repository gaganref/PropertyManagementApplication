package com.pma.web.blackbox;

import com.github.javafaker.Faker;
import com.pma.web.controller.TenancyInfoController;
import com.pma.web.controller.TenantController;
import com.pma.web.model.*;
import com.pma.web.service.HouseServiceImpl;
import com.pma.web.service.TenancyInfoServiceImpl;
import com.pma.web.service.TenantServiceImpl;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(TenantController.class)
public class TenantControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TenantServiceImpl tenantService;
    @MockBean
    private TenancyInfoServiceImpl tenancyInfoService;


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
    public void getAllTenantsTest() throws Exception{

        when(tenantService.getAllTenants()).thenReturn(tenants);

        this.mockMvc
                .perform(get("/tenant/getall"))
                .andExpect(status().isOk())
                .andExpect(view().name("tenants/allTenants"))
                .andExpect(model().attributeExists("tenants"))
                .andExpect(model().attribute("tenants", hasSize(FAKE_DATA_QUANTITY)))
                .andExpect(model().attribute("tenants", hasItems(tenants.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getAllTenantsOfTenancyTest() throws Exception{

        TenancyInfo thisTenancy = tenancies.get(currentRandIndex);

        when(tenantService.getTenantsByTenancy(tenancyInfoService.getTenancyInfo(thisTenancy.getTenancyInfoID()))).thenReturn(tenants);

        this.mockMvc
                .perform(get("/tenant/byTenancy/{tenancyId}", thisTenancy.getTenancyInfoID()))
                .andExpect(status().isOk())
                .andExpect(view().name("tenants/allTenants"))
                .andExpect(model().attributeExists("tenants"))
                .andExpect(model().attribute("tenants", hasSize(FAKE_DATA_QUANTITY)))
                .andExpect(model().attribute("tenants", hasItems(tenants.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void getTenantTest() throws Exception{
        Tenant thisTenant = tenants.get(currentRandIndex);
        when(tenantService.getTenant(thisTenant.getTenantId())).thenReturn(thisTenant);

        this.mockMvc
                .perform(get("/tenant/getbyId/{TenantId}", thisTenant.getTenantId()))
                .andExpect(status().isOk())
                .andExpect(view().name("tenants/allTenants"))
                .andExpect(model().attributeExists("tenants"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showAddTenantTest() throws Exception{

        when(tenancyInfoService.getAllTenancyInfos()).thenReturn(tenancies);

        this.mockMvc
                .perform(get("/tenant/showAdd"))
                .andExpect(status().isOk())
                .andExpect(view().name("tenants/add"))
                .andExpect(model().attributeExists("tenant"))
                .andExpect(model().attribute("tenancies", hasSize(tenancies.size())))
                .andExpect(model().attribute("tenancies", hasItems(tenancies.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addTenancyTest() throws Exception{

        when(tenancyInfoService.getAllTenancyInfos()).thenReturn(tenancies);

        Tenant thisTenant = tenants.get(currentRandIndex);

        String thisHouseFlatNo = "";
        if(thisTenant.getPreviousAddress().getFlatNo() != null){
            thisHouseFlatNo = thisTenant.getPreviousAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/tenant/add")
                        .param("name", thisTenant.getName())
                        .param("emailId", thisTenant.getEmailId())
                        .param("phoneNo", thisTenant.getPhoneNo())
                        .param("previousAddress.flatNo", thisHouseFlatNo)
                        .param("previousAddress.houseNo", thisTenant.getPreviousAddress().getHouseNo().toString())
                        .param("previousAddress.street", thisTenant.getPreviousAddress().getStreet())
                        .param("previousAddress.city", thisTenant.getPreviousAddress().getCity())
                        .param("previousAddress.postcode", thisTenant.getPreviousAddress().getPostcode())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/tenant/getall"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void addTenancyErrorTest() throws Exception{

        when(tenancyInfoService.getAllTenancyInfos()).thenReturn(tenancies);

        Tenant thisTenant = tenants.get(currentRandIndex);

        String thisHouseFlatNo = "";
        if(thisTenant.getPreviousAddress().getFlatNo() != null){
            thisHouseFlatNo = thisTenant.getPreviousAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/tenant/add")
                        .param("name", thisTenant.getName())
                        .param("emailId", "")
                        .param("phoneNo", "9865742310")
                        .param("previousAddress.flatNo", thisHouseFlatNo)
                        .param("previousAddress.houseNo", thisTenant.getPreviousAddress().getHouseNo().toString())
                        .param("previousAddress.street", thisTenant.getPreviousAddress().getStreet())
                        .param("previousAddress.city", thisTenant.getPreviousAddress().getCity())
                        .param("previousAddress.postcode", thisTenant.getPreviousAddress().getPostcode())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("tenants/add"))
                .andExpect(model().attributeHasFieldErrors("tenant", "emailId"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void showUpdateTenancyTest() throws Exception{

        Tenant thisTenant = tenants.get(currentRandIndex);
        when(tenantService.getTenant(thisTenant.getTenantId())).thenReturn(thisTenant);

        when(tenancyInfoService.getAllTenancyInfos()).thenReturn(tenancies);


        this.mockMvc
                .perform(get("/tenant/{tenantId}/edit", thisTenant.getTenantId()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(view().name("tenants/update"))
                .andExpect(model().attributeExists("tenant"))
                .andExpect(model().attribute("tenancies", hasSize(tenancies.size())))
                .andExpect(model().attribute("tenancies", hasItems(tenancies.toArray())));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void updateTenancyTest() throws Exception{
        when(tenancyInfoService.getAllTenancyInfos()).thenReturn(tenancies);

        Tenant thisTenant = tenants.get(currentRandIndex);

        String thisHouseFlatNo = "";
        if(thisTenant.getPreviousAddress().getFlatNo() != null){
            thisHouseFlatNo = thisTenant.getPreviousAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/tenant/{tenantId}/update", thisTenant.getTenantId())
                        .param("name", "Amigo Shelton")
                        .param("emailId", thisTenant.getEmailId())
                        .param("phoneNo", "9876543210")
                        .param("previousAddress.flatNo", thisHouseFlatNo)
                        .param("previousAddress.houseNo", thisTenant.getPreviousAddress().getHouseNo().toString())
                        .param("previousAddress.street", thisTenant.getPreviousAddress().getStreet())
                        .param("previousAddress.city", thisTenant.getPreviousAddress().getCity())
                        .param("previousAddress.postcode", thisTenant.getPreviousAddress().getPostcode())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/tenant/getall"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void updateTenancyErrorTest() throws Exception{
        when(tenancyInfoService.getAllTenancyInfos()).thenReturn(tenancies);

        Tenant thisTenant = tenants.get(currentRandIndex);

        String thisHouseFlatNo = "";
        if(thisTenant.getPreviousAddress().getFlatNo() != null){
            thisHouseFlatNo = thisTenant.getPreviousAddress().getFlatNo().toString();
        }

        this.mockMvc
                .perform(post("/tenant/{tenantId}/update", thisTenant.getTenantId())
                        .param("name", "Amigo Shelton")
                        .param("emailId", thisTenant.getEmailId())
                        .param("phoneNo", "")
                        .param("previousAddress.flatNo", thisHouseFlatNo)
                        .param("previousAddress.houseNo", thisTenant.getPreviousAddress().getHouseNo().toString())
                        .param("previousAddress.street", thisTenant.getPreviousAddress().getStreet())
                        .param("previousAddress.city", thisTenant.getPreviousAddress().getCity())
                        .param("previousAddress.postcode", thisTenant.getPreviousAddress().getPostcode())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("tenants/update"))
                .andExpect(model().attributeHasFieldErrors("tenant", "phoneNo"));
    }

    @RepeatedTest(TEST_REPETITIONS)
    public void removeTenantTest() throws Exception{
        this.mockMvc
                .perform(get("/tenant/{TenantId}/delete", currentRandIndex))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/tenant/getall"));
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
