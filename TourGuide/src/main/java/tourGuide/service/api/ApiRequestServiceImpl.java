package tourGuide.service.api;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.PriceDTO;
import tripPricer.Provider;

@Service
public class ApiRequestServiceImpl implements ApiRequestService {

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	ObjectMapper mapper;

	@Override
	public List<Attraction> getAttractions() {

		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/getAttraction",
				Object[].class);

		// Get the array of all the objects in the response
		Object[] objects = responseEntity.getBody();

		// Convert object to a list of Attraction
		List<Attraction> attractionList = Arrays.stream(objects).map(o -> mapper.convertValue(o, Attraction.class))
				.collect(Collectors.toList());

		return attractionList;
	}

	@Override
	public VisitedLocation getUserLocation(UUID uuid) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("id", uuid);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

		ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8081/getUserLocation",
				request, Object.class);

		return mapper.convertValue(responseEntity.getBody(), VisitedLocation.class);
	}

	@Override
	public int getAttractionRewardPoints(UUID attractionId, UUID userId) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("attractionId", attractionId);
		map.add("userId", userId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

		ResponseEntity<Object> responseEntity = restTemplate
				.postForEntity("http://localhost:8081/getAttractionRewardPoints", request, Object.class);

		return mapper.convertValue(responseEntity.getBody(), Integer.class);
	}

	@Override
	public List<Provider> getPrice(PriceDTO priceDTO) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("priceDTO", priceDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

		ResponseEntity<Object[]> responseEntity = restTemplate.postForEntity("http://localhost:8081/getPrice", request,
				Object[].class);

		Object[] objects = responseEntity.getBody();

		List<Provider> providerList = Arrays.stream(objects).map(o -> mapper.convertValue(o, Provider.class))
				.collect(Collectors.toList());

		return providerList;
	}

}
