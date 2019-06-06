package forster.movieparser;

public class MovieData {
	private int asset_id; //movie id
	private String title;// movie title
    private int production_year; //movie year
    private String [] fsk_level_list_facet; //array to store the fsk level
  
    //getters and setters
	public String [] getFsk_level_list_facet() {
		return fsk_level_list_facet;
	}
	public void setFsk_level_list_facet(String [] fsk_level_list_facet) {
		this.fsk_level_list_facet = fsk_level_list_facet;
	}
	public int getId() {
		return asset_id;
	}
	public void setId(int asset_id) {
		this.asset_id = asset_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return production_year;
	}
	public void setYear(int production_year) {
		this.production_year = production_year;
	}
}
