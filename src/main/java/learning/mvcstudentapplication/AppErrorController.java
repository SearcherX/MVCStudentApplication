package learning.mvcstudentapplication;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

// собственный контроллер обработки ошибок
@Controller
public class AppErrorController implements ErrorController {

    // метод обработки запрос с ошибкой
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        int errorCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("message", "Error: " +
                errorCode);

        if (errorCode == 403)
            return "layout/forbidden";

        return "layout/error";
    }
}
