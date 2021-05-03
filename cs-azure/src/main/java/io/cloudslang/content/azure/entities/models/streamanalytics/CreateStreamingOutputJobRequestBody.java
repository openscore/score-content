package io.cloudslang.content.azure.entities.models.streamanalytics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONArray;


public class CreateStreamingOutputJobRequestBody {


    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    Properties properties;





    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Properties {

        Datasource datasource;
        Serialization serialization;

        public Datasource getDatasource() {
            return datasource;
        }

        public void setDatasource(Datasource datasource) {
            this.datasource = datasource;
        }

        public Serialization getSerialization() {
            return serialization;
        }

        public void setSerialization(Serialization serialization) {
            this.serialization = serialization;
        }

    }


    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Serialization {
        public String type;


        public SerializationProperties getProperties() {
            return properties;
        }

        public void setProperties(SerializationProperties properties) {
            this.properties = properties;
        }

        SerializationProperties properties;
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class SerializationProperties {
        public String getFieldDelimiter() {
            return fieldDelimiter;
        }

        public void setFieldDelimiter(String fieldDelimiter) {
            this.fieldDelimiter = fieldDelimiter;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public String fieldDelimiter;
        public String encoding;

    }



    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Datasource {
        public String type;

        public SubProperties getProperties() {
            return properties;
        }

        public void setProperties(SubProperties properties) {
            this.properties = properties;
        }

        //@JsonProperty("properties")
        SubProperties properties;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class SubProperties {
            @JsonProperty("storageAccounts")
            JSONArray strogeaccounts;
            public String container;

            public String getContainer() {
                return container;
            }

            public void setContainer(String container) {
                this.container = container;
            }

            public String getPathPattern() {
                return pathPattern;
            }

            public void setPathPattern(String pathPattern) {
                this.pathPattern = pathPattern;
            }

            public String pathPattern;

            public JSONArray getStrogeaccounts() {
                return strogeaccounts;
            }

            public void setStrogeaccounts(JSONArray strogeaccounts) {
                this.strogeaccounts = strogeaccounts;
            }



            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            public static class StorageAccounts{
                public String accountName;

                public String getAccountName() {
                    return accountName;
                }

                public void setAccountName(String accountName) {
                    this.accountName = accountName;
                }

                public String getAccountKey() {
                    return accountKey;
                }

                public void setAccountKey(String accountKey) {
                    this.accountKey = accountKey;
                }

                public String accountKey;


            }


        }





    }

}
