package com.selenium.test.webtestsbase;

/**
 */
public enum  Browser {
    FIREFOX("firefox"),
    CHROME("chrome"),
    IE8("ie8"),
    SAFARI("safari");
    private String browserName;

    private Browser(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }

    /**
     */
    public static Browser getByName(String name){
        for(Browser browser : values()) {
            if(browser.getBrowserName().equalsIgnoreCase(name)) {
                return browser;
            }
        }
        return null;
    }


}
