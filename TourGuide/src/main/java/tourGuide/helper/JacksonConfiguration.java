package tourGuide.helper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.DTO.PriceDTO;
import tourGuide.helper.deserializer.AttractionDeserializer;
import tourGuide.helper.deserializer.LocationDeserializer;
import tourGuide.helper.deserializer.PriceDTODeserializer;
import tourGuide.helper.deserializer.ProviderDeserializer;
import tourGuide.helper.deserializer.VisitedLocationDeserializer;
import tripPricer.Provider;

@Configuration
public class JacksonConfiguration {

	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Attraction.class, new AttractionDeserializer());
		module.addDeserializer(VisitedLocation.class, new VisitedLocationDeserializer());
		module.addDeserializer(Location.class, new LocationDeserializer());
		module.addDeserializer(Provider.class, new ProviderDeserializer());
		module.addDeserializer(PriceDTO.class, new PriceDTODeserializer());
		mapper.registerModule(module);
		return mapper;
	}
}
