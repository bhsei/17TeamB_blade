package org.hmh;
import com.blade.Blade;
import com.blade.ioc.Ioc;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.ar.SampleActiveRecord;
import com.blade.jdbc.ds.DataSourceFactory;
import com.blade.mvc.view.ViewSettings;
import com.blade.mvc.view.template.JetbrickTemplateEngine;

import javax.sql.DataSource;

import static com.blade.Blade.$;

public class Application {

    public static void main(String[] args) {
        ViewSettings.$().templateEngine(new JetbrickTemplateEngine());
        init();
        $().get("/", (request, response) ->response.go("/login"));
        $().start(Application.class);
    }

    public static void init() {
        Ioc ioc = Blade.$().ioc();
        ActiveRecord activeRecord = new SampleActiveRecord(DefaultPool());
        ioc.addBean(activeRecord);
    }

    protected static DataSource DefaultPool() {
        try {
            return DataSourceFactory.createDataSource("jdbc.properties");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
