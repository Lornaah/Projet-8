package tourGuide.service.rewardService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.async.CalculateRewardsTask;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class RewardsServiceImpl implements RewardsService {

	@Autowired
	GpsUtil gpsUtil;

	@Autowired
	RewardCentral rewardCentral;

	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
	private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;

	public RewardsServiceImpl() {
	}

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	public void calculateRewards(User user) {
		List<VisitedLocation> userLocations = user.getVisitedLocations();
		List<Attraction> attractions = gpsUtil.getAttractions();

		for (VisitedLocation visitedLocation : userLocations) {
			for (Attraction attraction : attractions) {
				if (!user.getUserRewards().stream()
						.anyMatch(r -> r.getAttraction().attractionName.equals(attraction.attractionName))) {
					if (nearAttraction(visitedLocation, attraction)) {
						user.addUserReward(
								new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
					}
				}
			}
		}
	}

	public List<Future<Void>> calculateRewardsAsync(List<User> userList) {
		// Orchestrateur de threads : gère le nombre de threads à lancer, leur lancement
		// etc.
		ExecutorService executor = Executors.newCachedThreadPool();
		// Callable : Interface de la classe CalculateRewardsTask
		List<Callable<Void>> callables = new ArrayList<>();

		// Pour chaque utilisateur, on crée une tâche exécutable en parallèle : chacune
		// prend en paramètre l'utilisateur sur lequel effectuer le traitement + la
		// fonction à appeler.
		// Ce n'est pas systématique de passer une fonction en paramètre, ça nous
		// arrange juste dans notre cas
		userList.forEach(u -> callables.add(new CalculateRewardsTask(u, this::calculateRewards)));

		// Attention ici : un seul thread en erreur => aucun utilisateur ne sera traité.
		try {
			return executor.invokeAll(callables);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}

	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.location) > proximityBuffer ? false : true;
	}

	public int getRewardPoints(Attraction attraction, User user) {
		return rewardCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}

	public double getDistance(Location loc1, Location loc2) {
		double lat1 = Math.toRadians(loc1.latitude);
		double lon1 = Math.toRadians(loc1.longitude);
		double lat2 = Math.toRadians(loc2.latitude);
		double lon2 = Math.toRadians(loc2.longitude);

		double angle = Math
				.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		double nauticalMiles = 60 * Math.toDegrees(angle);
		double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
		return statuteMiles;
	}

}
