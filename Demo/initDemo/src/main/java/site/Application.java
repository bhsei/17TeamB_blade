package site;

import static com.blade.Blade.$;

import com.blade.mvc.view.ViewSettings;
import com.blade.mvc.view.template.JetbrickTemplateEngine;


public class Application {

    public static void main(String[] args) {
        ViewSettings.$().templateEngine(new JetbrickTemplateEngine());
        $().start(Application.class);
    }
}
