module com.github.livreprogramacao.example {
    requires java.sql;                                // add required platform modules
    exports com.github.livreprogramacao.example.App;  // packages to expose
    //opens com.example.app.impl to some.framework;   // optional reflection
}
