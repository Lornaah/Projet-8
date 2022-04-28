package tourGuide.service.api;

import java.util.List;
import java.util.UUID;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

public interface ApiRequestService {

	List<Attraction> getAttractions();

	VisitedLocation getUserLocation(UUID uuid);

}
