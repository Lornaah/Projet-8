package tourGuide.DTO;

import java.util.List;
import java.util.Objects;

public class VisitedLocationDTO {

	private String userId;
	private List<LocationDTO> location;

	public VisitedLocationDTO(String userId, List<LocationDTO> visitedLocation) {
		this.userId = userId.toString();
		this.location = visitedLocation;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<LocationDTO> getLocation() {
		return location;
	}

	public void setLocation(List<LocationDTO> location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		return Objects.hash(location, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VisitedLocationDTO other = (VisitedLocationDTO) obj;
		return Objects.equals(location, other.location) && Objects.equals(userId, other.userId);
	}

}
