package com.holynekk.kafka.api.request;

public class CustomerPurchaseWebRequest {

	private int purchaseAmount;
	
	private String browser;
	
	private String operatingSystem;

	public String getBrowser() {
		return browser;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public void setPurchaseAmount(int purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	@Override
	public String toString() {
		return "CustomerPurchaseWebMessage [purchaseAmount=" + purchaseAmount + ", browser=" + browser
				+ ", operatingSystem=" + operatingSystem + "]";
	}
}
