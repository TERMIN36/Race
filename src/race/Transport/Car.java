package race.Transport;

import java.awt.event.ActionListener;

/**
 *
 * @author TERMIN
 */
public class Car extends Transport {

    Integer countPeople;

    public Car(Integer id, Double speed, Integer countWheels, Double distance, ActionListener isFinish, Integer people) {
        super(id, speed, countWheels, distance, isFinish);
        type = Transport.TYPE_CAR;
        this.countPeople = people;
    }


    public Integer getCountPeople() {
        if (countPeople == null) {
            countPeople = 0;
        }
        return countPeople;
    }

    @Override
    public String toString() {
        String res = super.toString();
        res += "Количество пассажиров " + getCountPeople()+ "\n";
        return res;
    }


    @Override
    public Double getSpeed() {
         Double res;
        res = super.getSpeed() - (getCountPeople() * 5);
        if (res < 0) {
            res = 0.0;
        }
        return res;
    }

}
