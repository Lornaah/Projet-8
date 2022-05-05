package tourGuide.service.rewardService;

import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.user.User;

public interface RewardsService {

	void setProximityBuffer(int proximityBuffer);

	void setDefaultProximityBuffer();

	void calculateRewards(User user);

	boolean isWithinAttractionProximity(Attraction attraction, Location location);

	int getRewardPoints(Attraction attraction, User user);

	double getDistance(Location loc1, Location loc2);

}
