package kutuphane.kutuphane_otomasyonu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "penalties")
public class Penalties {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long penalty_ID;
    private boolean is_paid = false;
	private Double penalty_amount;
	private String penalty_reason;

	@ManyToOne
	@JoinColumn(name = "loan_id")
	private Loan loan;

	public Penalties() {
	}

	public Penalties(Double penalty_amount, String penalty_reason, Loan loan) {
		this.penalty_amount = penalty_amount;
		this.penalty_reason = penalty_reason;
		this.loan = loan;
	}
    public boolean isIs_paid() {
    return is_paid;
}

public void setIs_paid(boolean is_paid) {
    this.is_paid = is_paid;
}
	public Long getPenalty_ID() {
		return penalty_ID;
	}

	public void setPenalty_ID(Long penalty_ID) {
		this.penalty_ID = penalty_ID;
	}

	public Double getPenalty_amount() {
		return penalty_amount;
	}

	public void setPenalty_amount(Double penalty_amount) {
		this.penalty_amount = penalty_amount;
	}

	public String getPenalty_reason() {
		return penalty_reason;
	}

	public void setPenalty_reason(String penalty_reason) {
		this.penalty_reason = penalty_reason;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
}
