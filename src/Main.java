import java.util.*;
import java.io.*;

public class Main {
    public static void chooseShow(ArrayList<Sessions> shows, ArrayList<Client> clients, ArrayList<Movie> movies, ArrayList<CinemaTheatre> theatres) throws IOException
    {
        while(true) {
            System.out.println("Введите ваши ФИО, номер телефона, эл. почту и бюджет (каждый параметр в отдельной строке) или 0 для выхода в начальное меню:");
            Scanner sc = new Scanner(System.in);
            String name = "", phone = "", email = "";
            int budget = 0;
            try {
                name = sc.nextLine();
                phone = sc.nextLine();
                email = sc.nextLine();
                budget = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Вероятно, вы ввели бюджет в неверном формате");
                return;
            }

            //Проверяем выход в меню
            if(name.equals("0"))
                return;

            //Проверяем наличие данного клиента
            int clientInd = clients.size();
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).Equals(name, phone, email))
                    clientInd = i;
            }
            if (clientInd == clients.size())
                clients.add(new Client(name, phone, email, budget));
            else
                clients.get(clientInd).setBudget(budget);

            //сессия покупки билета
            clients.get(clientInd).BuyTicket(shows, movies, theatres);

            //запись измененного списка клиентов в базу
            if(clients.size()>0)
                clients.get(0).AddClientToBase(';', false);
            for(int i=1; i<clients.size(); i++)
            {
                clients.get(i).AddClientToBase(';', true);
            }
            SerializeClient.serialize(clients);
        }
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
    public static void AddClientToBase(Client c, char sep) throws IOException
    {
        FileWriter fw = new FileWriter ("admin/Clients.csv", true);
        fw.write(c.getName()+sep+c.getPhoneNumber()+sep+c.getEmail()+sep+c.getBudget()+sep+c.getStatus()+sep+c.getTotalTickets());
        fw.close();
    }
    public Client ReadClient(String sep) throws IOException
    {
        File file = new File("admin/Clients.csv");
        BufferedReader br = new BufferedReader(new FileReader((file)));
        String[] params = br.readLine().split(sep);
        Client c = new Client(params[0], params[1], params[2], Integer.parseInt(params[3]), params[4], Integer.parseInt(params[5]));
        return c;
    }

    public static void AdvMode(ArrayList<CinemaTheatre> theatres, ArrayList<Advertiser> ads)
    {
        System.out.println("Введите ваши ФИО, номер телефона, эл. почту и бюджет (каждый параметр в отдельной строке) или 0 для выхода в начальное меню:");
        Scanner sc = new Scanner(System.in);
        String name = "", phone = "", email = "", product="", text="";
        int budget = 0, money = 0;
        try {
            name = sc.nextLine();
            phone = sc.nextLine();
            email = sc.nextLine();
            budget = sc.nextInt();
            if(name.equals("0"))
                return;
            System.out.println("Введите название продукта, текст рекламы и стоимость предложения:");
            sc.nextLine();
            product = sc.nextLine();
            text = sc.nextLine();
            money = sc.nextInt();
            System.out.println("Выберите кинотеатр:");
            for(int i=0; i<theatres.size(); i++) {
                System.out.println(i+1 + " - " + theatres.get(i).output());
            }
            int ind = sc.nextInt()-1;
            int adsInd = ads.size();
            for (int i = 0; i < ads.size(); i++) {
                if (ads.get(i).Equals(new Advertiser(name, phone, email, budget, theatres.get(ind), product, text, money)))
                    adsInd = i;
            }
            if (adsInd == ads.size())
                ads.add(new Advertiser(name, phone, email, budget, theatres.get(ind), product, text, money));
            else
                ads.get(adsInd).setBudget(budget);
            //System.out.println("Индекс типа "+ adsInd);
            System.out.println("Эффективность по вышему предложению составляет "+ads.get(adsInd).getEfficiency());
        }
        catch(Exception e)
        {
            System.out.println("Вы ввели бюджет в неверном формате или неверный индекс кинотеатра");
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //ArrayList<Client> clienta = Client.getClients(";");
        //ArrayList<Client> clients = new ArrayList<Client>();
        //SerializeClient.serialize(clienta);
        //System.out.println("результат десериализации");
        ArrayList<Client> clients = SerializeClient.deserialize();
        for(int i=0; i<clients.size(); i++)
            System.out.println(clients.get(i).output());
        /*clients.get(0).setTotalTickets(1);
        SerializeClient.serialize(clients);
        clients = SerializeClient.deserialize();*/
        //создаем фильмы
        Movie DU = new Movie("Джанго Освобожденный", 1993, "драма, вестерн", 195, "2D");
        //printToFile((String)("admin/movies/"+DU.getName()+".txt"), true, "0");
        Movie Avatar2 = new Movie("Аватар 2 Путь воды", 2022, "фантастика, блокбастер", 192, "3D");
        //printToFile((String)("admin/movies/"+Avatar2.getName()+".txt"), true, "0");
        Movie JW3 = new Movie("Джон Уик 3", 2021, "боевик, триллер", 39, "2D");
        //printToFile((String)("admin/movies/"+JW3.getName()+".txt"), true, "0");

        //1
        /*ArrayList<Movie> movies = new ArrayList<Movie>();
        movies.add(DU);
        movies.add(Avatar2);
        movies.add(JW3);
        for(int i=0; i<movies.size(); i++)
        {
            movies.get(i).AddMovieToBase(';', true);
        }*/
        //2
        ArrayList<Movie> movies = Movie.getMovies(";");

        //создаем кинотеатры
        CinemaTheatre GUM = new CinemaTheatre("ГУМ Кинозал", "Октябрьская, 36", new String[]{"2D", "3D"});
        GUM.addHall(new CinemaHall(1, 10, 20, 300, 1));
        GUM.addHall(new CinemaHall(2, 10, 10, 500, 3));
        CinemaTheatre ERE1 = new CinemaTheatre("Стар Ереван Плаза", "Большая Черемушкинская, 1", new String[]{"2D", "3D"});
        ERE1.addHall(new CinemaHall(1, 15, 25, 300, 1));
        ERE1.addHall((new CinemaHall(2, 10, 12, 500, 2)));
        CinemaTheatre FORMULA = new CinemaTheatre("Формула Кино", "Братеевская, 24", new String[]{"2D", "3D"});
        FORMULA.addHall(new CinemaHall(1, 15, 15, 300, 2));

        ArrayList<CinemaTheatre> theatres = new ArrayList<CinemaTheatre>();
        theatres.add(GUM);
        theatres.add(ERE1);
        theatres.add(FORMULA);
        //1
        /*for(int i=0; i<theatres.size(); i++)
        {
            theatres.get(i).AddTheatreToBase(';', true);
        }*/
        //2
        ArrayList<CinemaTheatre> theatresFromFile = CinemaTheatre.getTheatres(";");
        for(int i=0; i<theatres.size(); i++)
        {
            for(int j=0; j<theatresFromFile.size(); j++)
            {
                if(theatres.get(i).Equals(theatresFromFile.get(j))) {
                    theatres.get(i).setTotalRevenue(theatresFromFile.get(j).getTotalRevenue());
                    theatres.get(i).setTotalTickets(theatresFromFile.get(j).getTotalTickets());
                }
            }
        }

        //создаем сеансы
        ArrayList<Sessions> shows=new ArrayList<Sessions>();
        shows.add(new Sessions(GUM, movies.get(0), new Date(123, 0, 1, 18, 0), 2));
        shows.add(new Sessions(ERE1, movies.get(0), new Date(123, 0, 2, 15, 0), 2));
        shows.add(new Sessions(GUM, movies.get(1), new Date(123, 0, 1, 12, 0), 1));
        shows.add(new Sessions(FORMULA, movies.get(1), new Date(123, 0, 1, 18, 0), 1));
        shows.add(new Sessions(GUM, movies.get(2), new Date(123, 0, 2, 15, 0), 0));
        shows.add(new Sessions(ERE1, movies.get(2), new Date(123, 0, 1, 18, 0), 2));
        shows.add(new Sessions(FORMULA, movies.get(2), new Date(123, 0, 2, 13, 0), 0));
        ArrayList<Advertiser> ads = new ArrayList<Advertiser>();
        //1
        /*for(int i = 0; i < shows.size(); i++){
            shows.get(i).AddShowToBase(';', true);
        }*/
        //2
        while (true)
        {
            System.out.println("Введите на английской раскладке c, чтобы войти как клиент; a, чтобы войти как администратор; v, чтобы войти как рекламодатель.");
            Scanner sc = new Scanner(System.in);
            String identifier = sc.nextLine();
            if(identifier.equals("c")) {
                chooseShow(shows, clients, movies, theatres);
            }
            else
            {
                if(identifier.equals("a"))
                {
                    Admin.AdminAccess(theatres, shows, ads);
                }
                else
                    if(identifier.equals("v"))
                    {
                        AdvMode(theatres, ads);
                    }
                    else
                        System.out.println("Введен неизвестный идентификатор, Попробуйте снова");
            }
            System.out.println("\n");
        }
    }
}