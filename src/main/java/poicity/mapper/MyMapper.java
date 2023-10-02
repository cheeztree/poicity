package poicity.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import poicity.dto.LanguageTextDTO;
import poicity.dto.PointOfInterestDTO;
import poicity.dto.UserDTO;
import poicity.dto.UserTagsDTO;
import poicity.entity.LanguageText;
import poicity.entity.PointOfInterest;
import poicity.entity.PointOfInterestImage;
import poicity.entity.User;
import poicity.entity.UserTags;

public class MyMapper extends ModelMapper{
   
	public UserDTO userToUserDTO(User user) {
		
		UserDTO userDTO = map(user, UserDTO.class);
		userDTO.setLang_id(user.getLang().getId());
		
		return userDTO;
		
	}
	
	public LanguageTextDTO langTextToLangTextDTO(LanguageText langText) {
		LanguageTextDTO langTextDTO = map(langText, LanguageTextDTO.class);
		return langTextDTO;
	}
	
	public HashMap<Long, UserTags> listUserTagsDTOToUserTags(List<UserTagsDTO> listDTO){
		HashMap<Long, UserTags> map = new HashMap<>();
		
		for(UserTagsDTO u : listDTO) {
			map.put(u.getId(), map(u, UserTags.class));
		}
		
		return map;
	}
	
	public Set<UserTags> listUserTagsDTOToUserTags2(List<UserTagsDTO> listDTO){
		Set<UserTags> map = new LinkedHashSet<>();
		
		for(UserTagsDTO u : listDTO) {
			map.add(map(u, UserTags.class));
		}

		return map;
	}
	
	public PointOfInterestDTO poiToPoiDTO(PointOfInterest poi) {
		
		PointOfInterestDTO poiDTO = new PointOfInterestDTO();
		
		poiDTO.setId(poi.getId());
		poiDTO.setName(poi.getName());
		poiDTO.setDescription(poi.getDescription());
		poiDTO.setLatitude(poi.getLatitude());
		poiDTO.setLongitude(poi.getLongitude());
		poiDTO.setRating(poi.getRating());
		poiDTO.setId_city(poi.getCity().getId());
		
		List<Long> list_id_img = new ArrayList<>();
		for(PointOfInterestImage poiImg : poi.getPoi()) {
			list_id_img.add(poiImg.getId());
		}
		poiDTO.setId_img(list_id_img);
		
//		List<String> list_img = new ArrayList<>();
//		for(PointOfInterestImage poiImg : poi.getPoi()) {
//			list_img.add(poiImg.getPathImgPoi());
//		}
//		poiDTO.setImg_path(list_img);
		
		poiDTO.setPoi_links(poi.getLinks());
		poiDTO.setPoi_orari(poi.getPoiTime());
		
		return poiDTO;
	}
	
	public List<UserDTO> listUserToUserDTO(List<User> list){
		List<UserDTO> listDTO = new ArrayList<>();
		
		for(User user : list) {
			UserDTO userDTO = map(user, UserDTO.class);
			userDTO.setLang_id(user.getLang().getId());
			
			listDTO.add(userDTO);
		}
		
		return listDTO;
	}
}
