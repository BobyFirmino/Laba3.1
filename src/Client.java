import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;
import java.io.*;

public class Client implements Serializable {
    protected String name;
    protected String phoneNumber;
    protected String email;
    private int budget;
    private int sessionNumber;
    private String status;
    private int maxSessions = 3;
    private ArrayList<Sessions> ticketsOverSession;
    private int totalTickets;
    private int discount;

    public Client() {
        name = "Иванов Иван Иванович";
        phoneNumber = "88005553535";
        email = "iviviv@iv.iv";
        budget = 1000;
        ticketsOverSession = new ArrayList<Sessions>();
        status = "стандарт";
        setTotalTickets(0);
        discount = 0;
    }

    public Client(String name, String phoneNumber, String email, int budget) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.budget = budget;
        ticketsOverSession = new ArrayList<Sessions>();
        status = "стандарт";
        setTotalTickets(0);
        discount = 0;
    }

    public Client(String name, String phoneNumber, String email, int budget, String status, int totalTickets) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.budget = budget;
        ticketsOverSession = new ArrayList<Sessions>();
        setStatus(status);
        this.totalTickets = totalTickets;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getBudget() {
        return budget;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public ArrayList<Sessions> chooseShow(Date wantedTime, ArrayList<Sessions> shows) {
        ArrayList<Sessions> result = new ArrayList<Sessions>();
        for (int i = 0; i < shows.size(); i++) {
            if (wantedTime.equals(shows.get(i).getTime()))
                result.add(shows.get(i));
        }
        return result;
    }

    public ArrayList<Sessions> chooseShow(int wantedPrice, ArrayList<Sessions> shows) {
        ArrayList<Sessions> result = new ArrayList<Sessions>();
        for (int i = 0; i < shows.size(); i++) {
            if (shows.get(i).getTheatre().getHalls().get(shows.get(i).getHallNumber()).getPrice() <= wantedPrice)
                result.add(shows.get(i));
        }
        return result;
    }

    public ArrayList<Sessions> chooseShow(String wantedName, ArrayList<Sessions> shows) {
        ArrayList<Sessions> result = new ArrayList<Sessions>();
        for (int i = 0; i < shows.size(); i++) {
            if (wantedName.equals(shows.get(i).getMovie().getName()))
                result.add(shows.get(i));
        }
        return result;
    }

    public ArrayList<Seat> chooseSeat(CinemaHall hall) {
        return hall.returnFreeSeats();
    }

    public String output()//доделать
    {
        return new String("Клиент: " + name + " " + phoneNumber + " " + email + "; остаток средств " + budget + "; " + totalTickets + " билетов всего купил");
    }

    public void AddTicket(Sessions session) {
        ticketsOverSession.add(session);
    }

    public void EraseTickets() {
        ticketsOverSession.clear();
    }

    public String getStatus() {
        return status;
    }

    public int getStatusNumber() {
        if (status.equals("стандарт"))
            return 0;
        if (status.equals("Друг сети"))
            return 1;
        if (status.equals("VIP"))
            return 2;
        return 0;
    }

    public void setStatus(String status) {
        this.status = status;
        if (status.equals("Друг сети"))
            discount = 10;
        else {
            if (status.equals("VIP"))
                discount = 20;
            else
                discount = 0;
        }
    }

    public void setStatus(int tickets) {
        totalTickets = tickets;
        if (totalTickets < 7) {
            if (totalTickets < 3)
                setStatus("стандарт");
            else
                setStatus("Друг сети");
        } else
            setStatus("VIP");
    }

    public void AddClientToBase(char sep, boolean append) throws IOException {
        FileWriter fw = new FileWriter("admin/Clients.csv", append);
        fw.write(name + sep + phoneNumber + sep + email + sep + budget + sep + status + sep + totalTickets + '\n');
        fw.close();

        /*ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("clients.out"));
        objectOutputStream.writeObject(this);*/
    }

    public static ArrayList<Client> getClients(String sep) throws IOException, ClassNotFoundException {
        ArrayList<Client> result = new ArrayList<Client>();
        File file = new File("admin/Clients.csv");
        BufferedReader br = new BufferedReader(new FileReader((file)));
        String line = br.readLine();
        while (line != null) {
            String[] params = line.split(sep);
            result.add(new Client(params[0], params[1], params[2], Integer.parseInt(params[3]), params[4], Integer.parseInt(params[5])));
            line = br.readLine();
        }
        return result;

    }

    private Client ReadClient(String sep) throws IOException {
        File file = new File("admin/Clients.csv");
        BufferedReader br = new BufferedReader(new FileReader((file)));
        String[] params = br.readLine().split(sep);
        Client c = new Client(params[0], params[1], params[2], Integer.parseInt(params[3]), params[4], Integer.parseInt(params[5]));
        return c;
    }

    private static boolean findClient(Client c, String sep) throws IOException {
        File file = new File("admin/Clients.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while (line != null) {
            String[] params = line.split(sep);
            if (c.Equals(params[0], params[1], params[2])) return true;
            line = br.readLine();
        }
        return false;
    }

    public void BuyTicket(ArrayList<Sessions> shows, ArrayList<Movie> movies, ArrayList<CinemaTheatre> theatres) {
        boolean showsFound = false;
        ArrayList<Sessions> foundShows = new ArrayList<Sessions>();
        Scanner sc = new Scanner(System.in);
        //boolean sessionActive = true;
        while (true) {
            //if (sessionActive) {
            System.out.println("Введите параметр для выбора фильма:\n1 - время сеанса, 2 - стоимость билета, 3 - название фильма или 0 - чтобы выйти в меню");
            int parameter = sc.nextInt();
            switch (parameter) {
                case 1:
                    System.out.println("Введите дату и время сеанса в формате дд-мм-гггг чч:мм:");
                    sc.nextLine();
                    String strDate = sc.nextLine();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    try {
                        Date date = dateformat.parse(strDate);
                        System.out.println(date);
                        foundShows = chooseShow(date, shows);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if (foundShows.size() != 0)
                        showsFound = true;
                    break;
                case 2:
                    System.out.println("Введите стоимость билета:");
                    int price = sc.nextInt();
                    foundShows = chooseShow(price, shows);
                    if (foundShows.size() != 0)
                        showsFound = true;
                    break;
                case 3:
                    System.out.println("Введите название фильма:");
                    String movieName;
                    sc.nextLine();
                    movieName = sc.nextLine();
                    foundShows = chooseShow(movieName, shows);
                    if (foundShows.size() != 0)
                        showsFound = true;
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ошибка: Вы ввели несуществующий параметр");
                    return;
            }
            if (showsFound) {
                System.out.println("Вам подойдут следующие сеансы:");
                for (int i = 0; i < foundShows.size(); i++) {
                    System.out.println((i + 1) + ": " + foundShows.get(i).output(foundShows.get(i).getHallNumber()));
                }
                while (true) {
                    System.out.println("Выберите подходящий Вам сеанс (или введите 0, чтобы отменить выбор):");
                    int ind = sc.nextInt() - 1;
                    if (ind == -1) {
                        System.out.println("Выбор сброшен");
                        break;
                    } else {
                        if (ind >= 0 && ind < foundShows.size()) {
                            if (getBudget() >= foundShows.get(ind).getTheatre().getHalls().get(foundShows.get(ind).getHallNumber()).getPrice()) {
                                Sessions foundShow = foundShows.get(ind);
                                int ticketPrice = foundShow.getTheatre().getHalls().get(foundShow.getHallNumber()).getPrice() * (100 - discount) / 100;
                                System.out.println("Стоимость фильма с учетом персональной скидки в " + discount + "% составит " + ticketPrice);
                                System.out.println("Далее представлены свободные места на выбранном сеансе: ");
                                ArrayList<Seat> freeSeats = chooseSeat(foundShow.getTheatre().getHalls().get(foundShows.get(ind).getHallNumber()));
                                for (int i = 0; i < freeSeats.size(); i++) {
                                    System.out.println(freeSeats.get(i).output());
                                }
                                while (true) {
                                    System.out.println("Введите номера ряда и места (в одну строку):");
                                    int line = sc.nextInt() - 1;
                                    int row = sc.nextInt() - 1;
                                    if (line == -1 && row == -1) {
                                        if (!addSessionNumber(false)) {
                                            return;
                                        }
                                        break;
                                    }
                                    try {
                                        if (foundShow.getTheatre().getHalls().get(foundShow.getHallNumber()).getSeats()[line][row].checkFree()) {
                                            setBudget(getBudget() - ticketPrice);
                                            foundShow.getTheatre().getHalls().get(foundShow.getHallNumber()).getSeats()[line][row].setFree(false); //место отмечается занятым

                                            //int rev = Integer.parseInt(readFromFile("admin/revenue info/TotalRevenue.txt"));
                                            //rev += ticketPrice;
                                            //printToFile("admin/revenue info/TotalRevenue.txt", false, Integer.toString(rev));

                                            //int sold = Integer.parseInt(readFromFile(("admin/revenue info/TotalSold.txt")));
                                            //sold++;
                                            //printToFile("admin/revenue info/TotalSold.txt", false, Integer.toString(sold));

                                            //добавляем инфу по выручке по фильму и кинотеатру
                                            int currentMovieRevenue = foundShow.getMovie().getTotalRevenue() + ticketPrice;
                                            foundShow.getMovie().setTotalRevenue(currentMovieRevenue);
                                            if (movies.size() > 0)
                                                movies.get(0).AddMovieToBase(';', false);
                                            for (int i = 1; i < movies.size(); i++)
                                                movies.get(i).AddMovieToBase(';', true);
                                            //по кинотеатру
                                            int currentTheatreRevenue = foundShow.getTheatre().getTotalRevenue() + ticketPrice;
                                            foundShow.getTheatre().setTotalRevenue(currentTheatreRevenue);
                                            int currentTotalTickets = foundShow.getTheatre().getTotalTickets()+1;
                                            foundShow.getTheatre().setTotalTickets(currentTotalTickets);
                                            if (theatres.size() > 0)
                                                theatres.get(0).AddTheatreToBase(';', false);
                                            for (int i = 1; i < theatres.size(); i++)
                                                theatres.get(i).AddTheatreToBase(';', true);

                                            System.out.println("Запись успешно завершена!\nСеанс: " + foundShow.output(foundShow.getHallNumber()) + ", " + line + "-й ряд, " + row + "-е место");
                                            if (!addSessionNumber(true)) {
                                                return;
                                            }
                                            break;
                                        } else {
                                            System.out.println("Увы, это место занято. Попробуйте снова или введите \"0 0\", чтобы выйти");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Кажется, вы ввели несуществующие места. Попробуйте снова или введите \"0 0\", чтобы выйти");
                                    }
                                }
                                break;
                            } else {
                                System.out.println("Увы, у Вас недостаточно средств для покупки билета на данный сеанс. Попробуйте снова или выберите другой сеанс");
                                if (!addSessionNumber(false)) {
                                    return;
                                }
                            }
                        } else {
                            System.out.println("Введен неверный номер сеанса. Попробуйте снова");
                        }
                    }
                }
            } else {
                System.out.println("Не удалось найти подходящий сеанс");
                /*if(addSessionNumber(false))
                {
                    sessionNumber=0;
                    sessionActive=false;
                }*/
            }
        }
    }

    public boolean addSessionNumber(boolean bought) {
        sessionNumber++;
        if (bought) {
            setTotalTickets(getTotalTickets() + 1);
            if (getTotalTickets() > 3) {
                setStatus("Друг сети");
            }
            if (getTotalTickets() > 7) {
                setStatus("VIP");
            }
        }
        //System.out.println("session Number = "+ sessionNumber);
        if (sessionNumber >= 3) {
            sessionNumber = 0;
            return false;
        }
        return true;
    }

    public boolean Equals(String name, String phone, String email) {
        if (this.name.equals(name) && phoneNumber.equals(phone) && this.email.equals(email))
            return true;
        else
            return false;
    }
    public boolean Equals(Client c) {
        if (this.name.equals(c.name) && phoneNumber.equals(c.phoneNumber) && this.email.equals(c.email))
            return true;
        else
            return false;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
        setStatus(totalTickets);
    }

    public String toString() {
        return new String(name + " " + phoneNumber + " " + email + " " + budget + " " + status + " " + totalTickets);
    }
}

