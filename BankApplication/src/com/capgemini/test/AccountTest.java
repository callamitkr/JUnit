package com.capgemini.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
public class AccountTest {

	AccountService accountService;
	
	@Mock
	AccountRepository accountRepository;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1.when the amount is less than 500 then system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialAmountException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 5000));
	}
	
	
	/* Deposit amount
	 * when account number is invalid, system should throw exception 
	 * When the valid info is passed, amount should be deposited successfully
	 * */
	
	@Test(expected = InvalidAccountNumberException.class)
	public void whenAccountNumberIsInvalidSystemShouldThrowException() throws InvalidAccountNumberException{
		
		accountService.depositAmount(99, 400);
		
	}
	@Test
	public void whenTheValidAccountNumberPassedAmountShouldDepositedSuccessfully() throws InvalidAccountNumberException {
		
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.saveAmount(account)).thenReturn(true);
		assertEquals(account, accountService.depositAmount(101, 5000));
		
	}
	
	/* Withdraw amount
	 * when account number is invalid, system should throw exception 
	 * when the amount is insufficient,system should exception
	 * When the valid info is passed, amount should be deposited successfully
	 * */
	
	@Test(expected = InvalidAccountNumberException.class)
	public void whenWithdrawAccountNumberIsInvalidSystemShouldThrowException() throws InvalidAccountNumberException,InsufficientBalanceException {	
		accountService.withdrawAmount(99, 400);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void whenWithdrawAmountIsInsufficientSystemShouldThrowException() throws InsufficientBalanceException, InvalidAccountNumberException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		//assertEquals(account, accountService.depositAmount(101, 5000));
	
		accountService.withdrawAmount(101, 8000);
		
	}
	
	@Test
	public void whenWithdrawAmountIsSufficientSystemShouldWithdrawAmount() throws InsufficientBalanceException, InvalidAccountNumberException{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		when(accountRepository.withdreawAmount(account)).thenReturn(true);
		assertEquals(account, accountService.withdrawAmount(account.getAccountNumber(),3000));
		
	}
	
	
	
	
	
	
	
	
	
	

}
