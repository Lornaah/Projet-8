package tourGuide.async;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import tourGuide.user.User;

// Bout de code exécuté en parallèle.
public class CalculateRewardsTask implements Callable<Void> {

	private User user;
	private Consumer<User> func;

	public CalculateRewardsTask(User user, Consumer<User> func) {
		this.user = user;
		this.func = func;
	}

	@Override
	public Void call() throws Exception {
		func.accept(user);
		return null;
	}

}
