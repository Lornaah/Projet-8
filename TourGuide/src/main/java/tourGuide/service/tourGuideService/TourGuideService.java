package tourGuide.service.tourGuideService;

import java.util.List;

import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.AttractionDTO;
import tourGuide.DTO.VisitedLocationDTO;
import tourGuide.user.User;
import tripPricer.Provider;

public interface TourGuideService {

	VisitedLocation getUserLocation(User user);

	User getUser(String userName);

	List<User> getAllUsers();

	void addUser(User user);

	List<Provider> getTripDeals(User user);

	VisitedLocation trackUserLocation(User user);

	List<AttractionDTO> getNearByAttractions(User user);

	List<VisitedLocationDTO> getAllCurrentLocations();

}
