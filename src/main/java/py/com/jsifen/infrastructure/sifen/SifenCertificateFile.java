package py.com.jsifen.infrastructure.sifen;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.annotation.PostConstruct;

import java.io.File;

@ApplicationScoped
public class SifenCertificateFile {

    /*
    @Inject
    SifenConfig sifenConfig;
*/

    private File p12File;

    @PostConstruct
    void init() {
        // La ruta proviene de sifen.path en sifen.properties

        //p12File = new File( sifenConfig.keystorePath() );
        p12File = new File( "" );

    }

    public File get() {
        return p12File;
    }
}
