package poicity.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import poicity.entity.PoiTime;
import poicity.entity.others.PoiLinks;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PointOfInterestDTO {

    private Long id;
    private String name;
	private String description;
	private double latitude;
	private double longitude;
	private double rating;
	private long id_city;
    private List<Long> id_img;
//    private List<String> img_path;
    private List<PoiLinks> poi_links;
    private List<PoiTime> poi_orari;
    
}
