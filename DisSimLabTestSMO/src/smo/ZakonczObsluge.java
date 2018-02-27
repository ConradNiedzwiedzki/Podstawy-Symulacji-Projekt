package smo;
/* Description: Zdarzenie koncowe aktywnosci gniazda obslugi. Konczy obsluge przez losowy czas obiektow - zgloszen.
 */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimParameters.SimDateField;

public class ZakonczObsluge extends BasicSimEvent<Smo, Zgloszenie> {
	private Smo smoParent;
	private SimGenerator generator;
	public static boolean flag;

	public ZakonczObsluge(Smo parent, double delay, Zgloszenie zgl) throws SimControlException {
		super(parent, delay, zgl);
		this.smoParent = parent;
		generator = new SimGenerator();
	}

	public ZakonczObsluge(Smo parent, SimEventSemaphore semafor, Zgloszenie zgl) throws SimControlException {
		super(parent, semafor, zgl);
		this.smoParent = parent;
		generator = new SimGenerator();
	}

	@Override
	protected void onInterruption() throws SimControlException {
	}

	@Override
	protected void onTermination() throws SimControlException {
	}

	@Override
	protected void stateChange() throws SimControlException {
		flag = generator.probability(smoParent.getP());
		if (smoParent.getSmo2().dodajFifo(transitionParams) || smoParent.getSmo2().dodajLifo(transitionParams)) {
			smoParent.setWolne(true);
			System.out.println(simTime() + " - " + simDate(SimDateField.HOUR24) + " - " + simDate(SimDateField.MINUTE)
					+ " - " + simDate(SimDateField.SECOND) + " - " + simDate(SimDateField.MILLISECOND)
					+ ": SMO- Koniec obslugi zgl. nr: " + transitionParams.getTenNr());
			smoParent.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasOdniesienia(), simTime());
			if (flag && smoParent.getSmo2().isWolne()) {
				smoParent.getSmo2().rozpocznijObsluge = new RozpocznijObslugeBis(smoParent.getSmo2());
			}else {
				smoParent.dodajFifo(transitionParams);
				smoParent.dodajLifo(transitionParams);
			}

			if (smoParent.liczbaZgl() > 0) {
				smoParent.rozpocznijObsluge = new RozpocznijObsluge(smoParent);
			}
		} else {
			System.out.println(simTime() + ": Oczekiwanie na semaforze - zgl. nr: " + transitionParams.getTenNr());
			smoParent.zakonczObsluge = new ZakonczObsluge(smoParent, smoParent.getSemafor(), transitionParams);
		}
	}

	@Override
	public Object getEventParams() {
		return null;
	}
}