package modelo.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Ingreso")
public class Ingreso extends Movimiento{

	private static final long serialVersionUID = 1L;

	public Ingreso() {
		super();
	}

}
