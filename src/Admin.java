import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Admin {
    private String username;
    private static String password = "password";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static String readFromFile(String fileName)
    {
        String strRes="";
        try(FileReader reader = new FileReader(fileName))
        {
            // читаем посимвольно
            int a;
            while((a=reader.read())!=-1){

                strRes+=(char)a;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return strRes;
    }
    public static void printToFile(String fileName, boolean append, String text)//append: true = дописать новые данные в конец файла, false = перезаписать содержимое файла
    {
        try(FileWriter writer = new FileWriter(fileName, append))
        {
            writer.write(text);
            writer.flush();
            writer.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    public static int TotalRevenue() throws IOException
    {
        int totalRevenue = 0;
        ArrayList<Movie> movies = Movie.getMovies(";");
        for(int i=0; i<movies.size(); i++)
        {
            totalRevenue+=movies.get(i).getTotalRevenue();
        }
        return totalRevenue;
    }
    public static int TotalTickets()   throws IOException, ClassNotFoundException
    {
        int totalTickets = 0;
        //ArrayList<Client> clients = Client.getClients(";");
        ArrayList<Client> clients = SerializeClient.deserialize();
        for(int i=0; i<clients.size(); i++)
        {
            totalTickets+=clients.get(i).getTotalTickets();
        }
        return totalTickets;
    }
    public static void MovieRevenue() throws IOException
    {
        ArrayList<Movie> movies = Movie.getMovies(";");
        while (true) {
            System.out.println("Выберите один из следующих фильмов или введите 0, чтобы выйти:");
            for(int i=0; i<movies.size(); i++)
                System.out.println((Integer)(i+1)+" - " + movies.get(i).getName());
            Scanner sc = new Scanner(System.in);
            int movieIndex = sc.nextInt();
            if (movieIndex > 0 && movieIndex <= movies.size()) {
                System.out.println("Выручка по фильму " + movies.get(movieIndex-1).getName() + " составила " + movies.get(movieIndex-1).getTotalRevenue() + " руб");
                return;
            } else {
                if (movieIndex == -1)
                    return;
                else
                    System.out.println("Введен неправильный индекс, попробуйте снова");
            }
        }
    }
    public static void TheatreRevenue(ArrayList<CinemaTheatre> theatres) throws IOException
    {
        while (true) {
            System.out.println("Выберите один из следующих кинотеатров или введите 0, чтобы выйти:");
            for(int i=0; i<theatres.size(); i++)
                System.out.println((Integer)(i+1)+" - " + theatres.get(i).getName());
            Scanner sc = new Scanner(System.in);
            int movieIndex = sc.nextInt();
            if (movieIndex > 0 && movieIndex <= theatres.size()) {
                System.out.println("Выручка по кинотеатру " + theatres.get(movieIndex-1).getName() + " составила " + theatres.get(movieIndex-1).getTotalRevenue() + " руб");
                return;
            } else {
                if (movieIndex == -1)
                    return;
                else
                    System.out.println("Введен неправильный индекс, попробуйте снова");
            }
        }
    }
    public static void NotRentalMovie() throws IOException
    {
        ArrayList<Movie> movies = Movie.getMovies(";");
        int minRevenue = movies.get(0).getTotalRevenue();
        ArrayList<Movie> notRental = new ArrayList<>();
        for(int i=0; i<movies.size(); i++)
        {
            if(movies.get(i).getTotalRevenue()<=minRevenue)
            {
                minRevenue = movies.get(i).getTotalRevenue();
            }
        }
        for(int i=0; i<movies.size(); i++)
        {
            if (movies.get(i).getTotalRevenue()==minRevenue)
                notRental.add(movies.get(i));
        }
        System.out.println("Список нерентабельных фильмов: ");
        for(int i=0; i<notRental.size(); i++)
            System.out.println(notRental.get(i).getName() + " - " +notRental.get(i).getTotalRevenue() +" руб");

    }
    public static void NotRentalTheatre(ArrayList<CinemaTheatre> theatres)
    {
        int minThRevenue = theatres.get(0).getTotalRevenue();
        ArrayList<CinemaTheatre> notRentalTh = new ArrayList<>();
        for(int i=0; i<theatres.size(); i++)
        {
            if(theatres.get(i).getTotalRevenue()<=minThRevenue)
            {
                minThRevenue = theatres.get(i).getTotalRevenue();
            }
        }
        for(int i=0; i<theatres.size(); i++)
        {
            if (theatres.get(i).getTotalRevenue()==minThRevenue)
                notRentalTh.add(theatres.get(i));
        }
        System.out.println("Список нерентабельных кинотеатров: ");
        for(int i=0; i<notRentalTh.size(); i++)
            System.out.println(notRentalTh.get(i).getName() + " - " +notRentalTh.get(i).getTotalRevenue() +" руб");

    }
    public static void TypeRevenue() throws IOException
    {
        int[] TypeToRevenue = new int[4];
        for(int i=0; i<TypeToRevenue.length; i++)
        {
            TypeToRevenue[i]=0;
        }
        ArrayList<Sessions> shows = Sessions.getShows(";");
        for(int i=0; i<shows.size(); i++)
        {
            TypeToRevenue[shows.get(i).getHallTypeNumber()]+=shows.get(i).getTotalRevenue();
        }
        System.out.println("Выручка по залам 2D составила "+ TypeToRevenue[0]+" руб.");
        System.out.println("Выручка по залам 3D составила "+ TypeToRevenue[1]+" руб.");
        System.out.println("Выручка по залам VIP составила "+ TypeToRevenue[2]+" руб.");
        System.out.println("Выручка по залам soft составила "+ TypeToRevenue[3]+" руб.");
    }
    public static void TotalClients() throws IOException, ClassNotFoundException
    {
        ArrayList<Client> clients = SerializeClient.deserialize();
        int[] result = new int[3];
        for(int i=0; i<result.length; i++)
            result[i]=0;
        for(int i=0; i<clients.size(); i++)
        {
            result[clients.get(i).getStatusNumber()]++;
        }
        System.out.println("Клиентов статуса стандарт всего "+ result[0] + "чел.");
        System.out.println("Клиентов статуса Друг сети всего "+ result[1] + "чел.");
        System.out.println("Клиентов статуса VIP всего "+ result[2] + "чел.");
    }
    public static void AddTheatre(ArrayList<CinemaTheatre> theatres) throws IOException
    {
        //name, address, revenue
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите название кинотеатра: ");
        String name = sc.nextLine();
        System.out.println("Введите адрес кинотеатра: ");
        String address = sc.nextLine();
        CinemaTheatre newTheatre = new CinemaTheatre(name, address, 0);
        theatres.add(newTheatre);
        newTheatre.AddTheatreToBase(';', true);
        System.out.println("Кинотеатр успешно создан!");
    }
    public static void ChangeTheatres(ArrayList<CinemaTheatre> theatres) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Вот список кинотеатров. Выберите нужный: ");
        for(int i=0; i<theatres.size(); i++) {
            System.out.println((i+1)+ " -" + theatres.get(i).output());
        }
        int ind = sc.nextInt()-1;
        try
        {
            System.out.println("Вы выбрали кинотеатр: "+ theatres.get(ind));
        }
        catch (Exception e)
        {
            System.out.println("Введен неверный индекс");
            return;
        }
        System.out.println("Введите, что хотите сделать:\n1 - изменить название\n2 - изменить адрес\n3 - добавить зал ");
        try
        {
            int answ = sc.nextInt();
            switch (answ)
            {
                case 1:
                    System.out.println("Введите новое название кинотеатра: ");
                    String newName = sc.nextLine();
                    theatres.get(ind).setName(newName);
                    break;
                case 2:
                    System.out.println("Введите новый адрескинотеатра: ");
                    String newAdress = sc.nextLine();
                    theatres.get(ind).setName(newAdress);
                case 3:
                    System.out.println("Введите количество рядов: ");
                    int lines = sc.nextInt();
                    System.out.println("Введите количество мест в ряду: ");
                    int rows = sc.nextInt();
                    System.out.println("Введите цену билета: ");
                    int price = sc.nextInt();
                    System.out.println("Ввыберите тип зала (1 - 2D, 2 - 3D, 3 - VIP, 4 - soft): ");
                    int typeHall = sc.nextInt() - 1;
                    CinemaHall newHall = new CinemaHall(theatres.get(ind).getHalls().size(),lines, rows, price, typeHall);
                    theatres.get(ind).addHall(newHall);
            }
            System.out.println("Изменения успешно внесены!");
        }
        catch(Exception e)
        {
            System.out.println("Неправильно введен индекс");
        }
        theatres.get(0).AddTheatreToBase(';', false);
        for(int i=1; i<theatres.size(); i++)
        {
            theatres.get(i).AddTheatreToBase(';', true);
        }
    }
    public static void AdMode(ArrayList<Advertiser> ads, ArrayList<CinemaTheatre> theatres) throws IOException
    {
        for (int i=0; i<ads.size(); i++) {
            System.out.println(ads.get(i).returnAd());
            System.out.println("1 - принять, 2 - отказать, 0 - выйти: ");
            try {
                Scanner sc = new Scanner(System.in);
                int ans = sc.nextInt();
                switch (ans) {
                    case 1:
                        AcceptOrDecline(ads.get(i), theatres, true);
                        break;
                    case 2:
                        AcceptOrDecline(ads.get(i), theatres, false);
                        break;
                    default:
                        return;
                }
            } catch (Exception e) {
                System.out.println("Введен неверный идентификатор");
            }
        }

    }
    public static void AcceptOrDecline(Advertiser ad, ArrayList<CinemaTheatre> theatres, boolean answ)
    {
        if(answ) {
            CinemaTheatre actualTheatre;
            for (int i = 0; i < theatres.size(); i++)
                if (theatres.get(i).equals(ad.getTheatre())) {
                    actualTheatre = theatres.get(i);
                    ad.setEfficiency(actualTheatre.getTotalTickets());
                    ad.getTheatre().setTotalRevenue(ad.getTheatre().getTotalRevenue()+ad.getMoney());
                    ad.setBudget(ad.getBudget()-ad.getMoney());
                }
        }
        return;
    }
    public static boolean Authorization()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите пароль:");
        String pass = sc.nextLine();
        if(pass.equals(password))
            return true;
        else
            return false;
    }
    public static void AdminAccess(ArrayList<CinemaTheatre> theatres, ArrayList<Sessions> shows, ArrayList<Advertiser> ads) throws IOException, ClassNotFoundException
    {
        Scanner sc = new Scanner(System.in);
        boolean authorized = Authorization();
        if (authorized) {
            System.out.println("Вы в режиме администратора");
            while(true)
            {

                System.out.println("1 - общая выручка\n2 - общее число проданных билетов\n3 - выручка по фильму\n4 - выручка по кинотеатру\n5 - нерентабельные фильмы\n6 - нерентабельные кинотеатры\n7 - выручка по типу залов\n8 - общее число клиентов в разных статусах\n9 - добавить кинотеатр\n10 - редактировать кинотеатр\n11 - реклама\n\n0 - вернуться в изначальное меню\n");
                int a=sc.nextInt();
                switch (a) {
                    case 1:
                        System.out.println("Общая выручка составила "+ Admin.TotalRevenue()+" руб.");
                        break;
                    case 2:
                        System.out.println("Общее число проданных билетов составило " + Admin.TotalTickets() +" билетов");
                        break;
                    case 3:
                        Admin.MovieRevenue();
                        break;
                    case 4:
                        Admin.TheatreRevenue(theatres);
                         break;
                    case 5:
                        //поиск нерентабельного фильма
                        Admin.NotRentalMovie();
                        break;
                    case 6:
                        //поиск нерентабельного фильма
                        Admin.NotRentalTheatre(theatres);
                        break;
                        //отсюда допилить
                    case 7:
                        TypeRevenue();
                        break;
                    case 8:
                        TotalClients();
                        break;
                    case 9:
                        AddTheatre(theatres);
                        break;
                    case 10:
                        ChangeTheatres(theatres);
                        break;
                    case 11:
                        AdMode(ads, theatres);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Введено неверное значение");
                        //sc.nextLine();
                }
            }
        }
        else {
            System.out.println("Неверный пароль");
        }
    }
}
