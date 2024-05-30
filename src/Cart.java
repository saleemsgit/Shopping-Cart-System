import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Cart extends JFrame {
    
    private JFrame frame;
    private int productItems;
    private double totalAmount,finalPrice;
    private double discountAmount,firstUserDiscount;
    private int clothingCount, electronicCount;
    private int purchaseHistory;
    public Cart(){   
       
        purchaseHistory = WestminsterShoppingManager.loggedInUser.getPurchaseHistory(); 

        ArrayList<Product> checkoutCart = new ArrayList<>();
        checkoutCart = GUIApplication.getShoppingList();
        productItems = 0;
        totalAmount = 0;
        discountAmount = 0;
        clothingCount = electronicCount = 0;

        frame = new JFrame();
        frame.setTitle("Shopping Cart");
        frame.setSize(640, 480);

        frame.setLayout(new GridLayout(2,1));
        


        Map<Product, Integer> productCounts = new HashMap<>();

        for (Product product : checkoutCart) {
            productCounts.put(product, productCounts.getOrDefault(product, 0) + 1);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (checkoutCart.size()>0){
    
            JPanel gridPanel = new JPanel(new GridLayout(0, 3));
            
            JLabel productLabelHeader = new JLabel("Product");
            productLabelHeader.setHorizontalAlignment(SwingConstants.CENTER);
            productLabelHeader.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(productLabelHeader);
            JLabel quantityLabelHeader = new JLabel("Quantity");
            quantityLabelHeader.setHorizontalAlignment(SwingConstants.CENTER);
            quantityLabelHeader.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(quantityLabelHeader);
            JLabel priceLabelHeader = new JLabel("Price");
            priceLabelHeader.setHorizontalAlignment(SwingConstants.CENTER);
            priceLabelHeader.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(priceLabelHeader);


            for (Map.Entry<Product, Integer> entry : productCounts.entrySet()) {
                productItems++;
                Product product = entry.getKey();
                int count = entry.getValue();

                JPanel productInfoPanel = new JPanel(new GridLayout(3,1));
                productInfoPanel.add(new JLabel(product.getProductID(), SwingConstants.CENTER));
                productInfoPanel.add(new JLabel(product.getProductName(), SwingConstants.CENTER));
                if (product instanceof Electronic){
                    electronicCount+= 1*count;
                    Electronic electronicProduct = (Electronic) product;
                    productInfoPanel.add(new JLabel(electronicProduct.getProductBrand()+","+electronicProduct.getProductWarranty(), SwingConstants.CENTER));
                } else if (product instanceof Clothing){
                    clothingCount+= 1*count;
                    Clothing clothingProduct = (Clothing) product;
                    productInfoPanel.add(new JLabel(clothingProduct.getProductColor()+","+clothingProduct.getProductSize(), SwingConstants.CENTER));
                }

                productInfoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(productInfoPanel);

                String productQuantity = Integer.toString(count);
                JLabel label2 = new JLabel(productQuantity);
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                label2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(label2);

                String productPrice = Integer.toString(product.getProductPrice());
                JLabel label3 = new JLabel(productPrice);
                label3.setHorizontalAlignment(SwingConstants.CENTER); 
                label3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(label3);


                totalAmount += product.getProductPrice()*count;

                 
            }

            if (clothingCount >= 3 || electronicCount >=3 ) discountAmount = totalAmount/100*20; 
            if (purchaseHistory == 1){
                firstUserDiscount = totalAmount/100*10;
            }
            finalPrice = totalAmount - discountAmount - firstUserDiscount;
            

            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            


     
            JScrollPane scrollPane1 = new JScrollPane(gridPanel);
         
            scrollPane1.setBorder(productItems == 1 ? new EmptyBorder(30, 30, 90, 30): new EmptyBorder(30, 30, 0, 30) );
    
            frame.add(scrollPane1);

        }
        else{
            JLabel noItems = new JLabel("No Items");
            noItems.setHorizontalAlignment(SwingConstants.CENTER);
            frame.add(noItems);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel priceDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        /////////////////////////////////ROW 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 4.0;
        JLabel totalLabel = new JLabel("Total");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceDetailsPanel.add(totalLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 4.0;
        JLabel total = new JLabel(Double.toString(totalAmount));
        total.setHorizontalAlignment(SwingConstants.CENTER);
        priceDetailsPanel.add(total, gbc);


        /////////////////////////////////ROW 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 4.0;
        JLabel firstDiscountLabel = new JLabel("First Purchase Discount (10%)");
        firstDiscountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceDetailsPanel.add(firstDiscountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 4.0;
        JLabel firstDiscount = new JLabel(Double.toString(firstUserDiscount));
        firstDiscount.setHorizontalAlignment(SwingConstants.CENTER);
        priceDetailsPanel.add(firstDiscount, gbc);


        /////////////////////////////////ROW 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 4.0;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel threeItemDiscountLabel = new JLabel("Three Items in Same Category Discount (20%)");
        threeItemDiscountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceDetailsPanel.add(threeItemDiscountLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 4.0;
        JLabel threeItemDiscount = new JLabel(Double.toString(discountAmount));
        threeItemDiscount.setHorizontalAlignment(SwingConstants.CENTER);
        priceDetailsPanel.add(threeItemDiscount, gbc);


        /////////////////////////////////ROW 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 4.0;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel finalAmountLabel = new JLabel("Final Total");
        finalAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceDetailsPanel.add(finalAmountLabel, gbc);
    

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 4.0;
        JLabel finalAmount = new JLabel(Double.toString(finalPrice));
        finalAmount.setHorizontalAlignment(SwingConstants.CENTER);
        priceDetailsPanel.add(finalAmount, gbc);
        

        priceDetailsPanel.setBorder(new EmptyBorder(0, 0, 0, 30));

        frame.add(priceDetailsPanel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                new GUIApplication();
            }
        });
    }    

}
