package com.op.cookit.model.inner;

import android.content.ContentValues;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.op.cookit.model.Product;
import com.op.cookit.syncadapter.ProductsContentProvider;

import java.util.List;

/*
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 */
@Table(name = "product")
public class ProductLocal extends Model {

	// Define table fields
	@Column(name = "name")
	private String name;

    @Column(name = "outid")
    private Integer outid;

    @Column(name = "note")
    private String note;

    @Column(name = "imgpath")
    private String imgPath;

    @Column(name = "shoplistid")
    private Integer shoplistid;

    @Column(name = "crossed")
    private Boolean crossed = false;

	public ProductLocal() {
		super();
	}
	
	public ProductLocal(Product product){
	    name = product.getName();
        note = product.getNote();
        crossed = product.getCrossed();
        outid = product.getId();
	}

    public ProductLocal(ContentValues values) {
        setName((String)values.get(ProductsContentProvider.Columns.NAME));
        setNote((String)values.get(ProductsContentProvider.Columns.NOTE));
        setOutid((Integer)values.get(ProductsContentProvider.Columns.OUTID));
        setCrossed((Boolean)values.get(ProductsContentProvider.Columns.CROSSED));
        setShoplistid((Integer)values.get(ProductsContentProvider.Columns.SHOPLISTID));
    }

    // Getters
	public String getName() {
		return name;
	}

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOutid() {
        return outid;
    }

    public void setOutid(Integer outid) {
        this.outid = outid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getShoplistid() {
        return shoplistid;
    }

    public void setShoplistid(Integer shoplistid) {
        this.shoplistid = shoplistid;
    }

    public Boolean getCrossed() {
        return crossed;
    }

    public void setCrossed(Boolean crossed) {
        this.crossed = crossed;
    }

    // Record Finders
	public static ProductLocal byId(long id) {
	   return new Select().from(ProductLocal.class).where("id = ?", id).executeSingle();
	}
	
	public static List<ProductLocal> recentItems() {

      return new Select().from(ProductLocal.class).execute();// .orderBy("id DESC").limit("300").execute();
	}
}
