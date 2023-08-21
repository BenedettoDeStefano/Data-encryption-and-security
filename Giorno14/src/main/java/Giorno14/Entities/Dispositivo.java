package Giorno14.Entities;

import java.util.UUID;

import Giorno14.Enum.TipoDispositivo;
import Giorno14.Enum.stato;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Dispositivo {

	@Id
	@GeneratedValue
	private UUID id;

	@Enumerated(EnumType.STRING)
	private TipoDispositivo tipo;

	@Enumerated(EnumType.STRING)
	private stato stato;
	//
	@JoinColumn(name = "utente_id")
	@ManyToOne
	private Utente utente;

	public Dispositivo(TipoDispositivo tipo, stato stato) {
		this.tipo = tipo;
		this.stato = stato;
	}

}
