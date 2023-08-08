package com.holynekk.kafka.broker.message;

public class CustomerPurchaseWebMessage {

	private String purchaseNumber;

	private int purchaseAmount;

	private String browser;

	private String operatingSystem;

	public CustomerPurchaseWebMessage() {
	}

	public CustomerPurchaseWebMessage(String purchaseNumber, int purchaseAmount, String browser,
			String operatingSystem) {
		super();
		this.purchaseNumber = purchaseNumber;
		this.purchaseAmount = purchaseAmount;
		this.browser = browser;
		this.operatingSystem = operatingSystem;
	}

	public String getBrowser() {
		return browser;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public int getPurchaseAmount() {
		return purchaseAmount;
	}

	public String getPurchaseNumber() {
		return purchaseNumber;
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

	public void setPurchaseNumber(String purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	@Override
	public String toString() {
		return "CustomerPurchaseWebMessage [purchaseAmount=" + purchaseAmount + ", browser=" + browser
				+ ", operatingSystem=" + operatingSystem + "]";
	}
}
