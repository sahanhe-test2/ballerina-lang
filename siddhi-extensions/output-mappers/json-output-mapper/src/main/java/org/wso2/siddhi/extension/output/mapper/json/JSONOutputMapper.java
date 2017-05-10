/*
 * Copyright (c)  2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.siddhi.extension.output.mapper.json;

import org.apache.log4j.Logger;
import org.wso2.siddhi.annotation.Extension;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.exception.ConnectionUnavailableException;
import org.wso2.siddhi.core.stream.output.sink.SinkListener;
import org.wso2.siddhi.core.stream.output.sink.SinkMapper;
import org.wso2.siddhi.core.util.config.ConfigReader;
import org.wso2.siddhi.core.util.transport.DynamicOptions;
import org.wso2.siddhi.core.util.transport.OptionHolder;
import org.wso2.siddhi.core.util.transport.TemplateBuilder;
import org.wso2.siddhi.query.api.definition.StreamDefinition;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Extension(
        name = "json",
        namespace = "sinkMapper",
        description = "Event to JSON output mapper."
)

// TODO : enable checkstyle, reformat
public class JSONOutputMapper extends SinkMapper {
    private static final Logger log = Logger.getLogger(JSONOutputMapper.class);
    private StreamDefinition streamDefinition;
    private static final String EVENT_PARENT_TAG = "event";
    private static final String ENCLOSING_ELEMENT_IDENTIFIER = "enclosing.element";
    private static final String JSON_VALIDATION_IDENTIFIER = "validate.json";
    private static final String JSON_EVENT_SEPERATOR = ",";
    private static final String JSON_ARRAY_START_SYMBOL = "[";
    private static final String JSON_ARRAY_END_SYMBOL = "]";
    private static final String UNDEFINED = "undefined";

    private String enclosingElement = null;
    private boolean isJsonValidationEnabled = false;


    @Override
    public String[] getSupportedDynamicOptions() {
        return new String[0];
    }

    /**
     * Initialize the mapper and the mapping configurations.
     *
     * @param streamDefinition       The stream definition
     * @param optionHolder           Option holder containing static and dynamic options
     * @param payloadTemplateBuilder Unmapped payload for reference
     */
    @Override
    public void init(StreamDefinition streamDefinition, OptionHolder optionHolder, TemplateBuilder payloadTemplateBuilder, ConfigReader mapperConfigReader) {
        this.streamDefinition = streamDefinition;
        enclosingElement = optionHolder.validateAndGetStaticValue(ENCLOSING_ELEMENT_IDENTIFIER, null);
        isJsonValidationEnabled = Boolean.parseBoolean(optionHolder.validateAndGetStaticValue(JSON_VALIDATION_IDENTIFIER, "false"));
    }

    @Override
    public void mapAndSend(Event[] events, OptionHolder optionHolder, TemplateBuilder payloadTemplateBuilder, SinkListener sinkListener, DynamicOptions dynamicOptions) throws ConnectionUnavailableException {
        StringBuilder sb = new StringBuilder();
        if (payloadTemplateBuilder == null) {
            sb.append(constructJsonForDefaultMapping(events));
        } else {
            sb.append(constructJsonForCustomMapping(events, payloadTemplateBuilder));
        }

        if (isJsonValidationEnabled && isValidJson(sb.toString())) {
            sinkListener.publish(sb.toString(), dynamicOptions);
        } else if (!isJsonValidationEnabled) {
            sinkListener.publish(sb.toString(), dynamicOptions);
        } else {
            log.error("Invalid json string : " + sb.toString());
        }
    }

    @Override
    public void mapAndSend(Event event, OptionHolder optionHolder, TemplateBuilder payloadTemplateBuilder, SinkListener sinkListener, DynamicOptions dynamicOptions) throws ConnectionUnavailableException {
        StringBuilder sb = new StringBuilder();
        if (payloadTemplateBuilder == null) {
            sb.append(constructJsonForDefaultMapping(event));
        } else {
            sb.append(constructJsonForCustomMapping(event, payloadTemplateBuilder));
        }

        if (isJsonValidationEnabled && isValidJson(sb.toString())) {
            sinkListener.publish(sb.toString(), dynamicOptions);
        } else if (!isJsonValidationEnabled) {
            sinkListener.publish(sb.toString(), dynamicOptions);
        } else {
            log.error("Invalid json string : " + sb.toString());
        }
    }

    private String constructJsonForDefaultMapping(Object eventObj) {
        if (eventObj instanceof Event) {
            Event event = (Event) eventObj;
            JsonObject jsonEvent = constructSingleEventForDefaultMapping(doPartialProcessing(event));
            return jsonEvent.toString();
        } else if (eventObj instanceof Event[]) {
            JsonArray eventArray = new JsonArray();
            for (Event event : (Event[]) eventObj) {
                eventArray.add(constructSingleEventForDefaultMapping(doPartialProcessing(event)));
            }
            return (eventArray.toString());
        } else {
            // TODO : log error
        }
        return null;
    }

    private String constructJsonForCustomMapping(Object eventObj, TemplateBuilder payloadTemplateBuilder) {
        if (eventObj instanceof Event) {
            JsonObject jsonObj = new JsonObject();
            Event event = doPartialProcessing((Event) eventObj);
            if (enclosingElement != null) {
                jsonObj.addProperty(enclosingElement, payloadTemplateBuilder.build(event));
                return jsonObj.toString();
            } else {
                return payloadTemplateBuilder.build(event);
            }
        } // TODO : use class name instead of "instance of"
        else if (eventObj instanceof Event[]) {
            StringBuilder sb = new StringBuilder();
            JsonArray jsonArray = new JsonArray();
            sb.append(JSON_ARRAY_START_SYMBOL);
            for (Event event : (Event[]) eventObj) {
                sb.append(payloadTemplateBuilder.build(doPartialProcessing(event))).append(JSON_EVENT_SEPERATOR).append("\n");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append(JSON_ARRAY_END_SYMBOL);
            if (enclosingElement != null) {
                JsonObject jsonObj = new JsonObject();
                jsonObj.addProperty(enclosingElement, jsonArray.toString());
                return jsonObj.toString();
            } else {
                return sb.toString();
            }
        }
        return null;
    }

    private JsonObject constructSingleEventForDefaultMapping(Event event) {
        Object[] data = event.getData();
        JsonObject jsonEventObject = new JsonObject();
        JsonObject innerParentObject = new JsonObject();

        for (int i = 0; i < data.length; i++) {
            // TODO : mote attribute name array to the top
            String attributeName = streamDefinition.getAttributeNameArray()[i];
            Object attributeValue = data[i];
            // TODO : use lambda expressions
            if (attributeValue != null) {
                if (attributeValue instanceof String) {
                    innerParentObject.addProperty(attributeName, attributeValue.toString());
                } else if (attributeValue instanceof Integer) {
                    innerParentObject.addProperty(attributeName, (Number) attributeValue);
                } else if (attributeValue instanceof Long) {
                    innerParentObject.addProperty(attributeName, (Number) attributeValue);
                } else if (attributeValue instanceof Float) {
                    innerParentObject.addProperty(attributeName, (Number) attributeValue);
                } else if (attributeValue instanceof Double) {
                    innerParentObject.addProperty(attributeName, (Number) attributeValue);
                } else if (attributeValue instanceof Boolean) {
                    innerParentObject.addProperty(attributeName, (Boolean) attributeValue);
                } else if (attributeValue instanceof Map) {
                    if (!((Map) attributeValue).isEmpty()) {
                        Gson gson = new Gson();
                        innerParentObject.add(attributeName, gson.toJsonTree(attributeValue));
                    }
                }
            }
        }
        jsonEventObject.add(EVENT_PARENT_TAG, innerParentObject);
        return jsonEventObject;
    }

    private Event doPartialProcessing(Event event) {
        Object[] data = event.getData();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                data[i] = UNDEFINED;
            }
        }
        return event;
    }

    private static boolean isValidJson(String jsonInString) {
        try {
            new Gson().fromJson(jsonInString, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }
}
