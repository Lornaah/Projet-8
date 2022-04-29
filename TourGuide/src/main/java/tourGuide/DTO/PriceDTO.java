package tourGuide.DTO;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class PriceDTO {
	private String trippricerapikey;
	private UUID userId;
	private int numberOfAdults;
	private int numberOfChildren;
	private int tripDuration;
	private int cumulatativeRewardPoints;

	public PriceDTO() {
	}

	public PriceDTO(String trippricerapikey, UUID userId, int numberOfAdults, int numberOfChildren, int tripDuration,
			int cumulatativeRewardPoints) {
		this.trippricerapikey = trippricerapikey;
		this.userId = userId;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.tripDuration = tripDuration;
		this.cumulatativeRewardPoints = cumulatativeRewardPoints;
	}

	public String getTrippricerapikey() {
		return trippricerapikey;
	}

	public void setTrippricerapikey(String trippricerapikey) {
		this.trippricerapikey = trippricerapikey;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public int getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}

	public int getCumulatativeRewardPoints() {
		return cumulatativeRewardPoints;
	}

	public void setCumulatativeRewardPoints(int cumulatativeRewardPoints) {
		this.cumulatativeRewardPoints = cumulatativeRewardPoints;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cumulatativeRewardPoints, numberOfAdults, numberOfChildren, tripDuration, trippricerapikey,
				userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceDTO other = (PriceDTO) obj;
		return cumulatativeRewardPoints == other.cumulatativeRewardPoints && numberOfAdults == other.numberOfAdults
				&& numberOfChildren == other.numberOfChildren && tripDuration == other.tripDuration
				&& Objects.equals(trippricerapikey, other.trippricerapikey) && Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		return "PriceDTO [trippricerapikey=" + trippricerapikey + ", userId=" + userId + ", numberOfAdults="
				+ numberOfAdults + ", numberOfChildren=" + numberOfChildren + ", tripDuration=" + tripDuration
				+ ", cumulatativeRewardPoints=" + cumulatativeRewardPoints + "]";
	}

}
