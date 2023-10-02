package poicity.entity;

import java.time.LocalTime;
import java.util.List;
import java.time.DayOfWeek;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "poi_time")
public class PoiTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalTime openingTime;
	private LocalTime closingTime;

    @ElementCollection
    @CollectionTable(name="poi_time_days")
	List<DayOfWeek> days;
	
//	@ElementCollection(targetClass = DaysWeek.class)
//	@JoinTable(name = "poi_time_days")
////	@Column(name = "days", nullable = false)
//	@Enumerated(EnumType.STRING)
//	Collection<DaysWeek> days;
	

}
