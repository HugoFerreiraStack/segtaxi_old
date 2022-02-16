package br.com.seg.maxipago.datacontract.transactional;

public class Item {
	
	//Attributes
	private Integer itemIndex;
	private String itemProductCode;
	private String itemDescription;
	private Integer itemQuantity;
	private Double itemTotalAmount;
	private Double itemUnitCost;
	private Double itemTaxAmount;

	//Getters and Setters 
	public Integer getItemIndex() {
		return itemIndex;
	}
	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}
	public String getItemProductCode() {
		return itemProductCode;
	}
	public void setItemProductCode(String itemProductCode) {
		this.itemProductCode = itemProductCode;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public Integer getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public Double getItemTotalAmount() {
		return itemTotalAmount;
	}
	public void setItemTotalAmount(Double itemTotalAmount) {
		this.itemTotalAmount = itemTotalAmount;
	}
	public Double getItemUnitCost() {
		return itemUnitCost;
	}
	public void setItemUnitCost(Double itemUnitCost) {
		this.itemUnitCost = itemUnitCost;
	}
	public Double getItemTaxAmount() {
		return itemTaxAmount;
	}
	public void setItemTaxAmount(Double itemTaxAmount) {
		this.itemTaxAmount = itemTaxAmount;
	}
}