package com.infy.cabbooking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.cabbooking.entity.Booking;
import com.infy.cabbooking.entity.Cab;

public interface CabRepository extends CrudRepository<Cab, Integer>{
	
	

}
