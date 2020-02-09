/*-
 * ​​​
 * meanbean
 * ⁣⁣⁣
 * Copyright (C) 2010 - 2020 the original author or authors.
 * ⁣⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ﻿﻿﻿﻿﻿
 */

package org.meanbean.test.beans.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Queue;

public class Employee extends Person {

	private EmployeeId employeeId;

	private Date startDate;

	private Employee manager;

	private Queue<Order> orderQueue;

	private BigDecimal magicNumber;

	public EmployeeId getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(EmployeeId employeeId) {
		this.employeeId = employeeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Queue<Order> getOrderQueue() {
		return orderQueue;
	}

	public void setOrderQueue(Queue<Order> orderQueue) {
		this.orderQueue = orderQueue;
	}

	public BigDecimal getMagicNumber() {
		return magicNumber;
	}

	public void setMagicNumber(BigDecimal magicNumber) {
		this.magicNumber = magicNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((magicNumber == null) ? 0 : magicNumber.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((orderQueue == null) ? 0 : orderQueue.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (magicNumber == null) {
			if (other.magicNumber != null)
				return false;
		} else if (!magicNumber.equals(other.magicNumber))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return true; // false;
		} else if (!manager.equals(other.manager))
			return true; // false;
		if (orderQueue == null) {
			if (other.orderQueue != null)
				return false;
		} else if (!orderQueue.equals(other.orderQueue))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

}
