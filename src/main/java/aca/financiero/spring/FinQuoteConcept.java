package  aca.financiero.spring;

public class FinQuoteConcept{
	private int quoteId;
	private int concId;
	private int noUnits;
	private Double amount;	
	private Double discountAmt;
	
	public FinQuoteConcept(){
		quoteId		= 0;
		concId		= 0;
		noUnits		= 0;
		amount		= null;
		discountAmt	= null;
	}

	public int getQuoteId() {
		return quoteId;
	}
	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
	}

	public int getConcId() {
		return concId;
	}
	public void setConcId(int concId) {
		this.concId = concId;
	}

	public int getNoUnits() {
		return noUnits;
	}
	public void setNoUnits(int noUnits) {
		this.noUnits = noUnits;
	}

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(Double discountAmt) {
		this.discountAmt = discountAmt;
	}
	
}
