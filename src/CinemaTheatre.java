import java.io.*;
import java.util.*;
public class CinemaTheatre {
    private ArrayList<CinemaHall> halls;
    private int capacity;
    private String name;
    private String address;
    private String[] formats;
    private int totalRevenue;
    private int totalTickets=0;
    public CinemaTheatre()
    {
        name="Кинотеатр №1";
        address = "Улица Пушкина, дом Колотушкина";
        capacity = 400;
        formats = new String[]{"2D", "3D"};
        halls = new ArrayList<CinemaHall>();
        halls.add(new CinemaHall());
        totalRevenue = 0;
    }
    public CinemaTheatre(String name, String address, String[] formats, ArrayList<CinemaHall> halls)
    {
        this.name = name;
        this.address = address;
        this.formats = formats;
        this.halls = halls;
        capacity=0;
        for(int i=0; i<halls.size(); i++)
        {
            capacity+=halls.get(i).getSeats().length*halls.get(i).getSeats()[0].length;
        }
        totalRevenue = 0;
    }
    public CinemaTheatre(String name, String address, ArrayList<CinemaHall> halls, int totalRevenue)
    {
        this.name = name;
        this.address = address;
        this.formats = new String[]{"2D", "3D"};
        this.halls = halls;
        capacity=0;
        for(int i=0; i<halls.size(); i++)
        {
            capacity+=halls.get(i).getSeats().length*halls.get(i).getSeats()[0].length;
        }
        this.totalRevenue = totalRevenue;
    }
    public CinemaTheatre(String name, String address, int totalRevenue)
    {
        this.name = name;
        this.address = address;
        this.formats = new String[]{"2D", "3D"};
        this.halls = new ArrayList<CinemaHall>();
        capacity=0;
        /*for(int i=0; i<halls.size(); i++)
        {
            capacity+=halls.get(i).getSeats().length*halls.get(i).getSeats()[0].length;
        }*/
        this.totalRevenue = totalRevenue;
    }
    public CinemaTheatre(String name, String address, int totalRevenue, int totalTickets)
    {
        this.name = name;
        this.address = address;
        this.formats = new String[]{"2D", "3D"};
        this.halls = new ArrayList<CinemaHall>();
        capacity=0;
        /*for(int i=0; i<halls.size(); i++)
        {
            capacity+=halls.get(i).getSeats().length*halls.get(i).getSeats()[0].length;
        }*/
        this.totalRevenue = totalRevenue;
        this.totalTickets = totalTickets;
    }
    public CinemaTheatre(String name, String address, String[] formats)
    {
        this.name = name;
        this.address = address;
        this.formats = formats;
        halls = new ArrayList<CinemaHall>();
        capacity=400;
        halls.add(new CinemaHall());
        totalRevenue = 0;
    }
    public ArrayList<CinemaHall> getHalls()
    {
        return halls;
    }
    public int getCapacity()
    {
        return capacity;
    }
    public String getName()
    {
        return name;
    }
    public String getAddress()
    {
        return address;
    }
    public String[] getFormats()
    {
        return formats;
    }
    public void setHall(ArrayList<CinemaHall> halls)
    {
        this.halls = halls;
    }
    /*public void setCapacity(int capacity)
    {
        this.capacity=capacity;
    }*/
    public void setName(String name)
    {
        this.name=name;
    }
    public void setAddress(String address)
    {
        this.address=address;
    }
    public void setFormats(String[] formats)
    {
        this.formats=formats;
    }
    public String output(int hallNumber)
    {
        try {
            return new String(name + ", " + address + ", " + halls.get(hallNumber).output());
        }
        catch(Exception e)
        {
            return "Ошибка вывода зала";
        }
    }
    public String output()
    {
        try {
            return new String(name + ", " + address);
        }
        catch(Exception e)
        {
            return "Ошибка вывода зала";
        }
    }
    public int getTotalRevenue() {
        return totalRevenue;
    }
    public void setTotalRevenue
            (int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    public void addHall(CinemaHall hall)
    {
        halls.add(hall);
        capacity+=hall.getLines()*hall.getRows();
    }
    public void AddTheatreToBase(char sep, boolean append) throws IOException
    {
        FileWriter fw = new FileWriter ("admin/Theatres.csv", append);
        fw.write(name+sep+address+sep+totalRevenue+sep+totalTickets+'\n');
        fw.close();
    }
    public static ArrayList<CinemaTheatre> getTheatres(String sep) throws IOException
    {
        ArrayList<CinemaTheatre> result = new ArrayList<CinemaTheatre>();
        File file = new File("admin/Theatres.csv");
        BufferedReader br = new BufferedReader(new FileReader((file)));
        String line = br.readLine();
        while(line != null) {
            String[] params = line.split(sep);
            result.add(new CinemaTheatre(params[0], params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3])));
            line = br.readLine();
        }
        return result;
    }
    public boolean Equals(CinemaTheatre theatre)
    {
        if(this.name.equals(theatre.name) && this.address.equals(theatre.address))
        {
            return true;
        }
        return false;
    }
    public void AddHall(CinemaHall newHall)
    {
        halls.add(newHall);
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }
}
