package smo;
/* Description: Description: Klasa gniazda obslugi obiektow - zgloszen
 */

import smo.RozpocznijObsluge;
import smo.ZakonczObsluge;
import smo.Zgloszenie;
import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;

public class Smo extends BasicSimObj {
	public boolean isFIFO;
	public int rozmiar;
	private Kolejka kolejka;
	private SmoBis smo2;
	SimEventSemaphore semafor;
	private boolean wolne = true;
	public RozpocznijObsluge rozpocznijObsluge;
	public ZakonczObsluge zakonczObsluge;
	public MonitoredVar MVczasy_obslugi;
	public MonitoredVar MVczasy_oczekiwania;
	public MonitoredVar MVdlKolejki;
	public MonitoredVar MVutraconeZgl;
	public static double p;

	public Smo(SmoBis smo, SimEventSemaphore semafor) throws SimControlException {
		kolejka = new Kolejka(false, 5);
		smo2 = smo;
		this.semafor = semafor;
		MVczasy_oczekiwania = new MonitoredVar();
		MVczasy_obslugi = new MonitoredVar();
		MVdlKolejki = new MonitoredVar();
		MVutraconeZgl = new MonitoredVar();
	}

	public int dodajLifo(Zgloszenie zgl) {
		if (kolejka.size() < kolejka.getRozm()) {
			kolejka.dodajLIFO(zgl);
			MVdlKolejki.setValue(kolejka.size(), simTime());
		}
		return kolejka.size();
	}

	public int dodajFifo(Zgloszenie zgl) {
		if (kolejka.size() < kolejka.getRozm()) {
			kolejka.dodajFIFO(zgl);
			MVdlKolejki.setValue(kolejka.size(), simTime());
		}
		return kolejka.size();
	}

	public Zgloszenie pobierz() {
		Zgloszenie zgl = (Zgloszenie) kolejka.pobierz();
		MVdlKolejki.setValue(kolejka.size(), simTime());
		return zgl;
	}

	public boolean usunWskazany(Zgloszenie zgl) {
		kolejka.usun(zgl);
		MVdlKolejki.setValue(kolejka.size(), simTime());
		return true;

	}

	public SimEventSemaphore getSemafor() {
		return semafor;
	}

	public void setSemafor(SimEventSemaphore semafor) {
		this.semafor = semafor;
	}

	public SmoBis getSmo2() {
		return smo2;
	}

	public void setSmo2(SmoBis smo2) {
		this.smo2 = smo2;
	}

	public int liczbaZgl() {
		return kolejka.size();
	}

	public boolean isWolne() {
		return wolne;
	}

	public void setWolne(boolean wolne) {
		this.wolne = wolne;
	}

	public boolean getFIFO() {
		return kolejka.getFIFO();
	}

	public void setFIFO(boolean fifo) {
		this.kolejka.setFIFO(fifo);
		this.isFIFO = fifo;
	}

	public void setRozm(int a) {
		this.kolejka.setRozm(a);
		this.rozmiar = a;
	}

	public int getRozm() {
		return this.kolejka.getRozm();
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {

	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}
}