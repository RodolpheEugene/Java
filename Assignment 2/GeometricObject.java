public class GeometricObject {

    // declare variables
    private String color = "White";
    private boolean filled;
    private java.util.Date dateCreated;

    // Constructors
    public GeometricObject(){
        dateCreated = new java.util.Date();
    }
    public GeometricObject(String color, boolean filled){
        dateCreated = new java.util.Date();
        this.color = color;
        this.filled = filled;
    }

    // Accessors and Mutators
    public String getColor(){
        return color;
    }
    public void setColor(String Color){
        this.color = color;
    }
    public boolean isFilled(){
        return filled;
    }
    public void setFilled(boolean filled){
        this.filled = filled;
    }
    public java.util.Date getDateCreated(){
        return dateCreated;
    }

    // Methods
    public String toString(){
        return "created on " + dateCreated + "\ncolor: " + color + " and filled: " + filled;
    }
}
