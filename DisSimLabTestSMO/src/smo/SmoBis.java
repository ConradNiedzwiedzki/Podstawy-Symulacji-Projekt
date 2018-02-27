package smo;


import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;

public class SmoBis extends BasicSimObj {

	public boolean isFIFO;
	public int rozmiar;
	private Kolejka kolejka;
	SimEventSemaphore semafor;
	private boolean wolne = true;
	public RozpocznijObslugeBis rozpocznijObsluge;
	public ZakonczObslugeBis zakonczObsluge;
	public MonitoredVar MVczasy_obslugi;
	public MonitoredVar MVczasy_oczekiwania;
	public MonitoredVar MVdlKolejki;

	public SmoBis(SimEventSemaphore semafor) throws SimControlException {
		kolejka = new Kolejka(false, 2);

		this.semafor = semafor;
		MVczasy_obslugi = new MonitoredVar();
		MVczasy_oczekiwania = new MonitoredVar();
		MVdlKolejki = new MonitoredVar();
	}

	public boolean dodajLifo(Zgloszenie zgl) {
		if (!isFIFO) {
			if (kolejka.size() < kolejka.getRozm()) {
				kolejka.dodajLIFO(zgl);
				MVdlKolejki.setValue(kolejka.size(), simTime());
				return true;
			}
		}
		return false;
	}

	public boolean dodajFifo(Zgloszenie zgl) {
		if (isFIFO) {
			if (kolejka.size() < kolejka.getRozm()) {
				kolejka.dodajFIFO(zgl);
				MVdlKolejki.setValue(kolejka.size(), simTime());
				return true;
			}
		}

		return false;
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

	public int liczbaZgl() {
		return kolejka.size();
	}

	public boolean isWolne() {
		return wolne;
	}

	public void setWolne(boolean wolne) {
		this.wolne = wolne;
	}

	public SimEventSemaphore getSemafor() {
		return semafor;
	}

	public void setSemafor(SimEventSemaphore semafor) {
		this.semafor = semafor;
	}

	public boolean getFIFO() {
		return kolejka.getFIFO();
	}

	public void setFIFO(boolean fifo) {
		this.kolejka.setFIFO(fifo);
		this.isFIFO = fifo;
	}

	public int getRozm() {
		return this.kolejka.getRozm();
	}

	public void setRozm(int rozmiar) {
		this.rozmiar = rozmiar;
		this.kolejka.setRozm(rozmiar);
	}

	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {
	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}
}