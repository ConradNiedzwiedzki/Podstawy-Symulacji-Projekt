package smo;

/* Description: Zdarzenie poczatkowe aktywnosci gniazda obslugi. Rozpoczyna obsluge przez losowy czas obiektoww - zgloszen.
 */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters.SimDateField;

public class RozpocznijObsluge extends BasicSimEvent<Smo, Zgloszenie> {
	private Smo smoParent;
	private SimGenerator generator;

	public RozpocznijObsluge(Smo parent, double delay) throws SimControlException {
		super(parent, delay);
		generator = new SimGenerator();
		this.smoParent = parent;
	}

	public RozpocznijObsluge(Smo parent) throws SimControlException {
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
			zgl.koniecNiecierpliwosci.interrupt();
			double czasObslugi = generator.normal(9.0, 1.0);
			smoParent.MVczasy_obslugi.setValue(czasObslugi);
			smoParent.MVczasy_oczekiwania.setValue(simTime() - zgl.getCzasOdniesienia(), simTime());
			zgl.setCzasOdniesienia(simTime());
			System.out.println(simTime() + " - " + simDate(SimDateField.HOUR24) + " - " + simDate(SimDateField.MINUTE)
					+ " - " + simDate(SimDateField.SECOND) + " - " + simDate(SimDateField.MILLISECOND)
					+ ": SMO- Poczatek obslugi zgl. nr: " + zgl.getTenNr());
			smoParent.zakonczObsluge = new ZakonczObsluge(smoParent, czasObslugi, zgl);
		}

	}

	@Override
	public Object getEventParams() {
		return null;
	}
}