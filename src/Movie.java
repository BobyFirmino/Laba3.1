import java.io.*;
import java.util.ArrayList;

public class Movie {
    private String name;
    private int year;
    private String genre;
    private int length;
    private String format;
    private int totalRevenue;
    public Movie()
    {
        name = "null";
        year = 0;
        genre = "null";
        length = 0;
        format = "null";
        totalRevenue = 0;
    }
    public Movie(String name, int year, String genre, int length, String format)
    {
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.length = length;
        this.format = format;
        totalRevenue = 0;
    }
    public Movie(String name, int year, String genre, int length, String format, int totalRevenue)
    {
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.length = length;
        this.format = format;
        this.totalRevenue = totalRevenue;
    }
    public String getName()
    {
        return name;
    }
    public int getYear()
    {
        return year;
    }
    public String getGenre()
    {
        return genre;
    }
    public int getLength()
    {
        return length;
    }
    public String getFormat()
    {
        return format;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public void setYear(int year)
    {
        this.year=year;
    }
    public void setGenre(String genre)
    {
        this.genre=genre;
    }
    public void setLength(int length)
    {
        this.length=length;
    }
    public void setFormat(String format)
    {
        this.format=format;
    }
    public String output()
    {
        return new String(name + ", " +year + ", " + genre + ", "+ length+" мин., "+ format);
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    public void AddMovieToBase(char sep, boolean append) throws IOException
    {
        FileWriter fw = new FileWriter ("admin/Movies.csv", append);
        fw.write(name+sep+year+sep+genre+sep+length+sep+format+sep+totalRevenue+'\n');
        fw.close();
    }
    public static ArrayList<Movie> getMovies(String sep) throws IOException
    {
        ArrayList<Movie> result = new ArrayList<Movie>();
        File file = new File("admin/Movies.csv");
        BufferedReader br = new BufferedReader(new FileReader((file)));
        String line = br.readLine();
        while(line != null) {
            String[] params = line.split(sep);
            result.add(new Movie(params[0], Integer.parseInt(params[1]), params[2], Integer.parseInt(params[3]), params[4], Integer.parseInt(params[5])));
            line = br.readLine();
        }
        return result;
    }
    public boolean Equals(Movie movie)
    {
        return (this.name.equals(movie.name) && this.year==movie.year);
    }
}
