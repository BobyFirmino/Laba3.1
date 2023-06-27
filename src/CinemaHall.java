import java.util.ArrayList;
import java.util.Random;

public class CinemaHall
{
    int hallNumber;
    private int lines;
    private int rows;
    private int price;
    private Seat[][] seats;
    private String type; //2D, 3D, VIP, soft
    public CinemaHall(int hallNumber, int lines, int rows, int price, Seat[][] seats, int hallType)
    {
        this.hallNumber=hallNumber;
        this.lines=lines;
        this.rows = rows;
        this.price=price;
        this.seats=seats;
        this.setType(hallType);
    }
    public CinemaHall(int hallNumber, int lines, int rows, int price, int hallType)//рандомный непустой зал
    {
        this.hallNumber=hallNumber;
        this.lines=lines;
        this.rows = rows;
        this.price=price;
        seats = new Seat[lines][rows];
        for(int i=0; i<lines; i++)
        {
            Random random = new Random();
            for(int j=0; j<rows; j++)
                seats[i][j]=new Seat(i, j, random.nextBoolean());
        }
        this.setType(hallType);
    }
    public CinemaHall()
    {
        this.hallNumber=0;
        this.lines=20; this.rows = 20;
        seats = new Seat[20][20];
        for(int i=0; i<20; i++)
        {
            for(int j=0; j<20; j++)
                seats[i][j]=new Seat(i, j, true);
        }
        this.setType(0);
        price=250;
    }
    public Seat[][] getSeats()
    {
        return seats;
    }
    public void setSeats(Seat[][] seats)
    {
        this.seats=seats;
    }
    public int getPrice()
    {
        return price;
    }
    public void setPrice(int price)
    {
        this.price=price;
    }
    public int getHallNumber()
    {
        return hallNumber;
    }
    public void setHallNumber(int hallNumber)
    {
        this.hallNumber=hallNumber;
    }
    public int getLines()
    {
        return lines;
    }
    public void setLine(int lines)
    {
        this.lines=lines;
    }
    public int getRows()
    {
        return rows;
    }
    public void setRows(int rows)
    {
        this.rows=rows;
    }
    public boolean hasFreeSeats()
    {
        for(int i=0; i<seats.length; i++)
        {
            for(int j=0; j<seats[0].length; i++)
            {
                if(seats[i][j].checkFree())
                    return true;
            }
        }
        return false;
    }
    public ArrayList<Seat> returnFreeSeats()
    {
        ArrayList<Seat> result =new ArrayList<Seat>();
        for(int i=0; i<seats.length; i++)
        {
            for(int j=0; j<seats[0].length; j++)
            {
                if(seats[i][j].checkFree())
                    result.add(seats[i][j]);
            }
        }
        return result;
    }
    public String getType() {
        return type;
    }
    public void setType(int hallType) {
        switch (hallType)
        {
            case 1: this.type = "3D"; break;
            case 2: this.type = "VIP"; break;
            case 3: this.type = "soft"; break;
            default: this.type = "2D"; break;
        }
    }
    public String output()
    {
        try {
            return new String((hallNumber+1) + "-й зал, " + getType()+"-зал, "+price+"руб.");
        }
        catch(Exception e)
        {
            return "Ошибка вывода зала";
        }
    }
}
