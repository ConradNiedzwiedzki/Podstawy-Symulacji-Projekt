package smo;

/* Description: Zdarzenie poczatkowe aktywnosci gniazda obslugi. Rozpoczyna obsluge przez losowy czas obiektoww - zgloszen. */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class RozpocznijObslugeBis extends BasicSimEvent<SmoBis, Zgloszenie> {
	private SmoBis smoParent;
	private SimGenerator generator;

	public RozpocznijObslugeBis(SmoBis parent, double delay) throws SimControlException {
		super(parent, delay);
		generator = new SimGenerator();
		this.smoParent = parent;
	}

	public RozpocznijObslugeBis(SmoBis parent) throws SimControlException {
		super(parent);
		generator = new SimGenerator();
		this.smoParent = parent;
	}

	@Override
	protected void onInterruption() throws SimControlException {
	}

	@Override
	protected void onTermination() throws SimControlException {
	}

	@Override
	protected void stateChange() throws SimControlException {
		
		if (smoParent.liczbaZgl() > 0) {
			smoParent.setWolne(false);
			Zgloszenie zgl = smoParent.pobierz();
			if (smoParent.liczbaZgl() == smoParent.getRozm() - 1) {
				try {
					System.out.println(simTime() + ": SMO2- otwarcie semafora - zwolnienie: "
							+ smoParent.getSemafor().readFirstBlocked().toString());
				} catch (Exception e) {
				}
				smoParent.getSemafor().open();
			}
			double czasObslugi = generator.normal(8.0, 1.0); 
			smoParent.MVczasy_obslugi.setValue(czasObslugi);
			smoParent.MVczasy_oczekiwania.setValue(simTime() - zgl.getCzasOdniesienia());
			System.out.println(simTime() + ": SMO2-Poczatek obslugi zgl. nr: " + zgl.getTenNr());
			smoParent.zakonczObsluge = new ZakonczObslugeBis(smoParent, czasObslugi, zgl);
		}

	}

	@Override
	public Object getEventParams() {
		return null;
	}
}