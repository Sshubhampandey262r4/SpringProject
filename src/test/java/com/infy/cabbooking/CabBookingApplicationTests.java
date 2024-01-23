package com.infy.cabbooking;


import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.cabbooking.dto.BookingDTO;
import com.infy.cabbooking.dto.CabDTO;
import com.infy.cabbooking.entity.Booking;
import com.infy.cabbooking.entity.Cab;
import com.infy.cabbooking.exception.CabBookingException;
import com.infy.cabbooking.repository.BookingRepository;
import com.infy.cabbooking.repository.CabRepository;
import com.infy.cabbooking.service.CabBookingService;
import com.infy.cabbooking.service.CabBookingServiceImpl;

@SpringBootTest
public class CabBookingApplicationTests {

	
	@Mock
	private CabRepository cabRepository;
	

	@Mock
	private BookingRepository bookingRepository;
	
	@InjectMocks
	private CabBookingService cabBookingService = new CabBookingServiceImpl();
	
	
	@Test
	public void bookCabInvalidCabNoTest() throws CabBookingException {
		BookingDTO bookingdt=new BookingDTO();
		bookingdt.setBookingId(10099);
		
		CabDTO cabdt=new CabDTO();
		cabdt.setCabNo(56789);
		bookingdt.setCabDTO(cabdt);
		
		Cab cab = new Cab();
		cab.setCabNo(56789);
		cab.setAvailability("No");
		Mockito.when(cabRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(cab));
		
		CabBookingException e = Assertions.assertThrows(CabBookingException.class, ()-> {
			cabBookingService.bookCab(bookingdt);
		});
		
		Assertions.assertEquals("Service.CAB_NOT_AVAILABLE", e.getMessage());
	}
	
	@Test
	public void getDetailsByBookingTypeNoDetailsFound() {

		BookingDTO bookingsdto=new BookingDTO();
		bookingsdto.setBookingType("normal");
		Booking booking=new Booking();
		
		
		Mockito.when(bookingRepository.findBybookingType(Mockito.anyString())).thenReturn(null);
		
		CabBookingException e=Assertions.assertThrows(CabBookingException.class, ()->{ 
			cabBookingService.getDetailsByBookingType(booking.getBookingType());
		});
		
		Assertions.assertEquals("Service.NO_DETAILS_FOUND",e.getMessage());
		
	}
	

}
