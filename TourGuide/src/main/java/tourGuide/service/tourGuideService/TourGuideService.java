package tourGuide.service.tourGuideService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.AttractionDTO;
import tourGuide.DTO.LocationDTO;
import tourGuide.DTO.ProviderDTO;
import tourGuide.user.User;

public interface TourGuideService {

	VisitedLocation getUserLocation(User user);

	User getUser(String userName);

	List<User> getAllUsers();

	void addUser(User user);

	List<ProviderDTO> getTripDeals(User user);

	VisitedLocation trackUserLocation(User user);

	List<AttractionDTO> getNearbyAttractions(User user);

	Map<String, LocationDTO> getAllCurrentLocations();

	void setInternalUsersNumberCount(int count);

	Future<VisitedLocation> trackUserLocationAsync(User user);

}
