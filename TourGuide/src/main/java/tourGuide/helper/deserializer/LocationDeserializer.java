package tourGuide.helper.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import gpsUtil.location.Location;

public class LocationDeserializer extends JsonDeserializer<Location> {

	@Override
	public Location deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		TreeNode tree = parser.getCodec().readTree(parser);
		Double latitude = mapper.convertValue(tree.get("latitude"), Double.class);
		Double longitude = mapper.convertValue(tree.get("longitude"), Double.class);
		Location location = new Location(latitude, longitude);

		return location;
	}

}
