package co.ninja.qa.pages;


import co.ninja.qa.lib.web.WebBrowser;

public class BasePage {
    protected WebBrowser webBrowser;

    public BasePage(WebBrowser webBrowser)
    {
        this.webBrowser = webBrowser;
    }

    public String getTitle() {
        return webBrowser.getTitle();
    }
}
