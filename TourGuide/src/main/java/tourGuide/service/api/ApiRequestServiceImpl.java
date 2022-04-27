package tourGuide.service.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gpsUtil.location.Attraction;

@Service
public class ApiRequestServiceImpl implements ApiRequestService {

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<Attraction> getAttractions() {
		ResponseEntity<ArrayList> forEntity = restTemplate.getForEntity("http://localhost:8081/getAttraction",
				ArrayList.class);
		List<Attraction> res = new ArrayList<>(forEntity.getBody());
		return res;
	}

}
