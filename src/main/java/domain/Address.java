package domain;

import lombok.Data;

@Data
public class Address {
	private int id;
	private int cityId;
	private String postCode;
	private String street;
	private int stateId;
}
