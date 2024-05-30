public class Clothing extends Product{
    private String productSize;
    private String productColor;

    public Clothing(String productID, String productName, int noProductAvail, int productPrice){
        super(productID, productName, noProductAvail, productPrice);
    }

    public Clothing(String productSize, String productColor) {
        this.productSize = productSize;
        this.productColor = productColor;
    }

    public Clothing(String productID, String productName, int noProductAvail, int productPrice, String productSize, String productColor) {
        super(productID, productName, noProductAvail, productPrice);
        this.productSize = productSize;
        this.productColor = productColor;
    }

    public String getProductSize() {
        return this.productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductColor() {
        return this.productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    @Override
    public String toString() {
        return String.format("Product ID : %s\nProduct Name : %s\nNo. Products Available : %d\nProduct Price : %d\nProduct Size : %s\nProduct Color : %s", this.getProductID(), this.getProductName(), this.getNoProductAvail(), this.getProductPrice(), this.getProductSize(), this.getProductColor());
    }

}
