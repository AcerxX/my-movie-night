package ro.projects.polls.config;

import org.jtwig.spring.JtwigView;
import org.jtwig.spring.JtwigViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Map;

@Service
public class JtwigRenderService {
    private final JtwigViewResolver jtwigViewResolver;

    @Autowired
    public JtwigRenderService(JtwigViewResolver jtwigViewResolver) {
        this.jtwigViewResolver = jtwigViewResolver;
    }

    public String renderTemplate(String tplName, Map<String, Object> tplParameters) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JtwigView view = null;
        try {
            view = (JtwigView) jtwigViewResolver.resolveViewName(tplName, Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert view != null;
        jtwigViewResolver.getRenderer()
                .dispatcherFor(view.getUrl())
                .with(tplParameters)
                .render(outputStream);

        return outputStream.toString();
    }
}
