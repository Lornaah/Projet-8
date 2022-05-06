package tourGuide.service.rewardService;

import java.util.List;
import java.util.concurrent.Future;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import tourGuide.user.User;

public interface RewardsService {

	void setProximityBuffer(int proximityBuffer);

	void setDefaultProximityBuffer();

	void calculateRewards(User user);

	boolean isWithinAttractionProximity(Attraction attraction, Location location);

	int getRewardPoints(Attraction attraction, User user);

	double getDistance(Location loc1, Location loc2);

	List<Future<Void>> calculateRewardsAsync(List<User> userList);

}
