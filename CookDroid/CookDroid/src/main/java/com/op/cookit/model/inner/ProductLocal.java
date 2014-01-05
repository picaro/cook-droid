package com.op.cookit.model.inner;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/*
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 */
@Table(name = "product")
public class ProductLocal extends Model {

	// Define table fields
	@Column(name = "name")
	private String name;
	
	public ProductLocal() {
		super();
	}
	
	// Parse model from JSON
	public ProductLocal(JSONObject object){
		super();

		try {
			this.name = object.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
