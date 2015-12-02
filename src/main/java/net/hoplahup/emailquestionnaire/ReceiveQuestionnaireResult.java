package net.hoplahup.emailquestionnaire;

import org.jdom2.JDOMException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

public class ReceiveQuestionnaireResult extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.webApp = (QuestionnaireWebApp) config.getServletContext().getAttribute(QuestionnaireWebApp.class.getName());
    }

    private QuestionnaireWebApp webApp = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // basic logging
            String pathInfo = req.getPathInfo();
            String remoteAddr = req.getRemoteAddr();
            if(req.getHeader("X-Forwarded-From")!=null) remoteAddr = req.getHeader("X-Forwarded-From");
            System.out.println("Receiving response at " + pathInfo + " from IP " + remoteAddr + " ("+new Date()+").");

            // identify the questionnaire by name
            pathInfo = pathInfo.substring(1);
            QuestionnaireDir quest = webApp.getQuestionnaireDir(pathInfo);
            quest.parseFields();

            // read and update the input-names
            quest.updateFieldNames(req.getParameterNames());
            quest.writeFields();

            // write data
            quest.receiveData(req.getParameterMap(), req.getSession(true).getId(), req.getHeader("User-Agent"));


            // dispatch to view
            webApp.getServletContext().getRequestDispatcher(quest.getThankYouPath()).forward(req, resp);
        } catch (JDOMException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
