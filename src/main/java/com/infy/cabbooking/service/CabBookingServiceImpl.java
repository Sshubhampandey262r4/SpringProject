package com.infy.cabbooking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.infy.cabbooking.dto.BookingDTO;
import com.infy.cabbooking.dto.CabDTO;
import com.infy.cabbooking.entity.Booking;
import com.infy.cabbooking.entity.Cab;
import com.infy.cabbooking.exception.CabBookingException;
import com.infy.cabbooking.repository.BookingRepository;
import com.infy.cabbooking.repository.CabRepository;



@Service
@Transactional
public class CabBookingServiceImpl implements CabBookingService {
    
    @Autowired
    Environment enviroment;
    
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private CabRepository cabRepository;


	@Override
	public List<BookingDTO> getDetailsByBookingType(String bookingType) throws CabBookingException {
		List<Booking> bookingEntity=  bookingRepository.findBybookingType(bookingType);
		if(bookingEntity.isEmpty())
		{
			throw new CabBookingException("Service.NO_DETAILS_FOUND");
		}
		
		List<BookingDTO> bookingListdto=new ArrayList<BookingDTO>();
		for(Booking b:bookingEntity)
		{
			BookingDTO bookingsdto=new BookingDTO();
			bookingsdto.setBookingId(b.getBookingId());
			bookingsdto.setBookingType(b.getBookingType());
			bookingsdto.setCustomerName(b.getCustomerName());
			bookingsdto.setPhoneNo(b.getPhoneNo());
			CabDTO cabs=new CabDTO();
			cabs.setCabNo(b.getCab().getCabNo());
			cabs.setAvailability(b.getCab().getAvailability());
			cabs.setDriverPhoneNo(b.getCab().getDriverPhoneNo());
			cabs.setModelName(b.getCab().getModelName());
			bookingsdto.setCabDTO(cabs);
			
			bookingListdto.add(bookingsdto);
		}
		
		return bookingListdto;
		}

	
	@Override
	public Integer bookCab(BookingDTO bookingDTO) throws CabBookingException {
		 
		Optional<Cab> cabsOptional= cabRepository.findById(bookingDTO.getCabDTO().getCabNo());
		  
		  Cab cabs=cabsOptional.orElseThrow(()-> new CabBookingException("Service.CAB_NOT_FOUND"));
		  if(cabs.getAvailability()=="No")
		  {
			  throw new CabBookingException("Service.CAB_NOT_AVAILABLE");
		  }
		  
		  Booking bookings=new Booking();
		  bookings.setPhoneNo(bookingDTO.getPhoneNo());
		  bookings.setBookingType(bookingDTO.getBookingType());
		  bookings.setCustomerName(bookingDTO.getCustomerName());
		  cabs.setAvailability("NO");
		  bookings.setCab(cabs);
		  bookingRepository.save(bookings);
		return bookings.getBookingId();
		}
	
	

}



