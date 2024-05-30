import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> cart;
    // private

    public ShoppingCart(){
        cart = new ArrayList<Product>();
    }
 
    public void addProduct(Product product){

        this.cart.add(product);
    }

    public void removeProduct(Product product){
        this.cart.remove(product);
    }

    public void calculateTotal(){
        for (Product item : cart){
            item.getProductPrice();
        }
    }
    public ArrayList<Product> getCart(){
        return cart;
    }
}
