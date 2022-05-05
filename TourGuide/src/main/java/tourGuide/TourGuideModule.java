package tourGuide;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rewardCentral.RewardCentral;
import tourGuide.tracker.Tracker;
import tripPricer.TripPricer;

@Configuration
public class TourGuideModule {

	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}

	@Bean
	public TripPricer getTripPricer() {
		return new TripPricer();
	}

	@Bean
	Tracker getTracker() {
		return new Tracker();
	}

}
