package race.Transport;

import java.awt.event.ActionListener;

/**
 *
 * @author TERMIN
 */
public class Bike extends Transport {

    boolean carriage;

    public Bike(Integer id, Double speed, Integer countWheels, Double distance, ActionListener isFinish, Boolean carriage) {
        super(id, speed, countWheels, distance, isFinish);
        type = Transport.TYPE_BIKE;
        this.carriage = carriage;
    }


    public String isCarriage() {
        if (carriage) {
            return "есть";
        } else {
            return "нет";
        }
    }

    @Override
    public Double getChancePuncture() {
        Double res;
        if ((chancePuncture != null) & (countWheels != null)) {
            if (carriage) {
                res = chancePuncture * (countWheels + 1);
            } else {
                res = chancePuncture * countWheels;
            }
        } else {
            res = 0.0;
        }
        return res;
    }

    @Override
    public String toString() {
        String res = super.toString();
        res += "Наличие коляски " + isCarriage() + "\n";
        return res;
    }

    @Override
    public Double getSpeed() {
        Double res = super.getSpeed();
        if (carriage) {
            res -= 5;
        }
        if (res < 0) {
            res = 0.0;
        }
        return res;
    }
}
