package py.com.jsifen.infrastructure.confg;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.File;

@ApplicationScoped
public class Configuracion {

    @Inject
    private SifenConfig cfg;
    private File file;

    @PostConstruct
    void init() {
        this.file = new File(cfg.path());
    }

    public File getFile() {
        return file;
    }

    public String getIdCSC() {
        return cfg.idCsc();
    }

    public String getCSC() {
        return cfg.csc();
    }

    public String getAmbiente() {
        return cfg.ambiente();
    }
}