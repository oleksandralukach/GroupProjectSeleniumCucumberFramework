package beans;

import lombok.Data;

@Data
public class CreateAccountPayload {
    String accountName;
    String accountTypeCode;
    Double openingDeposit;
    String ownerTypeCode;
}
