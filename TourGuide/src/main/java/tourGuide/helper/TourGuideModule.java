package tourGuide.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;
import tourGuide.tracker.Tracker;
import tripPricer.TripPricer;

@Configuration
public class TourGuideModule {
	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}

	@Bean
	public TripPricer getTripPricer() {
		return new TripPricer();
	}

	@Bean
	public GpsUtil getGpsUtil() {
		return new GpsUtil();
	}

	@Bean
	Tracker getTracker() {
		return new Tracker();
	}

	@Bean
	public ExecutorService getExercutorService() {
		return executor;
	}

}
