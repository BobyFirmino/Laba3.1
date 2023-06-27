import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Sessions {
    private CinemaTheatre theatre;
    private int hallNumber;
    private Movie movie;
    private Date time;
    private String hallType;
    private int totalRevenue;
    public Sessions()
    {
        theatre=new CinemaTheatre();
        movie=new Movie();
        time=new Date(0, 1, 1, 0, 0, 0);
        setHallNumber(1);
        hallType = theatre.getHalls().get(hallNumber).getType();
        setTotalRevenue(0);
    }
    public Sessions(CinemaTheatre theatre, Movie movie, Date time, int hallNumber)
    {
        this.theatre=theatre;
        this.movie=movie;
        this.time=time;
        this.setHallNumber(hallNumber);
        hallType = theatre.getHalls().get(hallNumber).getType();
        setTotalRevenue(0);
    }
    public Sessions(CinemaTheatre theatre, Movie movie, Date time, int hallNumber, String hallType, int totalRevenue)
    {
        this.theatre=theatre;
        this.movie=movie;
        this.time=time;
        this.setHallNumber(hallNumber);
        this.hallType = hallType;
        setTotalRevenue(totalRevenue);
    }
    public CinemaTheatre getTheatre()
    {
        return theatre;
    }
    public Movie getMovie()
    {
        return movie;
    }
    public Date getTime()
    {
        return time;
    }
    public void setTheatre(CinemaTheatre theatre)
    {
        this.theatre=theatre;
    }
    public void setMovie(Movie movie)
    {
        this.movie=movie;
    }
    public void setTime(Date time)
    {
        this.time=time;
    }
    public String output(int ind)
    {
        return new String(movie.output()+"; "+ theatre.output(ind) +"; "+time);
    }
    public int getHallNumber() {
        return hallNumber;
    }
    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }
    public String getHallType() {
        return hallType;
    }
    public int getTotalRevenue() {
        return totalRevenue;
    }
    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    public void AddShowToBase(char sep, boolean append) throws IOException
    {
        ArrayList<Movie> movies = Movie.getMovies(";");
        ArrayList<CinemaTheatre> theatres = CinemaTheatre.getTheatres(";");
        int movieInd=0, theatreInd=0;
        for(; movieInd<movies.size(); movieInd++)
        {
            if(movies.get(movieInd).Equals(movie))
                break;
        }
        for(; theatreInd<theatres.size(); theatreInd++)
        {
            if(theatres.get(theatreInd).Equals(theatre))
                break;
        }
        FileWriter fw = new FileWriter ("admin/Shows.csv", append);
        fw.write(Integer.toString(movieInd)+sep+Integer.toString(theatreInd)+sep+Integer.toString(time.getYear())+sep+Integer.toString(time.getMonth())+sep+Integer.toString(time.getDay())+sep+Integer.toString(time.getHours())+sep+Integer.toString(time.getMinutes())+sep+Integer.toString(hallNumber)+sep+hallType+sep+Integer.toString(totalRevenue)+'\n');
        fw.close();
    }
    public static ArrayList<Sessions> getShows(String sep) throws IOException
    {
        ArrayList<CinemaTheatre> theatres = CinemaTheatre.getTheatres(";");
        ArrayList<Movie> movies = Movie.getMovies(";");
        ArrayList<Sessions> result = new ArrayList<Sessions>();
        File file = new File("admin/Shows.csv");
        BufferedReader br = new BufferedReader(new FileReader((file)));
        String line = br.readLine();
        while(line != null) {
            String[] params = line.split(sep);
            result.add(new Sessions(theatres.get(Integer.parseInt(params[0])), movies.get(Integer.parseInt(params[1])), new Date(Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]), Integer.parseInt(params[6]), 0), Integer.parseInt(params[7]), params[8], Integer.parseInt(params[9])));
            line = br.readLine();
        }
        return result;
    }
    public boolean Equals(Sessions show)
    {
        return (this.movie.Equals(show.getMovie()) && this.theatre.Equals(show.getTheatre()) && this.time.equals(show.getTime()));
    }
    public int getHallTypeNumber()
    {
        if (hallType.equals("2D"))
            return 0;
        if (hallType.equals("3D"))
            return 1;
        if (hallType.equals("VIP"))
            return 2;
        if (hallType.equals("soft"))
            return 3;
        return 0;
    }
}
