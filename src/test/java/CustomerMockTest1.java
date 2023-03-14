import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
public class CustomerMockTest1 {

    private Customer customer;
    private Savings savingsAccountMock;
    private Checking checkingAccountMock;
    private Loan loanMock;
    private CreditReport creditReportMock;

    @BeforeEach
    public void setUp() throws Exception {
        savingsAccountMock = mock(Savings.class);
        checkingAccountMock = mock(Checking.class);
        loanMock = mock(Loan.class);
        creditReportMock = mock(CreditReport.class);
    }
    @Test
    public void testCreateCustomerCallsCreditReportGetScore() {
         int CUSTOMER_ID = 0;
         String CUSTOMER_NAME = "John Doe";
         String CUSTOMER_ADDRESS = "123 Main St";
         int CUSTOMER_AGE = 30;
         int CUSTOMER_INCOME = 100000;
         String CUSTOMER_PHONE = "(917) 555-1234";
         String CUSTOMER_SSN = "123-45-6789";
        customer = new Customer(creditReportMock, CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_AGE, CUSTOMER_ADDRESS, CUSTOMER_INCOME, CUSTOMER_PHONE, CUSTOMER_SSN);
        InOrder inOrder = inOrder(creditReportMock);
        inOrder.verify(creditReportMock, times(1)).getScore(CUSTOMER_SSN);
    }
}
