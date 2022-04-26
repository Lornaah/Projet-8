package tourGuide.service.api;

import java.util.List;

import gpsUtil.location.Attraction;

public interface ApiRequestService {

	List<Attraction> getAttractions();

}
