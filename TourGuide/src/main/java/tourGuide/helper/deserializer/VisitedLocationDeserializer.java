package tourGuide.helper.deserializer;

import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;

@JsonComponent
public class VisitedLocationDeserializer extends JsonDeserializer<VisitedLocation> {

	@Override
	public VisitedLocation deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		TreeNode tree = parser.getCodec().readTree(parser);
		UUID userId = mapper.convertValue(tree.get("userId"), UUID.class);

		TreeNode locationNode = tree.get("location");
		Double latitude = mapper.convertValue(locationNode.get("latitude"), Double.class);
		Double longitude = mapper.convertValue(locationNode.get("latitude"), Double.class);
		Location location = new Location(latitude, longitude);

		Date timeVisited = mapper.convertValue(tree.get("timeVisited"), Date.class);

		VisitedLocation visitedLocation = new VisitedLocation(userId, location, timeVisited);
		return visitedLocation;
	}

}
