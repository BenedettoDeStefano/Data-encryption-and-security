package Giorno14.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Giorno14.Entities.Dispositivo;
import Giorno14.Entities.Utente;
import Giorno14.Enum.stato;

@Service
public class AssegnazioneService {

	private final DispositivoService dispositivoService;
	private final UtenteService utenteService;

	@Autowired
	public AssegnazioneService(DispositivoService dispositivoService, UtenteService utenteService) {
        this.dispositivoService = dispositivoService;
        this.utenteService = utenteService;
    }

	public String assegnaDispositivo(UUID utenteId, UUID dispositivoId) {
		Dispositivo dispositivo = dispositivoService.findById(dispositivoId);
		if (dispositivo == null) {
			return "Dispositivo non trovato.";
		}
		if (dispositivo.getStato() == stato.IN_MANUTENZIONE) {
			return "Il dispositivo è in manutenzione e non può essere assegnato.";
		}


		if (dispositivo.getStato() == stato.ASSEGNATO && dispositivo.getUtente() != null
				&& dispositivo.getUtente().getId().equals(utenteId)) {
			return "Il dispositivo è già assegnato all'utente.";
		}


		Utente utente = utenteService.findById(utenteId);
		if (utente == null) {
			return "Utente non trovato.";
		}


		dispositivo.setUtente(utente);
		dispositivo.setStato(stato.ASSEGNATO);
		dispositivoService.saveDispositivo(dispositivo);

		return "Dispositivo assegnato con successo all'utente.";
	}


}
