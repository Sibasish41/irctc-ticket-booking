package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import ticket.booking.entitites.Train;
import ticket.booking.entitites.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {
    private Train train;
    private List<Train> trainList;
    private String trainInfo;

    private static final ObjectMapper objectMapper =
            new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    public static final String TRAIN_FILE_PATH = "app/src/main/java/ticket/booking/localDb/trains.json";


    public TrainService() throws IOException {
        loadTrains();

    }

    public List<Train> loadTrains() throws IOException {
        File trains = new File(TRAIN_FILE_PATH);

        if(trains.exists() && trains.length() != 0){
            trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
        } else {
            trainList = new ArrayList<>(); // initialize empty list if file not found or empty
        }

        return trainList;
    }

    public List<Train> searchTrain(String source, String destination){
        return trainList.stream()
                .filter(train -> validTrain(train, source, destination))
                .collect(Collectors.toList());
    }


    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

    public void addTrain(Train newTrain){
        // Check if a train with the same trainId already exists
        Optional<Train> existingTrain = trainList.stream().filter(train -> train.getTrainId().equalsIgnoreCase(newTrain.getTrainId())).findFirst();

        if(existingTrain.isPresent()){
            // If a train with the same trainId exists, update it instead of adding a new one
            updateTrain(newTrain);
        }
        else {
            trainList.add(newTrain);
            saveTrainListToFile();
        }
    }

    public void updateTrain(Train updatedTrain){
        // Find the index of the train with the same trainId
        OptionalInt index = IntStream.range(0,trainList.size()).filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId())).findFirst();
        if (index.isPresent()) {
            // If found, replace the existing train with the updated one
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();
        } else {
            // If not found, treat it as adding a new train
            addTrain(updatedTrain);
        }
    }

    private void saveTrainListToFile(){
        try{
            //serialization OBJECT -> JSON
            objectMapper.writeValue(new File(TRAIN_FILE_PATH),trainList);
        }catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's requirements
        }
    }
}
