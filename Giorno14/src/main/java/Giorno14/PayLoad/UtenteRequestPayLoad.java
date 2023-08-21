package Giorno14.PayLoad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UtenteRequestPayLoad {


	private String nome;
	private String cognome;
	private String email;
	private String username;

	private String password;

}
