public class User {
    private String userName;
    private String password;
    private int purchaseHistory;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.purchaseHistory = 0;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getPurchaseHistory(){
        return purchaseHistory;
    }
    public void setPurchaseHistory(){
        this.purchaseHistory++;
    }
    public void setPurchaseHistory(int purchaseHistory){
        this.purchaseHistory = purchaseHistory;
    }

}
