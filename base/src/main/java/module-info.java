module dictionary.base {
    requires freetts;
    requires org.json;
    requires org.apache.commons.text;
    requires org.apache.httpcomponents.httpclient;

    exports dictionary.base.api;
    exports dictionary.base;
}
