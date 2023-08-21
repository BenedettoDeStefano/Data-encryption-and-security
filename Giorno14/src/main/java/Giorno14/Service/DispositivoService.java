package Giorno14.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Giorno14.Entities.Dispositivo;
import Giorno14.Exception.NotFoundException;
import Giorno14.PayLoad.DispositivoRequestPayLoad;
import Giorno14.Repository.DispositivoRepository;

@Service
public class DispositivoService {
	private final DispositivoRepository dispoRepo;

	@Autowired
	private DispositivoService(DispositivoRepository dispoRepo) {
		this.dispoRepo = dispoRepo;
	    }

		public Dispositivo create(DispositivoRequestPayLoad body) {

			Dispositivo nuovoDispositivo = new Dispositivo(body.getTipo(), body.getStato());
		return dispoRepo.save(nuovoDispositivo);
	}

	public Page<Dispositivo> find(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		return dispoRepo.findAll(pageable);
	}

	public Dispositivo findById(UUID id) throws NotFoundException {
		return dispoRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public Dispositivo findByIdAndUpdate(UUID id, DispositivoRequestPayLoad body) throws NotFoundException {
		Dispositivo found = this.findById(id);
		found.setStato(body.getStato());
		found.setTipo(body.getTipo());
		return dispoRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		Dispositivo found = this.findById(id);
		dispoRepo.delete(found);
	}

	public Dispositivo saveDispositivo(Dispositivo dispositivo) {
		return dispoRepo.save(dispositivo);
	}

	public Dispositivo getDispositivoById(UUID id) {
		return dispoRepo.findById(id).orElse(null);
	}

}
