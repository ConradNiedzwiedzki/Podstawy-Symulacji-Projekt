package smo;

import smo.KoniecNiecierpliwienia;
import smo.Smo;
import smo.StartNiecierpliwienia;
import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

public class Zgloszenie extends BasicSimObj {
	double czasOdniesienia;
	private int priority;
	static int nr = 0;
	int tenNr;
	StartNiecierpliwienia startNiecierpliwienia;
	public KoniecNiecierpliwienia koniecNiecierpliwosci;
	public Smo smo;

	public Zgloszenie(int priority, double Czas, Smo smo) throws SimControlException {
		this.priority = priority;
		czasOdniesienia = Czas;
		setTenNr();
		this.smo = smo;
		startNiecierpliwienia = new StartNiecierpliwienia(this);
	}

	public double getCzasOdniesienia() {
		return czasOdniesienia;
	}

	public void setCzasOdniesienia(double czasOdniesienia) {
		this.czasOdniesienia = czasOdniesienia;
	}

	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {

	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}

	public void setTenNr() {
		this.tenNr = nr++;
	}

	public int getTenNr() {
		return tenNr;
	}

	public void setPrio(int a) {
		this.priority = a;
	}

	public int getPrio() {
		return this.priority;
	}

	public int getNum() {
		return this.nr;
	}

}