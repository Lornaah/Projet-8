package tourGuide.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.ProviderDTO;
import tourGuide.service.rewardService.RewardsService;
import tourGuide.service.tourGuideService.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;

	@Autowired
	RewardsService rewardsService;

	@Autowired
	ObjectMapper mapper;

	@RequestMapping("/")
	public String index() {
		return "Greetings from TourGuide!";
	}

	@PostMapping("/getLocation")
	public String getLocation(@RequestParam String userName) {
		try {
			VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
			return mapper.writeValueAsString(visitedLocation.location);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found" + e.getMessage());
		}
	}

	@PostMapping("/getNearbyAttractions")
	public String getNearbyAttractions(@RequestParam String userName) {
		try {
			User user = getUser(userName);
			return mapper.writeValueAsString(tourGuideService.getNearbyAttractions(user));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nearby Attractions not found" + e.getMessage());
		}
	}

	@PostMapping("/getRewards")
	public String getRewards(@RequestParam String userName) throws JsonProcessingException {
		rewardsService.calculateRewards(getUser(userName));
		List<UserReward> rewardsList = getUser(userName).getUserRewards();
		if (rewardsList.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user " + userName + " doesn't have rewards");
		}
		return mapper.writeValueAsString(getUser(userName).getUserRewards());
	}

	@PostMapping("/getAllCurrentLocations")
	public String getAllCurrentLocations() {
		try {
			return mapper.writeValueAsString(tourGuideService.getAllCurrentLocations());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "All current locations not found" + e.getMessage());
		}
	}

	@PostMapping("/getTripDeals")
	public String getTripDeals(@RequestParam String userName) {
		try {
			List<ProviderDTO> providers = tourGuideService.getTripDeals(getUser(userName));
			return mapper.writeValueAsString(providers);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip Deals not found " + e.getMessage());
		}
	}

	private User getUser(String userName) {
		User user = tourGuideService.getUser(userName);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user " + userName + " is not found");
		}
		return user;
	}

}