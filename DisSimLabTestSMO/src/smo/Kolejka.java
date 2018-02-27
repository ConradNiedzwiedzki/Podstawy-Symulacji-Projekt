package smo;

import java.util.LinkedList;

public class Kolejka {

	public LinkedList<Zgloszenie> kolejka;
	private boolean fifo;
	private int rozmiar;

	public Kolejka(boolean fifo, int rozmiar) {

		this.rozmiar = rozmiar;
		kolejka = new LinkedList<Zgloszenie>();
		this.fifo = fifo;
	}

	public void dodajFIFO(Zgloszenie z) {
		if (kolejka.isEmpty()) {
			kolejka.addLast(z);
		} else if (z.getPrio() > kolejka.getLast().getPrio()) {
			kolejka.addLast(z);
		} else {
			for (int i = 0; i < kolejka.size(); i++) {
				if (z.getPrio() <= kolejka.get(i).getPrio()) {
					kolejka.add(i, z);
					break;
				} else if (z.getPrio() > kolejka.get(i).getPrio() && z.getPrio() <= kolejka.get(i + 1).getPrio()) {
					kolejka.add(i + 1, z);
					break;
				}
			}
		}
	}

	public void dodajLIFO(Zgloszenie z) {
		if (kolejka.isEmpty()) {
			kolejka.addLast(z);
		} else if (z.getPrio() >= kolejka.getLast().getPrio()) {
			kolejka.addLast(z);
		} else {
			for (int i = 0; i < kolejka.size(); i++) {
				if (z.getPrio() < kolejka.get(i).getPrio()) {
					kolejka.add(i, z);
					break;
				} else if (z.getPrio() >= kolejka.get(i).getPrio() && z.getPrio() < kolejka.get(i + 1).getPrio()) {
					kolejka.add(i + 1, z);
					break;
				}
			}
		}
	}

	public void usun(Zgloszenie z) {
		for (int i = 0; i < kolejka.size(); i++) {
			if ((z.getPrio() == kolejka.get(i).getPrio()) && (z.getNum() == kolejka.get(i).getNum())) {
				kolejka.remove(i);
				break;
			}
		}
	}

	public Zgloszenie pobierz() {
		return kolejka.removeLast();
	}

	public int size() {
		return kolejka.size();
	}

	public void setRozm(int a) {
		this.rozmiar = a;
	}

	public int getRozm() {
		return this.rozmiar;
	}

	public boolean getFIFO() {
		return fifo;
	}

	public void setFIFO(boolean fifo) {
		this.fifo = fifo;
	}

}
