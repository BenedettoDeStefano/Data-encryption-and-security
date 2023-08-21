package Giorno14.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Giorno14.Entities.Utente;
import Giorno14.Exception.UnauthorizedException;
import Giorno14.PayLoad.LoginSuccessfullPayload;
import Giorno14.PayLoad.UtenteLoginPayLoad;
import Giorno14.PayLoad.UtenteRequestPayLoad;
import Giorno14.Service.UtenteService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UtenteService utenteService;

	@Autowired
	JWTTools jwtTools;
	
	@Autowired
	PasswordEncoder bcrypt;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Utente saveUser(@RequestBody UtenteRequestPayLoad body) {
		body.setPassword(bcrypt.encode(body.getPassword()));
		Utente created = utenteService.create(body);

		return created;
	}

	@PostMapping("/login")
	public LoginSuccessfullPayload login(@RequestBody UtenteLoginPayLoad body) {

		Utente user = utenteService.findByEmail(body.getEmail());

		if (bcrypt.matches(body.getPassword(), user.getPassword())) {
			
			String token = jwtTools.createToken(user);
			return new LoginSuccessfullPayload(token);

		} else {

			throw new UnauthorizedException("Credenziali non valide!");
		}
	}

}
