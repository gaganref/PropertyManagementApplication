package com.pma.web.whitebox;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
import com.pma.web.model.Address;
import com.pma.web.model.House;
import com.pma.web.model.Landlord;
import com.pma.web.repository.HouseRepository;
import com.pma.web.service.HouseServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HouseServiceTests {

    @InjectMocks
    HouseServiceImpl houseService;

    @Mock
    HouseRepository houseRepository;

    static List<House> houseList = new ArrayList<>();
    static List<Landlord> landlordList = new ArrayList<>();

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
                1,landlord2,
                new Address(9, 4, "Charles street", "Leicester", "LE2 CD5"),
                3, new BigDecimal("375.00")
        );

        House house3 = new House(
                1, landlord3,
                new Address(7, "Hinckley Road", "Hinckley", "HN3 SR2"),
                6, new BigDecimal("250.00")
        );

        House house4 = new House(
                1, landlord3,
                new Address(5, 13, "London Road", "Leicester", "LE1 1DS"),
                2, new BigDecimal("450.00")
        );

        House house5 = new House(
                1, landlord2,
                new Address(12, 156, "Square Park Road", "London", "LN3 NP9"),
                1, new BigDecimal("675.00")
        );

        houseList.add(house1);
        houseList.add(house2);
        houseList.add(house3);
        houseList.add(house4);
        houseList.add(house5);
    }

    @Test
    public void getAllHousesTests(){

        when(houseRepository.findAll()).thenReturn(houseList);

        List<House> checkHouseList = houseService.getAllHouses();

        assertEquals(5, checkHouseList.size());
        verify(houseRepository, times(1)).findAll();
    }

    @Test
    public void getAllHousesExceptionTests(){

        when(houseRepository.findAll()).thenThrow(new ModelEmptyListException(""));

        assertThrows(ModelEmptyListException.class,
                ()->{
                    houseService.getAllHouses();
                }
        );
        verify(houseRepository, times(1)).findAll();
    }

    @Test
    public void getHouseTest(){

        House inHouse = houseList.get(1);
        when(houseRepository.findByHouseID(anyLong())).thenReturn(Optional.of(inHouse));

        House outHouse = houseService.getHouse(inHouse.getHouseID());

        assertEquals(inHouse.getLandlord(), outHouse.getLandlord());
        assertEquals(inHouse.getCost(), outHouse.getCost());
        assertEquals(inHouse.getNoOfRooms(), outHouse.getNoOfRooms());
        assertEquals(inHouse.getAddress(), outHouse.getAddress());

        verify(houseRepository, times(1)).findByHouseID(inHouse.getHouseID());
    }

    @Test
    public void getHouseEmptyTest(){

        when(houseRepository.findByHouseID(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                ()->{
                    houseService.getHouse(1);
                }
        );

        verify(houseRepository, times(1)).findByHouseID(1);
    }

    @Test
    public void getHouseErrorTest(){

        when(houseRepository.findByHouseID(anyLong())).thenThrow(new ModelNotFoundException(""));

        assertThrows(ModelNotFoundException.class,
                ()->{
                    houseService.getHouse(1);
                }
        );

        verify(houseRepository, times(1)).findByHouseID(1);
    }

    @Test
    public void getHouseLandlordTest(){

        House inHouse = houseList.get(1);
        when(houseRepository.findByLandlord(any(Landlord.class))).thenReturn(houseList);

        List<House> outHouse = houseService.getHouseByLandlord(landlordList.get(1));

        assertEquals(5, outHouse.size());
        verify(houseRepository, times(1)).findByLandlord(any(Landlord.class));
    }

    @Test
    public void getHouseLandlordEmptyTest(){

        when(houseRepository.findByLandlord(any(Landlord.class))).thenThrow(new ModelEmptyListException(""));

        assertThrows(ModelEmptyListException.class,
                ()->{
                    houseService.getHouseByLandlord(landlordList.get(1));
                }
        );

        verify(houseRepository, times(1)).findByLandlord(any(Landlord.class));
    }

    @Test
    public void addHouseTest(){

        when(houseRepository.save(houseList.get(4))).thenReturn(null);

        houseService.addHouse(houseList.get(4));
        verify(houseRepository, times(1)).save(houseList.get(4));
    }

    @Test
    public void addHouseExceptionTest(){

        when(houseRepository.save(houseList.get(4))).thenThrow(new ModelAddException(""));

        assertThrows(ModelAddException.class,
                ()->{
                    houseService.addHouse(houseList.get(4));
                }
        );

        verify(houseRepository, times(1)).save(houseList.get(4));
    }

    @Test
    public void updateHouseTest(){
        House inHouse = houseList.get(2);
        when(houseRepository.findByHouseID(inHouse.getHouseID())).thenReturn(Optional.of(inHouse));

        houseService.updateHouse(inHouse.getHouseID(), houseList.get(4));
        verify(houseRepository, times(1)).findByHouseID(inHouse.getHouseID());
    }

    @Test
    public void updateHouseEmptyTest(){
        House inHouse = houseList.get(2);
        when(houseRepository.findByHouseID(inHouse.getHouseID())).thenReturn(Optional.empty());

        assertThrows(ModelUpdateException.class,
                ()->{
                    houseService.updateHouse(inHouse.getHouseID(), houseList.get(4));
                }
        );

        verify(houseRepository, times(1)).findByHouseID(inHouse.getHouseID());
    }

    @Test
    public void updateHouseErrorTest(){
        House inHouse = houseList.get(2);
        when(houseRepository.findByHouseID(inHouse.getHouseID())).thenThrow(new ModelUpdateException(""));

        assertThrows(ModelUpdateException.class,
                ()->{
                    houseService.updateHouse(inHouse.getHouseID(), houseList.get(4));
                }
        );

        verify(houseRepository, times(1)).findByHouseID(inHouse.getHouseID());
    }

    @Test
    public void getAllHousesByCostTests(){

        when(houseRepository.findHouseByCostBetween(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(houseList);

        List<House> checkHouseList = houseService.getHouseByCost(new BigDecimal("100.00"), new BigDecimal("300.00"));

        assertEquals(5, checkHouseList.size());
        verify(houseRepository, times(1)).findHouseByCostBetween(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    public void getAllHousesByCostExceptionTests(){

        when(houseRepository.findHouseByCostBetween(any(BigDecimal.class), any(BigDecimal.class))).thenThrow(new ModelEmptyListException(""));

        assertThrows(ModelEmptyListException.class,
                ()->{
                    houseService.getHouseByCost(new BigDecimal("100.00"), new BigDecimal("300.00"));
                }
        );
        verify(houseRepository, times(1)).findHouseByCostBetween(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    public void getAllHousesByRoomsTests(){

        when(houseRepository.findHouseByNoOfRoomsBetween(anyInt(), anyInt())).thenReturn(houseList);

        List<House> checkHouseList = houseService.getHouseByRooms(1, 5);

        assertEquals(5, checkHouseList.size());
        verify(houseRepository, times(1)).findHouseByNoOfRoomsBetween(anyInt(), anyInt());
    }

    @Test
    public void getAllHousesByRoomsExceptionTests(){

        when(houseRepository.findHouseByNoOfRoomsBetween(anyInt(), anyInt())).thenThrow(new ModelEmptyListException(""));

        assertThrows(ModelEmptyListException.class,
                ()->{
                    houseService.getHouseByRooms(1, 5);
                }
        );
        verify(houseRepository, times(1)).findHouseByNoOfRoomsBetween(anyInt(), anyInt());
    }

    @Test
    public void houseDeleteTest(){

        House inHouse = houseList.get(2);
        when(houseRepository.findByHouseID(anyLong())).thenReturn(Optional.of(inHouse));
        doNothing().when(houseRepository).delete(any(House.class));

        houseService.removeHouse(inHouse.getHouseID());
        verify(houseRepository, times(1)).findByHouseID(inHouse.getHouseID());
        verify(houseRepository, times(1)).delete(any(House.class));
    }

    @Test
    public void houseDeleteEmptyTest(){

        House inHouse = houseList.get(2);
        when(houseRepository.findByHouseID(anyLong())).thenReturn(Optional.empty());
        assertThrows(ModelNotFoundException.class,
                ()->{
                    houseService.removeHouse(inHouse.getHouseID());
                }
        );

        verify(houseRepository, times(1)).findByHouseID(inHouse.getHouseID());
        verify(houseRepository, never()).delete(any(House.class));
    }

    @Test
    public void houseDeleteExceptionTest(){

        House inHouse = houseList.get(2);
        when(houseRepository.findByHouseID(anyLong())).thenThrow(new ModelNotFoundException(""));

        assertThrows(ModelNotFoundException.class,
                ()->{
                    houseService.removeHouse(inHouse.getHouseID());
                }
        );

        verify(houseRepository, times(1)).findByHouseID(inHouse.getHouseID());
        verify(houseRepository, never()).delete(any(House.class));
    }

}
