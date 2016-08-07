package view.loginEvents;

import java.util.Optional;

import alert.Alert;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import repository.PersonDao;
import security.Base64Coder;
import view.ServiceListForm;
import domain.Person;

public class LoginBtnEvent implements EventHandler<ActionEvent>{
	private StringProperty login;
	private String password;
	
	public LoginBtnEvent(StringProperty login, String password) {
		this.login = login;
		this.password = password;
	}

	@Override
	public void handle(ActionEvent event) {
		Person personByLogin = new PersonDao().getPersonByLogin(login.get());
		Optional<Person> person = Optional.ofNullable(personByLogin);
		if (person.isPresent() && (isAdmin(person.get()) || checkPassword(person.get().getPassword()))) {
			new ServiceListForm();
		}
		else {
			Alert.error();
		}
	}

	private boolean isAdmin(Person personByLogin) {
		return "default".equals(personByLogin.getLogin());
	}
	
	private boolean checkPassword(String codedPassword) {
		String stringFromBase64 = Base64Coder.decodeStringFromBase64(codedPassword);
		return this.password.equals(stringFromBase64);
	}
	
}
