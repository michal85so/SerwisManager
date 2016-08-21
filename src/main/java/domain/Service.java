package domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {
	private int id;
	private int clientId;
	private String serviceName;
	private String info;
	private LocalDate dateOfOrder;
	private LocalDate dateOfReceipt;
	private int serviceStatusId;
	private int invoiceId;
	private int assignedPersonId;
	private String assignedPersonValue;
	private String serviceStatusValue;
	private int department;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return serviceName;
	}

	public void setName(String name) {
		this.serviceName = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public LocalDate getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(LocalDate dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public LocalDate getDateOfReceipt() {
		return dateOfReceipt;
	}

	public void setDateOfReceipt(LocalDate dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public int getserviceStatusId() {
		return serviceStatusId;
	}
	
	public void setServiceStatusValue(String serviceStatusValue) {
		this.serviceStatusValue = serviceStatusValue;
	}
	
	public String getServiceStatusValue() {
		return ServiceStatus.listOfStatus.get(serviceStatusId);
	}

	public void setserviceStatusId(int serviceStatusId) {
		this.serviceStatusId = serviceStatusId;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public int getAssignedPersonId() {
		return assignedPersonId;
	}

	public void setAssignedPersonId(int assignedPersonId) {
		this.assignedPersonId = assignedPersonId;
	}

	public String getAssignedPersonValue() {
		return assignedPersonValue;
	}

	public void setAssignedPersonValue(String assignedPersonValue) {
		this.assignedPersonValue = assignedPersonValue;
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}
}
