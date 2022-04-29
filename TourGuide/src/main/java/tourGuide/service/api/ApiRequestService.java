package tourGuide.service.api;

import java.util.List;
import java.util.UUID;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.PriceDTO;
import tripPricer.Provider;

public interface ApiRequestService {

	List<Attraction> getAttractions();

	VisitedLocation getUserLocation(UUID uuid);

	int getAttractionRewardPoints(UUID attractionId, UUID userId);

	List<Provider> getPrice(PriceDTO priceDTO);

}
