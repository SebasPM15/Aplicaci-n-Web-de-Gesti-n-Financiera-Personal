package modelo.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Transferencia")
public class Transferencia extends Movimiento {

	private static final long serialVersionUID = 1L;

	public Transferencia() {
		super();
	}

}
