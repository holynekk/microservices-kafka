package com.holynekk.kafka.api.request;

public class CustomerPurchaseMobileRequest {

	public static class Location {
		private double latitude;

		private double longitude;

		public double getLatitude() {
			return latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		@Override
		public String toString() {
			return "Location [latitude=" + latitude + ", longitude=" + longitude + "]";
		}
	}

	private int purchaseAmount;

	private String mobileAppVersion;

	private String operatingSystem;

	private Location location;

	public Location getLocation() {
		return location;
	}

	public String getMobileAppVersion() {
		return mobileAppVersion;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setMobileAppVersion(String mobileAppVersion) {
		this.mobileAppVersion = mobileAppVersion;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	@Override
	public String toString() {
		return "CustomerPurchaseWebMessage [purchaseAmount=" + purchaseAmount + ", mobileAppVersion=" + mobileAppVersion
				+ ", operatingSystem=" + operatingSystem + ", location=" + location + "]";
	}

}
