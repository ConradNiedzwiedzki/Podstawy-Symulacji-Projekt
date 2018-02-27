package smo;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

public class Otoczenie extends BasicSimObj {
	public Zglaszaj zglaszaj;
	public Smo smo;

	public Otoczenie(Smo smo) throws SimControlException {
		zglaszaj = new Zglaszaj(this, 0.0);
		this.smo = smo;
	}

	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {
	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}
}
