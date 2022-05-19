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

import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.AttractionDTO;
import tourGuide.DTO.ProviderDTO;
import tourGuide.service.rewardService.RewardsService;
import tourGuide.service.tourGuideService.TourGuideService;
import tourGuide.user.User;

@SpringBootTest
public class TourGuideServiceIntegrationTest {

	@Autowired
	RewardsService rewardsService;

	@Autowired
	TourGuideService tourGuideService;

	@BeforeEach
	public void beforeEach() {
		Locale.setDefault(Locale.US);
	}

	@Test
	public void getUserLocation() {

		tourGuideService.setInternalUsersNumberCount(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);

		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}

	@Test
	public void trackUser() {
		tourGuideService.setInternalUsersNumberCount(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

		assertEquals(user.getUserId(), visitedLocation.userId);
	}

	@Test
	public void getTripDeals() {

		tourGuideService.setInternalUsersNumberCount(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<ProviderDTO> providers = tourGuideService.getTripDeals(user);

		assertFalse(providers.isEmpty());
		assertFalse(providers == null);
	}

	@Test
	public void getNearbyAttraction() {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<AttractionDTO> attractionDTOs = tourGuideService.getNearbyAttractions(user);

		assertFalse(attractionDTOs == null);
		assertFalse(attractionDTOs.isEmpty());
		assertTrue(attractionDTOs.size() == 5);
		assertTrue(attractionDTOs.get(0).getDistanceBetweenUser() <= attractionDTOs.get(1).getDistanceBetweenUser());
	}
}
