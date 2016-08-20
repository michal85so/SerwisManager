package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Enviroment {
	private int id;
	private String category;
	private String producent;
	private String model;
	private double price;
	private int items;
}
