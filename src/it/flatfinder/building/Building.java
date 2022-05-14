package it.flatfinder.building;

public class Building {
	
	private String id;
	private String category;
	private int price;
	private String title;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Immobile con id: " + id + ", categoria: " + category + ", titolo: " + title + ", prezzo: " + price;
	}
	

}
