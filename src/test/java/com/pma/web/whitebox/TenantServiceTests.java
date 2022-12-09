package com.pma.web.whitebox;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
import com.pma.web.model.*;
import com.pma.web.repository.TenantRepository;
import com.pma.web.service.TenantServiceImpl;
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
public class TenantServiceTests {

    @InjectMocks
    TenantServiceImpl tenantService;

    @Mock
    TenantRepository tenantRepository;

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
    public void getAllTenantsTests(){

        when(tenantRepository.findAll()).thenReturn(tenantList);

        List<Tenant> tenants = tenantService.getAllTenants();

        assertEquals(3, tenants.size());
        verify(tenantRepository, times(1)).findAll();
    }

    @Test
    public void getAllTenantsExceptionTests(){

        when(tenantRepository.findAll()).thenThrow(new ModelEmptyListException(""));

        assertThrows(ModelEmptyListException.class,
                ()->{
                    tenantService.getAllTenants();
                }
        );

        verify(tenantRepository, times(1)).findAll();
    }

    @Test
    public void getTenantsByTenancyTests(){

        when(tenantRepository.findByTenancyInfo(tenancyInfoList.get(0))).thenReturn(tenantList);

        List<Tenant> tenants = tenantService.getTenantsByTenancy(tenancyInfoList.get(0));

        assertEquals(3, tenants.size());
        verify(tenantRepository, times(1)).findByTenancyInfo(tenancyInfoList.get(0));
    }

    @Test
    public void getTenantsByTenancyExceptionTests(){

        when(tenantRepository.findByTenancyInfo(tenancyInfoList.get(0))).thenThrow(new ModelEmptyListException(""));

        assertThrows(ModelEmptyListException.class,
                ()->{
                    tenantService.getTenantsByTenancy(tenancyInfoList.get(0));
                }
        );

        verify(tenantRepository, times(1)).findByTenancyInfo(tenancyInfoList.get(0));
    }

    @Test
    public void getTenantTest(){

        Tenant inTenant = tenantList.get(1);
        when(tenantRepository.findByTenantId(anyLong())).thenReturn(Optional.of(inTenant));

        Tenant outTenant = tenantService.getTenant(inTenant.getTenantId());

        assertEquals(inTenant.getName(), outTenant.getName());
        assertEquals(inTenant.getEmailId(), outTenant.getEmailId());

        verify(tenantRepository, times(1)).findByTenantId(anyLong());
    }

    @Test
    public void getTenantEmptyTest(){

        when(tenantRepository.findByTenantId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                ()->{
                    tenantService.getTenant(1);
                }
        );

        verify(tenantRepository, times(1)).findByTenantId(anyLong());
    }

    @Test
    public void getTenantErrorTest(){

        when(tenantRepository.findByTenantId(anyLong())).thenThrow(new ModelNotFoundException(""));

        assertThrows(ModelNotFoundException.class,
                ()->{
                    tenantService.getTenant(1);
                }
        );

        verify(tenantRepository, times(1)).findByTenantId(anyLong());
    }

    @Test
    public void tenantDeleteTest(){

        Tenant inTenant = tenantList.get(1);
        when(tenantRepository.findByTenantId(anyLong())).thenReturn(Optional.of(inTenant));

        doNothing().when(tenantRepository).delete(any(Tenant.class));

        tenantService.removeTenant(inTenant.getTenantId());
        verify(tenantRepository, times(1)).findByTenantId(anyLong());
        verify(tenantRepository, times(1)).delete(any(Tenant.class));
    }

    @Test
    public void tenantDeleteEmptyTest(){

        Tenant inTenant = tenantList.get(1);
        when(tenantRepository.findByTenantId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                ()->{
                    tenantService.removeTenant(inTenant.getTenantId());
                }
        );

        verify(tenantRepository, times(1)).findByTenantId(anyLong());
        verify(tenantRepository, never()).delete(any(Tenant.class));
    }

    @Test
    public void tenantDeleteExceptionTest(){

        Tenant inTenant = tenantList.get(1);
        when(tenantRepository.findByTenantId(anyLong())).thenThrow(new ModelNotFoundException(""));

        assertThrows(ModelNotFoundException.class,
                ()->{
                    tenantService.removeTenant(inTenant.getTenantId());
                }
        );

        verify(tenantRepository, times(1)).findByTenantId(anyLong());
        verify(tenantRepository, never()).delete(any(Tenant.class));
    }

    @Test
    public void addTenantTest(){

        when(tenantRepository.save(tenantList.get(0))).thenReturn(null);

        tenantService.addTenant(tenantList.get(0));
        verify(tenantRepository, times(1)).save(tenantList.get(0));
    }

    @Test
    public void addTenantExceptionTest(){

        when(tenantRepository.save(tenantList.get(0))).thenThrow(new ModelAddException(""));

        assertThrows(ModelAddException.class,
                ()->{
                    tenantService.addTenant(tenantList.get(0));
                }
        );

        verify(tenantRepository, times(1)).save(tenantList.get(0));
    }

    @Test
    public void updateTenantTest(){
        Tenant inTenant = tenantList.get(1);
        when(tenantRepository.findByTenantId(anyLong())).thenReturn(Optional.of(inTenant));

        tenantService.updateTenant(inTenant.getTenantId(), tenantList.get(1));
        verify(tenantRepository, times(1)).findByTenantId(anyLong());
    }

    @Test
    public void updateTenantEmptyTest(){
        Tenant inTenant = tenantList.get(1);
        when(tenantRepository.findByTenantId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelUpdateException.class,
                ()->{
                    tenantService.updateTenant(inTenant.getTenantId(), tenantList.get(1));
                }
        );

        verify(tenantRepository, times(1)).findByTenantId(anyLong());
    }

    @Test
    public void updateTenantErrorTest(){
        Tenant inTenant = tenantList.get(1);
        when(tenantRepository.findByTenantId(anyLong())).thenThrow(new ModelUpdateException(""));

        assertThrows(ModelUpdateException.class,
                ()->{
                    tenantService.updateTenant(inTenant.getTenantId(), tenantList.get(1));
                }
        );

        verify(tenantRepository, times(1)).findByTenantId(anyLong());
    }

}
