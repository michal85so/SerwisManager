package view.loginEvents;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import repository.PersonDao;
import view.MainForm;
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
		new MainForm();
//		if (KindOfPerson.OWNER.getKindOfPersonAsInt() == personByLogin.getKindOfPerson()
//				|| KindOfPerson.SERVICEMAN.getKindOfPersonAsInt() == personByLogin.getKindOfPerson()
//				|| KindOfPerson.DEALER.getKindOfPersonAsInt() == personByLogin.getKindOfPerson()) {
//			String decodedPassword = new String(Base64.getDecoder().decode(personByLogin.getPassword()));
//			if (password.equals(decodedPassword)) {
//				System.out.println("dupa");
//			}
//		}
		
	}
}
