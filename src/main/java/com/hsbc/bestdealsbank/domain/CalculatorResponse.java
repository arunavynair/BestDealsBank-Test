package com.hsbc.bestdealsbank.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeName("calculatorResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = CalculatorResponse.Builder.class)
public class CalculatorResponse {

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("converted")
    private double converted;
    
    public CalculatorResponse(double amount, double converted) {
        
        this.amount = amount;
        this.converted = converted;
    }
    
    public CalculatorResponse () {}

    public double getAmount() {
        return this.amount;
    }

    public double getConverted() {
        return this.converted;
    }

    public static final class Builder {
        private double amount;
        private double converted;

        @JsonCreator
        public Builder(@JsonProperty("amount") double amount, @JsonProperty("converted") double converted) {
            this.amount = amount;
            this.converted = converted;
        }

        public CalculatorResponse build() {

            return new CalculatorResponse(amount, converted);
        }

    }

}
