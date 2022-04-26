package tourGuide.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.AttractionDTO;
import tourGuide.DTO.LocationDTO;
import tourGuide.DTO.VisitedLocationDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.api.ApiRequestService;
import tourGuide.tracker.Tracker;
import tourGuide.user.User;
import tourGuide.user.UserReward;
import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final GpsUtil gpsUtil;
	private final RewardsService rewardsService;
	private final TripPricer tripPricer = new TripPricer();
	public final Tracker tracker;
	boolean testMode = true;

	@Autowired
	private ApiRequestService apiRequestService;

	public TourGuideService(GpsUtil gpsUtil, RewardsService rewardsService) {
		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;

		if (testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this);
		addShutDownHook();
	}

	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}

	public VisitedLocation getUserLocation(User user) {
		VisitedLocation visitedLocation = user.getVisitedLocations().size() > 0 ? user.getLastVisitedLocation()
				: trackUserLocation(user);
		return visitedLocation;
	}

	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}

	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}

	public void addUser(User user) {
		if (!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}

	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(),
				user.getUserPreferences().getNumberOfAdults(), user.getUserPreferences().getNumberOfChildren(),
				user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}

	public VisitedLocation trackUserLocation(User user) {
		// Ajout Service
		VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
		user.addToVisitedLocations(visitedLocation);
		rewardsService.calculateRewards(user);
		return visitedLocation;
	}

	public List<AttractionDTO> getNearByAttractions(User user) {
		VisitedLocation visitedLocation = getUserLocation(user);
		List<Attraction> allAttractions = apiRequestService.getAttractions();
		Collections.sort(allAttractions, new AttractionComparator(visitedLocation.location));
		List<AttractionDTO> firstFiveNearbyAttractions = new ArrayList<>();

		for (int i = 0; i < 5; i++) {

			Attraction currentAttraction = allAttractions.get(i);

			double latitudeAttraction = currentAttraction.latitude;
			double longitudeAttraction = currentAttraction.longitude;
			double distance = rewardsService.getDistance(new Location(latitudeAttraction, longitudeAttraction),
					visitedLocation.location);

			AttractionDTO attractionDTO = new AttractionDTO(currentAttraction,
					rewardsService.getRewardPoints(currentAttraction, user), visitedLocation, distance);
			firstFiveNearbyAttractions.add(attractionDTO);
		}

		return firstFiveNearbyAttractions;
	}

	private class AttractionComparator implements Comparator<Attraction> {
		private Location userLocation;

		public AttractionComparator(Location userLocation) {
			this.userLocation = userLocation;
		}

		@Override
		// return -1 (décroissant) 0 ou 1 (croissant)
		public int compare(Attraction o1, Attraction o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			if (o1 == null || o2 == null)
				return 0;
			return Double.compare(rewardsService.getDistance(o1, userLocation),
					rewardsService.getDistance(o2, userLocation));
		}

	}

	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				tracker.stopTracking();
			}
		});
	}

	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing purposes
	// internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();

	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);

			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}

	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i -> {
			user.addToVisitedLocations(new VisitedLocation(user.getUserId(),
					new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}

	private double generateRandomLongitude() {
		double leftLimit = -180;
		double rightLimit = 180;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
		double rightLimit = 85.05112878;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
		return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}

	public List<VisitedLocationDTO> getAllCurrentLocations() {
		List<VisitedLocationDTO> visitedLocationList = new ArrayList<>();
		List<User> userList = getAllUsers();

		userList.forEach(u -> {
			List<LocationDTO> list = new ArrayList<>();
			for (int i = u.getVisitedLocations().size() - 1; i > 0; i--) {
				list.add(new LocationDTO(u.getVisitedLocations().get(i).location.latitude,
						u.getVisitedLocations().get(i).location.longitude));
			}
			VisitedLocationDTO visitedLocationDTO = new VisitedLocationDTO(u.getUserId().toString(), list);
			visitedLocationList.add(visitedLocationDTO);
		});

		return visitedLocationList;
	}

}
