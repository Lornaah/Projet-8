package tourGuide.helper.deserializer;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import gpsUtil.location.Attraction;

@JsonComponent
public class AttractionDeserializer extends JsonDeserializer<Attraction> {

	@Override
	public Attraction deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		TreeNode tree = parser.getCodec().readTree(parser);

		String attractionName = mapper.convertValue(tree.get("attractionName"), String.class);
		String city = mapper.convertValue(tree.get("city"), String.class);
		double latitude = mapper.convertValue(tree.get("latitude"), Double.class);
		double longitude = mapper.convertValue(tree.get("longitude"), Double.class);
		String state = mapper.convertValue(tree.get("state"), String.class);

		Attraction attraction = new Attraction(attractionName, city, state, latitude, longitude);

		return attraction;
	}

}
