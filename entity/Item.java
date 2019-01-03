package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Because the original TicketMaster response is dirty. We need to 
 * reorganize it
 * @author Shu
 *
 */
public class Item {
	private String itemId; 
	private String name;
	private double rating; 
	private String address; 
	private Set<String> categories; 
	private String imageUrl; 
	private String url; 
	private double distance;
	
	public String getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public double getRating() {
		return rating;
	}
	public String getAddress() {
		return address;
	}
	public Set<String> getCategories() {
		return categories;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public double getDistance() {
		return distance;
	}
	
	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("item_id", itemId); 
			obj.put("name", name); 
			obj.put("rating", rating); 
			obj.put("address", address); 
			obj.put("categories", new JSONArray(categories)); 
			obj.put("image_url", imageUrl); 
			obj.put("url", url); 
			obj.put("distance", distance);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public static class ItemBuilder { // must be static, 如果不是static, 必须现先item类，但是
		private String itemId; 		  // 我就是要item类
		private String name; 
		private double rating; 
		private String address; 
		private Set<String> categories; 
		private String imageUrl; 
		private String url; 
		private double distance;
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setRating(double rating) {
			this.rating = rating;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public void setCategories(Set<String> categories) {
			this.categories = categories;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public void setDistance(double distance) {
			this.distance = distance;
		}
		
		public Item build() {
			return new Item(this);
		}
	}
	
	private Item(ItemBuilder builder) { // better to be private
		this.itemId = builder.itemId; 
		this.name = builder.name; 
		this.rating = builder.rating; 
		this.address = builder.address; 
		this.categories = builder.categories; 
		this.imageUrl = builder.imageUrl; 
		this.url = builder.url; 
		this.distance = builder.distance;
	}
	
//  builder pattern的好处，只需要把我需要的参数设定出来，不需要的用系统默认的就好
//      				  避免Item的constructor太长
//	public static void main(String args[]) {
//		ItemBuilder builder = new ItemBuilder();
//		builder.setName("music festival");
//		builder.setDistance(20);
//		builder.setRating(4.5);
//		Item item = builder.build();
//	}
}
