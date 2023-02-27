package com.pma.web.whitebox;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
import com.pma.web.model.*;
import com.pma.web.repository.TenancyInfoRepository;
import com.pma.web.service.TenancyInfoServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TenancyInfoServiceTests {

    @InjectMocks
    TenancyInfoServiceImpl tenancyInfoService;

    @Mock
    TenancyInfoRepository tenancyInfoRepository;

    static List<House> houseList = new ArrayList<>();
    static List<Landlord> landlordList = new ArrayList<>();

    static List<Tenant> tenantList = new ArrayList<>();
    static List<TenancyInfo> tenancyInfoList = new ArrayList<>();

    @BeforeAll
    public static void InitTests(){
        Landlord landlord1 = new Landlord(1, "Amigo Shelton", "amigoshelton38@gmail.com", "8957415415");
        Landlord landlord2 = new Landlord(2, "Max Roan", "ronan89@gmail.com", "9898748962");
        Landlord landlord3 = new Landlord(3, "Krishna", "krishna@gmail.com", "9876541230");

        landlordList.add(landlord1);
        landlordList.add(landlord2);
        landlordList.add(landlord3);

        House house1 = new House(
                1, landlord1,
                new Address(24, "St Steven Road", "Leicester", "LE3 SR2"),
                4, new BigDecimal("300.00")
        );

        House house2 = new House(
                2,landlord2,
                new Address(9, 4, "Charles street", "Leicester", "LE2 CD5"),
                3, new BigDecimal("375.00")
        );

        House house3 = new House(
                3, landlord3,
                new Address(7, "Hinckley Road", "Hinckley", "HN3 SR2"),
                6, new BigDecimal("250.00")
        );

        House house4 = new House(
                4, landlord3,
                new Address(5, 13, "London Road", "Leicester", "LE1 1DS"),
                2, new BigDecimal("450.00")
        );

        House house5 = new House(
                5, landlord2,
                new Address(12, 156, "Square Park Road", "London", "LN3 NP9"),
                1, new BigDecimal("675.00")
        );

        houseList.add(house1);
        houseList.add(house2);
        houseList.add(house3);
        houseList.add(house4);
        houseList.add(house5);

        Date startDate1 = Date.from(LocalDate.of(2021 ,1 ,21 ).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startDate2 = Date.from(LocalDate.of(2022 ,11 ,6 ).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startDate3 = Date.from(LocalDate.of(2021 ,4 ,1 ).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date endDate1 = Date.from(LocalDate.of(2022 ,1 ,20 ).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate2 = Date.from(LocalDate.of(2024 ,10 ,6 ).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate3 = Date.from(LocalDate.of(2023 ,5 ,4 ).atStartOfDay(ZoneId.systemDefault()).toInstant());

        TenancyInfo tenancyInfo1 = new TenancyInfo(1, house1, startDate1, endDate1);
        TenancyInfo tenancyInfo2 = new TenancyInfo(2, house2, startDate2, endDate2);
        TenancyInfo tenancyInfo3 = new TenancyInfo(3, house3, startDate3, endDate3);

        tenancyInfoList.add(tenancyInfo1);
        tenancyInfoList.add(tenancyInfo2);
        tenancyInfoList.add(tenancyInfo3);

        Tenant tenant1 = new Tenant(
                1, "Gabriel", "gabriel23@gmail.com", "7859854123",
                new Address(5, 3, "Spark Road", "Manchester", "MA6 4CN")
        );
        Tenant tenant2 = new Tenant(
                2, "Jacob", "jacob91@gmail.com", "8879636252",
                new Address(34, 66, "Kings Man Street", "London", "LN4 5AC")
        );
        Tenant tenant3 = new Tenant(
                3, "John Wick", "johnwick@gmail.com", "9632587474",
                new Address(88, "Greens Street", "Leicester", "LE3 1CN")
        );

        tenantList.add(tenant1);
        tenantList.add(tenant2);
        tenantList.add(tenant3);
    }


    @Test
    public void getAllTenanciesTests(){

        when(tenancyInfoRepository.findAll()).thenReturn(tenancyInfoList);

        List<TenancyInfo> tenancyInfos = tenancyInfoService.getAllTenancyInfos();

        assertEquals(3, tenancyInfos.size());
        verify(tenancyInfoRepository, times(1)).findAll();
    }

    @Test
    public void getAllTenanciesExceptionTests(){

        when(tenancyInfoRepository.findAll()).thenThrow(new ModelEmptyListException(""));

        assertThrows(ModelEmptyListException.class,
                ()->{
                    tenancyInfoService.getAllTenancyInfos();
                }
        );
        verify(tenancyInfoRepository, times(1)).findAll();
    }

    @Test
    public void getTenancyTest(){

        TenancyInfo inTenancy = tenancyInfoList.get(1);
        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenReturn(Optional.of(inTenancy));

        TenancyInfo outTenancy = tenancyInfoService.getTenancyInfo(inTenancy.getTenancyInfoID());

        assertEquals(inTenancy.getStartDateString(), outTenancy.getStartDateString());
        assertEquals(inTenancy.getEndDateString(), outTenancy.getEndDateString());

        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(inTenancy.getTenancyInfoID());
    }

    @Test
    public void getTenancyEmptyTest(){

        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                ()->{
                    tenancyInfoService.getTenancyInfo(1);
                }
        );

        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(1);
    }

    @Test
    public void getTenancyErrorTest(){

        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenThrow(new ModelNotFoundException(""));

        assertThrows(ModelNotFoundException.class,
                ()->{
                    tenancyInfoService.getTenancyInfo(1);
                }
        );

        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(1);
    }

    @Test
    public void addTenancyTest(){

        when(tenancyInfoRepository.save(tenancyInfoList.get(0))).thenReturn(null);

        tenancyInfoService.addTenancyInfo(tenancyInfoList.get(0));
        verify(tenancyInfoRepository, times(1)).save(tenancyInfoList.get(0));
    }

    @Test
    public void addTenancyExceptionTest(){

        when(tenancyInfoRepository.save(tenancyInfoList.get(0))).thenThrow(new ModelAddException(""));

        assertThrows(ModelAddException.class,
                ()->{
                    tenancyInfoService.addTenancyInfo(tenancyInfoList.get(0));
                }
        );

        verify(tenancyInfoRepository, times(1)).save(tenancyInfoList.get(0));
    }

    @Test
    public void tenancyDeleteTest(){

        TenancyInfo inTenancy = tenancyInfoList.get(1);
        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenReturn(Optional.of(inTenancy));
        doNothing().when(tenancyInfoRepository).delete(any(TenancyInfo.class));

        tenancyInfoService.removeTenancyInfo(inTenancy.getTenancyInfoID());
        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(inTenancy.getTenancyInfoID());
        verify(tenancyInfoRepository, times(1)).delete(any(TenancyInfo.class));
    }

    @Test
    public void tenancyDeleteEmptyTest(){

        TenancyInfo inTenancy = tenancyInfoList.get(1);
        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                ()->{
                    tenancyInfoService.removeTenancyInfo(inTenancy.getTenancyInfoID());
                }
        );

        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(inTenancy.getTenancyInfoID());
        verify(tenancyInfoRepository, never()).delete(any(TenancyInfo.class));
    }

    @Test
    public void tenancyDeleteExceptionTest(){

        TenancyInfo inTenancy = tenancyInfoList.get(1);
        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenThrow(new ModelNotFoundException(""));

        assertThrows(ModelNotFoundException.class,
                ()->{
                    tenancyInfoService.removeTenancyInfo(inTenancy.getTenancyInfoID());
                }
        );

        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(inTenancy.getTenancyInfoID());
        verify(tenancyInfoRepository, never()).delete(any(TenancyInfo.class));
    }

    @Test
    public void updateLandlordTest(){
        TenancyInfo inTenancy = tenancyInfoList.get(1);
        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenReturn(Optional.of(inTenancy));

        tenancyInfoService.updateTenancyInfo(inTenancy.getTenancyInfoID(), tenancyInfoList.get(1));
        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(inTenancy.getTenancyInfoID());
    }

    @Test
    public void updateLandlordEmptyTest(){
        TenancyInfo inTenancy = tenancyInfoList.get(1);
        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelUpdateException.class,
                ()->{
                    tenancyInfoService.updateTenancyInfo(inTenancy.getTenancyInfoID(), tenancyInfoList.get(1));
                }
        );

        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(inTenancy.getTenancyInfoID());
    }

    @Test
    public void updateLandlordErrorTest(){
        TenancyInfo inTenancy = tenancyInfoList.get(1);
        when(tenancyInfoRepository.findTenancyInfoByTenancyInfoID(anyLong())).thenThrow(new ModelUpdateException(""));

        assertThrows(ModelUpdateException.class,
                ()->{
                    tenancyInfoService.updateTenancyInfo(inTenancy.getTenancyInfoID(), tenancyInfoList.get(1));
                }
        );

        verify(tenancyInfoRepository, times(1)).findTenancyInfoByTenancyInfoID(inTenancy.getTenancyInfoID());
    }

}
