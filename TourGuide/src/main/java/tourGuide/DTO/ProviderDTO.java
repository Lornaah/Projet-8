package tourGuide.DTO;

import java.util.Objects;
import java.util.UUID;

import tripPricer.Provider;

public class ProviderDTO {

	private String name;
	private double price;
	private UUID tripId;

	public ProviderDTO(String name, double price, UUID tripId) {
		this.name = name;
		this.price = price;
		this.tripId = tripId;
	}

	public ProviderDTO(Provider provid) {
		this.name = provid.name;
		this.price = provid.price;
		this.tripId = provid.tripId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public UUID getTripId() {
		return tripId;
	}

	public void setTripId(UUID tripId) {
		this.tripId = tripId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, price, tripId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProviderDTO other = (ProviderDTO) obj;
		return Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(tripId, other.tripId);
	}

	@Override
	public String toString() {
		return "ProviderDTO [name=" + name + ", price=" + price + ", tripId=" + tripId + "]";
	}

}
