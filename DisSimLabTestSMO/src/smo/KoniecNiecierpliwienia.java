package smo;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters.SimDateField;

public class KoniecNiecierpliwienia extends BasicSimEvent<Zgloszenie, Object> {
	private Zgloszenie parent;
	public double lutraconych;

	public KoniecNiecierpliwienia(Zgloszenie parent, double delay) throws SimControlException {
		super(parent, delay);
		this.parent = parent;
	}

	public KoniecNiecierpliwienia(Zgloszenie parent) throws SimControlException {
		super(parent);
		this.parent = parent;
	}

	@Override
	protected void onInterruption() throws SimControlException {
		System.out.println(simTime() + " - " + simDate(SimDateField.HOUR24) + " - " + simDate(SimDateField.MINUTE)
				+ " - " + simDate(SimDateField.SECOND) + " - " + simDate(SimDateField.MILLISECOND)
				+ ": Przerwanie niecierpliwosci zgl. nr: " + parent.getTenNr());
	}

	@Override
	protected void onTermination() throws SimControlException {
	}

	@Override
	protected void stateChange() throws SimControlException {
		System.out.println(simTime() + " - " + simDate(SimDateField.HOUR24) + " - " + simDate(SimDateField.MINUTE)
				+ " - " + simDate(SimDateField.SECOND) + " - " + simDate(SimDateField.MILLISECOND)
				+ ": Koniec niecierpliwosci zgl. nr: " + parent.getTenNr());
		if (parent.smo.usunWskazany(parent)) {
			System.out.println(simTime() + " - " + simDate(SimDateField.HOUR24) + " - " + simDate(SimDateField.MINUTE)
					+ " - " + simDate(SimDateField.SECOND) + " - " + simDate(SimDateField.MILLISECOND)
					+ ": Usunieto z kolejki zgl. nr: " + parent.getTenNr());
			lutraconych = parent.smo.MVutraconeZgl.getValue();
			parent.smo.MVutraconeZgl.setValue(++lutraconych);
		} else
			System.out.println(simTime() + " - " + simDate(SimDateField.HOUR24) + " - " + simDate(SimDateField.MINUTE)
					+ " - " + simDate(SimDateField.SECOND) + " - " + simDate(SimDateField.MILLISECOND)
					+ ": Problem z usunieciem z kolejki zgl. nr: " + parent.getTenNr());
	}

	@Override
	public Object getEventParams() {
		return null;
	}
}