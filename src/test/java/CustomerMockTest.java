import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

public class CustomerMockTest {
//    @Mock
    private Customer customer;
    private Savings savingsAccount;
    private Checking checkingAccount;
    private Loan loan;
    private CreditReport creditReportMock;

    private static final int CUSTOMER_ID = 0;
    private static final String CUSTOMER_NAME = "John Doe";
    private static final String CUSTOMER_ADDRESS = "123 Main St";
    private static final int CUSTOMER_AGE = 30;
    private static final int CUSTOMER_INCOME = 100000;
    private static final String CUSTOMER_PHONE = "(917) 555-1234";
    private static final String CUSTOMER_SSN = "123-45-6789";


    @BeforeEach
    public void setup() throws Exception {
        savingsAccount = mock(Savings.class);
        checkingAccount = mock(Checking.class);
        loan = mock(Loan.class);
        creditReportMock = mock(CreditReport.class);
        // mock getScore
        when(creditReportMock.getScore(CUSTOMER_SSN)).thenReturn(700);
        customer = new Customer(creditReportMock, CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_AGE, CUSTOMER_ADDRESS, CUSTOMER_INCOME, CUSTOMER_PHONE, CUSTOMER_SSN);

    }

    // sanity check
    @Test
    public void testCustomerInitialization() {
        //mock get credit score
        assertEquals(CUSTOMER_ID, customer.getId());
        assertEquals(CUSTOMER_NAME, customer.getName());
        assertEquals(CUSTOMER_AGE, customer.getAge());
        assertEquals(CUSTOMER_ADDRESS, customer.getAddress());
        assertEquals(CUSTOMER_PHONE, customer.getPhoneNumber());
        assertEquals(CUSTOMER_SSN, customer.getSsn());
        assertEquals(CUSTOMER_INCOME, customer.getDeclaredAnnualIncome());
        assertEquals(700, customer.getCreditScore());
    }

    // Interactions:
    // 1. customer opens an account
    // 2. customer applies for a loan
    // 3. customer checks their credit report
    // 4. customer deposits money into their account
    // 5. customer withdraws money from their account
    // 6. customer transfers money between their accounts
    // 7. customer pays off a loan



    @Test
    public void testOpenCheckingAccount() {
        //mock checking account.open
        when(checkingAccount.open(customer, 100.0)).thenReturn(new Checking(100.0, customer, 1.0, 1.0));

        Checking newCheckingAccount = checkingAccount.open(customer, 100.0);
        boolean applicationStatus = customer.applyForCheckingAccount(newCheckingAccount);
        assertTrue(applicationStatus);
        ArrayList<Account> accounts = customer.getAccounts();
        assertTrue(accounts.contains(newCheckingAccount));
        assertEquals(1, accounts.size());
        assertEquals(100.0, newCheckingAccount.getBalance(), 0.0);
        assertEquals(1.0, newCheckingAccount.getATMFee(), 0.0);
        assertEquals(1.0, newCheckingAccount.getMaintenanceFee(), 0.0);
    }

    @Test
    public void testOpenSavingsAccount() {
        // mock savings account.open
        when(savingsAccount.open(customer, 100.0)).thenReturn(new Savings(100.0, customer, 0.02, true, Savings.CompoundType.Continuously));
        Savings newSavingsAccount = savingsAccount.open(customer, 100.0);
        double interestRate = customer.applyForSavingsAccount(newSavingsAccount);
        assertEquals(0.02, interestRate, 0.0);
        ArrayList<Account> accounts = customer.getAccounts();
        assertEquals(1, accounts.size());
        assertTrue(accounts.contains(newSavingsAccount));
        assertEquals(100.0, newSavingsAccount.getBalance(), 0.0);
        assertEquals( Savings.CompoundType.Continuously, newSavingsAccount.getCompoundType());
        assertEquals(0.02, newSavingsAccount.getInterestRate(), 0.0);
    }
//
//
    @Test
    public void testApplyForLoanWhenEligible() {
        // mock loan.setCustomer
        when(loan.requestLoan(customer, LoanType.Auto, 1000)).thenReturn(new Loan(1000, LoanType.Auto));
        Loan newLoan = loan.requestLoan(customer, LoanType.Auto, 1000);
        customer.applyForLoan(newLoan);
        ArrayList<Loan> loans = customer.getLoans();
        assertEquals(1, loans.size());
        assertTrue(loans.contains(newLoan));

        // TODO: use verify!
//        verify(newLoan).setCustomer(customer);
//        verify(newLoan).setApproved(false);
    }
//    @Test



//
//    @Test
//    public void testCheckCreditReport() {
//        customer.setCreditReport(creditReport);
//        customer.checkCreditReport();
//
//        verify(creditReport).getScore();
//    }
//
//    @Test
//    public void testDeposit() {
//        customer.addAccount(account);
//        customer.deposit(account, 100.0);
//
//        verify(account).deposit(100.0);
//    }
//
//    @Test
//    public void testWithdraw() {
//        customer.addAccount(account);
//        customer.withdraw(account, 100.0);
//
//        verify(account).withdraw(100.0);
//    }
//
//    @Test
//    public void testTransfer() {
//        customer.addAccount(savingsAccount);
//        customer.addAccount(checkingAccount);
//
//        customer.transfer(savingsAccount, checkingAccount, 100.0);
//
//        verify(savingsAccount).withdraw(100.0);
//        verify(checkingAccount).deposit(100.0);
//    }
//
//    @Test
//    public void testPayOffLoan() {
//        customer.addLoan(loan);
//        customer.payOffLoan(loan, 500.0);
//
//        verify(loan).makePayment(500.0);
//    }
//
//    // Interaction between Customer and Account classes
//    @Test
//    public void testCustomerOpenAccount() {
//        customer.openAccount(accountMock);
//        verify(accountMock, times(1)).setCustomer(customer);
//    }
//
//    // Interaction between Customer and Savings classes
//    @Test
//    public void testCustomerOpenSavingsAccount() {
//        customer.openSavingsAccount(savingsMock);
//        verify(savingsMock, times(1)).setCustomer(customer);
//    }
//
//    // Interaction between Customer and Checking classes
//    @Test
//    public void testCustomerOpenCheckingAccount() {
//        customer.openCheckingAccount(checkingMock);
//        verify(checkingMock, times(1)).setCustomer(customer);
//    }
//
//    // Interaction between Customer and Loan classes
//    @Test
//    public void testCustomerApplyForLoan() {
//        customer.applyForLoan(loanMock);
//        verify(loanMock, times(1)).setCustomer(customer);
//    }
//
//    // Interaction between Customer and CreditReport classes
//    @Test
//    public void testCustomerGetCreditReport() {
//        customer.getCreditReport(creditReportMock);
//        verify(creditReportMock, times(1)).getReportForCustomer(customer);
//    }
}
