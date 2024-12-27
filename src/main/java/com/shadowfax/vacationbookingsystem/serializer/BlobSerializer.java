package com.shadowfax.vacationbookingsystem.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Base64;

public class BlobSerializer extends JsonSerializer<byte[]> {
    @Override
    public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Convert the byte array to a base64 encoded string
        String base64String = Base64.getEncoder().encodeToString(value);
        gen.writeString(base64String);  // Write as a string in the JSON response
    }
}
