package modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cat")
    private int id_cat;
	@Column(name = "tipo_cat")
	private String tipo_cat;
	
	public Categoria() {}

	public Categoria(int idCat, String tipoCat) {
		super();
		this.id_cat = idCat;
		this.tipo_cat = tipoCat;
	}

	public int getIdCat() {
		return id_cat;
	}

	public void setIdCat(int idCat) {
		this.id_cat = idCat;
	}

	public String getTipoCat() {
		return tipo_cat;
	}

	public void setTipoCat(String tipoCat) {
		this.tipo_cat = tipoCat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
