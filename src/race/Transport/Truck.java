package race.Transport;

import race.Transport.Transport;
import java.awt.event.ActionListener;

/**
 *
 * @author TERMIN
 */
public class Truck extends Transport {

    private Double cargoWeight;

    public Truck(Integer id, Double speed, Integer countWheels, Double distance, ActionListener isFinish, Double cargoWeight) {
        super(id, speed, countWheels, distance, isFinish);
        type = Transport.TYPE_TRUCK;
        this.cargoWeight = cargoWeight;
    }
    

    public Double getCargoWeight() {
        if (cargoWeight != null) {
            return cargoWeight;
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        String res = super.toString();
        res += "Вес груза " + getCargoWeight() + " кг\n";
        return res;
    }

    @Override
    public Double getSpeed() {
        Double res;
        res = super.getSpeed() - (getCargoWeight() / 100);
        if (res < 0) {
            res = 0.0;
        }
        return res;
    }

    

}
