package Giorno14.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import Giorno14.Entities.Dispositivo;
import Giorno14.PayLoad.DispositivoRequestPayLoad;
import Giorno14.Service.AssegnazioneService;
import Giorno14.Service.DispositivoService;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {

	private final DispositivoService dispositivoSrv;
	private final AssegnazioneService assegnazioneServ;

	@Autowired
	public DispositivoController(DispositivoService dispositivoSrv, AssegnazioneService assegnazioneServ) {
		super();
		this.dispositivoSrv = dispositivoSrv;
		this.assegnazioneServ = assegnazioneServ;
	}


	@GetMapping
	public Page<Dispositivo> getDispositivo(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return dispositivoSrv.find(page, size, sortBy);
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Dispositivo saveDispositivo(@RequestBody DispositivoRequestPayLoad body) {
		Dispositivo created = dispositivoSrv.create(body);

		return created;
	}

	@GetMapping("/{dispositiviId}")
	public Dispositivo findById(@PathVariable UUID utenteId) {
		return dispositivoSrv.findById(utenteId);

	}

	@PutMapping("/{dispositiviId}")
	public Dispositivo updateDispositivo(@PathVariable UUID utenteId, @RequestBody DispositivoRequestPayLoad body) {
		return dispositivoSrv.findByIdAndUpdate(utenteId, body);
	}

	@DeleteMapping("/{dispositiviId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDispositivo(@PathVariable UUID utenteId) {
		dispositivoSrv.findByIdAndDelete(utenteId);
	}

	@PutMapping("/{dId}/assegna/{uId}")
	public ResponseEntity<String> assegnaDispositivo(@PathVariable UUID dId, @PathVariable UUID uId) {
		String result = assegnazioneServ.assegnaDispositivo(uId, dId);
		return ResponseEntity.ok(result);
	}

}


