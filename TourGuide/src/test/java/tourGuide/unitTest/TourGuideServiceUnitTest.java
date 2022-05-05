package tourGuide.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.AttractionDTO;
import tourGuide.DTO.VisitedLocationDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.rewardService.RewardsService;
import tourGuide.service.tourGuideService.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;
import tripPricer.TripPricer;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TourGuideServiceUnitTest {

	@Autowired
	TourGuideService tourGuideService;

	@MockBean
	GpsUtil gpsUtil;

	@MockBean
	TripPricer tripPricer;

	@MockBean
	RewardsService rewardsService;

	@BeforeEach
	public void beforeEach() {
		Locale.setDefault(Locale.US);
	}

	@Test
	public void getUserLocation_WhenGetVisitedLocationSizeIsEmpty() {

		InternalTestHelper.setInternalUserNumber(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		VisitedLocation visitedLocationMock = new VisitedLocation(user.getUserId(), null, null);
		Mockito.when(gpsUtil.getUserLocation(user.getUserId())).thenReturn(visitedLocationMock);

		VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);

		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}

	@Test
	public void getUserLocation_WhenGetVisitedLocationSizeIsNotEmpty() {

		InternalTestHelper.setInternalUserNumber(0);

		// Mock 2 functions, one deeper the first one ex :
		// user.getVisitedLocations().size()
		User user = Mockito.mock(User.class, Mockito.RETURNS_DEEP_STUBS);
		Mockito.when(user.getVisitedLocations().size()).thenReturn(5);
		VisitedLocation visitedLocationMock = new VisitedLocation(UUID.randomUUID(), new Location(10, 10), new Date());
		Mockito.when(user.getLastVisitedLocation()).thenReturn(visitedLocationMock);

		VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);

		assertEquals(visitedLocation, visitedLocationMock);

	}

	@Test
	public void addUser() {
		InternalTestHelper.setInternalUserNumber(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);

		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}

	@Test
	public void getAllUsers() {
		InternalTestHelper.setInternalUserNumber(0);

		User user = new User(UUID.randomUUID(), "jonnhy", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jonnhy2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);

		List<User> allUsers = tourGuideService.getAllUsers();

		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}

	@Test
	public void trackUser() {
		InternalTestHelper.setInternalUserNumber(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		VisitedLocation visitedLocationMock = new VisitedLocation(user.getUserId(), null, null);
		Mockito.when(gpsUtil.getUserLocation(user.getUserId())).thenReturn(visitedLocationMock);

		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user);

		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}

	@Test
	public void getNearbyAttractions() {
		InternalTestHelper.setInternalUserNumber(0);
		List<Attraction> attractions = new ArrayList<>();

		Attraction att = Mockito.mock(Attraction.class);
		for (int i = 0; i < 5; i++)
			attractions.add(att);

		Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(10, 10), new Date());
		user.addToVisitedLocations(visitedLocation);
		Mockito.when(rewardsService.getDistance(any(Location.class), any(Location.class))).thenReturn(10.00);
		Mockito.when(rewardsService.getRewardPoints(any(Attraction.class), any(User.class))).thenReturn(100);

		List<AttractionDTO> attractionDTOs = tourGuideService.getNearByAttractions(user);

		assertFalse(attractionDTOs.isEmpty());
		assertFalse(attractionDTOs == null);
		assertEquals(5, attractionDTOs.size());

	}

	@Test
	public void getTripDeals() {

		InternalTestHelper.setInternalUserNumber(0);

		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		List<Provider> providerList = new ArrayList<>();

		Provider provider = new Provider(UUID.randomUUID(), "name", 10d);
		providerList.add(provider);

		Mockito.when(tripPricer.getPrice(any(String.class), any(UUID.class), any(Integer.class), any(Integer.class),
				any(Integer.class), any(Integer.class))).thenReturn(providerList);

		List<Provider> providers = tourGuideService.getTripDeals(user);

		assertFalse(providers.isEmpty());
		assertFalse(providers == null);

	}

	@Test
	public void getAllCurrentLocations() {
		List<VisitedLocationDTO> visitedLocationDTOs = tourGuideService.getAllCurrentLocations();

		assertFalse(visitedLocationDTOs == null);
		assertFalse(visitedLocationDTOs.isEmpty());
	}
}
