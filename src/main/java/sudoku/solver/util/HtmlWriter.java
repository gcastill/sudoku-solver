package sudoku.solver.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import sudoku.solver.core.Iteration;

import java.io.StringWriter;
import java.time.LocalDateTime;

public class HtmlWriter {
    public static final TemplateEngine ENGINE  = new TemplateEngine();
    static {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.HTML); // HTML5 option was deprecated in 3.0.0
        ENGINE.setTemplateResolver(resolver);
    }

    public static String processHMTLTemplate(String templateName, Iteration iteration, int maxIteration) {


        Context ct = new Context();
        ct.setVariable("iteration",iteration);
        ct.setVariable("maxIteration",maxIteration);
        String html=ENGINE.process(templateName, ct);

        return html;

    }
}