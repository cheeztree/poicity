package poicity.dto;

import java.time.LocalTime;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poicity.entity.others.DaysWeek;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PoiTimeDTO {

	private LocalTime openingTime;
	private LocalTime closingTime;
	Collection<DaysWeek> days;
	
}
