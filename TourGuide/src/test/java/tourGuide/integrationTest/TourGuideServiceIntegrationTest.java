package tourGuide.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tourGuide.DTO.AttractionDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.VisitedLocation;
import tourGuide.service.api.ApiRequestService;
import tourGuide.service.rewardService.RewardsService;
import tourGuide.service.tourGuideService.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

@SpringBootTest
public class TourGuideServiceIntegrationTest {

	@Autowired
	RewardsService rewardsService;

	@Autowired
	TourGuideService tourGuideService;

	@Autowired
	ApiRequestService apiRequestService;

	@BeforeEach
	public void beforeEach() {
		Locale.setDefault(Locale.US);
	}

	@Test
	public void getUserLocation() {

		InternalTestHelper.setInternalUserNumber(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);

		assertTrue(visitedLocation.getId().equals(user.getUserId()));
	}

	@Test
	public void trackUser() {
		InternalTestHelper.setInternalUserNumber(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

		assertEquals(user.getUserId(), visitedLocation.getId());
	}

	@Test
	public void getTripDeals() {

		InternalTestHelper.setInternalUserNumber(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<Provider> providers = tourGuideService.getTripDeals(user);

		assertFalse(providers.isEmpty());
		assertFalse(providers == null);
	}

	@Test
	public void getNearbyAttraction() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<AttractionDTO> attractionDTOs = tourGuideService.getNearByAttractions(user);

		assertFalse(attractionDTOs == null);
		assertFalse(attractionDTOs.isEmpty());
		assertTrue(attractionDTOs.size() == 5);
		assertTrue(attractionDTOs.get(0).getDistanceBetweenUser() <= attractionDTOs.get(1).getDistanceBetweenUser());
	}
}
