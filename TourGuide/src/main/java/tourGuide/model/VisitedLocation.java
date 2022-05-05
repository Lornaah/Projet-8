package tourGuide.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class VisitedLocation {

	private UUID id;
	private Location location;
	private Date timeVisited;

	public VisitedLocation() {
	}

	public VisitedLocation(UUID id, Location location, Date timeVisited) {
		this.id = id;
		this.location = location;
		this.timeVisited = timeVisited;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getTimeVisited() {
		return timeVisited;
	}

	public void setTimeVisited(Date timeVisited) {
		this.timeVisited = timeVisited;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, location, timeVisited);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VisitedLocation other = (VisitedLocation) obj;
		return Objects.equals(id, other.id) && Objects.equals(location, other.location)
				&& Objects.equals(timeVisited, other.timeVisited);
	}

}
