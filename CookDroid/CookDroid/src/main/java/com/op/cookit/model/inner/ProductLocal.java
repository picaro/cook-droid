package com.op.cookit.model.inner;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.op.cookit.model.Product;

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

    @Column(name = "shoplistid")
    private Integer shoplistid;

    @Column(name = "crossed")
    private Boolean crossed = false;

	public ProductLocal() {
		super();
	}
	
	public ProductLocal(Product object){

	    ///this.name = .getString("title");
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	// Record Finders
	public static ProductLocal byId(long id) {
	   return new Select().from(ProductLocal.class).where("id = ?", id).executeSingle();
	}
	
	public static List<ProductLocal> recentItems() {
      return new Select().from(ProductLocal.class).orderBy("id DESC").limit("300").execute();
	}
}
