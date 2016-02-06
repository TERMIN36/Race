package race;

import race.Transport.Truck;
import race.Transport.Transport;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import race.Transport.Bike;
import race.Transport.Car;

/**
 *
 * @author TERMIN
 */
public class Config {

    static Integer currentID = 0;
    public static Double distance;

    public static ArrayList<Transport> getConfig(String filename, ActionListener transportFinish) {
        currentID = 0;
        ArrayList<Transport> res = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            String source = readFile(filename);
            if (source == null) {
                return null;
            }
            JSONObject obj = (JSONObject) parser.parse(source);
            Double dis = ((Long) obj.get("distance")).doubleValue();
            distance = dis;
            JSONArray transports = (JSONArray) obj.get("transports");
            for (Object object : transports) {
                JSONObject aJson = (JSONObject) object;
                String type = (String) aJson.get("type");
                Long speed = (Long) aJson.get("speed");
                Integer countWheels = ((Long) aJson.get("countWheels")).intValue();
                if (type.toLowerCase().equals(Transport.TYPE_TRUCK.toLowerCase())) {
                    Double cargoWeight = (Double) aJson.get("cargoWeight");
                    Truck truck = new Truck(++currentID, speed.doubleValue(), countWheels, distance, transportFinish, cargoWeight);
                    res.add(truck);
                } else if (type.toLowerCase().equals(Transport.TYPE_CAR.toLowerCase())) {
                    Integer people = ((Long) aJson.get("people")).intValue();
                    Car car = new Car(++currentID, speed.doubleValue(), countWheels, distance, transportFinish, people);
                    res.add(car);
                } else if (type.toLowerCase().equals(Transport.TYPE_BIKE.toLowerCase())) {
                    Boolean carriage = (Boolean) aJson.get("carriage");
                    Bike bike = new Bike(++currentID, speed.doubleValue(), countWheels, distance, transportFinish, carriage);
                    res.add(bike);
                } else {
                    System.err.println("Возникла ошибка распознавания транспорта. Проверьте файл");
                }

            }

        } catch (ParseException pe) {
            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }
        System.out.println("Файл транспорта прочитан");
        return res;
    }

    public static String readFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));

            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
            in.close();

        } catch (IOException e) {
            System.err.println("Файл с транспортом не найден. При текущих настройках запуска я его жду тут: " + fileName);
            return null;
        }
        return sb.toString();
    }
}
