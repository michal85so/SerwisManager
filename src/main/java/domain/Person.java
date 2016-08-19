package domain;

import javafx.beans.property.SimpleStringProperty;

public class Person {
	private int id;
	private SimpleStringProperty firstName = new SimpleStringProperty();
	private SimpleStringProperty lastName = new SimpleStringProperty();
	private SimpleStringProperty login = new SimpleStringProperty();
	private SimpleStringProperty phoneNumber = new SimpleStringProperty();
	private SimpleStringProperty email = new SimpleStringProperty();
	private int kindOfPerson;
	private SimpleStringProperty password = new SimpleStringProperty();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName.get();
	}
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	public String getLastName() {
		return lastName.get();
	}
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	public String getLogin() {
		return login.get();
	}
	public void setLogin(String login) {
		this.login.set(login);
	}
	public String getPhoneNumber() {
		return phoneNumber.get();
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}
	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
	public int getKindOfPerson() {
		return kindOfPerson;
	}
	public void setKindOfPerson(int kindOfPerson) {
		this.kindOfPerson = kindOfPerson;
	}
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	
	public static class Builder{
		private int id;
		private String firstName;
		private String lastName;
		private String login;
		private String phoneNumber;
		private String email;
		private int kindOfPerson;
		private String password;
		public Builder() {}
		public Builder id(int val) {this.id = val; return this;}
		public Builder login(String val) {this.login = val; return this;}
		public Builder password(String val) {this.password = val; return this;}
		public Builder firstName(String val) {this.firstName = val; return this;}
		public Builder lastName(String val) {this.lastName = val; return this;}
		public Builder phoneNumber(String val) {this.phoneNumber = val; return this;}
		public Builder email(String val) {this.email = val; return this;}
		public Builder kindOfPerson(int kindOfPerson) {this.kindOfPerson = kindOfPerson; return this;}
		public Person build() {
			Person person = new Person();
			person.setId(id);
			person.setLogin(login);
			person.setPassword(password);
			person.setFirstName(firstName);
			person.setLastName(lastName);
			person.setPhoneNumber(phoneNumber);
			person.setEmail(email);
			person.setKindOfPerson(kindOfPerson);
			return person;
		}
	}
	
}
