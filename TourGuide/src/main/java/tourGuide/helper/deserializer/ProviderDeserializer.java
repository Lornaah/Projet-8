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

import tripPricer.Provider;

@JsonComponent
public class ProviderDeserializer extends JsonDeserializer<Provider> {

	@Override
	public Provider deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {

		ObjectMapper mapper = new ObjectMapper();
		TreeNode tree = parser.getCodec().readTree(parser);
		UUID tripId = mapper.convertValue(tree.get("tripId"), UUID.class);
		String name = mapper.convertValue(tree.get("name"), String.class);
		double price = mapper.convertValue(tree.get("price"), Double.class);

		Provider provider = new Provider(tripId, name, price);

		return provider;
	}

}
