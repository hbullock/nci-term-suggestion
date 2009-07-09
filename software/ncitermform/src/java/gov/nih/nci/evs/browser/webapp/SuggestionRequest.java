package gov.nih.nci.evs.browser.webapp;

import gov.nih.nci.evs.browser.properties.*;
import gov.nih.nci.evs.browser.utils.*;

import javax.servlet.http.*;

public class SuggestionRequest extends FormRequest {
    // List of attribute name(s):
    public static final String EMAIL = "Email";
    public static final String OTHER = "Other";
    public static final String VOCABULARY = "Vocabulary";
    public static final String TERM = "Term";
    public static final String SYNONYMS = "Synonym(s)";
    public static final String NEAREST_CODE = "Nearest Code/CUI";
    public static final String DEFINITION = "Definition/Other";
    public static final String REASON = "Reason for suggestion plus any" + 
        " other additional information";
    public static final String SOURCE = "Source";
    public static final String CADSR = "caDSR Type";

    // Parameter list(s):
    public static final String[] ALL_PARAMETERS = new String[] { 
        EMAIL, OTHER, VOCABULARY, TERM, SYNONYMS, NEAREST_CODE, 
        DEFINITION, REASON, SOURCE, CADSR };
    public static final String[] MOST_PARAMETERS = new String[] { 
        /* EMAIL, OTHER, VOCABULARY, */ TERM, SYNONYMS, NEAREST_CODE, 
        DEFINITION, REASON, SOURCE, CADSR };
    
    public SuggestionRequest(HttpServletRequest request) {
        super(request, VOCABULARY);
        setParameters(ALL_PARAMETERS);
    }

    public String submitForm() {
        clearAttributes(FormRequest.ALL_PARAMETERS);
        updateAttributes();
        
        String warnings = validate();
        if (warnings.length() > 0) {
            _request.setAttribute(WARNINGS, warnings);
            return WARNING_STATE;
        }

        AppProperties appProperties = AppProperties.getInstance();
        String vocabulary = _parametersHashMap.get(VOCABULARY);
        String mailServer = appProperties.getMailSmtpServer();
        String from = _parametersHashMap.get(EMAIL);
        String[] recipients = appProperties.getVocabularyEmails(vocabulary);
        String subject = getSubject();
        String emailMsg = getEmailMessage();

        try {
            if (_isSendEmail)
                MailUtils.postMail(mailServer, from, recipients, subject, emailMsg);
        } catch (Exception e) {
            _request.setAttribute(WARNINGS,
                    e.getLocalizedMessage());
            e.printStackTrace();
            return WARNING_STATE;
        }

        clearAttributes(MOST_PARAMETERS);
        String msg = "FYI: The following request has been sent:\n";
        msg += "    * " + getSubject();
        _request.setAttribute(MESSAGE, msg);
        printSendEmailWarning();
        return SUCCESSFUL_STATE;
    }
    
    private String validate() {
        StringBuffer buffer = new StringBuffer();
        String email = _parametersHashMap.get(EMAIL);
        validate(buffer, MailUtils.isValidEmailAddress(email), 
            "* Please enter a valid email address.");

        String vocabulary = _parametersHashMap.get(VOCABULARY);
        validate(buffer, vocabulary != null && vocabulary.length() > 0, 
            "* Please select a vocabulary.");

        String term = _parametersHashMap.get(TERM);
        validate(buffer, term != null && term.length() > 0,
            "* Please enter a term.");
        return buffer.toString();
    }

    private String getSubject() {
        String term = _parametersHashMap.get(TERM);
        String value = "Term Suggestion for";
        if (term.length() > 0)
            value += ": " + term;
        return value;
    }
    
    private String getEmailMessage() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getSubject() + "\n\n");
        itemizeParameters(buffer, "Contact information:",
            new String[] { EMAIL, OTHER });
        Prop.Version version = Prop.Version.valueOfOrDefault(
            (String) _request.getSession().getAttribute(VERSION));
        if (version == Prop.Version.CADSR) {
            itemizeParameters(buffer, "Term Information:",
                new String[] { VOCABULARY, TERM, SYNONYMS, NEAREST_CODE, 
                    DEFINITION, SOURCE, CADSR });
        } else {
            itemizeParameters(buffer, "Term Information:",
                new String[] { VOCABULARY, TERM, SYNONYMS, NEAREST_CODE, 
                    DEFINITION });
        }
        itemizeParameters(buffer, "Additional information:",
            new String[] { REASON });
        return buffer.toString();
    }
    
    protected String printSendEmailWarning() {
        if (_isSendEmail)
            return "";
        
        String warning = super.printSendEmailWarning();
        StringBuffer buffer = new StringBuffer(warning);
        buffer.append("Subject: " + getSubject() + "\n");
        buffer.append("Message:\n");
        String emailMsg = getEmailMessage();
        emailMsg = INDENT + emailMsg.replaceAll("\\\n", "\n" + INDENT);
        buffer.append(emailMsg);
        
        _request.setAttribute(WARNINGS, buffer.toString());
        return buffer.toString();
    }
}
