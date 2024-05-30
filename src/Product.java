
public abstract class Product implements java.io.Serializable{
    private String productID;
    private String productName;
    private int noProductAvail;
    private int productPrice;

    public Product() {
    }

    public Product(String productID, String productName, int noProductAvail, int productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.noProductAvail = noProductAvail;
        this.productPrice = productPrice;
    }

    public String getProductID() {
        return this.productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNoProductAvail() {
        return this.noProductAvail;
    }

    public void setNoProductAvail(int noProductAvail) {
        this.noProductAvail = noProductAvail;
    }

    public int getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public abstract String toString();

    
}