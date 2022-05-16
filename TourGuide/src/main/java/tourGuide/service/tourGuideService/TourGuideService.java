package tourGuide.service.tourGuideService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.AttractionDTO;
import tourGuide.user.User;
import tripPricer.Provider;

public interface TourGuideService {

	VisitedLocation getUserLocation(User user);

	User getUser(String userName);

	List<User> getAllUsers();

	void addUser(User user);

	List<Provider> getTripDeals(User user);

	VisitedLocation trackUserLocation(User user);

	List<AttractionDTO> getNearbyAttractions(User user);

	Map<String, Location> getAllCurrentLocations();

	void setInternalUsersNumberCount(int count);

	Future<VisitedLocation> trackUserLocationAsync(User user);

}
