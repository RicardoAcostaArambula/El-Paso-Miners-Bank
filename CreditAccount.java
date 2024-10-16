import java.util.Date;

public class CreditAccount extends Account {
    // Attributes
    private float CreditAccountLimit;
    private String goodStanding;
    private Date paymentDate;

    // Constructors, no-argument constructor of the Account class
    public CreditAccount() {
        super(); 
    }

    public CreditAccount(String routing_number, float CreditAccountLimit, String goodStanding, Date paymentDate) {
        super(routing_number); // Calls the constructor of the Account class
        this.CreditAccountLimit = CreditAccountLimit;
        this.goodStanding = goodStanding;
        this.paymentDate = paymentDate;
    }

    // Setters
    public void set_CreditAccount_limit(float CreditAccountLimit) {
        if (CreditAccountLimit >= 0) {
            this.CreditAccountLimit = CreditAccountLimit;
        }
    }

    public boolean set_good_standing(String goodStanding) {
        if (goodStanding != null && !goodStanding.isEmpty()) {
            this.goodStanding = goodStanding;
            return true;
        }
        return false;
    }

    public void set_payment_date(Date paymentDate) {
        if (paymentDate != null) {
            this.paymentDate = paymentDate;
        }
    }

    // Getters
    public float get_CreditAccount_limit() {
        return this.CreditAccountLimit;
    }

    public String get_good_standing() {
        return this.goodStanding;
    }

    public Date get_payment_date() {
        return this.paymentDate;
    }
}
