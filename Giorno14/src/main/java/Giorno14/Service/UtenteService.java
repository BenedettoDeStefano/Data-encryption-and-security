package Giorno14.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Giorno14.Entities.Utente;
import Giorno14.Exception.BadRequestException;
import Giorno14.Exception.NotFoundException;
import Giorno14.PayLoad.UtenteRequestPayLoad;
import Giorno14.Repository.UtenteRepository;

@Service
public class UtenteService {

	private final UtenteRepository utenteRepository;

	@Autowired
	public UtenteService(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository;
	}

	public Utente create(UtenteRequestPayLoad body) {

		utenteRepository.findByEmail(body.getEmail()).ifPresent(utente -> {
			throw new BadRequestException("L'email è già stata utilizzata");
		});
		Utente nuovoUtente = new Utente(body.getNome(), body.getCognome(), body.getEmail(), body.getUsername(),
				body.getPassword());
		return utenteRepository.save(nuovoUtente);
	}

	public Page<Utente> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort)); // (numero di pagina, numero di elementi per
																		// pagina, nome del campo per cui sortare)
		return utenteRepository.findAll(pageable);
	}

	public Utente findById(UUID id) throws NotFoundException {
		return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public Utente findByIdAndUpdate(UUID id, UtenteRequestPayLoad body) throws NotFoundException {
		Utente found = this.findById(id);
		found.setEmail(body.getEmail());
		found.setNome(body.getNome());
		found.setCognome(body.getCognome());
		found.setUsername(body.getUsername());
		found.setPassword(body.getPassword());

		return utenteRepository.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Utente found = this.findById(id);
		utenteRepository.delete(found);
	}

	public Utente findByEmail(String email) {
		return utenteRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato"));
	}
}
