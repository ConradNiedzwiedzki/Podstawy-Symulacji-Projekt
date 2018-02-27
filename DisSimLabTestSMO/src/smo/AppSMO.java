package smo;

import java.math.BigDecimal;
import java.util.Scanner;
import dissimlab.monitors.Diagram;
import dissimlab.monitors.Diagram.DiagramType;
import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters.SimControlStatus;

public class AppSMO {
	public static void main(String[] args) {

		try {
			SimManager model = SimManager.getInstance();
			SimEventSemaphore semafor = new SimEventSemaphore("Semafor");
			SmoBis smoBis = new SmoBis(semafor);
			Smo smo = new Smo(smoBis, semafor);
			System.out.println("podaj rodzaj kolejki: FIFO - napisz 'true' LIFO - 'false' i nacisnij enter ");
			Scanner odczyt = new Scanner(System.in);
			smo.setFIFO(odczyt.nextBoolean());
			System.out.println("podaj rozmiar kolejki smo i nacisnij enter ");
			Scanner odczyt2 = new Scanner(System.in);
			smo.setRozm(odczyt2.nextInt());
			System.out.println("podaj rozmiar kolejki smoBis i nacisnij enter ");
			Scanner odczyt3 = new Scanner(System.in);
			smoBis.setRozm(odczyt3.nextInt());
			System.out.println("podaj wspolczynnik p i nacisnij enter ");
			Scanner odczyt4 = new Scanner(System.in);
			smo.setP(odczyt4.nextDouble());
			System.out.println("podaj rodzaj kolejki BIS: FIFO - napisz 'true' LIFO - 'false' i nacisnij enter ");
			Scanner odczyt5 = new Scanner(System.in);
			smoBis.setFIFO(odczyt5.nextBoolean());
			Otoczenie generatorZgl = new Otoczenie(smo);
			SimControlEvent stopEvent = new SimControlEvent(1000.0, SimControlStatus.STOPSIMULATION);
			model.startSimulation();

			double wynik = new BigDecimal(Statistics.arithmeticMean(smo.MVczasy_oczekiwania))
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out.println("Œredni czas oczekiwania na obsluge:   " + wynik);
			wynik = new BigDecimal(Statistics.standardDeviation(smo.MVczasy_oczekiwania))
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			System.out.println("Odchylenie standardowe dla czasu obslugi:   " + wynik);
			wynik = new BigDecimal(Statistics.max(smo.MVczasy_oczekiwania)).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			System.out.println("Maksymalna wartosc czasu oczekiwania na obsluge: " + wynik);
			wynik = new BigDecimal(Statistics.max(smo.MVutraconeZgl)).setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			System.out.println("ilosc odrzuconych zgloszen: " + wynik);

			Diagram d1 = new Diagram(DiagramType.HISTOGRAM, "Czas obslugiwania");
			d1.add(smo.MVczasy_obslugi, java.awt.Color.GREEN);
			d1.show();
			
			Diagram d5 = new Diagram(DiagramType.HISTOGRAM, "Czas obslugiwania Bis");
			d5.add(smoBis.MVczasy_obslugi, java.awt.Color.ORANGE);
			d5.show();

			Diagram d2 = new Diagram(DiagramType.DISTRIBUTION, "Czasy oczekiwania na obsluge");
			d2.add(smo.MVczasy_oczekiwania, java.awt.Color.BLUE);
			d2.show();

			Diagram d3 = new Diagram(DiagramType.TIME_FUNCTION, "Dlugosc kolejki");
			d3.add(smo.MVdlKolejki, java.awt.Color.RED);
			d3.show();
			
			Diagram d4 = new Diagram(DiagramType.TIME_FUNCTION, "Dlugosc kolejki Bis");
			d4.add(smoBis.MVdlKolejki, java.awt.Color.MAGENTA);
			d4.show();

		} catch (SimControlException e) {
			e.printStackTrace();
		}

	}
}
