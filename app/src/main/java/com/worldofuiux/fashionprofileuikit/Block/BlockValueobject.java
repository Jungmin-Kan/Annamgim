package com.worldofuiux.fashionprofileuikit.Block;

import java.util.ArrayList;

public class BlockValueobject extends ArrayList<BlockValueobject> {
    private String $class;
    private String asset;
    private String newUser;
    private String newPrice;
    private String transactionId;
    private String timestamp;

    public BlockValueobject(String $class,String asset,String newUser,String newPrice,String transactionId,String timestamp){
        this.$class = $class;
        this.asset = asset;
        this.newUser = newUser;
        this.newPrice = newPrice;
        this.transactionId = transactionId;
        this.timestamp = timestamp;
    }

    public void set$class(String $class) { this.$class = $class; }
    public String get$class() { return $class; }

    public void setAsset(String asset) { this.asset = asset; }
    public String getAsset() { return asset; }

    public void setNewUser(String newUser) { this.newUser = newUser; }
    public String getNewUser() { return newUser; }

    public void setNewPrice(String newPrice) { this.newPrice = newPrice; }
    public String getNewPrice() { return newPrice; }

    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getTransactionId() { return transactionId; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getTimestamp() { return timestamp; }
}
