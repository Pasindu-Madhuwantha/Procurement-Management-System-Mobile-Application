package com.example.product_management;

public class Products {
    private int id;
    private String ProductName;
    private String ProductDescription;
    private String supplier;
    private String Quantity;
    private String price;
    byte[] productImage;
    byte[] coverimage;


    public Products(int id, String ProductName, String supplier, String Quantity, byte[] productImage) {
        this.id = id;
        this.ProductName = ProductName;
        this.supplier=supplier;
        this.productImage = productImage;
        this.Quantity=Quantity;
    }



    public void MoivesUpdate(int id, String movivename, String movietype, String moviestatus, String moviehours, String description, byte[] movieimage){

        this.id=id;
        this.ProductName =movivename;
        this.ProductDescription =movietype;
        this.Quantity =moviehours;
        this.supplier =moviestatus;
        this.price =description;
        this.productImage =movieimage;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return Quantity;
    }
}



