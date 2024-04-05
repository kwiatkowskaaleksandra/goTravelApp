package biuropodrozy.gotravel.rest.dto.response;

import lombok.Data;

import java.util.List;

/**
 * Represents a response from DeepL translation service.
 */
@Data
public class DeepLResponse {

    /**
     * List of translations.
     */
    private List<Translation> translations;

    /**
     * Represents a translation.
     */
    @Data
    public static class Translation {

        /**
         * The detected source language.
         */
        private String detected_source_language;

        /**
         * The translated text.
         */
        private String text;
    }
}
