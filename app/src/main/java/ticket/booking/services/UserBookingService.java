package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import ticket.booking.entitites.Train;
import ticket.booking.entitites.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserBookingService {
    private User user;
    private List<User> userList;

    // This is used for Deserialisation (Mapping objects from JSON) . EX> user_id => userId
    private static final ObjectMapper objectMapper =
            new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    private static final String USER_FILE_PATH="app/src/main/java/ticket/booking/localDb/users.json";

   public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException{
        loadUsers();
    }

    public List<User> loadUsers() throws IOException {
        File users = new File(USER_FILE_PATH);

        if(users.exists() && users.length() != 0){
            userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        } else {
            userList = new ArrayList<>(); // initialize empty list if file not found or empty
        }

        return userList;
    }


    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        //Object --> JSON (serialization)
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBookings(){
       Optional<User> userFetched = userList.stream().filter(user1 -> {
           return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(),user1.getHashedPassword());
       }).findFirst();
       if(userFetched.isPresent()){
           userFetched.get().printTickets();
       }
    }

    public boolean cancelBooking(String ticketId){
       Scanner sc = new Scanner(System.in);
       System.out.println("Enter the ticket id to cancel:");
       ticketId = sc.next();

       if(ticketId == null || ticketId.isEmpty()){
           System.out.println("Ticket id cant be null or empty.");
                   return Boolean.FALSE;
       }

       String finalTicketId1 = ticketId;
       boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));

       String finalTicketId = ticketId;
       user.getTicketsBooked().removeIf(Ticket -> Ticket.getTicketId()
               .equals(finalTicketId));

        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }

    public List<Train> getTrains(String source, String destination){
       try{
            TrainService trainService = new TrainService();
            return trainService.searchTrain(source,destination);
       }catch(IOException e){
            return new ArrayList<>();
       }
    }

}
