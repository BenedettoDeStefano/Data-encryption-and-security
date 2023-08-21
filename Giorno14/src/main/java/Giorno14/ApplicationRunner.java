package Giorno14;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import Giorno14.Enum.TipoDispositivo;
import Giorno14.Enum.stato;
import Giorno14.PayLoad.DispositivoRequestPayLoad;
import Giorno14.PayLoad.UtenteRequestPayLoad;
import Giorno14.Service.AssegnazioneService;
import Giorno14.Service.DispositivoService;
import Giorno14.Service.UtenteService;

@Component
public class ApplicationRunner implements CommandLineRunner{

	@Autowired
	UtenteService utenteServ;

	@Autowired
	DispositivoService dispServ;

	@Autowired
	AssegnazioneService assServ;


	@Override
	public void run(String... args) throws Exception {
		
		Faker faker = new Faker(new Locale("it"));

		for (int i = 0; i < 5; i++) {
			String nome = faker.name().firstName();
			String cognome = faker.name().lastName();
			String email = faker.internet().emailAddress();
			String username = (nome + cognome).toLowerCase().trim();
			String password = "1234";
			UtenteRequestPayLoad user = new UtenteRequestPayLoad(nome, cognome, email, username, password);
			utenteServ.create(user);

		}

		DispositivoRequestPayLoad dispositivo1 = new DispositivoRequestPayLoad(TipoDispositivo.PC, stato.ASSEGNATO);
		DispositivoRequestPayLoad dispositivo2 = new DispositivoRequestPayLoad(TipoDispositivo.SMARTPHONE,
				stato.DISMESSO);
		DispositivoRequestPayLoad dispositivo3 = new DispositivoRequestPayLoad(TipoDispositivo.SMARTPHONE,
				stato.IN_MANUTENZIONE);
		DispositivoRequestPayLoad dispositivo4 = new DispositivoRequestPayLoad(TipoDispositivo.TABLET,
				stato.DISPONIBILE);
		dispServ.create(dispositivo1);
		dispServ.create(dispositivo2);
		dispServ.create(dispositivo3);
		dispServ.create(dispositivo4);


	}


}

