public class Advertiser extends Client{

    private CinemaTheatre theatre;
    private String product;
    private String text;
    private int money;
    private int efficiency;
    public Advertiser(String name, String phone, String email, int budget, CinemaTheatre theatre, String prod, String text, int mon)
    {
        super(name, phone, email, budget);
        this.setTheatre(theatre);
        this.product = prod;
        this.text = text;
        this.setMoney(mon);
    }
    public String returnAd ()
    {
        return new String(getTheatre().output() +"; " + product+"; "+ text +"; " + getMoney() +" руб.");
    }
    public int getEfficiency() {
        return efficiency;
    }
    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public CinemaTheatre getTheatre() {
        return theatre;
    }

    public void setTheatre(CinemaTheatre theatre) {
        this.theatre = theatre;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
