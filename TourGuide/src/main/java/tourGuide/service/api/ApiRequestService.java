package tourGuide.service.api;

import java.util.List;
import java.util.UUID;

import tourGuide.DTO.PriceDTO;
import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;
import tripPricer.Provider;

public interface ApiRequestService {

	List<Attraction> getAttractions();

	VisitedLocation getUserLocation(UUID uuid);

	int getAttractionRewardPoints(UUID attractionId, UUID userId);

	List<Provider> getPrice(PriceDTO priceDTO);

}
