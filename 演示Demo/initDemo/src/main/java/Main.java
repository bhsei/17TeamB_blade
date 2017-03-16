import static com.blade.Blade.$;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.route.RouteBuilder;
import controller.AuthorizationController;
import com.blade.ioc.annotation.Inject;

import com.blade.mvc.annotation.Controller;

import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.view.ModelAndView;


public class Main {

    public static void main(String[] args) {
        $().route("/", AuthorizationController.class, "init",HttpMethod.GET);
        $().route("/postForm", AuthorizationController.class, "trans",HttpMethod.POST);
        $().start(Main.class);
    }
}
