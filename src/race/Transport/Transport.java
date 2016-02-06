package race.Transport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import race.Core;

/**
 *
 * @author TERMIN
 */
public class Transport implements Runnable {

    public static String TYPE_NONE = null;
    public static String TYPE_TRUCK = "Truck";
    public static String TYPE_CAR = "Car";
    public static String TYPE_BIKE = "Bike";

    public Integer id;
    public Boolean circleComplete = false;
    String type = TYPE_NONE;
    private Double speed;
    Double chancePuncture = 0.01;
    Integer countWheels;
    Double distance;
    ActionListener isFinish;

    public String getType() {
        return type;
    }

    public Transport(Integer id, Double speed, Integer countWheels, Double distance, ActionListener isFinish) {
        this.id = id;
        this.speed = speed;
        this.countWheels = countWheels;
        this.distance = distance;
        this.isFinish = isFinish;
    }

    public Double getSpeed() {
        Double res;
        if (speed == null) {
            res = 0.0;
        } else {
            res = speed;
        }
        if (res < 0) {
            res = 0.0;
        }
        return res;
    }

    public Double getChancePuncture() {
        Double res;
        if ((chancePuncture != null) & (countWheels != null)) {
            res = chancePuncture * countWheels;
        } else {
            res = 0.0;
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "";
        res += "Уникальный номер: " + id + "\n";
        res += "тип: " + getType() + "\n";
        res += "скорость: " + getSpeed() + "\n";
        res += "шанс прокола колеса: " + getChancePuncture() + "\n";
        return res;
    }

    ;

    @Override
    public void run() {
        Double distanceRest = distance;
        circleComplete = false;
        if (distance == null) {
            sendMessage("Дистанция не определена!");
            return;
        }
        synchronized (Core.start) {
            try {
                System.out.println("Участник " + id + " к старту готов!");
                Core.start.wait();
            } catch (InterruptedException ex) {
                sendMessage("Фальстарт или отмена");
            }
        }
        System.out.println("Участник " + id + " поехал!");
        while (!Thread.interrupted()) {
            while (!circleComplete) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    sendMessage("Отмена");
                }
                if (isPuncture()) {
                    sendMessage("Проколол колесо и задерживается");
                    try {
                        Thread.sleep((new Random()).nextInt(10) * 1000);
                    } catch (InterruptedException ex) {
                    }
                    sendMessage("Починил прокол");
                }
                if (distanceRest > 0.0) {
                    if (distanceRest >= getSpeed()) {
                        distanceRest -= getSpeed();
                    } else {
                        distanceRest = 0.0;
                    }
                    sendMessage("Проехал уже " + (distance - distanceRest));
                }
                if (distanceRest == 0.0) {
                    circleComplete = true;
                    sendMessage("Завершил круг");
                    isFinish.actionPerformed(new ActionEvent(this, id, "Finish"));
                    return;
                }
            }
        }
        sendMessage("Остановило ГАИ :)");
    }

    public Boolean isPuncture() {
        Random random = new Random();
        return random.nextDouble() <= getChancePuncture();
    }

    void sendMessage(String mess) {
        System.out.println("Транспорт №" + id + ". " + mess);
    }
}
