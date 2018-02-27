package smo;

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters.SimDateField;

public class Zglaszaj extends BasicSimEvent<Otoczenie, Object> {
	private SimGenerator generator;
	private Otoczenie parent;

	public Zglaszaj(Otoczenie parent, double delay) throws SimControlException {
		super(parent, delay);
		generator = new SimGenerator();
	}

	public Zglaszaj(Otoczenie parent) throws SimControlException {
		super(parent);
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
		parent = getSimObj();
		Zgloszenie zgl = new Zgloszenie(generator.uniformInt(1, 10), simTime(), parent.smo);
		if (parent.smo.getFIFO() == true) {
			parent.smo.dodajFifo(zgl);
		} else {
			parent.smo.dodajLifo(zgl);
		}
		System.out.println(simTime() + " - " + simDate(SimDateField.HOUR24) + " - " + simDate(SimDateField.MINUTE)
				+ " - " + simDate(SimDateField.SECOND) + " - " + simDate(SimDateField.MILLISECOND)
				+ ": Otoczenie- Dodano nowe zgl. nr: " + zgl.getTenNr());
		if (parent.smo.liczbaZgl() == 1 && parent.smo.isWolne()) {
			parent.smo.rozpocznijObsluge = new RozpocznijObsluge(parent.smo);
		}
		double odstep = generator.normal(5.0, 1.0);
		parent.zglaszaj = new Zglaszaj(parent, odstep);
	}

	@Override
	public Object getEventParams() {
		return null;
	}
}