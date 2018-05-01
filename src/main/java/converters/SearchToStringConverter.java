package converters;

import domain.Search;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class SearchToStringConverter implements Converter<Search, String> {

    @Override
    public String convert( Search configuration) {
        String result;

        if (configuration == null)
            result = null;
        else
            result = String.valueOf(configuration.getId());

        return result;
    }

}
