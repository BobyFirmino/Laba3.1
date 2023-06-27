import java.io.*;
import java.util.ArrayList;

public class SerializeClient {
    private static String name = "clients.out";
    public static void serialize(ArrayList<Client> clients) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name)))
        {
            oos.writeObject(clients);
            System.out.println("File has been written");
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<Client> deserialize() {
        ArrayList<Client> clients= new ArrayList<Client>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name)))
        {

            clients=((ArrayList<Client>)ois.readObject());
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return clients;
    }
}
