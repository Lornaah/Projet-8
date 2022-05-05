package tourGuide.model;

import java.util.Objects;
import java.util.UUID;

public class Attraction extends Location {

	private String attractionName;
	private String city;
	private String state;
	private UUID attractionId;

	public Attraction() {
	}

	public Attraction(String attractionName, String city, String state, double latitude, double longitude,
			UUID attractionId) {
		this.attractionName = attractionName;
		this.city = city;
		this.state = state;
		this.attractionId = attractionId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getAttractionName() {
		return attractionName;
	}

	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UUID getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(UUID attractionId) {
		this.attractionId = attractionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attractionId, attractionName, city, latitude, longitude, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attraction other = (Attraction) obj;
		return Objects.equals(attractionId, other.attractionId) && Objects.equals(attractionName, other.attractionName)
				&& Objects.equals(city, other.city)
				&& Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude)
				&& Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude)
				&& Objects.equals(state, other.state);
	}

}
