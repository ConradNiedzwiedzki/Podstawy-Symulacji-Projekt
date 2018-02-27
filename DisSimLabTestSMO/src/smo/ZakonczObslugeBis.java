package smo;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class ZakonczObslugeBis extends BasicSimEvent<SmoBis, Zgloszenie> {
	private SmoBis smoParent;

	public ZakonczObslugeBis(SmoBis parent, double delay, Zgloszenie zgl) throws SimControlException {
		super(parent, delay, zgl);
		this.smoParent = parent;
	}

	@Override
	protected void onInterruption() throws SimControlException {
		System.out.println(simTime() + ": !Przerwanie obslugi przy zgl. nr: " + transitionParams.getTenNr());
	}

	@Override
	protected void onTermination() throws SimControlException {

	}

	@Override
	protected void stateChange() throws SimControlException {
		smoParent.setWolne(true);
		System.out.println(simTime() + ": SMO2-Koniec obslugi zgl. nr: " + transitionParams.getTenNr());
		smoParent.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasOdniesienia());
		if (smoParent.liczbaZgl() > 0) {
			smoParent.rozpocznijObsluge = new RozpocznijObslugeBis(smoParent);
		}
	}

	@Override
	public Object getEventParams() {
		return (Zgloszenie) transitionParams;
	}
}