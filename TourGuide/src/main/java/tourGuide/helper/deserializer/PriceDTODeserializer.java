package tourGuide.helper.deserializer;

import java.io.IOException;
import java.util.UUID;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import tourGuide.DTO.PriceDTO;

@JsonComponent
public class PriceDTODeserializer extends JsonDeserializer<PriceDTO> {

	@Override
	public PriceDTO deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
		ObjectMapper mapper = new ObjectMapper();
		TreeNode tree = parser.getCodec().readTree(parser);

		String trippricerapikey = mapper.convertValue(tree.get("trippricerapikey"), String.class);
		UUID userId = mapper.convertValue(tree.get("userId"), UUID.class);
		int numberOfAdults = mapper.convertValue(tree.get("numberOfAdults"), Integer.class);
		int numberOfChildren = mapper.convertValue(tree.get("numberOfChildren"), Integer.class);
		int tripDuration = mapper.convertValue(tree.get("tripDuration"), Integer.class);
		int cumulatativeRewardPoints = mapper.convertValue(tree.get("cumulatativeRewardPoints"), Integer.class);

		PriceDTO priceDTO = new PriceDTO(trippricerapikey, userId, numberOfAdults, numberOfChildren, tripDuration,
				cumulatativeRewardPoints);

		return priceDTO;
	}

}
