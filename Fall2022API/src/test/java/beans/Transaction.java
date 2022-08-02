package beans;

import lombok.Data;

@Data
public class Transaction {
    private double amount;
    private String description;
    private String transactionTypeCode;


}
