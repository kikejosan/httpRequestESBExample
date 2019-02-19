package esper;

import org.mule.api.MuleMessage;
import org.mule.api.annotations.ContainsTransformerMethods;
import org.mule.api.transformer.TransformerException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.mule.transformer.AbstractMessageTransformer;

import com.thoughtworks.xstream.mapper.Mapper;


/**
 * @author Juan Boubeta-Puig <juan.boubeta@uca.es>
 *
 */
@ContainsTransformerMethods
public class JsonTransformerAirQualityEvent extends AbstractMessageTransformer
{
    static DecimalFormat df2 = new DecimalFormat("#,00");
	
	@Override
	public synchronized Map<String, Object> transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		/* Preparamos el payload de la peticion HTTP a Map<String,Object> eventMap */
		Map<String, Object> eventMap = new LinkedHashMap<String, Object>();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map = message.getInboundProperty("http.query.params");
		Set<String> claves = map.keySet();
		Map<String, Object> eventPayload = new LinkedHashMap<String, Object>();
        
		//Sin tipos mandamos un map para luego pasarlo al broker como json, formato facil de manipular
		for (String s : claves) {
			eventPayload.put(s, map.get(s));	
			
		}
		eventMap.put("AirMeasurement", eventPayload);

		System.out.println("===NetworkEvent created: " + eventMap);
		return eventMap;
	}

}