package race;

import race.Transport.Transport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import static race.Config.getConfig;

/**
 *
 * @author TERMIN
 */
public class Core {

    public static Object start = new Object();

    ArrayList<Transport> liders = new ArrayList<>();
    ActionListener transportFinish = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            liders.add((Transport) e.getSource());
        }
    };

    public void main() throws InterruptedException {
        System.err.println("Чтобы начать гонку, надо набрать start");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner in = new Scanner(System.in);
                while (true) {
                    String s = findCommand(in.nextLine());
                    if (s != null) {
                        System.out.println("\n" + s);
                    }
                }
            }
        }, "consoleInput");
        thread.setDaemon(false);
        thread.start();
    }

    private String findCommand(String s) {
        if (s.equals("start")) {
            return start();
        } else {
            return "Чтобы начать конку, надо набрать start";
        }
    }

    public String start() {
        ArrayList<Transport> trs = getConfig(System.getProperty("user.dir") + "/config.json", transportFinish);
        if (trs != null) {
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    liders.clear();
                    ArrayList<Thread> racers = new ArrayList<>();
                    System.out.println("Перечисляем участников гонки");
                    racers = preStartRacers(trs, racers);
                    sleep(3000);
                    racersStart();
                    waitRacersFinish(racers);
                    System.out.println("Все прибыли");
                    printLiders(liders);
                    System.out.println("\n\nЧтобы начать конку, надо набрать start");

                }
            });
            t.setDaemon(false);
            t.setName("Круг");
            t.start();
        }
        return "Чтобы повторить круг, наберите start";
    }

    private ArrayList<Thread> preStartRacers(ArrayList<Transport> trs, ArrayList<Thread> racers) {
        for (Transport tr : trs) {
            System.out.println(tr);
            sleep(1000);
            racers.add(new Thread(tr));
        }
        System.out.println("");
        for (Thread racer : racers) {
            racer.start();
        }
        return racers;
    }

    private void racersStart() {
        System.out.println("START!");
        synchronized (start) {
            start.notifyAll();
        }
    }

    private ArrayList<Thread> waitRacersFinish(ArrayList<Thread> racers) {
        for (Thread racer : racers) {
            try {
                racer.join();
            } catch (InterruptedException ex) {

            }
        }
        return racers;
    }

    private void sleep(Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
        }
    }

    public void printLiders(ArrayList list) {
        if (list.isEmpty()) {
            System.out.println("Участники еще не прибыли");
            return;
        }
        System.out.println("И победителем у нас является " + ((Transport) list.get(0)).getType() + " под номером " + ((Transport) list.get(0)).id);
        System.out.println("А вот участники и их порядок прибытия на финиш");
        for (Object list1 : list) {
            System.out.println(((Transport) list1).getType() + " под номером " + ((Transport) list1).id);
        }
    }
}
