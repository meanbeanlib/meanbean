package org.meanbean.test.beans.domain;

public class EmployeeWithBrokenManagerSetter extends Employee {

	@Override
	public void setManager(Employee manager) {
		Employee myManager = new Employee();
		myManager.setFirstName("MY_MANAGER");
		super.setManager(myManager);
	}
}