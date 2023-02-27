package com.pma.web.whitebox;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
import com.pma.web.model.House;
import com.pma.web.model.Landlord;
import com.pma.web.repository.LandlordRepository;
import com.pma.web.service.LandlordServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class LandlordServiceTests {

    @InjectMocks
    LandlordServiceImpl landlordService;

    @Mock
    LandlordRepository landlordRepository;

    static List<Landlord> landlordList = new ArrayList<>();

    @BeforeAll
    public static void InitTests() {
        Landlord landlord1 = new Landlord(1, "Amigo Shelton", "amigoshelton38@gmail.com", "8957415415");
        Landlord landlord2 = new Landlord(2, "Max Roan", "ronan89@gmail.com", "9898748962");
        Landlord landlord3 = new Landlord(3, "Krishna", "krishna@gmail.com", "9876541230");

        landlordList.add(landlord1);
        landlordList.add(landlord2);
        landlordList.add(landlord3);

    }

    @Test
    public void getAllLandlordTests(){

        when(landlordRepository.findAll()).thenReturn(landlordList);

        List<Landlord> allLandlords = landlordService.getAllLandlords();

        assertEquals(3, allLandlords.size());
        verify(landlordRepository, times(1)).findAll();
    }

    @Test
    public void getAllLandlordsExceptionTests(){

        when(landlordRepository.findAll()).thenThrow(new ModelEmptyListException(""));

        assertThrows(ModelEmptyListException.class,
                ()->{
                    landlordService.getAllLandlords();
                }
        );
        verify(landlordRepository, times(1)).findAll();
    }

    @Test
    public void getLandlordTest(){

        Landlord inLandlord = landlordList.get(1);
        when(landlordRepository.findByLandlordId(anyLong())).thenReturn(Optional.of(inLandlord));

        Landlord outLandlord = landlordService.getLandlord(inLandlord.getLandlordId());

        assertEquals(inLandlord.getName(), outLandlord.getName());
        assertEquals(inLandlord.getEmailId(), outLandlord.getEmailId());
        assertEquals(inLandlord.getPhoneNo(), outLandlord.getPhoneNo());

        verify(landlordRepository, times(1)).findByLandlordId(inLandlord.getLandlordId());
    }

    @Test
    public void getLandlordEmptyTest(){

        when(landlordRepository.findByLandlordId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                ()->{
                    landlordService.getLandlord(1);
                }
        );

        verify(landlordRepository, times(1)).findByLandlordId(1);
    }

    @Test
    public void getLandlordErrorTest(){

        when(landlordRepository.findByLandlordId(anyLong())).thenThrow(new ModelNotFoundException(""));

        assertThrows(ModelNotFoundException.class,
                ()->{
                    landlordService.getLandlord(1);
                }
        );

        verify(landlordRepository, times(1)).findByLandlordId(1);
    }

    @Test
    public void landlordDeleteTest(){

        Landlord inLandlord = landlordList.get(1);
        when(landlordRepository.findByLandlordId(anyLong())).thenReturn(Optional.of(inLandlord));
        doNothing().when(landlordRepository).delete(any(Landlord.class));

        landlordService.removeLandlord(inLandlord.getLandlordId());
        verify(landlordRepository, times(1)).findByLandlordId(inLandlord.getLandlordId());
        verify(landlordRepository, times(1)).delete(any(Landlord.class));
    }

    @Test
    public void landlordDeleteEmptyTest(){

        Landlord inLandlord = landlordList.get(1);
        when(landlordRepository.findByLandlordId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class,
                ()->{
                    landlordService.removeLandlord(inLandlord.getLandlordId());
                }
        );

        verify(landlordRepository, times(1)).findByLandlordId(inLandlord.getLandlordId());
        verify(landlordRepository, never()).delete(any(Landlord.class));
    }

    @Test
    public void landlordDeleteExceptionTest(){

        Landlord inLandlord = landlordList.get(1);
        when(landlordRepository.findByLandlordId(anyLong())).thenThrow(new ModelNotFoundException(""));

        assertThrows(ModelNotFoundException.class,
                ()->{
                    landlordService.removeLandlord(inLandlord.getLandlordId());
                }
        );

        verify(landlordRepository, times(1)).findByLandlordId(inLandlord.getLandlordId());
        verify(landlordRepository, never()).delete(any(Landlord.class));
    }

    @Test
    public void addLandlordTest(){

        when(landlordRepository.save(landlordList.get(2))).thenReturn(null);

        landlordService.addLandlord(landlordList.get(2));
        verify(landlordRepository, times(1)).save(landlordList.get(2));
    }

    @Test
    public void addLandlordExceptionTest(){

        when(landlordRepository.save(landlordList.get(2))).thenThrow(new ModelAddException(""));

        assertThrows(ModelAddException.class,
                ()->{
                    landlordService.addLandlord(landlordList.get(2));
                }
        );

        verify(landlordRepository, times(1)).save(landlordList.get(2));
    }

    @Test
    public void updateLandlordTest(){
        Landlord inLandlord = landlordList.get(1);
        when(landlordRepository.findByLandlordId(anyLong())).thenReturn(Optional.of(inLandlord));

        landlordService.updateLandlord(inLandlord.getLandlordId(), landlordList.get(1));
        verify(landlordRepository, times(1)).findByLandlordId(inLandlord.getLandlordId());
    }

    @Test
    public void updateLandlordEmptyTest(){
        Landlord inLandlord = landlordList.get(1);
        when(landlordRepository.findByLandlordId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ModelUpdateException.class,
                ()->{
                    landlordService.updateLandlord(inLandlord.getLandlordId(), landlordList.get(1));
                }
        );

        verify(landlordRepository, times(1)).findByLandlordId(inLandlord.getLandlordId());
    }

    @Test
    public void updateLandlordErrorTest(){
        Landlord inLandlord = landlordList.get(1);
        when(landlordRepository.findByLandlordId(anyLong())).thenThrow(new ModelUpdateException(""));

        assertThrows(ModelUpdateException.class,
                ()->{
                    landlordService.updateLandlord(inLandlord.getLandlordId(), landlordList.get(1));
                }
        );

        verify(landlordRepository, times(1)).findByLandlordId(inLandlord.getLandlordId());
    }


}


