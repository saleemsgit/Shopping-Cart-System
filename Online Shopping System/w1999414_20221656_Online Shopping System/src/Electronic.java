public class Electronic extends Product {
    private String productBrand;
    private int productWarranty;

    public Electronic(String productID, String productName, int noProductAvail, int productPrice){
        super(productID, productName, noProductAvail, productPrice);
    }

    public Electronic(String productBrand, int productWarranty) {
        this.productBrand = productBrand;
        this.productWarranty = productWarranty;
    }

    public Electronic(String productID, String productName, int noProductAvail, int productPrice, String productBrand, int productWarranty) {
        super(productID, productName, noProductAvail, productPrice);
        this.productBrand = productBrand;
        this.productWarranty = productWarranty;
    }


    public String getProductBrand() {
        return this.productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getProductWarranty() {
        return this.productWarranty;
    }

    public void setProductWarranty(int productWarranty) {
        this.productWarranty = productWarranty;
    }

    @Override
    public String toString() {
        return String.format("Product ID : %s\nProduct Name : %s\nNo. Products Available : %d\nProduct Price : %d\nProduct Brand : %s\nProduct Warranty : %d", this.getProductID(), this.getProductName(), this.getNoProductAvail(), this.getProductPrice(), this.getProductBrand(), this.getProductWarranty());
    }
}
