package net.hoplahup.emailquestionnaire;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionnaireWebApp implements ServletContextListener {

    private ServletContext servletContext = null;

    private File pathToQuestionnaires = null;

    private File trashDir = null;

    private Logger log = Logger
            .getLogger(QuestionnaireWebApp.class.getName());

    private File getPathToQuestionnaire(String questionnaireName) {
        if (questionnaireName.contains(File.separator))
            throw new IllegalStateException("Can't have dir separators here!");
        return new File(pathToQuestionnaires, questionnaireName);
    }

    public QuestionnaireDir getQuestionnaireDir(String name) {
        return new QuestionnaireDir(getPathToQuestionnaire(name));
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void contextInitialized(ServletContextEvent arg0) {
        this.servletContext = arg0.getServletContext();
        try {
            System.err.println("Initializing....");
            servletContext.setAttribute(this.getClass().getName(), this);
            pathToQuestionnaires = new File(servletContext.getInitParameter("pathToQuestionnaires"));
            trashDir = new File(servletContext.getInitParameter("trashDir"));
            System.err.println("Initialized....");
        } catch (Exception e) {
            log.log(Level.SEVERE,"Couldn't initialize", e);
        }
        log.info("Webapp created");
    }

    public File getTrashFile(File input) {
        input = input.getAbsoluteFile();
        String questionnaireName = input.getParentFile().getName();
        return new File(trashDir, questionnaireName + "_" + input.getName());
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            servletContext.removeAttribute(this.getClass().getName());
            // shutdown code
        } catch (Exception e) {
            log.log(Level.SEVERE, "Couldn't destroy", e);
        }
        this.servletContext = null;
        log.info("Webapp destroyed");
    }

}
