package org.hmh;

import com.blade.Blade;
import com.blade.ioc.Ioc;
import com.blade.mvc.view.ViewSettings;
import com.blade.mvc.view.template.JetbrickTemplateEngine;

import static com.blade.Blade.$;

/**
 * Created this one by HMH on 2017/5/2.
 */
public class Main {
    public static void main(String[] args) {
        ViewSettings.$().templateEngine(new JetbrickTemplateEngine());
        $().start(Main.class);
    }
}
