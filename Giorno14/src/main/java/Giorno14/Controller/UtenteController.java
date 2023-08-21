package Giorno14.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Giorno14.Entities.Utente;
import Giorno14.PayLoad.UtenteRequestPayLoad;
import Giorno14.Service.UtenteService;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

	private final UtenteService utenteServ;

	@Autowired
	public UtenteController(UtenteService utenteServ) {
		this.utenteServ = utenteServ;
	}

	@GetMapping
	public Page<Utente> getUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return utenteServ.find(page, size, sortBy);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Utente saveUser(@RequestBody UtenteRequestPayLoad body) {
		Utente created = utenteServ.create(body);

		return created;
	}

	@GetMapping("/{userId}")
	public Utente findById(@PathVariable UUID userId) {
		return utenteServ.findById(userId);

	}

	@PutMapping("/{userId}")
	public Utente updateUser(@PathVariable UUID userId, @RequestBody UtenteRequestPayLoad body) {
		return utenteServ.findByIdAndUpdate(userId, body);
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID userId) {
		utenteServ.findByIdAndDelete(userId);
	}

}
