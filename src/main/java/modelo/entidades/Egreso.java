package modelo.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Egreso")
public class Egreso extends Movimiento {

	private static final long serialVersionUID = 1L;

	public Egreso() {
		super();
	}
	
}
