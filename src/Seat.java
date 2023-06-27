public class Seat {
    private int line;
    private int row;
    private boolean free;//true = свободно, false = занято
    public Seat() {
        this.line=1;
        this.row=1;
        this.free=true;
    }
    public Seat(int line, int row, boolean free) {
        this.line=line;
        this.row=row;
        this.free=free;
    }
    public int[] getSeat(int row, int seat)
    {
        return new int[]{row, seat};
    }
    public boolean checkFree()
    {
        return free;
    }
    public void setFree(boolean free)
    {
        this.free=free;
    }
    public String output()
    {
        return new String((line+1)+"-й ряд, "+(row+1)+"-е место");
    }
    /*public int getLine()
    {
        return line;
    }
    public void setLine(int line)
    {
        this.line=line;
    }*/
}
