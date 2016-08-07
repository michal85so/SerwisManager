package domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
public class Service {
	private int id;
	private int clientId;
	private String serviceName;
	private String info;
	private LocalDate dateOfOrder;
	private LocalDate dateOfReceipt;
	private int serviceStatusId;
	private int invoiceId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPersonId() {
		return clientId;
	}
	public void setPersonId(int personId) {
		this.clientId = personId;
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
	public void setserviceStatusId(int serviceStatusId) {
		this.serviceStatusId = serviceStatusId;
	}
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
}
