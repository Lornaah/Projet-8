package tourGuide.DTO;

import java.util.Objects;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

public class AttractionDTO {

	private String name;
	private double attractionLatitude;
	private double attractionLongitude;
	private double userLatitude;
	private double userLongitude;
	private double distanceBetweenUser;
	private int rewardPoints;

	public AttractionDTO(Attraction currentAttraction, int rewardPoints, VisitedLocation visitedLocation,
			double distance) {
		this.name = currentAttraction.attractionName;
		this.attractionLatitude = currentAttraction.latitude;
		this.attractionLongitude = currentAttraction.longitude;
		this.userLatitude = visitedLocation.location.latitude;
		this.userLongitude = visitedLocation.location.longitude;
		this.distanceBetweenUser = distance;
		this.rewardPoints = rewardPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAttractionLatitude() {
		return attractionLatitude;
	}

	public void setAttractionLatitude(double attractionLatitude) {
		this.attractionLatitude = attractionLatitude;
	}

	public double getAttractionLongitude() {
		return attractionLongitude;
	}

	public void setAttractionLongitude(double attractionLongitude) {
		this.attractionLongitude = attractionLongitude;
	}

	public double getUserLatitude() {
		return userLatitude;
	}

	public void setUserLatitude(double userLatitude) {
		this.userLatitude = userLatitude;
	}

	public double getUserLongitude() {
		return userLongitude;
	}

	public void setUserLongitude(double userLongitude) {
		this.userLongitude = userLongitude;
	}

	public double getDistanceBetweenUser() {
		return distanceBetweenUser;
	}

	public void setDistanceBetweenUser(double distanceBetweenUser) {
		this.distanceBetweenUser = distanceBetweenUser;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attractionLatitude, attractionLongitude, distanceBetweenUser, name, rewardPoints,
				userLatitude, userLongitude);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttractionDTO other = (AttractionDTO) obj;
		return Double.doubleToLongBits(attractionLatitude) == Double.doubleToLongBits(other.attractionLatitude)
				&& Double.doubleToLongBits(attractionLongitude) == Double.doubleToLongBits(other.attractionLongitude)
				&& Double.doubleToLongBits(distanceBetweenUser) == Double.doubleToLongBits(other.distanceBetweenUser)
				&& Objects.equals(name, other.name) && rewardPoints == other.rewardPoints
				&& Double.doubleToLongBits(userLatitude) == Double.doubleToLongBits(other.userLatitude)
				&& Double.doubleToLongBits(userLongitude) == Double.doubleToLongBits(other.userLongitude);
	}

	@Override
	public String toString() {
		return "AttractionDTO [name=" + name + ", attractionLatitude=" + attractionLatitude + ", attractionLongitude="
				+ attractionLongitude + ", userLatitude=" + userLatitude + ", userLongitude=" + userLongitude
				+ ", distanceBetweenUser=" + distanceBetweenUser + ", rewardPoints=" + rewardPoints + "]";
	}

}
