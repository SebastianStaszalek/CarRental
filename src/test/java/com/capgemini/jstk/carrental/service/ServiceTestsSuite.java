package com.capgemini.jstk.carrental.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CarServiceTest.class, LocationServiceTest.class, EmployeeServiceTest.class,
CustomerServiceTest.class, RentalServiceTest.class})
public class ServiceTestsSuite {
}
