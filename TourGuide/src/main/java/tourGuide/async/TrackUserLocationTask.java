package tourGuide.async;

import java.util.concurrent.Callable;
import java.util.function.Function;

import gpsUtil.location.VisitedLocation;
import tourGuide.user.User;

public class TrackUserLocationTask implements Callable<VisitedLocation> {
	private User user;
	private Function<User, VisitedLocation> func;

	public TrackUserLocationTask(User user, Function<User, VisitedLocation> func) {
		this.user = user;
		this.func = func;
	}

	@Override
	public VisitedLocation call() throws Exception {
		return func.apply(user);
	}

}
