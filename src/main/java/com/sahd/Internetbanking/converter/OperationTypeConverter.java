package com.sahd.Internetbanking.converter;

import com.sahd.Internetbanking.enums.OperationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OperationTypeConverter implements AttributeConverter<OperationType, String> {
    @Override
    public String convertToDatabaseColumn(OperationType operationType) {
        return operationType.getCode();
    }

    @Override
    public OperationType convertToEntityAttribute(String s) {
        return OperationType.get(s);
    }
}
